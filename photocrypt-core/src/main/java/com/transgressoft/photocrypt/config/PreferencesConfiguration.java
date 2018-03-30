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

package com.transgressoft.photocrypt.config;

import com.transgressoft.photocrypt.factories.*;
import org.springframework.context.annotation.*;

import java.util.prefs.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@Configuration
public class PreferencesConfiguration {

    @Bean
    Preferences albumFactoryPreferences() {
        return Preferences.userNodeForPackage(SimpleAlbumFactory.class);
    }

    @Bean
    Preferences photoFactoryPreferences() {
        return Preferences.userNodeForPackage(SimplePhotoFactory.class);
    }

    @Bean
    Preferences personFactoryPreferences() {
        return Preferences.userNodeForPackage(SimplePersonFactory.class);
    }
}