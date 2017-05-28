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

import java.io.*;
import java.util.concurrent.atomic.*;
import java.util.prefs.*;

/**
 * Singleton class that isolates some user preferences,
 * using the java predefined class {@link Preferences}.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
@Singleton
public class PhotoCryptPreferences {

    private static final String MEDIA_ITEM_SEQUENCE = "media_item_sequence";
    private static final String ALBUM_SEQUENCE = "album_sequence";
    private static final String PERSON_SEQUENCE = "person_sequence";

    /**
     * The path where the application files will be stored
     */
    private static final String PHOTOCRYPT_FOLDER = "photocrypt_folder";

    @Inject
    private MediaLibrary mediaLibrary;
    @Inject
    private AlbumsLibrary albumsLibrary;
    @Inject
    private PersonsLibrary personsLibrary;
    private AtomicInteger mediaItemSequence;
    private AtomicInteger albumSequence;
    private AtomicInteger personSequence;
    private Preferences preferences;

    public PhotoCryptPreferences() {
        preferences = Preferences.userNodeForPackage(getClass());
        mediaItemSequence = new AtomicInteger(preferences.getInt(MEDIA_ITEM_SEQUENCE, 0));
        albumSequence = new AtomicInteger(preferences.getInt(ALBUM_SEQUENCE, 0));
        personSequence = new AtomicInteger(preferences.getInt(PERSON_SEQUENCE, 0));
    }

    /**
     * Returns 0 if the application is used in the first time, that is,
     * if there is no record for the media item sequence in the class
     * {@link Preferences}, or the next integer to use.
     *
     * @return The next integer to use
     */
    public int getMediaItemSequence() {
        while (mediaLibrary.getMediaItem(mediaItemSequence.getAndIncrement()).isPresent()) ;
        preferences.putInt(MEDIA_ITEM_SEQUENCE, mediaItemSequence.get());
        return mediaItemSequence.get();
    }

    public void resetMediaItemSequence() {
        mediaItemSequence.set(0);
        preferences.putInt(MEDIA_ITEM_SEQUENCE, 0);
    }

    /**
     * Returns 0 if the application is used in the first time, that is,
     * if there is no record for the album sequence in the class
     * {@link Preferences}, or the next integer to use.
     *
     * @return The next integer to use
     */
    public int getAlbumSequence() {
        while (albumsLibrary.getAlbum(albumSequence.getAndIncrement()).isPresent()) ;
        preferences.putInt(ALBUM_SEQUENCE, albumSequence.get());
        return albumSequence.get();
    }

    public void resetAlbumSequence() {
        albumSequence.set(0);
        preferences.putInt(ALBUM_SEQUENCE, 0);
    }

    /**
     * Returns 0 if the application is used in the first time, that is,
     * if there is no record for the person sequence in the class
     * {@link Preferences}, or the next integer to use.
     *
     * @return The next integer to use
     */
    public int getPersonSequence() {
        while (personsLibrary.getPerson(personSequence.getAndIncrement()).isPresent()) ;
        preferences.putInt(PERSON_SEQUENCE, albumSequence.get());
        return personSequence.get();
    }

    public void resetPersonSequence() {
        personSequence.set(0);
        preferences.putInt(PERSON_SEQUENCE, 0);
    }

    /**
     * Sets the application folder path and resets the media item sequences
     *
     * @param path The path to the application folder
     *
     * @return <tt>true</tt> if the creation of the directory was successful, <tt>false</tt> otherwise
     */
    public void setPhotoCryptUserFolder(String path) throws IOException {
        Files.createParentDirs(new File(path, "test"));
        preferences.put(PHOTOCRYPT_FOLDER, path);
    }

    public String getPhotocryptFolderUserFolder() {
        return preferences.get(PHOTOCRYPT_FOLDER, null);
    }
}