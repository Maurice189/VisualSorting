package main;

import java.io.IOException;
import java.io.InputStream;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ConfigXML {

	private Element element;
	private String source;

	public ConfigXML() {
		// TODO Auto-generated constructor stub
	}
	
	public String getValue(String xmlTag){
		
		return element.getChild(xmlTag).getValue();
	}
	
	public void setValue(String xmlTag,String value){
		
		element.getChild(xmlTag).addContent(value);
		
	}

	public boolean readXML(String source) {

		this.source = source;
		
		InputStream in = Statics.class.getResourceAsStream(source);

		try {
			long t = System.currentTimeMillis();
			element = new SAXBuilder().build(in).getRootElement();
			
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

	public void closeDOM() {
		element = null;

	}

	

}
