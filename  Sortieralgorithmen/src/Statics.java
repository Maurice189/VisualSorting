import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Statics {

	private static Font defaultFont = new Font("Monospace", Font.PLAIN, 20);

	public static enum COMPONENT_TITLE {
		ADD_SORT, STARTANI, STOPANI, ADD, ITERATION, RESET, SETTINGS, SORTLIST, DELAY, HELP, ABOUT, INFO, REMOVE, EXIT, SET, MANUAL,LANG,ERROR0,
		VERSION,SLANGUAGE,RNUMBERS
	};

	private static String VERSION,LANGUAGE_SET;
	
	public static final String ADD_SORT = "action_add";
	public static final String REMOVE_SORT = "action_remove";
	public static final String START = "action_start";
	public static final String NEW_ELEMENTS = "action_setElements";
	public static final String NEXT_ITERATION = "action_nextIter";
	public static final String RESET = "action_reset";

	public static final String DELAY = "action_delay";
	public static final String INFO = "action_Info";
	public static final String POPUP_MENU_SORT = "action_sort";
	public static final String POPUP_MENU_ELEMENT = "action_element";

	public static final String LANG_DE = "action_de", LANG_EN = "action_en", LANG_FR = "action_fr";
	public static final String POPUP_MENU_HEAP = "action_heap ";
	public static final String POPUP_MENU_QUICK = "action_quick";
	public static final String POPUP_MENU_BUBBLE = "action_bubble";
	public static final String POPUP_ST = "action_st";
	public static final String POPUP_REMOVE = "action_remove";
	public static final String DIALOG_EXIT = "action_exitDialog";
	public static final String MANUAL = "action_manual";

	private static Element element;

	public static final String SORT_ALGORITHMNS[] = { "Heapsort", "Bubblesort",
			"Quicksort", "Binary Tree Sort", "Combsort", "Gnomesort",
			"Shakersort (Cocktailsort)", "Mergesort", "Bitonicsort",
			"Radixsort", "Shellsort", "Insertionsort" };

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

		switch (title) {

		case ABOUT:
			return element.getChild("about").getValue();
		case ADD:
			return element.getChild("add").getValue();
		case ADD_SORT:
			return element.getChild("add").getValue();
		case DELAY:
			return element.getChild("delay").getValue();
		case EXIT:
			return element.getChild("exit").getValue();
		case HELP:
			return element.getChild("help").getValue();
		case INFO:
			return element.getChild("info").getValue();
		case ITERATION:
			return element.getChild("iteration").getValue();
		case REMOVE:
			return element.getChild("remove").getValue();
		case RESET:
			return element.getChild("reset").getValue();
		case SET:
			return element.getChild("set").getValue();
		case SETTINGS:
			return element.getChild("settings").getValue();
		case SORTLIST:
			return element.getChild("sortlist").getValue();
		case STARTANI:
			return element.getChild("startani").getValue();
		case STOPANI:
			return element.getChild("stopani").getValue();
		case MANUAL:
			return element.getChild("manual").getValue();
		case LANG:
			return element.getChild("lang").getValue();
		case ERROR0:
			return element.getChild("error0").getValue();
		case VERSION:
			return element.getChild("version").getValue();
		case SLANGUAGE:
			return element.getChild("language").getValue();
		case RNUMBERS:
			return element.getChild("rnumber").getValue();
		default:
			return null;
		}

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

	public static Font getDefaultFont(float size) {
		return defaultFont.deriveFont(size);

	}

}
