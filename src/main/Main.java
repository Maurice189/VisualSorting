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

import java.util.HashMap;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

import main.InternalConfig.LANG;
import main.Statics.SORTALGORITHMS;
import dialogs.InfoDialog;
import dialogs.OptionDialog;

public class Main {

	public static void main(String[] args) {
		
		try {
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			try {
			    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
			} catch (ClassNotFoundException
				| InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			    e.printStackTrace();
			}
		}

		System.setProperty("apple.laf.useScreenMenuBar", "true");
			
		InternalConfig.setNewLangDefEntry(LANG.de,"/resources/lang_de.xml");
		InternalConfig.setNewLangDefEntry(LANG.en,"/resources/lang_en.xml");
		InternalConfig.setNewLangDefEntry(LANG.fr,"/resources/lang_fr.xml");
		
		for(int i = 0; i<args.length;i++){
			if(args[i].startsWith("-configdir:")){
				InternalConfig.setConfigFileDirectory(args[i].subSequence("-configdir:".length(), args[i].length()).toString());
			}
			else if(args[i].startsWith("-lang:")){
				InternalConfig.setLanguage(args[i].subSequence("-lang:".length(), args[i].length()).toString());
			}
		}

		LanguageFileXML configLanguage = new LanguageFileXML();
		InternalConfig.loadConfigFile();
		configLanguage.readXML(InternalConfig.getLanguageSetPath());
		
		// define resources
		OptionDialog.setLanguageFileXML(configLanguage);
		PanelUI.setLanguageFileXML(configLanguage);
		
		// this font is used under the GPL from google fonts under 'OpenSans'
		Window.setComponentFont("/resources/Fonts/OpenSans-Regular.ttf");
		Window.setInfoFont("/resources/Fonts/Oxygen-Regular.ttf",30f);
		
		javax.swing.UIManager.put("OptionPane.messageFont", new FontUIResource(Window.getComponentFont(13f))); 
		javax.swing.UIManager.put("Button.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("EditorPane.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("ComboBox.font", new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("Label.font", new FontUIResource(Window.getComponentFont(12f)));
		javax.swing.UIManager.put("MenuBar.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("MenuItem.font", new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("RadioButton.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("CheckBox.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("RadioButtonMenuItem.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("CheckBoxMenuItem.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("TextField.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("TitledBorder.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("Menu.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("Spinner.font",new FontUIResource(Window.getComponentFont(13f)));
		javax.swing.UIManager.put("Slider.font",new FontUIResource(Window.getComponentFont(13f)));
		
		
		// hashmap for resolving sort into the respective infopage file
		HashMap<SORTALGORITHMS,String> map = new HashMap<SORTALGORITHMS,String>();
		map.put(SORTALGORITHMS.Bubblesort, "infopage_bubblesort.html");
		map.put(SORTALGORITHMS.Combsort, "infopage_combsort.html");
		map.put(SORTALGORITHMS.Heapsort, "infopage_heapsort.html");
		map.put(SORTALGORITHMS.Insertionsort, "infopage_insertionsort.html");
		map.put(SORTALGORITHMS.Mergesort, "infopage_mergesort.html");
		map.put(SORTALGORITHMS.Shakersort, "infopage_shakersort.html");
		map.put(SORTALGORITHMS.Shellsort, "infopage_shellsort.html");
		map.put(SORTALGORITHMS.Bogosort, "infopage_bogosort.html");
		map.put(SORTALGORITHMS.Introsort, "infopage_introsort.html");
		InfoDialog.initInfoPageResolver(map);
		
		Controller controller = new Controller(configLanguage);
		Window window = new Window(controller,configLanguage,"Visual Sorting - ".concat(InternalConfig.getVersion()), 1200, 800);
		controller.setView(window);
	
		
	}

}
