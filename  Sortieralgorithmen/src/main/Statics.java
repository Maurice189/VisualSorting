package main;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import sorting_algorithms.Sort;

/**
 * @author Maurice Koch
 * @version BETA
 * 
 * 
 */

public class Statics {
	
	private static ConfigXML configLang,configSetting;

	// this font is used for components, the default font is monospace
	private static Font defaultFont = new Font("Monospace", Font.PLAIN, 20);

	// statics for component title
	public static enum COMPONENT_TITLE {
		ADD_SORT, STARTANI, STOPANI, ADD, RESET, SETTINGS, SORTLIST, DELAY, HELP, ABOUT, INFO, REMOVE, EXIT, SET, MANUAL,LANG,ERROR0,
		VERSION,SLANGUAGE,RNUMBERS,REPORT,ITERATIONS
	};

	private static String VERSION,LANGUAGE_SET; // prg version, language set
	
	// actionlistener use actioncommands 
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

	// name of all sort algorithms
	public static final String SORT_ALGORITHMNS[] = { "Heapsort", "Bubblesort",
			"Quicksort", "Binary Tree Sort", "Combsort", "Gnomesort",
			"Shakersort (Cocktailsort)", "Mergesort", "Bitonicsort",
			"Radixsort", "Shellsort", "Insertionsort" };
	
	/*
	 * The view class should'nt work directly with the xml-tags, that's the reason why I use 
	 * here a hashmap
	 * 
	 * with this indirect way by resolving COMPONENT_TITLE to the respective xml-tag
	 * we reach less coupling.
	 * 
	 */
	private static HashMap<COMPONENT_TITLE,String> xmlDef;
	
	
	public static void setConfigXML(ConfigXML configLang,ConfigXML configSetting){
		
		Statics.configSetting = configSetting;
		Statics.configLang  = configLang;
		
		Statics.VERSION =  configSetting.getValue("version");
		Statics.LANGUAGE_SET =  configSetting.getValue("language");
		System.out.println("LANGUAGE: "+Statics.LANGUAGE_SET);
		configLang.readXML("/resources/".concat(Statics.LANGUAGE_SET),true);
		
		Sort.setDelayMs(Long.parseLong(configSetting.getValue("delayms")));
		Sort.setDelayNs(Integer.parseInt(configSetting.getValue("delayns")));
		
	}

	
	public static void initDefaultFont(String source) {


			try {

				InputStream in = Statics.class.getResourceAsStream(source);
				defaultFont = Font.createFont(Font.TRUETYPE_FONT,in);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FontFormatException e) {
				e.printStackTrace();
			}
		

	}
	
	
	public static void setLanguage(String lang_name){
		Statics.LANGUAGE_SET = lang_name;
		
	}
	


	// here the component title is resolved into the respective xml-tag
	public static String getNamebyXml(COMPONENT_TITLE title) {

		String key = xmlDef.get(title);
		if(key != null)  return configLang.getValue(xmlDef.get(title));
		return null;
	}

	
	public static String getLanguageSet(){
		return Statics.LANGUAGE_SET;
	}
	
	public static String getVersion(){
		return Statics.VERSION;
	}
	

	
    // here the component-titles and the respective xml-tags are linked in the hash-map
	public static void initXMLDefintions(){
		
		xmlDef = new HashMap<COMPONENT_TITLE,String>();
		
		xmlDef.put(COMPONENT_TITLE.ABOUT, "about");
		xmlDef.put(COMPONENT_TITLE.ADD, "add");
		xmlDef.put(COMPONENT_TITLE.ADD_SORT, "add");
		xmlDef.put(COMPONENT_TITLE.DELAY, "delay");
		xmlDef.put(COMPONENT_TITLE.EXIT, "exit");
		xmlDef.put(COMPONENT_TITLE.HELP, "help");
		xmlDef.put(COMPONENT_TITLE.INFO, "info");
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
		xmlDef.put(COMPONENT_TITLE.ITERATIONS, "iterations");
	}

	public static Font getDefaultFont(float size) {
		return defaultFont.deriveFont(size);

	}

}
