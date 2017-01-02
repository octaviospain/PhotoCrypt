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

import java.util.concurrent.atomic.*;

/**
 * Singleton class that handles the exceptions.
 *
 * @author Octavio Calleya
 * @version 0.1
 */
public class ErrorDaemon {

    private AtomicInteger errorCount = new AtomicInteger(0);

    public static ErrorDaemon getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {

        static final ErrorDaemon INSTANCE = new ErrorDaemon();
    }

    public CryptoException exception(ErrorCase errorCase) {
        return new CryptoException(errorCount.getAndIncrement(), errorCase);
    }

    public CryptoException exception(ErrorCase errorCase, Throwable cause) {
        return new CryptoException(errorCount.getAndIncrement(), errorCase, cause);
    }
}