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

package com.transgressoft.photocrypt.error;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public enum DefaultErrorCase implements ErrorCase {

    CRYPTO_ERROR("ENCRYPT_DECRYPT", "Error applying crypto to media item"),
    ITEM_NOT_ENCRYPTED("ITEM_NOT_ENCRYPTED", "Media item is not encrypted"),
    ITEM_ALREADY_ENCRYPTED("ITEM_ALREADY_ENCRYPTED", "Media item already encrypted");

    private String title;
    private String errorMessage;

    DefaultErrorCase(String title, String errorMessage) {
        this.title = title;
        this.errorMessage = errorMessage;
    }

    public String title() {
        return this.title;
    }

    public String errorMessage() {
        return this.errorMessage;
    }

    @Override
    public String toString() {
        return title + "_" + errorMessage;
    }
}