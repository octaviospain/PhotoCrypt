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

import java.nio.file.*;
import java.time.*;
import java.util.concurrent.atomic.*;
import java.util.prefs.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@Component
public class SimplePhotoFactory implements PhotoFactory {

    public static final String PHOTO_SEQUENCE = "photo_sequence";

    private Preferences photoFactoryPreferences;
    private AtomicInteger photoSequence;

    public SimplePhotoFactory(AtomicInteger photoSequence, Preferences photoFactoryPreferences) {
        this.photoSequence = photoSequence;
        this.photoFactoryPreferences = photoFactoryPreferences;
    }

    @Override
    public Photo create(Path filePath) {
        return create(filePath, "", "", "", null);
    }

    @Override
    public Photo create(Path filePath, String title, String description, String location, LocalDate date) {
        int newId = photoSequence.getAndIncrement();
        photoFactoryPreferences.putInt(PHOTO_SEQUENCE, newId);
        return new SimplePhoto(newId, filePath, title, description, location, date);
    }
}