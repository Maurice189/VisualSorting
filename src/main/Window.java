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
 * <h3>Used Design Patterns</h3></br>
 * <ul>
 * 		<li>Model-<b>View</b>-Controller</br></li>
 * </ul>
 * </br><h3>Abstract</h3></br>
 * This class represents, as the name implies, the view(GUI) in the MVC pattern.
 * 
 * @author Maurice Koch
 * @category MVC
 * @version BETA
 * 
 * 
 * 
 * 
 */



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import algorithms.Sort;
import dialogs.InfoDialog;
import dialogs.OptionDialog;
import main.Statics.SORTALGORITHMS;




public class Window extends JFrame {

	private static Font componentFont = new Font("Monospace", Font.BOLD, 13),
						     infoFont = new Font("Monospace", Font.BOLD, 43);
	
	
	private String title;
	private boolean stateStButton = true;
	private LanguageFileXML langXML;
	
	// component references
	private JButton next, newSort, nextStep, reset,delayBtn,listBtn;
	private JPanel content;
	private Controller controller;
	private JLabel info,clock,nofLabel;
	private JComboBox<String> sortChooser;
	private JMenuItem about, list, delay;
	private JRadioButtonMenuItem de, en, fr;
	private JCheckBoxMenuItem switchIntPause;
	private JMenu help, settings, languages, programmFunctions;
	private JToolBar toolBar;
	private JPanel bottomBar;
	
	// we store the visualization panels dynamically, so we can add and remove it much easier
	private ArrayList<SortVisualtionPanel> vsPanel;
	
	// the filler is used for the vertical space between the visualization panels
	private ArrayList<Component> filler;

