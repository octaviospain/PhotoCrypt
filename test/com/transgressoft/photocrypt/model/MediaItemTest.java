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

import com.google.inject.*;
import com.transgressoft.photocrypt.*;
import com.transgressoft.photocrypt.tests.*;
import com.transgressoft.photocrypt.util.guice.factories.*;
import com.transgressoft.photocrypt.util.guice.modules.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@ExtendWith (MockitoExtension.class)
public class MediaItemTest {
    
    @Mock
    PhotoCryptPreferences preferencesMock;
    MediaItemFactory mediaItemFactory;
    Injector injector;
    Path gnuPhotoPath = Paths.get("test-resources", "photo_gnu.png");
    Path apachePhotoPath = Paths.get("test-resources", "photo_apache.jpg");

    @BeforeEach
    void beforeEach() {
        injector = Guice.createInjector(binder -> binder.bind(PhotoCryptPreferences.class).toInstance(preferencesMock));
        injector = injector.createChildInjector(new PhotoCryptModule());
        when(preferencesMock.getMediaItemSequence()).thenReturn(0);
        mediaItemFactory = injector.getInstance(MediaItemFactory.class);
    }
    
    @Test
    @DisplayName ("Constructor")
    void constructorTest() {
        Photo photo = mediaItemFactory.create(gnuPhotoPath);
        assertAll(() -> assertEquals(gnuPhotoPath.getFileName().toString(), photo.getFileName()),
                  () -> assertEquals(gnuPhotoPath.toFile().getParent(), photo.getFileFolder()),
                  () -> assertEquals(0, photo.getId()), () -> assertEquals(Optional.empty(), photo.getDate()),
                  () -> assertTrue(photo.getTitle().isEmpty()), () -> assertTrue(photo.getDescription().isEmpty()),
                  () -> assertTrue(photo.getLocation().isEmpty()), () -> assertFalse(photo.isEncrypted()));
    }

    @Test
    @DisplayName ("File path")
    void filePathTest() {
        Photo photo = mediaItemFactory.create(gnuPhotoPath);
        photo.setFileFolder(gnuPhotoPath);
        assertEquals(gnuPhotoPath.toFile().getParent(), photo.getFileFolder());
        assertEquals(gnuPhotoPath.getFileName().toString(), photo.getFileName());
    }

    @Test
    @DisplayName ("Title")
    void titleTest() {
        Photo photo = mediaItemFactory.create(apachePhotoPath);
        photo.setTitle("Apaches everywhere!");
        assertEquals("Apaches everywhere!", photo.getTitle());
    }

    @Test
    @DisplayName ("Description")
    void descriptionTest() {
        Photo photo = mediaItemFactory.create(apachePhotoPath);
        photo.setDescription("Are you apache?");
        assertEquals("Are you apache?", photo.getDescription());
    }

    @Test
    @DisplayName ("Location")
    void locationTest() {
        Photo photo = mediaItemFactory.create(apachePhotoPath);
        photo.setLocation("Far west");
        assertEquals("Far west", photo.getLocation());
    }

    @Test
    @DisplayName ("Datetime")
    void dateTime() {
        Photo photo = mediaItemFactory.create(apachePhotoPath);
        LocalDate testDate = LocalDate.now();
        photo.setDate(testDate);
        assertEquals(testDate, photo.getDate().get());
    }

    @Test
    @DisplayName ("toString")
    void toStringTest() {
        Photo photo = mediaItemFactory.create(apachePhotoPath);
        String expectedString = "[0] " + apachePhotoPath.getFileName().toString();
        assertEquals(expectedString, photo.toString());
    }

    @Test
    @DisplayName ("Hashcode")
    void hashCodeTest() {
        Photo photo = mediaItemFactory.create(gnuPhotoPath);
        int hash = Objects.hash(gnuPhotoPath.toFile().getParent(), gnuPhotoPath.getFileName());
        assertEquals(hash, photo.hashCode());
    }

    @Test
    @DisplayName ("Equals")
    void equalsTest() {
        Photo gnuPhoto = mediaItemFactory.create(gnuPhotoPath);
        Photo gnuPhoto2 = mediaItemFactory.create(gnuPhotoPath);
        Photo apachePhoto = mediaItemFactory.create(apachePhotoPath);
        assertFalse(gnuPhoto.equals(apachePhoto));
        assertTrue(gnuPhoto.equals(gnuPhoto2));
    }

    @Test
    @DisplayName ("Not Equals with other class")
    void notEqualsTest() {
        Photo gnuPhoto = mediaItemFactory.create(gnuPhotoPath);
        Album album = new Album(0, "Album");
        assertFalse(gnuPhoto.equals(album));
    }

    @Test
    @DisplayName ("Not Equals photo in other folder")
    void notEqualsDifferentFolder() throws Exception {
        Photo gnuPhoto = mediaItemFactory.create(gnuPhotoPath);

        File photoPathTemporary =  File.createTempFile("photo_apache.jpg", "");
        photoPathTemporary.deleteOnExit();
        Files.copy(apachePhotoPath, photoPathTemporary.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Photo apachePhoto = new Photo(1, photoPathTemporary.toPath());

        assertFalse(gnuPhoto.equals(apachePhoto));
    }
}