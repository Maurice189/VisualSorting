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
import java.util.HashMap;
import java.util.Properties;

import algorithms.Sort;

public class InternalConfig {

    private static String PROPORTIES_NAME = System.getProperty("user.home") + "/config.txt";
    private static Properties prop;

    private static String version;
    private static boolean autoPauseOn;
    private static int[] elements;
    private static int delayNs;
    private static int delayMs;


    public static void setConfigFileDirectory(String configPath) {
        PROPORTIES_NAME = configPath + "config.txt";
    }

    public static void loadConfigFile() {

        FileReader reader = null;

        try {
            reader = new FileReader(PROPORTIES_NAME);
        } catch (FileNotFoundException e1) {

            System.out.println("Info: config file does not exist\n --> create config file with default parameters");
            FileWriter writer;
            Properties prConfig = new Properties(System.getProperties());

            try {
                writer = new FileWriter(PROPORTIES_NAME);
                prConfig.setProperty("version", "0.5 Beta");
                prConfig.setProperty("delayms", "100");
                prConfig.setProperty("delayns", "10");
                prConfig.setProperty("nofelements", "128");
                prConfig.setProperty("auto_pause", "true");
                prConfig.store(writer, null);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            reader = new FileReader(PROPORTIES_NAME);
            prop = new Properties();
            prop.load(reader);
            reader.close();
            setValues();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getRandomNumber(int low, int high) {
        return (int) (Math.random() * (high - low) + low);
    }

    public static void setElements(int[] elements) {
        InternalConfig.elements = elements;
    }

    public static int[] getElements() {
        return elements;
    }

    public static int getExecutionSpeedDelayMs() {
        return delayMs;
    }

    public static int getExecutionSpeedDelayNs() {
        return delayNs;
    }

    public static void setExecutionSpeedParameters(int delayMs, int delayNs) {
        InternalConfig.delayMs = delayMs;
        InternalConfig.delayNs = delayNs;
    }

    private static void setValues() {
        version = getValue("version");
        int nofelements = Integer.parseInt(getValue("nofelements"));
        autoPauseOn = Boolean.parseBoolean(getValue("auto_pause"));
        delayMs = Integer.parseInt(getValue("delayms"));
        delayNs = Integer.parseInt(getValue("delayns"));

        elements = new int[nofelements];
        for (int i = 0; i < nofelements; i++)
            elements[i] = InternalConfig.getRandomNumber(0, nofelements / 3);

    }

    private static void setValue(String key, Object value) {
        prop.setProperty(key, String.valueOf(value));
    }

    private static String getValue(String key) {
        return prop.getProperty(key);
    }

    public static void saveChanges() {
        setValue("delayms", delayMs);
        setValue("delayns", delayNs);
        setValue("nofelements", elements.length);
        setValue("auto_pause", String.valueOf(autoPauseOn));

        FileWriter writer;
        try {
            writer = new FileWriter(PROPORTIES_NAME);
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
