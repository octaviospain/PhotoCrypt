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

import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@SpringBootTest
@ExtendWith ({SpringExtension.class, MockitoExtension.class})
public class AlbumTest {

    @Autowired
    AtomicInteger albumSequence;
    @Autowired
    SimpleAlbumFactory albumFactory;
    @Autowired
    SimplePhotoFactory photoFactory;

    Path gnuPhotoPath = Paths.get("test-resources", "photo_gnu.png");
    Path apachePhotoPath = Paths.get("test-resources", "photo_apache.jpg");

    @BeforeEach
    void beforeEach() {
        albumSequence.set(0);
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
        assertEquals(0, album.id());
        assertEquals("Trip to Hawaii", album.name());
    }

    @Test
    @DisplayName ("Name")
    void nameTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        assertEquals("Trip to Hawaii", album.name());
    }

    @Test
    @DisplayName ("Most recent photo")
    void mostRecentPhotoTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        assertEquals(Optional.empty(), album.mostRecentPhoto());
    }

    @Test
    @DisplayName ("Older photo")
    void olderPhotoTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        assertEquals(Optional.empty(), album.olderPhoto());
    }

    @Test
    @DisplayName ("Location")
    void locationTest() {
        Album album = albumFactory.create("Trip to Hawaii", "USA");
        assertTrue(album.location().isPresent());
        assertEquals("USA", album.location().get());
    }

    @Test
    @DisplayName ("Photos")
    void photosTest() {
        Photo apachePhoto = photoFactory.create(apachePhotoPath);
        Photo gnuPhoto = photoFactory.create(gnuPhotoPath);
        List<Photo> photos = new ArrayList<>();
        photos.add(apachePhoto);

        Album album = albumFactory.create("Trip to Hawaii");
        album.addPhotos(photos);

        assertEquals(photos, album.photos());

        photos.add(gnuPhoto);
        album.addPhotos(Collections.singleton(gnuPhoto));

        assertEquals(photos, album.photos());
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
        Photo apachePhoto = photoFactory.create(apachePhotoPath);
        Photo gnuPhoto = photoFactory.create(gnuPhotoPath);
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
        Album album = albumFactory.create("Trip to Hawaii");
        Photo apachePhoto = photoFactory.create(apachePhotoPath);
        album.addPhotos(Collections.singletonList(apachePhoto));

        Album album2 = albumFactory.create("Trip to Hawaii");
        Photo gnuPhoto = photoFactory.create(gnuPhotoPath);
        album2.addPhotos(Collections.singletonList(gnuPhoto));

        assertFalse(album.equals(album2));

        album2.photos().remove(gnuPhoto);
        album2.photos().add(apachePhoto);

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
        Album album = albumFactory.create("Trip to Hawaii", "USA");
        Album album2 = albumFactory.create("Trip to Hawaii", "Hawaii");
        assertFalse(album.equals(album2));
    }

    @Test
    @DisplayName("Not Equals with different name album")
    void notEqualsDifferentNameTest() {
        Album album = albumFactory.create("Trip to Hawaii");
        Album album2 = albumFactory.create("Trip to Maldives");
        assertFalse(album.equals(album2));
    }
}