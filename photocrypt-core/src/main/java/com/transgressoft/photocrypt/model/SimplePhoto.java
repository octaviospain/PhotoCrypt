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

package com.transgressoft.photocrypt.model;

import com.transgressoft.photocrypt.crypto.*;

import java.nio.file.*;
import java.time.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class SimplePhoto extends CryptableItemBase implements Photo {

    public SimplePhoto(int id, Path pathToMediaItem) {
        this(id, pathToMediaItem, "", "", "", null);
    }

    public SimplePhoto(int id, Path pathToMediaItem, String title, String description, String location, LocalDate date) {
        super(id, pathToMediaItem, title, description, location, date);
    }

    @Override
    public Photo title(String title) {
        return new SimplePhoto(id(), pathToMediaItem(), title, description(), location(), date().orElse(null));
    }

    @Override
    public MediaItem description(String description) {
        return new SimplePhoto(id(), pathToMediaItem(), title(), description, location(), date().orElse(null));
    }

    @Override
    public MediaItem location(String location) {
        return new SimplePhoto(id(), pathToMediaItem(), title(), description(), location, date().orElse(null));
    }

    @Override
    public MediaItem date(LocalDate date) {
        return new SimplePhoto(id(), pathToMediaItem(), title(), description(), location(), date);
    }

}