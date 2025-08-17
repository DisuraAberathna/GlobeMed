/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disuraaberathna.globemed.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author User
 */
public class LoggerUtil {

    private static final Logger LOGGER = Logger.getLogger("GlobeMedLogger");

    static {
        try {
            FileHandler handler = new FileHandler("globemed.log", true);
            handler.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(handler);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Faild to initialize logger file handler", e);
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
