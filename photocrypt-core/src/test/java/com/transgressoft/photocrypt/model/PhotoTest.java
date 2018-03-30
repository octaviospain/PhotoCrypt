/*
 * This file is part of PhotoCrypt software.
 *
 * PhotoCrypt software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PhotoCrypt software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Musicott. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2016, 2017 Octavio Calleya
 */

package com.transgressoft.photocrypt.model;

import com.transgressoft.photocrypt.*;
import com.transgressoft.photocrypt.factories.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@SpringBootTest
@ExtendWith ({SpringExtension.class, MockitoExtension.class})
public class PhotoTest {

    @Autowired
    AtomicInteger photoSequence;
    @Autowired
    SimplePhotoFactory photoFactory;

    Path gnuPhotoPath = Paths.get("src/test/resources", "photo_gnu.png");
    Path apachePhotoPath = Paths.get("src/test/resources", "photo_apache.jpg");

    @BeforeEach
    void beforeEach() {
        photoSequence.set(0);
    }

    @Test
    @DisplayName ("Constructor")
    void constructorTest() {
        Photo photo = photoFactory.create(gnuPhotoPath);
        assertAll(() -> assertEquals(gnuPhotoPath.getFileName().toString(), photo.fileName()),
                  () -> assertEquals(gnuPhotoPath.getParent(), photo.fileFolder()),
                  () -> assertEquals(0, photo.id()), () -> assertEquals(Optional.empty(), photo.date()),
                  () -> assertTrue(photo.title().isEmpty()), () -> assertTrue(photo.description().isEmpty()),
                  () -> assertTrue(photo.location().isEmpty()), () -> assertFalse(photo.isEncrypted()));
    }

    @Test
    @DisplayName ("Title")
    void titleTest() {
        Photo photo = photoFactory.create(apachePhotoPath, "Title", "", "", null);
        photo = photo.title("Apaches everywhere!");
        assertEquals("Apaches everywhere!", photo.title());
    }

    @Test
    @DisplayName ("Description")
    void descriptionTest() {
        Photo photo = photoFactory.create(apachePhotoPath, "", "Description", "", null);
        photo = photo.description("Are you apache?");
        assertEquals("Are you apache?", photo.description());
    }

    @Test
    @DisplayName ("Location")
    void locationTest() {
        Photo photo = photoFactory.create(apachePhotoPath, "", "", "USA", null);
        photo = photo.location("Far west");
        assertEquals("Far west", photo.location());
    }

    @Test
    @DisplayName ("Datetime")
    void dateTime() {
        Photo photo = photoFactory.create(apachePhotoPath, "", "", "", null);
        LocalDate testDate = LocalDate.now();
        photo = photo.date(testDate);
        assertEquals(testDate, photo.date().get());
    }

    @Test
    @DisplayName ("toString")
    void toStringTest() {
        Photo photo = photoFactory.create(apachePhotoPath);
        String expectedString = "[0] " + apachePhotoPath.getFileName().toString();
        assertEquals(expectedString, photo.toString());
    }

    @Test
    @DisplayName ("Hashcode")
    void hashCodeTest() {
        Photo photo = photoFactory.create(gnuPhotoPath);
        int hash = Objects.hash(gnuPhotoPath.toFile().getParent(), gnuPhotoPath.getFileName());
        assertEquals(hash, photo.hashCode());
    }

    @Test
    @DisplayName ("Equals")
    void equalsTest() {
        Photo gnuPhoto = photoFactory.create(gnuPhotoPath);
        Photo gnuPhoto2 = photoFactory.create(gnuPhotoPath);
        Photo apachePhoto = photoFactory.create(apachePhotoPath);
        assertFalse(gnuPhoto.equals(apachePhoto));
        assertTrue(gnuPhoto.equals(gnuPhoto2));
    }

    @Test
    @DisplayName ("Not Equals with other class")
    void notEqualsTest() {
        Photo gnuPhoto = photoFactory.create(gnuPhotoPath);
        SimpleAlbum album = new SimpleAlbum(0, "SimpleAlbum", "");
        assertFalse(gnuPhoto.equals(album));
    }

    @Test
    @DisplayName ("Not Equals photo in other folder")
    void notEqualsDifferentFolder() throws Exception {
        Photo gnuPhoto = photoFactory.create(gnuPhotoPath);

        File photoPathTemporary =  File.createTempFile("photo_apache.jpg", "");
        photoPathTemporary.deleteOnExit();
        Files.copy(apachePhotoPath, photoPathTemporary.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Photo apachePhoto = photoFactory.create(photoPathTemporary.toPath());

        assertFalse(gnuPhoto.equals(apachePhoto));
    }
}