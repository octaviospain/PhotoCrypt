package com.transgressoft.photocrypt.crypto;

import com.transgressoft.photocrypt.error.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public interface CryptableCollection {

    void encryptAll() throws CryptoException;
}