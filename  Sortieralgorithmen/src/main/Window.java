package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sorting_algorithms.Sort;


/**
 * @author Maurice Koch
 * @version BETA
 * 
 * This class respresents, as the name implies, the view(GUI) in the MVC pattern.
 * 
 */


public class Window extends JFrame {

	private static Font componentFont = new Font("Monospace", Font.BOLD, 13);
	private static final long serialVersionUID = 1L;

	// we store the visualisation panels dynamically, so we can add and remove it much easier
	private ArrayList<SortVisualtionPanel> vsPanel;
	
	// the filler is used for the vertical space between the visualisation panels
	private ArrayList<Component> filler;
	private String title;
	
	
	private JButton next, newSort, nextStep, reset;
	private JPanel content;
	private Controller controller;
	private JLabel info;
	private JComboBox<String> sortChooser;
	private JMenuItem about, list, delay, manual,report;
	private JRadioButtonMenuItem de, en, fr;
	private JMenu help, settings, languages;
	private JToolBar toolBar;

	public Window(Controller controller, String title, int width, int height) {

		JMenuBar menuBar;
		ButtonGroup bg = new ButtonGroup();
		filler = new ArrayList<Component>();
		this.title = title;
		this.controller = controller;
		
		/*
		 *  the respective title for the components will be loaded from the xml-language definitions files
		 *  The language depends on which language was last used. The default language is english.
		 *  This routine can be seen on every initalized component
		 */
		
		info = new JLabel(Statics.getNamebyXml(Statics.COMPONENT_TITLE.INFO),
				JLabel.CENTER);
		info.setFont(Statics.getDefaultFont(30f));
		info.setForeground(Color.GRAY);

		setTitle(title);
		setFont(componentFont);
		setSize(width, height);
		vsPanel = new ArrayList<SortVisualtionPanel>();
		addWindowListener(controller);

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setRollover(true);

		menuBar = new JMenuBar();

		languages = new JMenu(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.LANG));
		languages.setFont(componentFont);
		settings = new JMenu(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.SETTINGS));
		settings.setFont(componentFont);
		help = new JMenu(Statics.getNamebyXml(Statics.COMPONENT_TITLE.HELP));
		help.setFont(componentFont);
		list = new JMenuItem(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.SORTLIST));
		list.addActionListener(controller);
		list.setActionCommand(Statics.NEW_ELEMENTS);
		list.setFont(componentFont);
		
		delay = new JMenuItem(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.DELAY));
		delay.addActionListener(controller);
		delay.setActionCommand(Statics.DELAY);
		delay.setFont(componentFont);

		about = new JMenuItem(Statics.getNamebyXml(
				Statics.COMPONENT_TITLE.ABOUT).concat(" ").concat(title));
		about.addActionListener(controller);
		about.setActionCommand(Statics.INFO);
		about.setFont(componentFont);

		manual = new JMenuItem(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.MANUAL));
		manual.addActionListener(controller);
		manual.setActionCommand(Statics.MANUAL);
		manual.setFont(componentFont);
		
		report = new JMenuItem(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.REPORT));
		
		report.setFont(componentFont);
		manual.addActionListener(controller);
		manual.setActionCommand(Statics.REPORT);

		de = new JRadioButtonMenuItem("German");
		de.addActionListener(controller);
		de.setActionCommand(Statics.LANG_DE);
		de.setFont(componentFont);

		en = new JRadioButtonMenuItem("English");
		en.addActionListener(controller);
		en.setActionCommand(Statics.LANG_EN);
		en.setFont(componentFont);

		fr = new JRadioButtonMenuItem("France");
		fr.addActionListener(controller);
		fr.setActionCommand(Statics.LANG_FR);
		fr.setFont(componentFont);

		bg.add(de);
		bg.add(en);
		bg.add(fr);

		String tmp = Statics.getLanguageSet();
		if (tmp.equals(de.getText()))
			de.setSelected(true);
		else if (tmp.equals(en.getText()))
			en.setSelected(true);
		else if (tmp.equals(fr.getText()))
			fr.setSelected(true);

		languages.add(en);
		languages.add(de);
		languages.add(fr);

		help.add(manual);
		help.add(report);
		help.add(about);
		settings.add(list);
		settings.add(languages);
		settings.add(delay);

		menuBar.add(settings);
		menuBar.add(help);
		setJMenuBar(menuBar);

		sortChooser = new JComboBox<String>(Statics.SORT_ALGORITHMNS);
		sortChooser.setFont(componentFont);
		content = new JPanel();
		content.setLayout(new BorderLayout());

		next = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.STARTANI));
		next.addActionListener(controller);
		next.setActionCommand(Statics.START);
		next.setFont(componentFont);

		newSort = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.ADD_SORT));
		newSort.addActionListener(controller);
		newSort.setActionCommand(Statics.ADD_SORT);
		newSort.setFont(componentFont);

		nextStep = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.ITERATION));
		nextStep.addActionListener(controller);
		nextStep.setActionCommand(Statics.NEXT_ITERATION);
		nextStep.setFont(componentFont);

		reset = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.RESET));
		reset.addActionListener(controller);
		reset.setActionCommand(Statics.RESET);
		reset.setFont(componentFont);

		content.add(BorderLayout.CENTER, info);

		toolBar.add(next);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(nextStep);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(reset);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(newSort);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(sortChooser);

		reset.setEnabled(false);
		next.setEnabled(false);
		nextStep.setEnabled(false);

		java.net.URL helpURL = Window.class.getClassLoader().getResource(
				"resources/frameIcon2.png");
		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}
		setLayout(new BorderLayout());
		add(BorderLayout.PAGE_START, toolBar);
		add(BorderLayout.CENTER, content);
		// setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	
	  // if the language was changed, component titles will be updated by this method
	  public void updateLanguage(){
	  
		  info.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.INFO));
	  
		  if (next.getText().equals(Statics.getNamebyXml(Statics.COMPONENT_TITLE.STARTANI)))
			  next.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.STOPANI));
	  
		  else 
			  next.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.STARTANI));
	  
		  nextStep.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.ITERATION));
		  newSort.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.ADD_SORT));
		  reset.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.RESET));
		  about.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.ABOUT));
		  list.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.SORTLIST));
		  delay.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.DELAY));
		  manual.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.MANUAL));
		  help.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.HELP));
		  settings.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.SETTINGS));
	  	  languages.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.LANG)); 
	  	
	  }
	
	// start-stop functionality for the animation
	public void toggleStartStop() {

		if (next.getText().equals(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.STARTANI))) {
			next.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.STOPANI));
			newSort.setEnabled(false);
			reset.setEnabled(false);
		} else {
			next.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.STARTANI));
			newSort.setEnabled(true);
			reset.setEnabled(true);
		}
	}

	public void addNewSort(Sort sort, String selectedSort) {

		
		if (vsPanel.size() == 0) {
			content.remove(info);
			content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
			content.add(Box.createVerticalStrut(20));
			next.setEnabled(true);
			reset.setEnabled(true);
		}
		final SortVisualtionPanel temp = new SortVisualtionPanel(controller,
				selectedSort, this.getWidth(), this.getHeight());
		
		temp.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (Sort.isStopped() && e.getButton() == 3) temp.showPopUpMenu(e.getX(), e.getY());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		sort.setSortVisualtionPanel(temp);
		vsPanel.add(temp);
		content.add(temp);
		
		Component c = Box.createVerticalStrut(50);
		filler.add(c);
		content.add(c);
		revalidate();


	}
	
	public void appReleased(){
		
		this.setTitle(title);
	}
	
	public void appStopped(){
		this.setTitle(title.concat(" - Stopped"));
	}

	public String getSelectedSort() {

		return (String) sortChooser.getSelectedItem();
	}

	public void unlockManualIteration(boolean lock) {

		nextStep.setEnabled(lock);
	}

	public void removeSort(int index) {

		content.remove(filler.get(index));
		content.remove(vsPanel.get(index));
		vsPanel.remove(index);
		filler.remove(index);

		if (vsPanel.size() == 0) {

			content.removeAll();
			content.setLayout(new BorderLayout());
			content.add(info);
			content.repaint();
			next.setEnabled(false);
			reset.setEnabled(false);
			nextStep.setEnabled(false);
		}
		
		
		
		for (int i = 0; i < vsPanel.size(); i++) {
			if (i != index)
				vsPanel.get(i).updatePanelSize();
		}

		revalidate();
		repaint();
		
	}
	
	
	public static void initDefaultFont(String source, float size) {


		try {

			InputStream in = Statics.class.getResourceAsStream(source);
			componentFont = Font.createFont(Font.TRUETYPE_FONT,in);
			componentFont = componentFont.deriveFont(size);
		

		} catch (IOException e) {
			componentFont = new Font("Monospace", Font.BOLD, 13);
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			componentFont = new Font("Monospace", Font.BOLD, 13);
		}
	

}

	// TODO: use argument parameters for setting language
	public static void main(String[] args) {

		// set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

		// define resources
		Statics.initXMLDefintions();
		Statics.loadConfig("/resources/config.xml");
		Statics.readLang("/resources/lang_de.xml", "German");
		
		// this font is used under the GPL from google fonts under 'Oxygen'
		Statics.initDefaultFont("/resources/OxygenFont/Oxygen-Regular.ttf");
		// this font is used under the GPL from google fonts under 'OpenSans'
		Window.initDefaultFont("/resources/OpenSans/OpenSans-Semibold.ttf",13f);
		
		// init view and controller
		Controller controller = new Controller();
		Window window = new Window(controller,"Visual Sorting - ".concat(Statics.getVersion()), 800, 550);
		controller.setView(window);

	}

}
