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
 * Copyright (C) 2016-2018 Octavio Calleya
 */

package com.transgressoft.photocrypt.factories;

import com.transgressoft.photocrypt.model.*;
import org.springframework.stereotype.*;

import java.util.concurrent.atomic.*;
import java.util.prefs.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@Component
public class SimplePersonFactory implements PersonFactory {

    public static final String PERSON_SEQUENCE = "person_sequence";

    private Preferences personFactoryPreferences;
    private AtomicInteger personSequence;

    public SimplePersonFactory(AtomicInteger personSequence, Preferences personFactoryPreferences) {
        this.personSequence = personSequence;
        this.personFactoryPreferences = personFactoryPreferences;
    }

    public Person create(String fullName) {
        int newId = personSequence.getAndIncrement();
        personFactoryPreferences.putInt(PERSON_SEQUENCE, newId);
        return new SimplePerson(newId, fullName);
    }
}