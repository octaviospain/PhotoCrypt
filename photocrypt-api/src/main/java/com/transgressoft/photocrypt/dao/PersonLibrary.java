package com.transgressoft.photocrypt.dao;

import com.transgressoft.photocrypt.model.*;

import java.util.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public interface PersonLibrary {

    Optional<Person> getPerson(int personId);
}