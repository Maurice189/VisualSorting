package main;

/*
VisualSorting
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class LanguageFileXML {

	private Document document;

	public LanguageFileXML() {
	}
	
	
	/**
	 * 
	 * @param xmlTag the addressed component title
	 * @return value(title)
	 */
	public String getValue(String xmlTag){
		return document.getElementsByTagName(xmlTag).item(0).getTextContent();
		//return element.getChild(xmlTag).getValue();
	}
	
	/**
	 * 
	 * @param xmlTag key
	 * @param value value
	 */
	public void setValue(String xmlTag,String value){
		
		
	}

	/**
	 * @param source path to the xml-file
	 * @return true when language xml-file could be loaded
	 */
	public boolean readXML(String source) {
		
		InputStream in = Statics.class.getResourceAsStream(source);
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
	  
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(in);
			in.close();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return true;

	}


}
