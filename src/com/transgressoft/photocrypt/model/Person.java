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

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public class Person {

    private int id;
    private String fullName;

    public Person(String fullName) {
        id = PhotoCryptPreferences.getInstance().getPersonSequence();
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + fullName;
    }

    @Override
    public int hashCode() {
        int hash = 73 + fullName.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean equals = false;
        if (object instanceof Person) {
            Person personObject = (Person) object;
            if (personObject.getFullName().equals(fullName))
                equals = true;
        }
        return equals;
    }
}