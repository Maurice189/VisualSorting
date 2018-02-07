package dialogs;

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

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import main.LanguageFileXML;



public abstract class OptionDialog extends JDialog implements ActionListener{

	
	private static final long serialVersionUID = 1L;
	protected String titleXMLTag;
	protected static LanguageFileXML langXML;
	
	/**
	 * 
	 * the default frame icon is set to every dialog
	 * 
	 * @param titleXMLTag the title is set by the value of the respective xml-tag in the language file
	 * @param width width of the dialog
	 * @param height width of the dialog
	 */
	public OptionDialog(String titleXMLTag,int width, int height) {
		
		this.titleXMLTag = titleXMLTag;
		initComponents();
		setSize(width, height);
		setTitle(langXML.getValue(titleXMLTag));
		setLocationRelativeTo(null);
		
		java.net.URL helpURL = OptionDialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");
		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}
		setVisible(true);
	}
	
	public OptionDialog(){
		setLocationRelativeTo(null);
		java.net.URL helpURL = OptionDialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");
		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}
	}
	

	/**
	 * 
	 * @param langXML interface to the language definitions
	 */
	public static void setLanguageFileXML(LanguageFileXML langXML){
		OptionDialog.langXML = langXML;
	}
	
	/**
	 * When the language was changed at the run time, every dialog 
	 * has to change it's component titles.
	 * has to changed certainly.
	 * Every dialogs(subclass) can override this method, in order
	 * to define which titles should be changed. If it's not overridden
	 * just the title is going to be changed. 
	 */
	public void updateComponentsLabel(){
		setTitle(langXML.getValue(titleXMLTag));
	}
	
	/**
	 * Declare and init the components should be displayed
	 */
	protected abstract void initComponents();
		

}
