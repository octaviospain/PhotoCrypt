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
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class MediaItemTest {

    String home = System.getProperty("os.home");
    Path gnuPhotoPath = Paths.get("test-resources", "photo_gnu.png");
    Path apachePhotoPath = Paths.get("test-resources", "photo_apache.jpg");

    @BeforeEach
    void beforeEach() {
        PhotoCryptPreferences.getInstance().setPhotoCryptUserFolder(home + "PhotoCrypt" + File.separator);
    }

    @AfterEach
    void afterEach() {
        assertTrue(new File(home + "PhotoCrypt" + File.separator).delete());
    }

    @Test
    @DisplayName ("Constructor")
    void constructorTest() {
        Photo photo = new Photo(gnuPhotoPath);
        assertAll(() -> assertEquals(gnuPhotoPath.getFileName().toString(), photo.getFileName()),
                  () -> assertEquals(gnuPhotoPath.toFile().getParent(), photo.getFileFolder()),
                  () -> assertEquals(1, photo.getId()), () -> assertEquals(Optional.empty(), photo.getDate()),
                  () -> assertTrue(photo.getTitle().isEmpty()), () -> assertTrue(photo.getDescription().isEmpty()),
                  () -> assertTrue(photo.getLocation().isEmpty()), () -> assertFalse(photo.isEncrypted()));
    }

    @Test
    @DisplayName ("File path")
    void filePathTest() {
        Photo photo = new Photo(gnuPhotoPath);
        photo.setFileFolder(gnuPhotoPath);
        assertEquals(gnuPhotoPath.toFile().getParent(), photo.getFileFolder());
        assertEquals(gnuPhotoPath.getFileName().toString(), photo.getFileName());
    }

    @Test
    @DisplayName ("Title")
    void titleTest() {
        Photo photo = new Photo(apachePhotoPath);
        photo.setTitle("Apaches everywhere!");
        assertEquals("Apaches everywhere!", photo.getTitle());
    }

    @Test
    @DisplayName ("Description")
    void descriptionTest() {
        Photo photo = new Photo(apachePhotoPath);
        photo.setDescription("Are you apache?");
        assertEquals("Are you apache?", photo.getDescription());
    }

    @Test
    @DisplayName ("Location")
    void locationTest() {
        Photo photo = new Photo(apachePhotoPath);
        photo.setLocation("Far west");
        assertEquals("Far west", photo.getLocation());
    }

    @Test
    @DisplayName ("Datetime")
    void dateTime() {
        Photo photo = new Photo(apachePhotoPath);
        LocalDate testDate = LocalDate.now();
        photo.setDate(testDate);
        assertEquals(testDate, photo.getDate().get());
    }

    @Test
    @DisplayName ("toString")
    void toStringTest() {
        Photo photo = new Photo(apachePhotoPath);
        String expectedString = "[1] " + apachePhotoPath.getFileName().toString();
        assertEquals(expectedString, photo.toString());
    }

    @Test
    @DisplayName ("Hashcode")
    void hashCodeTest() {
        Photo photo = new Photo(gnuPhotoPath);
        int hash = 73;
        hash = 73 * hash + gnuPhotoPath.toFile().getParent().hashCode();
        hash = 73 * hash + gnuPhotoPath.getFileName().toString().hashCode();
        assertEquals(hash, photo.hashCode());
    }

    @Test
    @DisplayName ("Equals")
    void equalsTest() {
        Photo gnuPhoto = new Photo(gnuPhotoPath);
        Photo gnuPhoto2 = new Photo(gnuPhotoPath);
        Photo apachePhoto = new Photo(apachePhotoPath);
        assertFalse(gnuPhoto.equals(apachePhoto));
        assertTrue(gnuPhoto.equals(gnuPhoto2));
    }

    @Test
    @DisplayName ("Not Equals with other class")
    void notEqualsTest() {
        Photo gnuPhoto = new Photo(gnuPhotoPath);
        Album album = new Album("Album");
        assertFalse(gnuPhoto.equals(album));
    }

    @Test
    @DisplayName ("Not Equals photo in other folder")
    void notEqualsDifferentFolder() throws Exception {
        Photo gnuPhoto = new Photo(gnuPhotoPath);

        File photoPathTemporary =  File.createTempFile("photo_apache.jpg", "");
        photoPathTemporary.deleteOnExit();
        Files.copy(apachePhotoPath, photoPathTemporary.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Photo apachePhoto = new Photo(photoPathTemporary.toPath());

        assertFalse(gnuPhoto.equals(apachePhoto));
    }
}