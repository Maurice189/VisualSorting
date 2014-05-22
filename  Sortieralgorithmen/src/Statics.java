import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Statics {

	private static Font defaultFont = new Font("Monospace", Font.PLAIN, 20);

	public static enum COMPONENT_TITLE {
		ADD_SORT, STARTANI, STOPANI, ADD, ITERATION, RESET, SETTINGS, SORTLIST, DELAY, HELP, ABOUT, INFO, REMOVE, EXIT, SET, MANUAL,LANG,ERROR0,
		VERSION,SLANGUAGE,RNUMBERS,REPORT
	};

	private static String VERSION,LANGUAGE_SET;
	
	public static final String ADD_SORT = "action_add";
	public static final String REMOVE_SORT = "action_remove";
	public static final String START = "action_start";
	public static final String NEW_ELEMENTS = "action_setElements";
	public static final String NEXT_ITERATION = "action_nextIter";
	public static final String RESET = "action_reset";
	public static final String REPORT = "action_report";
	
	public static final String DELAY = "action_delay";
	public static final String INFO = "action_Info";
	public static final String POPUP_MENU_SORT = "action_sort";
	public static final String POPUP_MENU_ELEMENT = "action_element";

	public static final String LANG_DE = "action_de", LANG_EN = "action_en", LANG_FR = "action_fr";
	public static final String POPUP_REMOVE = "action_remove";
	public static final String DIALOG_EXIT = "action_exitDialog";
	public static final String MANUAL = "action_manual";

	private static Element element;

	public static final String SORT_ALGORITHMNS[] = { "Heapsort", "Bubblesort",
			"Quicksort", "Binary Tree Sort", "Combsort", "Gnomesort",
			"Shakersort (Cocktailsort)", "Mergesort", "Bitonicsort",
			"Radixsort", "Shellsort", "Insertionsort" };
	
	
	private static HashMap<COMPONENT_TITLE,String> xmlDef;

	public static void initDefaultFont(String source) {


			try {

				InputStream in = Statics.class.getResourceAsStream(source);
				defaultFont = Font.createFont(Font.TRUETYPE_FONT,in);
				
			

			} catch (IOException e) {
				defaultFont = new Font("Monospace", Font.PLAIN, 20);
			} catch (FontFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				defaultFont = new Font("Monospace", Font.PLAIN, 20);
			}
		

	}
	
	
	public static void readLang(String source, String lang_name){
		readXML(source);
		Statics.LANGUAGE_SET = lang_name;
		
	}
	

	private static void readXML(String source) {

	
			InputStream in = Statics.class.getResourceAsStream(source);

			try {
				long t = System.currentTimeMillis();
				element = new SAXBuilder().build(in).getRootElement();

				System.out.println("Fully read in "
						+ (System.currentTimeMillis() - t) + "ms");
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	

	public static String getNamebyXml(COMPONENT_TITLE title) {

		String key = xmlDef.get(title);
		if(key != null)  return element.getChild(xmlDef.get(title)).getValue();
		return null;
	}
	
	public static void loadConfig(String source){
		readXML(source);
		
	
		Statics.VERSION =  element.getChild("version").getValue();
		Statics.LANGUAGE_SET =  element.getChild("language").getValue();
		
		
	}
	
	
	public static String getLanguageSet(){
		return Statics.LANGUAGE_SET;
	}
	
	public static String getVersion(){
		return Statics.VERSION;
	}

	public static void closeDOM() {
		element = null;

	}
	
	/**
	 * We also could make the 'COMPONENT_TITLE' values equal to those from xml tags, but 
	 * I don't like the resultating linkage. But if we define the xml tags independantly, we are able to change these 
	 * very easily .
	 */
	public static void initHashTable(){
		
		xmlDef = new HashMap<COMPONENT_TITLE,String>();
		
		xmlDef.put(COMPONENT_TITLE.ABOUT, "about");
		xmlDef.put(COMPONENT_TITLE.ADD, "add");
		xmlDef.put(COMPONENT_TITLE.ADD_SORT, "add");
		xmlDef.put(COMPONENT_TITLE.DELAY, "delay");
		xmlDef.put(COMPONENT_TITLE.EXIT, "exit");
		xmlDef.put(COMPONENT_TITLE.HELP, "help");
		xmlDef.put(COMPONENT_TITLE.INFO, "info");
		xmlDef.put(COMPONENT_TITLE.ITERATION, "iteration");
		xmlDef.put(COMPONENT_TITLE.REMOVE, "remove");
		xmlDef.put(COMPONENT_TITLE.RESET, "reset");
		xmlDef.put(COMPONENT_TITLE.SET,"set");
		xmlDef.put(COMPONENT_TITLE.SETTINGS, "settings");
		xmlDef.put(COMPONENT_TITLE.SORTLIST, "sortlist");
		xmlDef.put(COMPONENT_TITLE.STARTANI, "startani");
		xmlDef.put(COMPONENT_TITLE.STOPANI, "stopani");
		xmlDef.put(COMPONENT_TITLE.MANUAL, "manual");
		xmlDef.put(COMPONENT_TITLE.LANG, "lang");
		xmlDef.put(COMPONENT_TITLE.ERROR0, "error0");
		xmlDef.put(COMPONENT_TITLE.VERSION, "version");
		xmlDef.put(COMPONENT_TITLE.SLANGUAGE, "language");
		xmlDef.put(COMPONENT_TITLE.RNUMBERS, "rnumber");
		xmlDef.put(COMPONENT_TITLE.REPORT, "report");
	}

	public static Font getDefaultFont(float size) {
		return defaultFont.deriveFont(size);

	}

}
