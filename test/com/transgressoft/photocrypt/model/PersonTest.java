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

import com.transgressoft.photocrypt.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class PersonTest {

    String home = System.getProperty("os.home");

    @BeforeEach
    void beforeEach() {
        PhotoCryptPreferences.getInstance().setPhotoCryptUserFolder(home + "PhotoCrypt" + File.separator);
    }

    @AfterEach
    void afterEach() {
        assertTrue(new File(home + "PhotoCrypt" + File.separator).delete());
    }


    @Test
    @DisplayName("Constructor")
    void constructorTest() {
        Person person = new Person("Linus Torvalds");
        assertEquals("Linus Torvalds", person.getFullName());
        assertEquals(1, person.getId());
    }

    @Test
    @DisplayName("Full name")
    void fullNameTest() {
        Person person = new Person("Linus Torvalds");
        person.setFullName("Dennis Ritchie");
        assertEquals("Dennis Ritchie", person.getFullName());
    }

    @Test
    @DisplayName("toString")
    void toStringTest() {
        Person person = new Person("Linus Torvalds");
        String expectedString = "[1] Linus Torvalds";
        assertEquals(expectedString, person.toString());
    }

    @Test
    @DisplayName("hashCode")
    void hashCodeTest() {
        Person person = new Person("Linus Torvalds");
        int hashCode = 73 + "Linus Torvalds".hashCode();
        assertEquals(hashCode, person.hashCode());
    }

    @Test
    @DisplayName("Equals")
    void equalsTest() {
        Person linus = new Person("Linus Torvalds");
        Person dennis = new Person("Dennis Ritchie");
        Person linus2 = new Person("Linus Torvalds");
        assertFalse(linus.equals(dennis));
        assertTrue(linus.equals(linus2));
    }

    @Test
    @DisplayName("Not equals with class")
    void notEqualsTest() {
        Person linus = new Person("Linus Torvalds");
        Album album = new Album("Album");
        assertFalse(linus.equals(album));
    }
}