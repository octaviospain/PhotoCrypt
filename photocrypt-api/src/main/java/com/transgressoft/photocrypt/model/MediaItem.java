package com.transgressoft.photocrypt.model;

import java.nio.file.*;
import java.time.*;
import java.util.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public interface MediaItem {

    int id();

    Path fileFolder();

    String fileName();

    String title();

    <T extends MediaItem> T title(String title);

    String description();

    <T extends MediaItem> T description(String description);

    String location();

    <T extends MediaItem> T location(String location);

    Optional<LocalDate> date();

    <T extends MediaItem> T date(LocalDate date);
}