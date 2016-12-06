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

package com.transgressoft.photocrypt.crypto;

import com.transgressoft.photocrypt.model.*;
import org.slf4j.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public abstract class CryptableItemBase extends MediaItem implements CryptableItem {

    private static final Logger LOG = LoggerFactory.getLogger(CryptableItemBase.class);

    private static final String ALGORITHM = "AES/CTR/NoPadding";
    private static final String ENCRYPTED_EXTENSION = ".enc";

    private boolean isEncrypted;
    private Cipher cipher;

    public CryptableItemBase(Path pathToMedia) {
        super(pathToMedia);
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public void encrypt(final String password) throws CryptoException {
        if (isEncrypted)
            throw new CryptoException("Media item already encrypted");
        doCrypto(Cipher.ENCRYPT_MODE, password);
        isEncrypted = true;
    }

    public void decrypt(final String password) throws CryptoException {
        if (! isEncrypted)
            throw new CryptoException("Media item is not encrypted");
        doCrypto(Cipher.DECRYPT_MODE, password);
        isEncrypted = false;
    }

    private void doCrypto(int mode, final String password) throws CryptoException {
        try {
            byte[] keyHash = createPasswordHash(password);
            SecretKeySpec keySpec = new SecretKeySpec(keyHash, "AES");
            cipher = Cipher.getInstance(ALGORITHM);
            IvParameterSpec initializationVector = createInitializationVector();
            cipher.init(mode, keySpec, initializationVector);


            byte[] outputBytes = cipher.doFinal(readFileBytes());

            String newFileName = resolveCryptedFileName(mode);
            writeFileBytes(outputBytes, fileFolder + "/" + newFileName);

            File oldFile = new File(fileFolder + "/" + fileName);
            oldFile.delete();
            fileName = newFileName;
        }
        catch (InvalidKeyException | IOException | BadPaddingException | IllegalBlockSizeException |
                NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException exception) {
            fileName = fileName.replaceAll(ENCRYPTED_EXTENSION, "");
            String errorMessage = "Error encrypting media item " + this.toString() + ": " + exception.getMessage();
            LOG.error(errorMessage, exception);
            throw new CryptoException(errorMessage, exception);
        }
    }

    private byte[] createPasswordHash(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] key = password.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // use only first 128 bit
        return key;
    }

    private IvParameterSpec createInitializationVector() throws NoSuchAlgorithmException {
        SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] iv = new byte[cipher.getBlockSize()];
        randomSecureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private byte[] readFileBytes() throws IOException {
        byte[] inputBytes;
        File mediaFile = new File(fileFolder + "/" + fileName);

        try (FileInputStream inputStream = new FileInputStream(fileFolder + "/" + fileName)) {
            inputBytes = new byte[(int) mediaFile.length()];
            inputStream.read(inputBytes);
        }
        return inputBytes;
    }

    private String resolveCryptedFileName(int mode) {
        String resolvedFileName = fileName;
        if (mode == Cipher.ENCRYPT_MODE)
            resolvedFileName += ENCRYPTED_EXTENSION;
        else if (mode == Cipher.DECRYPT_MODE)
            resolvedFileName = resolvedFileName.replaceAll(ENCRYPTED_EXTENSION, "");
        return resolvedFileName;
    }

    private void writeFileBytes(byte[] bytesToWrite, String outputPath) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(new File(outputPath));
        outputStream.write(bytesToWrite);
        outputStream.close();
    }
}