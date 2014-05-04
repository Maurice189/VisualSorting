import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// TODO schließe fenster, wenn haupfenster geschlossen wurde
public class Dialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static boolean DIALOG_OPEN = false;


	// TODO mach schöner 
	public static JDialog openDelayDialog(Controller controller,String title,int width,int height){
		
		final JDialog dialog = new JDialog();
		final JLabel delay = new JLabel();
		final JSlider slider = new JSlider( 5, 300, 50 );
		slider.setValue(Sort.getDelay());
		
		final TitledBorder titleb = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY), "title");
		titleb.setTitleJustification(TitledBorder.LEFT);
		
		
		delay.setText((String.valueOf(Sort.getDelay())).concat(" milliseconds"));
		JButton exit = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.EXIT));
		JButton set = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.SET));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		dialog.setLayout(new BoxLayout(dialog.getContentPane(),BoxLayout.Y_AXIS));
		panel.add(set);
		panel.add(Box.createHorizontalGlue());
		panel.add(exit);
		panel.setBorder(titleb);
		
		
		dialog.add(Box.createVerticalStrut(4));
		dialog.add(delay);
		dialog.add(Box.createVerticalStrut(10));
		dialog.add(slider);
		dialog.add(Box.createVerticalStrut(20));
		dialog.add(panel);
		
		
		dialog.addWindowListener(new WindowListener(){

			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				Dialog.DIALOG_OPEN = false;
			}

			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
				
			}

			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		exit.addActionListener(controller);
		exit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				   Dialog.DIALOG_OPEN = false;
				   dialog.dispose();
			}
			
		});
		
		set.addActionListener(controller);
		set.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				   Sort.setDelay(slider.getValue());
			}
			
		});
		
		slider.setPaintTicks( true );
		slider.setMajorTickSpacing(10 );
		slider.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				
				titleb.setTitle((String.valueOf(slider.getValue())).concat(" milliseconds"));
			}
			
		});

		
		Dialog.DIALOG_OPEN = true;
		
		java.net.URL helpURL = Dialog.class.getClassLoader().getResource("resources/frameIcon2.png");
		if (helpURL != null) {
		    dialog.setIconImage(new ImageIcon(helpURL).getImage());
		}
		
		dialog.setSize(width,height);
		dialog.setTitle(title);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		
		return dialog;
		
	}
	
	public static JDialog openEnterDialog(Controller controller,String title,int width,int height){
		
		
		final JDialog dialog = new JDialog();
		final DefaultListModel<Integer> listModel = new DefaultListModel<Integer>();
		final JList<Integer> elements = new JList<Integer>(listModel);
		final JTextField value  = new JTextField();
		final JSpinner values = new JSpinner();
		JButton enterValue,ok,remove,crNmb;
		
		JPanel list = new JPanel();
		JPanel list2 = new JPanel();
		list.setLayout(new BoxLayout(list,BoxLayout.X_AXIS));
		list2.setLayout(new BoxLayout(list2,BoxLayout.X_AXIS));
		
		JScrollPane pane = new JScrollPane(elements);
		
		remove = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.REMOVE));
		remove.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(! elements.isSelectionEmpty()){
					
					int selIndices[] = elements.getSelectedIndices();
					for(int i = 0; i<selIndices.length;i++) listModel.remove(selIndices[i]);
					
				}
			}
		});
		
		crNmb = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.RNUMBERS));
		crNmb.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int temp = 0;
				
				if(value.getText() != null){
					
					temp = (int)(values.getValue());
					listModel.removeAllElements();
					for(int i = 0; i<temp; i++) listModel.addElement(Controller.getRandomNumber(0, temp));
	
				}
				
			}
			
		});
		
		enterValue = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.ADD));
		enterValue.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int temp = 0;
				
				if(value.getText() != null){
					
					boolean error = false;
	
					try{
						
						temp = Integer.parseInt(value.getText());
					}catch(NumberFormatException ex){
						
						error = true;
						JOptionPane.showMessageDialog(null,
								Statics.getNamebyXml(Statics.COMPONENT_TITLE.ERROR0),
							    "Ungueltige Eingabe",
							    JOptionPane.ERROR_MESSAGE);
					}
					
					if(!error)listModel.add(0, temp);
					
					value.setText("");
					value.requestFocus();
				}
				
			}
			
		});
		
		ok = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.EXIT));
		ok.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				int [] temp = new int[listModel.size()];
				for(int i = 0; i<listModel.size(); i++)temp[i] = listModel.get(i);
				Sort.setElements(temp);
				
				Dialog.DIALOG_OPEN = false;
				dialog.dispose();
				
			}
			
		});
		
	
		dialog.setLayout(new BoxLayout(dialog.getContentPane(),BoxLayout.Y_AXIS));
		
		int tempElements[] = Sort.getElements();
		for(int i = 0 ; i<tempElements.length ; i++){
			listModel.addElement(new Integer(tempElements[i]));
		}
		
		
		
		list.add(crNmb);
		list.add(Box.createHorizontalStrut(5));
		list.add(values);
		list.add(Box.createHorizontalStrut(5));
		//list.add(value);
		//list.add(Box.createHorizontalStrut(40));
		list.add(ok);
		
		list2.add(enterValue);
		list2.add(value);
		list2.add(remove);
		
		dialog.add(pane);
		dialog.add(Box.createVerticalStrut(7));
		dialog.add(list);
		dialog.add(list2);
		java.net.URL helpURL = Dialog.class.getClassLoader().getResource("resources/frameIcon2.png");
		
		if (helpURL != null) {
		    dialog.setIconImage(new ImageIcon(helpURL).getImage());
		}
		dialog.setSize(width,height);
		dialog.setTitle(title);
		dialog.setResizable(false);
		dialog.setModal(true); 
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		return dialog;
	}
	
	public static JDialog openInfoDialog(Controller controller,String title)
	
	{
		
		final JDialog dialog = new JDialog();
		JLabel cpr = new JLabel("©2014 M.Koch - ".concat(Statics.getVersion()));
		cpr.setFont(Statics.getDefaultFont(12f));
		
		dialog.setLayout(new BorderLayout());
		ImageIcon bg = null;
		
		java.net.URL helpURL = Dialog.class.getClassLoader().getResource("resources/LOGO_MANUAL.png");
		if (helpURL != null) {
		    bg = new ImageIcon(helpURL);
		    
		
		
		dialog.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				dialog.dispose();
				
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		dialog.add(BorderLayout.CENTER,new JLabel(bg));
		dialog.add(BorderLayout.SOUTH,cpr);
		helpURL = Dialog.class.getClassLoader().getResource("resources/frameIcon2.png");
		if (helpURL != null) {
		    dialog.setIconImage(new ImageIcon(helpURL).getImage());
		}
		dialog.setSize(bg.getIconWidth(),bg.getIconHeight()+65);
		dialog.setTitle(title);
		dialog.setResizable(false);
		dialog.setModal(true); 
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		
		} 
		
		return dialog;
	}
	
	public static JDialog openManualDialog(Controller controller,String title,int width,int height){
		
		final JDialog dialog = new JDialog();
	
		JEditorPane manual = new JEditorPane();
		manual.setEditable(false);
		java.net.URL helpURL = Dialog.class.getClassLoader().getResource("resources/manual.html");
		if (helpURL != null) {
		    try {
		    	manual.setPage(helpURL);
		    } catch (IOException e) {
		        System.err.println("Attempted to read a bad URL: " + helpURL);
		    }
		} else {
		    System.err.println("Couldn't find file: TextSamplerDemoHelp.html");
		}

		//Put the editor pane in a scroll pane.
		JScrollPane editorScrollPane = new JScrollPane(manual);
		editorScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(250, 145));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));
		
		
		dialog.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				dialog.dispose();
				
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		dialog.setSize(width, height);
		dialog.add(editorScrollPane);
		helpURL = Dialog.class.getClassLoader().getResource("resources/frameIcon2.png");
		if (helpURL != null) {
		    dialog.setIconImage(new ImageIcon(helpURL).getImage());
		}
		dialog.setTitle(title);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		return dialog;
		
	}
	
	
	public static boolean isDialogOpen(){
		return Dialog.DIALOG_OPEN;
	}
		

}
