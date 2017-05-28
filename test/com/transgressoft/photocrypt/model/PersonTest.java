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

package com.transgressoft.photocrypt.model;

import com.google.inject.*;
import com.transgressoft.photocrypt.*;
import com.transgressoft.photocrypt.tests.*;
import com.transgressoft.photocrypt.util.guice.factories.*;
import com.transgressoft.photocrypt.util.guice.modules.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@ExtendWith (MockitoExtension.class)
public class PersonTest {

    @Mock
    PhotoCryptPreferences preferencesMock;
    PersonFactory personFactory;
    Injector injector;

    @BeforeEach
    void beforeEach() {

        injector = Guice.createInjector(binder -> binder.bind(PhotoCryptPreferences.class).toInstance(preferencesMock));
        injector = injector.createChildInjector(new PhotoCryptModule());
        when(preferencesMock.getPersonSequence()).thenReturn(0);
        personFactory = injector.getInstance(PersonFactory.class);
    }

    @Test
    @DisplayName("Constructor")
    void constructorTest() {
        Person person = personFactory.create("Linus Torvalds");
        assertEquals("Linus Torvalds", person.getFullName());
        assertEquals(0, person.getId());
    }

    @Test
    @DisplayName("Full name")
    void fullNameTest() {
        Person person = personFactory.create("Linus Torvalds");
        person.setFullName("Dennis Ritchie");
        assertEquals("Dennis Ritchie", person.getFullName());
    }

    @Test
    @DisplayName("toString")
    void toStringTest() {
        Person person = personFactory.create("Linus Torvalds");
        String expectedString = "[0] Linus Torvalds";
        assertEquals(expectedString, person.toString());
    }

    @Test
    @DisplayName("hashCode")
    void hashCodeTest() {
        Person person = personFactory.create("Linus Torvalds");
        int hashCode = Objects.hash("Linus Torvalds");
        assertEquals(hashCode, person.hashCode());
    }

    @Test
    @DisplayName("Equals")
    void equalsTest() {
        Person linus = personFactory.create("Linus Torvalds");
        Person dennis = personFactory.create("Dennis Ritchie");
        Person linus2 = personFactory.create("Linus Torvalds");
        assertFalse(linus.equals(dennis));
        assertTrue(linus.equals(linus2));
    }

    @Test
    @DisplayName("Not equals with class")
    void notEqualsTest() {
        Person linus = personFactory.create("Linus Torvalds");
        Album album = new Album(1, "Album");
        assertFalse(linus.equals(album));
    }
}