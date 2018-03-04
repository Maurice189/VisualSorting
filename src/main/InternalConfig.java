package main;

/*
 * This software is licensed under the MIT License.
 * Copyright 2018, Maurice Koch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class InternalConfig {

    private static String CONFIG_FILE_PATH = "config.txt";
    private static Properties prop;

    private static String version;
    private static boolean autoPauseOn;
    private static int delayNs;
    private static int delayMs;
    private static int numberOfElements;

    private final static String PROPERTY_VERSION = "version";
    private final static String PROPERTY_DELAY_MS = "delay_ms";
    private final static String PROPERTY_DELAY_NS = "delay_ns";
    private final static String PROPERTY_NUMBER_OF_ELEMENTS = "number_of_elements";
    private final static String PROPERTY_AUTO_PAUSE_ENABLED = "auto_pause_enabled";

    public static void setConfigFileDirectory(String configPath) {
        CONFIG_FILE_PATH = configPath + "config.txt";
    }

    public static void loadConfigFile() {

        if (Files.exists(Paths.get(CONFIG_FILE_PATH))) {
            try {
                FileReader reader = new FileReader(CONFIG_FILE_PATH);
                prop = new Properties();
                prop.load(reader);
                reader.close();
                setValues();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            prop = new Properties();
            version = "/";
            delayMs = 100;
            delayNs = 0;
            numberOfElements = 100;
            autoPauseOn = false;
        }
    }

    public static int getExecutionSpeedDelayMs() {
        return delayMs;
    }

    public static int getExecutionSpeedDelayNs() {
        return delayNs;
    }

    public static int getNumberOfElements() {
        return numberOfElements;
    }

    public static void setExecutionSpeedParameters(int delayMs, int delayNs) {
        InternalConfig.delayMs = delayMs;
        InternalConfig.delayNs = delayNs;
    }

    public static void setNumberOfElements(int numberOfElements) {
        InternalConfig.numberOfElements = numberOfElements;
    }

    private static void setValues() {
        version = getValue(PROPERTY_VERSION);
        numberOfElements = Integer.parseInt(getValue(PROPERTY_NUMBER_OF_ELEMENTS));
        autoPauseOn = Boolean.parseBoolean(getValue(PROPERTY_AUTO_PAUSE_ENABLED));
        delayMs = Integer.parseInt(getValue(PROPERTY_DELAY_MS));
        delayNs = Integer.parseInt(getValue(PROPERTY_DELAY_NS));
    }

    private static void setValue(String key, Object value) {
        prop.setProperty(key, String.valueOf(value));
    }

    private static String getValue(String key) {
        return prop.getProperty(key);
    }

    public static void saveChanges() {
        setValue(PROPERTY_VERSION, version);
        setValue(PROPERTY_DELAY_MS, delayMs);
        setValue(PROPERTY_DELAY_NS, delayNs);
        setValue(PROPERTY_NUMBER_OF_ELEMENTS, numberOfElements);
        setValue(PROPERTY_AUTO_PAUSE_ENABLED, String.valueOf(autoPauseOn));

        try {
            FileWriter writer = new FileWriter(CONFIG_FILE_PATH);
            prop.store(writer, null);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void toggleAutoPause() {
        InternalConfig.autoPauseOn = !InternalConfig.autoPauseOn;
    }

    public static void setVersion(String version) {
        InternalConfig.version = version;
    }

    public static boolean isAutoPauseEnabled() {
        return InternalConfig.autoPauseOn;
    }

    public static String getVersion() {
        return InternalConfig.version;
    }

}
