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

package com.transgressoft.photocrypt.util.guice.modules;

import com.google.inject.*;
import com.google.inject.assistedinject.*;
import com.transgressoft.photocrypt.model.*;
import com.transgressoft.photocrypt.util.guice.factories.*;

/**
 * @author Octavio Calleya
 */
public class MediaItemFactoryModule extends AbstractModule {

    @Override
    protected void configure() {
        Module module = new FactoryModuleBuilder().implement(MediaItem.class, Photo.class)
                                                  .build(MediaItemFactory.class);
        install(module);
    }
}