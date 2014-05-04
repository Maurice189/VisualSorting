import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.TitledBorder;

/** @author maurice
 *  {@literal }
 * 
 * 
 * */

public class SortVisualtionPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private static Color backgroundColor = Color.white;
	private static final int border = 4,marginTop = 30,margin = 5,offsetY = 20;
	
	
	private JPopupMenu menu;
	private TitledBorder leftBorder;
	private BufferedImage buffer;
	private Graphics2D gbuffer;
	private int width,height,refWidth,refHeight;
	private int elements[],lstIndex1 = -1 ,lstIndex2 = -1;
	private int lstInsert = -1;

	
	
	public SortVisualtionPanel(Controller controller,String selectedSort,int width,int height){
		
		this.width = width;
		this.height = height;
		
		buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		gbuffer = (Graphics2D) buffer.getGraphics();
		gbuffer.setFont(Statics.getDefaultFont(14f));
		
		menu  = new JPopupMenu();
		JMenuItem mtDelete = new JMenuItem(Statics.getNamebyXml(Statics.COMPONENT_TITLE.REMOVE));

		
		leftBorder = BorderFactory.createTitledBorder("");
	    leftBorder.setTitleJustification(TitledBorder.ABOVE_TOP);
	    
		
		mtDelete.addActionListener(controller);
		mtDelete.setActionCommand(Statics.POPUP_REMOVE);
		menu.add(mtDelete);
		
		
		
		setBorder(leftBorder);
		addMouseListener(controller);
		add(menu);
		
		
	}
	
	public void setInfo(String info){
		// FIXME info hinkt hinterher; sychronisiere
		leftBorder.setTitle(info);
	}
	
	public static void setBackgroundColor(Color color){
		SortVisualtionPanel.backgroundColor = color;
	}
	
	
	public void showPopUpMenu(int x, int y){
		
		menu.show(this,x,y);
	}
	
	public void setElements(int elements[]){
		
		this.elements = elements;
		int  max  = 1;
		
		for(int i = 0; i<elements.length;i++){
			if(elements[i] > max) max = elements[i];
		}
		
		
		refHeight = (height-offsetY-SortVisualtionPanel.marginTop) / max;
		refWidth = (width-(elements.length*SortVisualtionPanel.border)-(SortVisualtionPanel.margin*2))/elements.length;
		
		drawElements();
	}
	
	/** @deprecated */
	public void updatePanelSize(int width, int height){
		
		
		this.width = width;
		this.height = height;
 
		
		buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		gbuffer = (Graphics2D) buffer.getGraphics();
		gbuffer.setBackground(SortVisualtionPanel.backgroundColor);
		
		gbuffer.setFont(Statics.getDefaultFont(14f));
		
		setElements(elements);
		
		
	}
	
	public void updatePanelSize(){
		
		
		width = this.getWidth();
		height = this.getHeight();

		buffer = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
		gbuffer = (Graphics2D) buffer.getGraphics();
		gbuffer.setBackground(SortVisualtionPanel.backgroundColor);
		gbuffer.setFont(Statics.getDefaultFont(14f));
		
		setElements(elements);

		
	}
	
	public void drawElements(){

		
		gbuffer.clearRect(0, 0, width, height);
		gbuffer.setColor(Color.GRAY);
		
		for(int i = 0; i<elements.length;i++){
			
			gbuffer.drawRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[i]))-offsetY, refWidth, refHeight*elements[i]);
			gbuffer.fillRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[i]))-offsetY, refWidth, refHeight*elements[i]);
			
		}
		
		repaint();
	}
	
	
	public void drawElements(int c, int value){

		
		gbuffer.clearRect(0, 0, width, height);
		gbuffer.setColor(Color.GRAY);
		
		for(int i = 0; i<elements.length;i++){
			
			if(i == c){
				gbuffer.setColor(Color.GREEN);
				gbuffer.drawRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*value))-offsetY, refWidth, refHeight*elements[i]);
				gbuffer.fillRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*value))-offsetY, refWidth, refHeight*elements[i]);
				gbuffer.setColor(Color.GRAY);
			
				
			}
			
			else{
				gbuffer.drawRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[i]))-offsetY, refWidth, refHeight*elements[i]);
				gbuffer.fillRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[i]))-offsetY, refWidth, refHeight*elements[i]);
				//System.out.println((i*(refWidth+SortVisualtionPanel.border))+"|"+(height-(refHeight*elements[i])));
			
			}
			
		}
		
		repaint();
	}
	
	
	
	
	/** 
	 * 
	 * @param c1 compared element1 
	 * @param c2 compared element2
	 * @param exchanged if exchanged
	 * @param info standard information like number of iterations and sort type...
	 */
	
	public void drawElements(int c1,int c2, boolean exchanged){
		
		//long t = System.currentTimeMillis();
		gbuffer.clearRect(0, 0, width, height);
		
		gbuffer.setColor(Color.GRAY);
		
		for(int i = 0; i<elements.length;i++){
			
			if(i == c1 || i == c2){
				gbuffer.setColor(Color.RED);
				gbuffer.drawRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[i]))-offsetY, refWidth, refHeight*elements[i]);
				gbuffer.fillRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[i]))-offsetY, refWidth, refHeight*elements[i]);
				gbuffer.setColor(Color.GRAY);
			
				
			}
			
			else{
				gbuffer.drawRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[i]))-offsetY, refWidth, refHeight*elements[i]);
				gbuffer.fillRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[i]))-offsetY, refWidth, refHeight*elements[i]);
				//System.out.println((i*(refWidth+SortVisualtionPanel.border))+"|"+(height-(refHeight*elements[i])));
			
			}
			
		}
		//System.out.println("TIME: "+(System.currentTimeMillis()-t));
		if(exchanged) signalExchangedElements(c1,c2,refWidth);
		
		
		repaint();
		
	}
	
	
	public void visualInsert(int c, int value){
		
		

		
		if(lstInsert >= 0){
			gbuffer.setColor(Color.GRAY);
			gbuffer.drawRect((lstInsert*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[lstInsert]))-offsetY, refWidth, refHeight*elements[lstInsert]);
			gbuffer.fillRect((lstInsert*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[lstInsert]))-offsetY, refWidth, refHeight*elements[lstInsert]);
			
		}
		
		gbuffer.setColor(SortVisualtionPanel.backgroundColor);
		gbuffer.drawRect((c*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[c]))-offsetY, refWidth, refHeight*elements[c]);
		gbuffer.fillRect((c*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[c]))-offsetY, refWidth, refHeight*elements[c]);
		
		gbuffer.setColor(Color.GREEN);
		gbuffer.drawRect((c*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*value))-offsetY, refWidth, refHeight*value);
		gbuffer.fillRect((c*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*value))-offsetY, refWidth, refHeight*value);
		
		
		lstInsert  = c;
		
		repaint();	
		
	}
	
	
	
	public void visualCmp(int c1,int c2,boolean changed){
		
		//long t = System.currentTimeMillis();
		int x1,x2,y1,y2,h1,h2;
		
		//nur unteren bereich loeschen
		gbuffer.clearRect(0, height-offsetY, width,height);
		
		
		if(lstIndex1 >= 0 && lstIndex2 >=0){
			
			x1 = (lstIndex1*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin;
			x2 = (lstIndex2*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin;
			y1 = (height-(refHeight*elements[lstIndex1]))-offsetY;
			y2 = (height-(refHeight*elements[lstIndex2]))-offsetY;
			h1 =  refHeight*elements[lstIndex1];
			h2 =  refHeight*elements[lstIndex2];
			
			
			gbuffer.setColor(Color.GRAY);
			//gbuffer.setClip(r1);
			gbuffer.drawRect(x1,y1,refWidth,h1);
			gbuffer.fillRect(x1,y1,refWidth,h1);
					
			//gbuffer.setClip(r2);
			gbuffer.drawRect(x2,y2,refWidth,h2);
			gbuffer.fillRect(x2,y2,refWidth,h2);
			
			
		}
		
		x1 = (c1*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin;
		x2 = (c2*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin;
		y1 = (height-(refHeight*elements[c1]))-offsetY;
		y2 = (height-(refHeight*elements[c2]))-offsetY;
		h1 =  refHeight*elements[c1];
		h2 =  refHeight*elements[c2];
		
		
		if(changed){
			
			gbuffer.setColor(SortVisualtionPanel.backgroundColor);
			gbuffer.drawRect(x1,y2,refWidth,h2);
			gbuffer.fillRect(x1,y2,refWidth,h2);
			
			gbuffer.drawRect(x2,y1,refWidth,h1);
			gbuffer.fillRect(x2,y1,refWidth,h1);
			
			signalExchangedElements(c1,c2,refWidth);
		}
		
	
			

			gbuffer.setColor(Color.RED);
			gbuffer.drawRect(x1,y1,refWidth,h1);
			gbuffer.fillRect(x1,y1,refWidth,h1);
					
			gbuffer.drawRect(x2,y2,refWidth,h2);
			gbuffer.fillRect(x2,y2,refWidth,h2);
			
		
			lstIndex1 = c1;
			lstIndex2 = c2;
			//System.out.println("TIME: "+(System.currentTimeMillis()-t));
			repaint();	
			
	
		
		
		
	}
	
	public void dimPanel(String text){
		
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    gbuffer.setRenderingHints(rh);
		
		this.setOpaque(false);
		gbuffer.setBackground(new Color(0f, 0f, 0f, 0.40f));
		gbuffer.clearRect(0, 0, width, height);
		gbuffer.setColor(new Color(0.16f, 0.16f, 0.16f, 0.16f));
		
		for(int i = 0; i<elements.length;i++){
			
			gbuffer.draw3DRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[i]))-offsetY, refWidth, refHeight*elements[i],true);
			gbuffer.fill3DRect((i*(refWidth+SortVisualtionPanel.border))+SortVisualtionPanel.margin, (height-(refHeight*elements[i]))-offsetY, refWidth, refHeight*elements[i],true);
		}
		
		gbuffer.setColor(Color.white);
		gbuffer.setFont(Statics.getDefaultFont(45f));
		gbuffer.drawString(text, (int)(width*0.41), height>>1);
		
		// TODO: antianalaysing ausschalten
		repaint();
		
		rh = new RenderingHints(
		             RenderingHints.KEY_TEXT_ANTIALIASING,
		             RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		    gbuffer.setRenderingHints(rh);
		
	}
	
	private void signalExchangedElements(int i1, int i2,int refWidth){
		
		int x1 = (int)(i1*(refWidth+SortVisualtionPanel.border)+(refWidth*0.5))+SortVisualtionPanel.margin;
		int x2 = (int)(i2*(refWidth+SortVisualtionPanel.border)+(refWidth*0.5))+SortVisualtionPanel.margin;
		
		
		gbuffer.setColor(Color.BLUE);
		gbuffer.setStroke( new BasicStroke(1));
		gbuffer.drawLine(x1, height-offsetY+5, x1, (int)(height-(offsetY*0.70)));
		gbuffer.drawLine(x2, height-offsetY+5, x2, (int)(height-(offsetY*0.70)));
		gbuffer.drawLine(x1, (int)(height-(offsetY*0.70)),x2, (int)(height-(offsetY*0.70)));
		

	}
	
	public void signalExchangedElement(int i1, int i2){
		
		int x1 = (int)(i1*(refWidth+SortVisualtionPanel.border)+(refWidth*0.5))+SortVisualtionPanel.margin;
		int x2 = (int)(i2*(refWidth+SortVisualtionPanel.border)+(refWidth*0.5))+SortVisualtionPanel.margin;
		
		
		gbuffer.setColor(Color.BLUE);
		gbuffer.setStroke( new BasicStroke(1));
		gbuffer.drawLine(x1, height-offsetY+5, x1, (int)(height-(offsetY*0.70)));
		gbuffer.drawLine(x2, height-offsetY+5, x2, (int)(height-(offsetY*0.70)));
		gbuffer.drawLine(x1, (int)(height-(offsetY*0.70)),x2, (int)(height-(offsetY*0.70)));
		

	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.drawImage(buffer, 0, 0, width, height, this);

	}
	


}
