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
import com.transgressoft.photocrypt.util.guice.annotations.*;

import java.util.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class Album {

    private int id;
    private String name;
    private Photo mostRecentPhoto;
    private Photo olderPhoto;
    private String location;
    private List<Photo> photos;

    @Inject
    public Album(@AlbumSequence int id, @Assisted String name) {
        this.id = id;
        this.name = name;
        location = "";
        photos = new ArrayList<>();
    }

    public void encryptAll() {
        throw new UnsupportedOperationException("Unsupported yet");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Photo> getMostRecentPhoto() {

        return Optional.empty();
    }

    public Optional<Photo> getOlderPhoto() {

        return Optional.empty();
    }

    public Optional<String> getLocation() {
        return location.isEmpty() ? Optional.empty() : Optional.of(location);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void addPhotos(Collection<Photo> photosToAdd) {
        photos.addAll(photosToAdd);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " (" + photos.size() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, getLocation(), photos);
    }

    @Override
    public boolean equals(Object object) {
        boolean equals = false;
        if (object instanceof  Album) {
            Album albumObject = (Album) object;
            if (albumObject.getName().equals(name) &&
                    albumObject.getLocation().equals(getLocation()) &&
                    albumObject.getPhotos().equals(photos))
                equals = true;
        }
        return equals;
    }
}