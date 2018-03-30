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
public class SimpleAlbumFactory implements AlbumFactory {

    public static final String ALBUM_SEQUENCE = "album_sequence";

    private Preferences albumFactoryPreferences;
    private AtomicInteger albumSequence;

    public SimpleAlbumFactory(AtomicInteger albumSequence, Preferences albumFactoryPreferences) {
        this.albumSequence = albumSequence;
        this.albumFactoryPreferences = albumFactoryPreferences;
    }

    @Override
    public Album create(String name) {
        return create(name, "");
    }

    @Override
    public Album create(String name, String location) {
        int newId = albumSequence.getAndIncrement();
        albumFactoryPreferences.putInt(ALBUM_SEQUENCE, newId);
        return new SimpleAlbum(newId, name, location);
    }
}