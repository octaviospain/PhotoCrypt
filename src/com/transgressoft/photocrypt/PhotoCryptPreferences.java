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

import java.io.*;
import java.util.prefs.*;

/**
 * Singleton class that isolates some user preferences,
 * using the java predefined class {@link Preferences}.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class PhotoCryptPreferences {

    private static final String MEDIA_ITEM_SEQUENCE = "media_item_sequence";
    private static final String ALBUM_SEQUENCE = "album_sequence";
    private static final String PERSON_SEQUENCE = "person_sequence";

    /**
     * The path where the application files will be stored
     */
    private static final String PHOTOCRYPT_FOLDER = "photocrypt_folder";

    private static PhotoCryptPreferences instance;
    private Preferences preferences;

    private PhotoCryptPreferences() {
        preferences = Preferences.userNodeForPackage(getClass());
    }

    public static PhotoCryptPreferences getInstance() {
        if (instance == null) {
            instance = new PhotoCryptPreferences();
        }
        return instance;
    }

    /**
     * Returns 0 if the application is used in the first time, that is,
     * if there is no record for the media item sequence in the class
     * {@link Preferences}, or the next integer to use.
     *
     * @return The next integer to use
     */
    public int getMediaItemSequence() {
        int sequence = preferences.getInt(MEDIA_ITEM_SEQUENCE, 0);
        preferences.putInt(MEDIA_ITEM_SEQUENCE, ++ sequence);
        return sequence;
    }

    /**
     * Returns 0 if the application is used in the first time, that is,
     * if there is no record for the album sequence in the class
     * {@link Preferences}, or the next integer to use.
     *
     * @return The next integer to use
     */
    public int getAlbumSequence() {
        int sequence = preferences.getInt(ALBUM_SEQUENCE, 0);
        preferences.putInt(ALBUM_SEQUENCE, ++ sequence);
        return sequence;
    }

    /**
     * Returns 0 if the application is used in the first time, that is,
     * if there is no record for the person sequence in the class
     * {@link Preferences}, or the next integer to use.
     *
     * @return The next integer to use
     */
    public int getPersonSequence() {
        int sequence = preferences.getInt(PERSON_SEQUENCE, 0);
        preferences.putInt(PERSON_SEQUENCE, ++ sequence);
        return sequence;
    }

    /**
     * Sets the application folder path and resets the media item sequences
     *
     * @param path The path to the application folder
     *
     * @return <tt>true</tt> if the creation of the directory was successful, <tt>false</tt> otherwise
     */
    public boolean setPhotoCryptUserFolder(String path) {
        preferences.put(PHOTOCRYPT_FOLDER, path);
        preferences.putInt(MEDIA_ITEM_SEQUENCE, 0);
        preferences.putInt(ALBUM_SEQUENCE, 0);
        preferences.putInt(PERSON_SEQUENCE, 0);
        return new File(path).mkdirs();
    }

    public String getPhotocryptFolderUserFolder() {
        return preferences.get(PHOTOCRYPT_FOLDER, null);
    }
}