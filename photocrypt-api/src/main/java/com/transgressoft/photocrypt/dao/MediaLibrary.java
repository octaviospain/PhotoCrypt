package com.transgressoft.photocrypt.dao;

import com.transgressoft.photocrypt.crypto.*;

import java.util.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public interface MediaLibrary {

    Optional<CryptableItem> getMediaItem(int itemId);
}