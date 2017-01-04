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

package com.transgressoft.photocrypt.error;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Octavio Calleya
 */
public class ErrorCaseTest {

    @Test
    @DisplayName ("Title")
    void titleTest() {
        assertEquals("ENCRYPT_DECRYPT", ErrorCase.CRYPTO_ERROR.getTitle());
    }

    @Test
    @DisplayName("toString")
    void toStringTest() {
        assertEquals("ITEM_NOT_ENCRYPTED_Media item is not encrypted", ErrorCase.ITEM_NOT_ENCRYPTED.toString());
    }
}