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

import java.util.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class SimplePerson implements Person {

    private int id;
    private String fullName;

    public SimplePerson(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String fullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + fullName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

    @Override
    public boolean equals(Object object) {
        boolean equals = false;
        if (object instanceof SimplePerson) {
            SimplePerson personObject = (SimplePerson) object;
            if (personObject.fullName().equals(fullName))
                equals = true;
        }
        return equals;
    }
}