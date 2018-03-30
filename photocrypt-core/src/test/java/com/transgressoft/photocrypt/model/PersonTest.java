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
import com.transgressoft.photocrypt.factories.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;
import java.util.concurrent.atomic.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@SpringBootTest
@ExtendWith ({SpringExtension.class, MockitoExtension.class})
public class PersonTest {

    @Autowired
    AtomicInteger personSequence;
    @Autowired
    SimplePersonFactory personFactory;

    @BeforeEach
    void beforeEach() {
        personSequence.set(0);
    }

    @Test
    @DisplayName("Constructor")
    void constructorTest() {
        Person person = personFactory.create("Linus Torvalds");
        assertEquals("Linus Torvalds", person.fullName());
        assertEquals(0, person.id());
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
        Album album = new SimpleAlbum(1, "SimpleAlbum", "");
        assertFalse(linus.equals(album));
    }
}