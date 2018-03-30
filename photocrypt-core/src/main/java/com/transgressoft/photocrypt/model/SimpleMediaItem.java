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

import java.nio.file.*;
import java.time.*;
import java.util.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public abstract class SimpleMediaItem implements MediaItem {

    private int id;
    private Path pathToMediaItem;
    private String title;
    private String description;
    private String location;
    private LocalDate date;

    public SimpleMediaItem(int id, Path pathToMediaItem, String title, String description, String location, LocalDate date) {
        this.id = id;
        this.pathToMediaItem = pathToMediaItem;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
    }

    protected Path pathToMediaItem() {
        return pathToMediaItem;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Path fileFolder() {
        return pathToMediaItem.getParent();
    }

    @Override
    public String fileName() {
        return pathToMediaItem.getFileName().toString();
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String location() {
        return location;
    }

    @Override
    public Optional<LocalDate> date() {
        return Optional.ofNullable(date);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + fileName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileFolder(), fileName());
    }

    @Override
    public boolean equals(Object object) {
        boolean equals = false;
        if (object instanceof SimpleMediaItem) {
            SimpleMediaItem mediaItemObject = (SimpleMediaItem) object;
            if (mediaItemObject.fileFolder().equals(fileFolder()) &&
                    mediaItemObject.fileName().equals(fileName()))
                equals = true;
        }
        return equals;
    }
}