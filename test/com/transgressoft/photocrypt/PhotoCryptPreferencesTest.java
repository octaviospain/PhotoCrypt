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

package com.transgressoft.photocrypt;

import com.google.common.io.*;
import com.google.inject.*;
import com.transgressoft.photocrypt.model.*;
import com.transgressoft.photocrypt.tests.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;

import java.io.*;
import java.util.*;
import java.util.prefs.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@ExtendWith (MockitoExtension.class)
public class PhotoCryptPreferencesTest {

    @Mock
    MediaLibrary mediaLibraryMock;
    @Mock
    AlbumsLibrary albumsLibraryMock;
    @Mock
    PersonsLibrary personsLibraryMock;
    File tempDir = Files.createTempDir();
    Injector injector;

    @BeforeEach
    void beforeEach() {
        injector = Guice.createInjector(binder -> {
            binder.bind(MediaLibrary.class).toInstance(mediaLibraryMock);
            binder.bind(AlbumsLibrary.class).toInstance(albumsLibraryMock);
            binder.bind(PersonsLibrary.class).toInstance(personsLibraryMock);
        });
        cleanPreferences();
    }

    @AfterEach
    void afterEach() {
        if (tempDir.exists())
            assertTrue(tempDir.delete());
        cleanPreferences();
    }

    private void cleanPreferences() {
        Preferences prefs = Preferences.userNodeForPackage(PhotoCryptPreferences.class);
        prefs.remove("media_item_sequence");
        prefs.remove("album_sequence");
        prefs.remove("person_sequence");
    }

    @Test
    @DisplayName ("Singleton instance")
    void singletonInstance() {
        PhotoCryptPreferences photoCryptPreferences = injector.getInstance(PhotoCryptPreferences.class);
        PhotoCryptPreferences another = injector.getInstance(PhotoCryptPreferences.class);

        assertSame(photoCryptPreferences, another);
    }

    @Test
    @DisplayName ("User folder")
    void userFolderMethodTest() throws Exception {
        PhotoCryptPreferences photoCryptPreferences = injector.getInstance(PhotoCryptPreferences.class);
        photoCryptPreferences.setPhotoCryptUserFolder(tempDir.getAbsolutePath());

        assertEquals(tempDir.getAbsolutePath(), photoCryptPreferences.getPhotocryptFolderUserFolder());
        assertAll(() -> assertEquals(tempDir.getAbsolutePath(), photoCryptPreferences.getPhotocryptFolderUserFolder()),
                  () -> assertEquals(1, photoCryptPreferences.getMediaItemSequence()),
                  () -> assertEquals(1, photoCryptPreferences.getAlbumSequence()),
                  () -> assertEquals(1, photoCryptPreferences.getPersonSequence()));
    }

    @Test
    @DisplayName ("MediaItem sequence")
    @SuppressWarnings ("unchecked")
    void mediaItemSequence() {
        when(mediaLibraryMock.getMediaItem(anyInt())).thenReturn(Optional.empty(),
                                                                 Optional.of(mock(MediaItem.class)),
                                                                 Optional.empty(),
                                                                 Optional.of(mock(MediaItem.class)),
                                                                 Optional.empty());
        Preferences prefs = Preferences.userNodeForPackage(PhotoCryptPreferences.class);
        prefs.putInt("media_item_sequence", 12);

        PhotoCryptPreferences photoCryptPreferences = injector.getInstance(PhotoCryptPreferences.class);
        assertEquals(13, photoCryptPreferences.getMediaItemSequence());
        assertEquals(15, photoCryptPreferences.getMediaItemSequence());
        assertEquals(17, photoCryptPreferences.getMediaItemSequence());
        assertEquals(18, photoCryptPreferences.getMediaItemSequence());
        assertEquals(19, photoCryptPreferences.getMediaItemSequence());

        photoCryptPreferences.resetMediaItemSequence();
        assertEquals(1, photoCryptPreferences.getMediaItemSequence());
    }

    @Test
    @DisplayName ("Album sequence")
    @SuppressWarnings ("unchecked")
    void albumSequence() {
        when(albumsLibraryMock.getAlbum(anyInt())).thenReturn(Optional.empty(),
                                                                 Optional.of(mock(Album.class)),
                                                                 Optional.empty(),
                                                                 Optional.of(mock(Album.class)),
                                                                 Optional.empty());
        Preferences prefs = Preferences.userNodeForPackage(PhotoCryptPreferences.class);
        prefs.putInt("album_sequence", 12);

        PhotoCryptPreferences photoCryptPreferences = injector.getInstance(PhotoCryptPreferences.class);
        assertEquals(13, photoCryptPreferences.getAlbumSequence());
        assertEquals(15, photoCryptPreferences.getAlbumSequence());
        assertEquals(17, photoCryptPreferences.getAlbumSequence());
        assertEquals(18, photoCryptPreferences.getAlbumSequence());
        assertEquals(19, photoCryptPreferences.getAlbumSequence());

        photoCryptPreferences.resetAlbumSequence();
        assertEquals(1, photoCryptPreferences.getAlbumSequence());
    }

    @Test
    @DisplayName ("Person sequence")
    @SuppressWarnings ("unchecked")
    void personSequence() {
        when(personsLibraryMock.getPerson(anyInt())).thenReturn(Optional.empty(),
                                                                 Optional.of(mock(Person.class)),
                                                                 Optional.empty(),
                                                                 Optional.of(mock(Person.class)),
                                                                 Optional.empty());
        Preferences prefs = Preferences.userNodeForPackage(PhotoCryptPreferences.class);
        prefs.putInt("person_sequence", 12);

        PhotoCryptPreferences photoCryptPreferences = injector.getInstance(PhotoCryptPreferences.class);
        assertEquals(13, photoCryptPreferences.getPersonSequence());
        assertEquals(15, photoCryptPreferences.getPersonSequence());
        assertEquals(17, photoCryptPreferences.getPersonSequence());
        assertEquals(18, photoCryptPreferences.getPersonSequence());
        assertEquals(19, photoCryptPreferences.getPersonSequence());

        photoCryptPreferences.resetPersonSequence();
        assertEquals(1, photoCryptPreferences.getPersonSequence());
    }
}