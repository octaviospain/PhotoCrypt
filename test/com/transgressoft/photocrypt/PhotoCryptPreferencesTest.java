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

import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class PhotoCryptPreferencesTest {

    @Test
    @DisplayName ("User folder")
    void userFolderMethodTest() {
        PhotoCryptPreferences photoCryptPreferences = PhotoCryptPreferences.getInstance();
        String sep = File.separator;
        String userHome = System.getProperty("user.home");
        String newUserFolder = userHome + sep + "PhotoCrypt";
        File newUserFolderFile = new File(newUserFolder);
        photoCryptPreferences.setPhotoCryptUserFolder(newUserFolder);

        assertAll(() -> assertEquals(newUserFolder, photoCryptPreferences.getPhotocryptFolderUserFolder()),
                  () -> assertEquals(1, photoCryptPreferences.getMediaItemSequence()),
                  () -> assertEquals(1, photoCryptPreferences.getAlbumSequence()),
                  () -> assertEquals(1, photoCryptPreferences.getPersonSequence()),
                  () -> assertTrue(newUserFolderFile.exists()), () -> assertTrue(newUserFolderFile.delete()));
    }
}