	public Window(Controller controller,LanguageFileXML langXML, String title, int width, int height) {

		
		filler = new ArrayList<Component>();
		this.title = title;
		this.controller = controller;
		this.langXML = langXML;
	
		initComponents(width,height);
		


	}
	/*
	 * initialize all frame components 
	 * 
	 */
	private void initComponents( int width, int height){
		
		Font font = componentFont.deriveFont(13f);
		JMenuBar menuBar;
		ButtonGroup bg = new ButtonGroup();
		
		/*
		 *  the respective title for the components will be loaded from the xml-language definitions files
		 *  The language depends on which language was last used. The default language is English.
		 *  This routine can be seen on every initialized component
		 */
		
		info = new JLabel(langXML.getValue("info"),
				JLabel.CENTER);
		info.setFont(infoFont);
		info.setForeground(Color.GRAY);
		
		clock = new JLabel();
		clock.setFont(font);
		clock.setIcon(new ImageIcon(Statics.class.getResource("/resources/stop_watch_icon2.png")));
		
		switchIntPause = new JCheckBoxMenuItem(langXML.getValue("autopause"));
		switchIntPause.addActionListener(controller);
		switchIntPause.setActionCommand(Statics.AUTO_PAUSE);
		switchIntPause.setState(InternalConfig.isAutoPauseEnabled());
		switchIntPause.setFont(font);
		
		programmFunctions = new JMenu(langXML.getValue("prgfunc"));
		programmFunctions.setFont(font);
		programmFunctions.add(switchIntPause);
			
		nofLabel = new JLabel();
		nofLabel.setFont(font);
		
		setTitle(title);
		setFont(font);
		setSize(width, height);
		vsPanel = new ArrayList<SortVisualtionPanel>();
		addWindowListener(controller);

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		menuBar = new JMenuBar();
		
		Border border = BorderFactory.createEtchedBorder();
		Border margin = new EmptyBorder(5,1,5,1);
		toolBar.setBorder(new CompoundBorder(border, margin));
		
		bottomBar = new JPanel();
		bottomBar.setLayout(new BoxLayout(bottomBar,BoxLayout.X_AXIS));
		bottomBar.setBorder(BorderFactory.createEtchedBorder());
		bottomBar.add(clock);
		bottomBar.add(Box.createHorizontalGlue());
		bottomBar.add(nofLabel);

		languages = new JMenu(
				langXML.getValue("lang"));
		languages.setFont(font);
		settings = new JMenu(
				langXML.getValue("settings"));
		settings.setFont(font);
		help = new JMenu(langXML.getValue("help"));
		help.setFont(font);
		list = new JMenuItem(
				langXML.getValue("sortlist"));
		list.addActionListener(controller);
		list.setActionCommand(Statics.NEW_ELEMENTS);
		list.setFont(font);
		
		delay = new JMenuItem(
				langXML.getValue("delay"));
		delay.addActionListener(controller);
		delay.setActionCommand(Statics.DELAY);
		delay.setFont(font);

		about = new JMenuItem(langXML.getValue("about").concat(" ").concat(title));
		
		about.addActionListener(controller);
		about.setActionCommand(Statics.ABOUT);
		about.setFont(font);

		de = new JRadioButtonMenuItem("German");
		de.addActionListener(controller);
		de.setActionCommand(Statics.LANG_DE);
		de.setFont(font);

		en = new JRadioButtonMenuItem("English");
		en.addActionListener(controller);
		en.setActionCommand(Statics.LANG_EN);
		en.setFont(font);

		fr = new JRadioButtonMenuItem("France");
		fr.addActionListener(controller);
		fr.setActionCommand(Statics.LANG_FR);
		fr.setFont(font);

		bg.add(de);
		bg.add(en);
		bg.add(fr);

		String tmp = InternalConfig.getLanguageSet();
		if (tmp.equals("lang_de.xml"))
			de.setSelected(true);
		else if (tmp.equals("lang_en.xml"))
			en.setSelected(true);
		else if (tmp.equals("lang_fr.xml"))
			fr.setSelected(true);

		languages.add(en);
		languages.add(de);
		languages.add(fr);

		help.add(about);
		settings.add(programmFunctions);
		settings.add(languages);
		settings.add(list);
		settings.add(delay);
		

		menuBar.add(settings);
		menuBar.add(help);
		setJMenuBar(menuBar);

		String names[] = new String[SORTALGORITHMS.length()];
		for(int i = 0; i<SORTALGORITHMS.length(); i++) 
			names[i] = SORTALGORITHMS.values()[i].toString();
		
		sortChooser = new JComboBox<String>(names);
		sortChooser.setFont(font);
		sortChooser.setMaximumSize(new Dimension(230, 30));
		
		content = new JPanel();
		content.setLayout(new BorderLayout());

		next = new JButton(); 
		next.addActionListener(controller);
		next.setActionCommand(Statics.START);
		next.setBorder(BorderFactory.createEmptyBorder());
		next.setIcon(new ImageIcon(Statics.class.getResource("/resources/start_visualsort_1.png")));
		next.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/start_visualsort_rollover_1.png")));
		
		newSort = new JButton(); 
		newSort.addActionListener(controller);
		newSort.setActionCommand(Statics.ADD_SORT);
		
		newSort.setBorder(BorderFactory.createEmptyBorder());
		newSort.setIcon(new ImageIcon(Statics.class.getResource("/resources/add_visualsort_1.png")));
		newSort.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/add_visualsort_rollover_1.png")));
		
		
		delayBtn = new JButton();
		delayBtn.addActionListener(controller);
		delayBtn.setActionCommand(Statics.DELAY);
		
		delayBtn.setBorder(BorderFactory.createEmptyBorder());
		delayBtn.setIcon(new ImageIcon(Statics.class.getResource("/resources/delay_visualsort_1.png")));
		delayBtn.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/delay_visualsort_rollover_1.png")));
		
		listBtn = new JButton();
		listBtn.addActionListener(controller);
		listBtn.setActionCommand(Statics.NEW_ELEMENTS);
		
		listBtn.setBorder(BorderFactory.createEmptyBorder());
		listBtn.setIcon(new ImageIcon(Statics.class.getResource("/resources/elements_visualsort_1.png")));
		listBtn.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/elements_visualsort_rollover_1.png")));
		
		
		nextStep = new JButton(); 
		nextStep.addActionListener(controller);
		nextStep.setActionCommand(Statics.NEXT_ITERATION);
		
		nextStep.setBorder(BorderFactory.createEmptyBorder());
		nextStep.setIcon(new ImageIcon(Statics.class.getResource("/resources/nextIter_visualsort_1.png")));
		nextStep.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/nextIter_visualsort_rollover_1.png")));
		
		
		reset = new JButton(); //Statics.getNamebyXml(Statics.COMPONENT_TITLE.RESET)
		reset.addActionListener(controller);
		reset.setActionCommand(Statics.RESET);
		
		reset.setBorder(BorderFactory.createEmptyBorder());
		reset.setIcon(new ImageIcon(Statics.class.getResource("/resources/reset_visualsort_1.png")));
		reset.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/reset_visualsort_rollover_1.png")));

		content.add(BorderLayout.CENTER, info);
		
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		Dimension size = new Dimension(
		    separator.getPreferredSize().width,
		    separator.getMaximumSize().height);
		separator.setMaximumSize(size);

		toolBar.add(Box.createHorizontalStrut(3));
		toolBar.add(next);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(reset);
		toolBar.add(Box.createHorizontalStrut(10));
		toolBar.add(separator);
		toolBar.add(Box.createHorizontalStrut(10));
		toolBar.add(nextStep);
		toolBar.add(Box.createHorizontalStrut(10));
		toolBar.add(delayBtn);
		toolBar.add(Box.createHorizontalStrut(10));
		toolBar.add(listBtn);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(newSort);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(sortChooser);
		toolBar.add(Box.createHorizontalStrut(3));

		reset.setEnabled(false);
		next.setEnabled(false);
		nextStep.setEnabled(false);

		java.net.URL helpURL = Window.class.getClassLoader().getResource(
				"resources/frameIcon2.png");
		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		add(toolBar);
		add(content);
		add(bottomBar);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
	  /*
	   *  if the language was changed, component titles will be updated by this method
	   */
	  public void updateLanguage(){
	  
		  info.setText(langXML.getValue("info"));
		  switchIntPause.setText(langXML.getValue("autopause"));
		  programmFunctions.setText(langXML.getValue("prgfunc"));
		  about.setText(langXML.getValue("about"));
		  list.setText(langXML.getValue("sortlist"));
		  delay.setText(langXML.getValue("delay"));
		  help.setText(langXML.getValue("help"));
		  settings.setText(langXML.getValue("settings"));
	  	  languages.setText(langXML.getValue("lang"));
	  	  nofLabel.setText(String.valueOf(Sort.getElements().length).concat(" ").concat
	  	  (langXML.getValue("nof")));
	  	
	  }
	  
	/*
	 * start-stop functionality for the animation
	 */
	public void toggleStartStop() {
		
		
		if(stateStButton){
			next.setIcon(new ImageIcon(Statics.class.getResource("/resources/pause_visualsort_1.png")));
			next.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/pause_visualsort_rollover_1.png")));
			reset.setEnabled(false);
		}
		else{
			next.setIcon(new ImageIcon(Statics.class.getResource("/resources/start_visualsort_1.png")));
			next.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/start_visualsort_rollover_1.png")));
			reset.setEnabled(true);
		}
		
		stateStButton = !stateStButton;
		
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
		this.setTitle(title.concat(" - Paused"));
	}

	public String getSelectedSort() {

		return (String) sortChooser.getSelectedItem();
	}

	public void unlockManualIteration(boolean lock) {

		nextStep.setEnabled(lock);
		
	}
	
	public void unlockAddSort(boolean lock){
		
		newSort.setEnabled(lock);

	}

	public void removeSort(int index) {

		content.remove(filler.get(index));
		content.remove(vsPanel.get(index));
		vsPanel.remove(index);
		filler.remove(index);

		// remove all components and show launching message
		if (vsPanel.size() == 0) {

			content.removeAll();
			content.setLayout(new BorderLayout());
			content.add(info);
			content.repaint();
			next.setEnabled(false);
			reset.setEnabled(false);
			nextStep.setEnabled(false);
			newSort.setEnabled(true);
		}
		
		
		/*
		 *  signal that parameters for 'SortVisualisationPanel' objects to 
		 *  adjust their bar width
		 */
		for (int i = 0; i < vsPanel.size(); i++) {
			if (i != index)
				vsPanel.get(i).updatePanelSize();
		}

		// show results on frame
		revalidate();
		repaint();
		
	}
	
	
	// we use another component font than monospace
	public static void setComponentFont(String source) {
		
		try {

			InputStream in = Window.class.getResourceAsStream(source);
			componentFont = Font.createFont(Font.TRUETYPE_FONT,in);			

		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	
	}
	
	public static Font getComponentFont(float size){
		return componentFont.deriveFont(size);
	}
	
	// font for the launching message
	public static void setInfoFont(String source, float size) {
		try {

			InputStream in = Window.class.getResourceAsStream(source);
			infoFont = Font.createFont(Font.TRUETYPE_FONT,in);
			infoFont = infoFont.deriveFont(size);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void updateNumberOfElements(int nof){
		
		nofLabel.setText(String.valueOf(nof).concat(" ").concat(langXML.getValue("nof")));
		
	}
	
	public void setClockParam(int sec, int msec){
		
		String smsec,ssec;
		
		if(msec < 10) smsec = "00".concat(String.valueOf(msec));
		else if(msec < 100) smsec = "0".concat(String.valueOf(msec));
		else smsec = String.valueOf(msec);
		
		if(sec < 10) ssec = "0".concat(String.valueOf(sec));
		else ssec = String.valueOf(sec);
		
		
		clock.setText(ssec.concat("s : ").concat(smsec).concat("ms"));
		
	}

	// TODO: use argument parameters for setting language
	public static void main(String[] args) {
		/*
		// set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}*/
		
		LanguageFileXML configLanguage = new LanguageFileXML();
		InternalConfig.loadConfigFile();
		configLanguage.readXML("/resources/".concat(InternalConfig.getLanguageSet()));
		
		// define resources
		OptionDialog.setLanguageFileXML(configLanguage);
		SortVisualtionPanel.setLanguageFileXML(configLanguage);
		
		// this font is used under the GPL from google fonts under 'OpenSans'
		Window.setComponentFont("/resources/Fonts/OpenSans-Regular.ttf");
		Window.setInfoFont("/resources/Fonts/Oxygen-Regular.ttf",30f);
		
		// hashmap for resolving sort into the respective infopage file
		HashMap<SORTALGORITHMS,String> map = new HashMap<SORTALGORITHMS,String>();
		map.put(SORTALGORITHMS.Bitonicsort, "infopage_bitonicsort.html");
		map.put(SORTALGORITHMS.BTS, "infopage_btssort.html");
		map.put(SORTALGORITHMS.Bubblesort, "infopage_bubblesort.html");
		map.put(SORTALGORITHMS.Combsort, "infopage_combsort.html");
		map.put(SORTALGORITHMS.Gnomesort, "infopage_gnomesort.html");
		map.put(SORTALGORITHMS.Heapsort, "infopage_heapsort.html");
		map.put(SORTALGORITHMS.Insertionsort, "infopage_insertionsort.html");
		map.put(SORTALGORITHMS.Mergesort, "infopage_mergesort.html");
		map.put(SORTALGORITHMS.Quicksort, "infopage_quicksort.html");
		map.put(SORTALGORITHMS.Radixsort, "infopage_radixsort.html");
		map.put(SORTALGORITHMS.Shakersort, "infopage_shakersort.html");
		map.put(SORTALGORITHMS.Shellsort, "infopage_shellsort.html");
		map.put(SORTALGORITHMS.Bogosort, "infopage_bogosort.html");
		map.put(SORTALGORITHMS.Introsort, "infopage_introsort.html");
		InfoDialog.initInfoPageResolver(map);
		
		
		// init. view and controller
		Controller controller = new Controller(configLanguage);
		Window window = new Window(controller,configLanguage,"Visual Sorting - ".concat(InternalConfig.getVersion()), 800, 550);
		controller.setView(window);
	

	}
	
	

}
