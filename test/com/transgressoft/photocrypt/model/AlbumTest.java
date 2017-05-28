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

import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@ExtendWith (MockitoExtension.class)
public class AlbumTest {

    @Mock
    PhotoCryptPreferences preferencesMock;
    AlbumFactory albumFactory;
    Injector injector;
    Path gnuPhotoPath = Paths.get("test-resources", "photo_gnu.png");
    Path apachePhotoPath = Paths.get("test-resources", "photo_apache.jpg");

    @BeforeEach
    void beforeEach() {
        injector = Guice.createInjector(binder -> binder.bind(PhotoCryptPreferences.class).toInstance(preferencesMock));
        injector = injector.createChildInjector(new PhotoCryptModule());
        when(preferencesMock.getAlbumSequence()).thenReturn(0);
        albumFactory = injector.getInstance(AlbumFactory.class);
    }
    
    @Test
    @DisplayName("Encrypt All")
    void encryptAllTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, album::encryptAll);
        assertEquals("Unsupported yet", exception.getMessage());
    }

    @Test
    @DisplayName ("Constructor")
    void constructorTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        assertEquals(0, album.getId());
        assertEquals("Trip to Hawaii", album.getName());
    }

    @Test
    @DisplayName ("Name")
    void nameTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        album.setName("Trip to the Pacific");
        assertEquals("Trip to the Pacific", album.getName());
    }

    @Test
    @DisplayName ("Most recent photo")
    void mostRecentPhotoTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        assertEquals(Optional.empty(), album.getMostRecentPhoto());
    }

    @Test
    @DisplayName ("Older photo")
    void olderPhotoTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        assertEquals(Optional.empty(), album.getOlderPhoto());
    }

    @Test
    @DisplayName ("Location")
    void locationTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        album.setLocation("Hawaii");
        assertTrue(album.getLocation().isPresent());
        assertEquals("Hawaii", album.getLocation().get());
    }

    @Test
    @DisplayName ("Photos")
    void photosTest() {
        Photo apachePhoto = new Photo(0, apachePhotoPath);
        Photo gnuPhoto = new Photo(1 ,gnuPhotoPath);
        List<Photo> photos = new ArrayList<>();
        photos.add(apachePhoto);

        Album album = albumFactory.create("Trip to Hawaii");
        album.addPhotos(photos);

        assertEquals(photos, album.getPhotos());

        photos.add(gnuPhoto);
        album.addPhotos(Collections.singleton(gnuPhoto));

        assertEquals(photos, album.getPhotos());
    }

    @Test
    @DisplayName ("toString")
    void toStringTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        String expectedString = "[0] Trip to Hawaii (0)";
        assertEquals(expectedString, album.toString());
    }

    @Test
    @DisplayName("hashCode")
    void hashCodeTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        Photo apachePhoto = new Photo(0, apachePhotoPath);
        Photo gnuPhoto = new Photo(1, gnuPhotoPath);
        List<Photo> photos = new ArrayList<>();
        photos.add(apachePhoto);
        photos.add(gnuPhoto);
        album.addPhotos(photos);
        int hash = Objects.hash("Trip to Hawaii", Optional.empty(), photos);

        assertEquals(hash, album.hashCode());
    }

    @Test
    @DisplayName("Equals")
    void equalsTest() {
        Album album = new Album(1, "Trip to Hawaii");
        Photo apachePhoto = new Photo(1, apachePhotoPath);
        album.addPhotos(Collections.singletonList(apachePhoto));

        Album album2 = new Album(1, "Trip to Hawaii");
        Photo gnuPhoto = new Photo(1, gnuPhotoPath);
        album2.addPhotos(Collections.singletonList(gnuPhoto));

        assertFalse(album.equals(album2));

        album2.getPhotos().remove(gnuPhoto);
        album2.getPhotos().add(apachePhoto);

        assertTrue(album.equals(album2));
    }

    @Test
    @DisplayName("Not Equals with other class")
    void notEqualsTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        assertFalse(album.equals(apachePhotoPath));
    }

    @Test
    @DisplayName("Not Equals with different location album")
    void notEqualsDifferentLocationTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        Album album2 = new Album(1, "Trip to Hawaii");
        album2.setLocation("Hawaii");
        assertFalse(album.equals(album2));
    }

    @Test
    @DisplayName("Not Equals with different name album")
    void notEqualsDifferentNameTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        Album album2 = new Album(1, "Trip to Maldives");
        assertFalse(album.equals(album2));
    }
}