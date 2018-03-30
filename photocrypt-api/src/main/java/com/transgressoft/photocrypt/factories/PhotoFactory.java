package com.transgressoft.photocrypt.factories;

import com.transgressoft.photocrypt.model.*;

import java.nio.file.*;
import java.time.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
public interface PhotoFactory {

    Photo create(Path filePath);

    Photo create(Path filePath, String title, String description, String location, LocalDate date);
}