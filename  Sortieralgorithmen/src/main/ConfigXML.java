package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ConfigXML {

	private Element element;
	private Document document;
	private String source;

	public ConfigXML() {
		// TODO Auto-generated constructor stub
	}
	
	public String getValue(String xmlTag){
		
		return element.getChild(xmlTag).getValue();
	}
	
	public void setValue(String xmlTag,String value){
		System.out.println("SET");
		//element.getChild(xmlTag).addContent(value);
		if(element.removeChild(xmlTag)){
			System.out.println("SUCESSFULLY REMOVED");	
		}
		Element ne = new Element(xmlTag);
		ne.addContent(value);
		element.addContent(ne);
		
	}

	public boolean readXML(String source) {

		this.source = source;
		
		InputStream in = Statics.class.getResourceAsStream(source);

		try {
			long t = System.currentTimeMillis();
			document = new SAXBuilder().build(in);
			element = document.getRootElement();
			
	
			System.out.println("Fully read in "
					+ (System.currentTimeMillis() - t) + "ms");
		} catch (JDOMException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;

	}

	public void closeDOM() throws JDOMException, IOException, URISyntaxException {
		
		File file = new File(Statics.class.getResource("/resources/config.xml").toURI());
		FileOutputStream fos = new FileOutputStream(file);
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.output(document, fos);
		fos.flush();
		fos.close();
		element = null;

	}

	

}
