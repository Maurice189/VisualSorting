package main;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Maurice Koch
 * @version BETA
 * 
 * 
 */

public class Statics {
	

	// this font is used for components, the default font is monospace
	private static Font defaultFont = new Font("Monospace", Font.PLAIN, 20);

	public static enum SORTALGORITHMS {
		Heapsort, Bubblesort, Quicksort, BST, Combsort,Gnomesort, Shakersort, Mergesort, Bitonicsort,
		Radixsort, Shellsort, Insertionsort;
		
		public static int length(){
			return SORTALGORITHMS.values().length;
		}
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
	public static final String ABOUT = "action_about";
	public static final String INFO = "action_info";
	public static final String POPUP_MENU_SORT = "action_sort";
	public static final String POPUP_MENU_ELEMENT = "action_element";

	public static final String LANG_DE = "action_de", LANG_EN = "action_en", LANG_FR = "action_fr";
	public static final String POPUP_REMOVE = "action_remove";
	public static final String DIALOG_EXIT = "action_exitDialog";


	
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
	
	public static void setVersion(String version){
		Statics.VERSION = version;
		
	}
	
	public static String getLanguageSet(){
		return Statics.LANGUAGE_SET;
	}
	
	public static String getVersion(){
		return Statics.VERSION;
	}

	public static Font getDefaultFont(float size) {
		return defaultFont.deriveFont(size);

	}

}
