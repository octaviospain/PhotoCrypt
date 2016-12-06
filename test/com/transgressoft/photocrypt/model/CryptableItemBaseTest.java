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

import com.transgressoft.photocrypt.crypto.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.expectThrows;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class CryptableItemBaseTest {

    static final String ENCRYPTED_EXTENSION = ".enc";
    String gnuPhotoFileName = "photo_gnu.png";
    Path photoPath = Paths.get("test-resources", gnuPhotoFileName);
    File photoPathTemporary;
    File photoPathTemporaryEncrypted;

    @BeforeEach
    void beforeEach() throws Exception {
        photoPathTemporary =  File.createTempFile(gnuPhotoFileName, "");
        photoPathTemporary.deleteOnExit();
        photoPathTemporaryEncrypted = new File(photoPathTemporary.getAbsolutePath() + ENCRYPTED_EXTENSION);
        photoPathTemporaryEncrypted.deleteOnExit();
        Files.copy(photoPath, photoPathTemporary.toPath(), REPLACE_EXISTING);
    }

    @Test
    @DisplayName ("Encrypt")
    void encryptTest() throws Exception {
        Photo gnuPhoto = new Photo(photoPathTemporary.toPath());
        String password = "Symmetric encryption password";

        assertFalse(gnuPhoto.isEncrypted());

        gnuPhoto.encrypt(password);
        File encryptedFile = new File(photoPathTemporary.toString() + ENCRYPTED_EXTENSION);

        assertTrue(encryptedFile.exists());
        assertFalse(photoPathTemporary.exists());
        assertEquals(photoPathTemporary.getName() + ENCRYPTED_EXTENSION, gnuPhoto.getFileName());
    }

    @Test
    @DisplayName ("Encrypt an already encrypted item")
    void invalidEncryption() throws Exception {
        Photo gnuPhoto = new Photo(photoPathTemporary.toPath());
        String password = "Symmetric encryption password";

        assertFalse(gnuPhoto.isEncrypted());

        gnuPhoto.encrypt(password);
        File encryptedFile = new File(photoPathTemporary.toString() + ENCRYPTED_EXTENSION);

        assertTrue(encryptedFile.exists());

        CryptoException exception = expectThrows(CryptoException.class,
                                                 () -> gnuPhoto.encrypt(password));
        assertEquals("Media item already encrypted", exception.getMessage());
    }

    @Test
    @DisplayName ("Decrypt")
    void decryptTest() throws Exception {
        Photo gnuPhoto = new Photo(photoPathTemporary.toPath());
        String password = "Symmetric encryption password";

        gnuPhoto.encrypt(password);
        gnuPhoto.decrypt(password);
        File encryptedFile = new File(photoPathTemporary.toString() + ENCRYPTED_EXTENSION);

        assertTrue(photoPathTemporary.exists());
        assertFalse(encryptedFile.exists());
    }
}