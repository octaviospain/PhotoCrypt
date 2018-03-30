package com.transgressoft.photocrypt.factories;

import com.transgressoft.photocrypt.model.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public interface AlbumFactory {

    Album create(String name);

    Album create(String name, String location);
}