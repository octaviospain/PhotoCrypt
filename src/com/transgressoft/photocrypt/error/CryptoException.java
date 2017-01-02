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
 * Copyright (C) 2016 Octavio Calleya
 */

package com.transgressoft.photocrypt.error;

/**
 * This exception is thrown if an attempt to encrypt or decrypt
 * a {@link com.transgressoft.photocrypt.model.MediaItem} instance was unsuccessful.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class CryptoException extends Exception {

    private ErrorCase errorCase;
    private int exceptionId;

    public CryptoException(int id,  ErrorCase errorCase) {
        super(errorCase.getErrorMessage());
        exceptionId = id;
        this.errorCase = errorCase;
    }

    public CryptoException(int id, ErrorCase errorCase, Throwable cause) {
        super(errorCase.getErrorMessage(), cause);
        exceptionId = id;
        this.errorCase = errorCase;
    }

    @Override
    public String toString() {
        return "[" + exceptionId + " " + errorCase.getTitle() + "]: " + errorCase.getErrorMessage();
    }
}