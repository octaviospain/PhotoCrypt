package com.transgressoft.photocrypt.factories;

import com.transgressoft.photocrypt.model.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public interface PersonFactory {

    Person create(String fullName);
}