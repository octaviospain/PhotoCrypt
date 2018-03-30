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
import com.transgressoft.photocrypt.error.*;

import java.util.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class SimpleAlbum implements Album, CryptableCollection {

    private int id;
    private String name;
    private Photo mostRecentPhoto;
    private Photo olderPhoto;
    private String location;
    private List<Photo> photos;

    public SimpleAlbum(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        photos = new ArrayList<>();
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Optional<Photo> mostRecentPhoto() {
        return Optional.empty();
    }

    @Override
    public Optional<Photo> olderPhoto() {
        return Optional.empty();
    }

    @Override
    public Optional<String> location() {
        return location.isEmpty() ? Optional.empty() : Optional.of(location);
    }

    @Override
    public List<Photo> photos() {
        return photos;
    }

    @Override
    public void addPhotos(Collection<Photo> photos) {
        this.photos.addAll(photos);
    }

    @Override
    public void encryptAll() throws CryptoException {
        throw new UnsupportedOperationException("Unsupported yet");
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " (" + photos.size() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location(), photos);
    }

    @Override
    public boolean equals(Object object) {
        boolean equals = false;
        if (object instanceof SimpleAlbum) {
            SimpleAlbum albumObject = (SimpleAlbum) object;
            if (albumObject.name().equals(name) &&
                    albumObject.location().equals(location()) &&
                    albumObject.photos().equals(photos))
                equals = true;
        }
        return equals;
    }
}