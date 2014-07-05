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
 * 
 * The application provides multiple languages.
 * This class is used to load a specific language file. The respective 
 * values (often times component titles) are stored in a XML file. 
 * 
 * For loading/reading the XML file I used <a href="http://www.jdom.org/">http://www.jdom.org/</a>
 * API. It's easier to deal with than the API in the Java SDK.
 * 
 * 
 * @author maurice
 * @category persistence
 * @version BETA
 * 
 *  
 */

import java.io.IOException;
import java.io.InputStream;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class LanguageFileXML {

	private Element element;
	private Document document;

	public LanguageFileXML() {
	}
	
	
	/**
	 * 
	 * @param xmlTag the addressed component title
	 * @return value(title)
	 */
	public String getValue(String xmlTag){
		
		return element.getChild(xmlTag).getValue();
	}
	
	/**
	 * 
	 * @param xmlTag key
	 * @param value value
	 */
	public void setValue(String xmlTag,String value){
		
		element.removeChild(xmlTag);
		Element ne = new Element(xmlTag);
		ne.addContent(value);
		element.addContent(ne);
		
	}

	/**
	 * @param source path to the xml-file
	 * @return true when language xml-file could be loaded
	 */
	public boolean readXML(String source) {
		
		InputStream in = Statics.class.getResourceAsStream(source);

		try {
			//long t = System.currentTimeMillis();
			document = new SAXBuilder().build(in);
			element = document.getRootElement();
			

			/*System.out.println("Fully read in "
					+ (System.currentTimeMillis() - t) + "ms");*/
		} catch (JDOMException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;

	}


}
