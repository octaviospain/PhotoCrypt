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

import com.transgressoft.photocrypt.error.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public interface CryptableItem {

    /**
     * Performs the encryption applying the <tt>ALGORITHM</tt> cryptography suite.
     * The name of the encrypted file is be the same as the original with <tt>.enc</tt> at the end,
     * in the same folder where the original file was.
     * The original file is deleted if the encryption is successful.
     *
     * @param password The <tt>String</tt> used as password for the encryption process.
     *
     * @throws CryptoException If something goes wrong.
     */
    void encrypt(final String password) throws CryptoException;


    /**
     * Performs the decryption applying the <tt>ALGORITHM</tt> cryptography suite.
     * The name of the decrypted file wil be the original one without the <tt>.enc</tt> suffix.
     * The previous decrypted file is deleted if the decryption is successful.
     *
     * @param password The <tt>String</tt> used as password for the decryption process.
     *
     * @throws CryptoException If something goes wrong.
     */
    void decrypt(final String password) throws CryptoException;

    boolean isEncrypted();
}