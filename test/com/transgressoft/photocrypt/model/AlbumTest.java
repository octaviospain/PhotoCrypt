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
 * Copyright (C) 2016 Octavio Calleya
 */

package com.transgressoft.photocrypt.model;

import com.transgressoft.photocrypt.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class AlbumTest {

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
    @DisplayName("Encrypt All")
    void encryptAllTest() {
        Album album = new Album("Trip to Hawaii");
        UnsupportedOperationException exception = expectThrows(UnsupportedOperationException.class,
                                                 () -> album.encryptAll());
        assertEquals("Unsupported yet", exception.getMessage());
    }

    @Test
    @DisplayName ("Constructor")
    void constructorTest() {
        Album album = new Album("Trip to Hawaii");
        assertEquals(1, album.getId());
        assertEquals("Trip to Hawaii", album.getName());
    }

    @Test
    @DisplayName ("Name")
    void nameTest() {
        Album album = new Album("Trip to Hawaii");
        album.setName("Trip to the Pacific");
        assertEquals("Trip to the Pacific", album.getName());
    }

    @Test
    @DisplayName ("Most recent photo")
    void mostRecentPhotoTest() {
        Album album = new Album("Trip to Hawaii");
        assertEquals(Optional.empty(), album.getMostRecentPhoto());
    }

    @Test
    @DisplayName ("Older photo")
    void olderPhotoTest() {
        Album album = new Album("Trip to Hawaii");
        assertEquals(Optional.empty(), album.getOlderPhoto());
    }

    @Test
    @DisplayName ("Location")
    void locationTest() {
        Album album = new Album("Trip to Hawaii");
        album.setLocation("Hawaii");
        assertTrue(album.getLocation().isPresent());
        assertEquals("Hawaii", album.getLocation().get());
    }

    @Test
    @DisplayName ("Photos")
    void photosTest() {
        Photo apachePhoto = new Photo(apachePhotoPath);
        Photo gnuPhoto = new Photo(gnuPhotoPath);
        List<Photo> photos = new ArrayList<>();
        photos.add(apachePhoto);

        Album album = new Album("Trip to Hawaii");
        album.addPhotos(photos);

        assertEquals(photos, album.getPhotos());

        photos.add(gnuPhoto);
        album.addPhotos(Collections.singleton(gnuPhoto));

        assertEquals(photos, album.getPhotos());
    }

    @Test
    @DisplayName ("toString")
    void toStringTest() {
        Album album = new Album("Trip to Hawaii");
        String expectedString = "[1] Trip to Hawaii (0)";
        assertEquals(expectedString, album.toString());
    }

    @Test
    @DisplayName("hashCode")
    void hashCodeTest() {
        Album album = new Album("Trip to Hawaii");
        Photo apachePhoto = new Photo(apachePhotoPath);
        Photo gnuPhoto = new Photo(gnuPhotoPath);
        List<Photo> photos = new ArrayList<>();
        photos.add(apachePhoto);
        photos.add(gnuPhoto);
        album.addPhotos(photos);
        int hash = 73;
        hash = 73 * hash + "Trip to Hawaii".hashCode();
        hash = 73 * hash + Optional.empty().hashCode();
        hash = 73 * hash + photos.hashCode();

        assertEquals(hash, album.hashCode());
    }

    @Test
    @DisplayName("Equals")
    void equalsTest() {
        Album album = new Album("Trip to Hawaii");
        Photo apachePhoto = new Photo(apachePhotoPath);
        album.addPhotos(Collections.singletonList(apachePhoto));

        Album album2 = new Album("Trip to Hawaii");
        Photo gnuPhoto = new Photo(gnuPhotoPath);
        album2.addPhotos(Collections.singletonList(gnuPhoto));

        assertFalse(album.equals(album2));

        album2.getPhotos().remove(gnuPhoto);
        album2.getPhotos().add(apachePhoto);

        assertTrue(album.equals(album2));
    }

    @Test
    @DisplayName("Not Equals with other class")
    void notEqualsTest() {
        Album album = new Album("Trip to Hawaii");
        assertFalse(album.equals(apachePhotoPath));
    }

    @Test
    @DisplayName("Not Equals with different location album")
    void notEqualsDifferentLocationTest() {
        Album album = new Album("Trip to Hawaii");
        Album album2 = new Album("Trip to Hawaii");
        album2.setLocation("Hawaii");
        assertFalse(album.equals(album2));
    }

    @Test
    @DisplayName("Not Equals with different name album")
    void notEqualsDifferentNameTest() {
        Album album = new Album("Trip to Hawaii");
        Album album2 = new Album("Trip to Maldives");
        assertFalse(album.equals(album2));
    }
}