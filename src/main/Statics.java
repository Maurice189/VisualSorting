package main;
import java.awt.Font;

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
 * This class is used as an interface between the View and the Controller.
 * Different static parameters are determined as well as action commands, that 
 * are important for the event handeling.
 * 
 * @author Maurice Koch
 * @version BETA
 * @category MVC
 * 

 */

public class Statics {
	

	// this font is used for components, the default font is monospace
	private static Font defaultFont = new Font("Monospace", Font.PLAIN, 20);

	public static enum SORTALGORITHMS {
		Heapsort, Bubblesort, Quicksort, BST, Combsort,Gnomesort, Shakersort, Mergesort, Bitonicsort,
		Radixsort, Shellsort, Insertionsort,Bogosort;
		
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


	
	public static void setDefaultFont(Font defaultFont) {
		Statics.defaultFont = defaultFont;

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
