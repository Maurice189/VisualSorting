package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class InternalConfig {

	private static final String PROPORTIES_NAME = "config.txt";
	private static Properties prop;

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


	public static void setValue(String key, Object value) {
		prop.setProperty(key, String.valueOf(value));
	}

	public static String getValue(String key) {

		return prop.getProperty(key);

	}

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
