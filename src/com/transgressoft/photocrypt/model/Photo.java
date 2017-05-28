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
import com.google.inject.assistedinject.*;
import com.transgressoft.photocrypt.crypto.*;
import com.transgressoft.photocrypt.util.guice.annotations.*;

import java.nio.file.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class Photo extends CryptableItemBase {

    @Inject
    public Photo(@MediaItemSequence int id, @Assisted Path filePath) {
        super(id ,filePath);
    }
}