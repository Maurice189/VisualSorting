package main;

/*
Visualsorting
Copyright (C) 2014  Maurice Koch

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * @author maurice
 * @category persistence
 * @see java.util.Properties
 * @version beta
 * 
 * 
 * This is used to provide a access to the configuration file.
 * The programm settings are stored in "config.txt." an loaded/saved with Proporties
 * 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class InternalConfig {

	private static final String PROPORTIES_NAME = "config.txt";
	private static Properties prop;

	/**
	 * 
	 * @throws IOException
	 * When config.txt could'nt be loaded, the file will be
	 * created. This exception is always fired, whene the app
	 * is launched the first time
	 * 
	 */
	public static void loadConfigFile() throws IOException {

		FileReader reader = null;

		try {
			reader = new FileReader(PROPORTIES_NAME);
		} catch (FileNotFoundException e1) {

			System.out.println("Info: config file does not exist\n --> create config file with default parameters");
			FileWriter writer;
			writer = new FileWriter(PROPORTIES_NAME);

			Properties prConfig = new Properties(System.getProperties());
			prConfig.setProperty("version", "0.5 Beta");
			prConfig.setProperty("language", "lang_de.xml");
			prConfig.setProperty("delayms", "100");
			prConfig.setProperty("delayns", "10");
			prConfig.setProperty("nofelements", "100");
			prConfig.store(writer, null);
			writer.close();

		}

		reader = new FileReader(PROPORTIES_NAME);
		prop = new Properties();
		prop.load(reader);
		reader.close();

	}

	/**
	 * 
	 * @param key config setting
	 * @param value value
	 */
	public static void setValue(String key, Object value) {
		prop.setProperty(key, String.valueOf(value));
	}

	/**
	 * 
	 * @param key 
	 * @return the specific value, that was requested
	 */
	public static String getValue(String key) {

		return prop.getProperty(key);

	}
	
	/**
	 * the changes are saved in the proporties
	 */
	public static void saveChanges() {
		
		FileWriter writer;
		try {
			writer = new FileWriter(PROPORTIES_NAME);
			prop.store(writer, null);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
