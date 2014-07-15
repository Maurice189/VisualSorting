package dialogs;

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
 * This class serves as an interface to all dialogs, that are used in the application.
 * 
 * @author Maurice Koch
 * @category Dialgos
 * @version BETA
 * 
 * 
 * 
 * 
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
