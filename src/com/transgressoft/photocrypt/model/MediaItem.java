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

import com.transgressoft.photocrypt.*;

import java.nio.file.*;
import java.time.*;
import java.util.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public abstract class MediaItem {

    private int id;
    protected String fileFolder;
    protected String fileName;
    private String title;
    private String description;
    private String location;
    private Optional<LocalDate> date;

    public MediaItem(Path pathToMedia) {
        id = PhotoCryptPreferences.getInstance().getMediaItemSequence();
        fileFolder = pathToMedia.toFile().getParent();
        fileName = pathToMedia.getFileName().toString();
        title = "";
        description = "";
        location = "";
        date = Optional.empty();
    }

    public int getId() {
        return id;
    }

    public String getFileFolder() {
        return fileFolder;
    }

    public void setFileFolder(Path path) {
        this.fileFolder = path.toFile().getParent();
        this.fileName = path.getFileName().toString();
    }

    public String getFileName() {
        return fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Optional<LocalDate> getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = Optional.of(date);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + fileName;
    }

    @Override
    public int hashCode() {
        int hash = 73;
        hash = 73 * hash + fileFolder.hashCode();
        hash = 73 * hash + fileName.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean equals = false;
        if (object instanceof  MediaItem) {
            MediaItem mediaItemObject = (MediaItem) object;
            if (mediaItemObject.getFileFolder().equals(fileFolder) &&
                    mediaItemObject.getFileName().equals(fileName))
                equals = true;
        }
        return equals;
    }
}