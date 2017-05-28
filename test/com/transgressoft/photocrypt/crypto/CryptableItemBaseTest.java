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

package com.transgressoft.photocrypt.crypto;

import com.google.common.io.Files;
import com.google.inject.*;
import com.transgressoft.photocrypt.*;
import com.transgressoft.photocrypt.error.*;
import com.transgressoft.photocrypt.model.*;
import com.transgressoft.photocrypt.tests.*;
import com.transgressoft.photocrypt.util.guice.factories.*;
import com.transgressoft.photocrypt.util.guice.modules.*;
import org.apache.commons.io.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;

import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@ExtendWith (MockitoExtension.class)
public class CryptableItemBaseTest {

    static final String ENCRYPTED_EXTENSION = ".enc";
    @Mock
    PhotoCryptPreferences preferencesMock;
    MediaItemFactory mediaItemFactory;
    Injector injector;
    String gnuPhotoFileName = "photo_gnu.png";
    Path photoPath = Paths.get("test-resources", gnuPhotoFileName);
    File photoPathTemporary = Files.createTempDir();

    @BeforeEach
    void beforeEach() throws Exception {
        photoPathTemporary = File.createTempFile(gnuPhotoFileName, "");
        photoPathTemporary.deleteOnExit();
        Files.copy(photoPath.toFile(), photoPathTemporary);

        injector = Guice.createInjector(binder ->
            binder.bind(PhotoCryptPreferences.class).toInstance(preferencesMock));
        injector = injector.createChildInjector(new PhotoCryptModule());
        when(preferencesMock.getMediaItemSequence()).thenReturn(0);
        mediaItemFactory = injector.getInstance(MediaItemFactory.class);
    }

    @Test
    @DisplayName ("Encrypt and decrypt")
    void encryptDecrypt() throws Exception {
        File photoCopy = File.createTempFile(gnuPhotoFileName, "");
        photoCopy.deleteOnExit();
        Files.copy(photoPathTemporary, photoCopy);

        assertTrue(FileUtils.contentEquals(photoPathTemporary, photoCopy));

        Photo gnuPhoto = mediaItemFactory.create(photoPathTemporary.toPath());
        String password = "Symmetric encryption password";


        gnuPhoto.encrypt(password);
        File encryptedFile = new File(photoPathTemporary.toString() + ENCRYPTED_EXTENSION);

        assertTrue(encryptedFile.exists());
        assertFalse(photoPathTemporary.exists());
        assertFalse(FileUtils.contentEquals(photoPathTemporary, photoCopy));


        gnuPhoto.decrypt(password);

        assertTrue(photoPathTemporary.exists());
        assertFalse(encryptedFile.exists());
        assertTrue(FileUtils.contentEquals(photoPathTemporary, photoCopy));
    }

    @Test
    @DisplayName ("Encrypt")
    void encryptTest() throws Exception {
        Photo gnuPhoto = mediaItemFactory.create(photoPathTemporary.toPath());
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
        Photo gnuPhoto = mediaItemFactory.create(photoPathTemporary.toPath());
        String password = "Symmetric encryption password";

        assertFalse(gnuPhoto.isEncrypted());

        gnuPhoto.encrypt(password);
        File encryptedFile = new File(photoPathTemporary.toString() + ENCRYPTED_EXTENSION);

        assertTrue(encryptedFile.exists());

        CryptoException exception = assertThrows(CryptoException.class, () -> gnuPhoto.encrypt(password));
        assertEquals("Media item already encrypted", exception.getMessage());
    }

    @Test
    @DisplayName ("Encryption failed")
    void encryptionFailed() throws Exception {
        Photo gnuPhoto = mediaItemFactory.create(new File("./nonexistentfile.jpg").toPath());

        CryptoException exception = assertThrows(CryptoException.class, () -> gnuPhoto.encrypt(""));
        assertEquals("Error applying crypto to media item", exception.getMessage());
        assertTrue(exception.getCause().getMessage().contains("(No such file or directory)"));
    }

    @Test
    @DisplayName ("Decrypt")
    void decryptTest() throws Exception {
        Photo gnuPhoto = mediaItemFactory.create(photoPathTemporary.toPath());
        String password = "Symmetric encryption password";

        gnuPhoto.encrypt(password);
        gnuPhoto.decrypt(password);
        File encryptedFile = new File(photoPathTemporary.toString() + ENCRYPTED_EXTENSION);

        assertTrue(photoPathTemporary.exists());
        assertFalse(encryptedFile.exists());
    }

    @Test
    @DisplayName ("Decrypt an already decrypted item")
    void invalidDecryption() throws Exception {
        Photo gnuPhoto = mediaItemFactory.create(photoPathTemporary.toPath());
        String password = "Symmetric encryption password";

        assertFalse(gnuPhoto.isEncrypted());

        CryptoException exception = assertThrows(CryptoException.class, () -> gnuPhoto.decrypt(password));
        assertEquals("Media item is not encrypted", exception.getMessage());
    }
}