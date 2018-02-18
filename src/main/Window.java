package main;

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


import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import algorithms.Sort;
import com.bulenkov.iconloader.IconLoader;
import dialogs.InfoDialog;
import main.Statics.SortAlgorithm;


/**
 * 
 * @author maurice
 *
 */
public class Window extends JFrame {

	private static Font componentFont = new Font("Monospace", Font.BOLD, 13);
	private static Font infoFont = new Font("Monospace", Font.BOLD, 43);

	private JLabel algorithmInfo;
	
	private String title;
	private boolean stateStButton = true;

	// component references
	private JButton newSort, nextStep, reset,delayBtn,listBtn;
	private PlayPauseToggle next;
	private JPanel content;
	private Controller controller;
	private JLabel info,clock,nofLabel;
	private GroupComboBox sortChooser;
	private JMenuItem about, list, delay;
	private JCheckBoxMenuItem switchIntPause;
	private JMenu help, settings, programmFunctions;
	private JToolBar toolBar;
	private JPanel bottomBar;
	private JSlider speed;

	// we store the visualization panels dynamically, so we can add and remove it much easier
	private List<SortVisualisationPanel> vsPanel;

	private HashMap<String, Statics.SortAlgorithm> titleToAlg;
    private HashMap<Statics.SortAlgorithm, String> algToTitle;
	
	
	// FIXME : BAD the filler is used for the vertical space between the visualization panels
	// the filler is used for the vertical space between the visualization panels
	private List<Component> filler = new LinkedList<Component>();

	/**
	 * 
	 * Initalizes the View with all needed
	 * parameters.
	 * 
	 * @param controller as MVC pattern arranged
	 * @param title of the application window
	 * @param width of the applications window
	 * @param height of the applications window
	 */
	public Window(Controller controller, String title, int width, int height) {

		this.title = title;
		this.controller = controller;

		initComponents(width,height);

	}
	/**
	 * Just a wrapper method for
	 * creating the main application window.
	 * 
	 * 
	 * @param width of the applications window
	 * @param height of the applications window
	 */
	private void initComponents( int width, int height){
		
		JMenuBar menuBar;
		ButtonGroup bg = new ButtonGroup();
		
		/*
		 *  the respective title for the components will be loaded from the xml-language definitions files
		 *  The language depends on which language was last used. The default language is English.
		 *  This routine can be seen on every initialized component
		 */
		info = new JLabel("Add some sort algorithm",
				JLabel.CENTER);
		info.setFont(infoFont);
		info.setForeground(Color.GRAY);

        clock = new JLabel();
        clock.setToolTipText("Execution time.");
		clock.setForeground(Color.black);
		clock.setIcon(IconLoader.getIcon("/resources/icons/timer.png"));
		
		switchIntPause = new JCheckBoxMenuItem("Automatic pause enabled");
		switchIntPause.addActionListener(controller);
		switchIntPause.setActionCommand(Statics.AUTO_PAUSE);
		switchIntPause.setState(InternalConfig.isAutoPauseEnabled());
		
		programmFunctions = new JMenu("Options");
		programmFunctions.add(switchIntPause);
			
		nofLabel = new JLabel();
		nofLabel.setToolTipText("Number of elements in list.");
		nofLabel.setForeground(Color.black);
		
		setTitle(title);
		setSize(width, height);
		vsPanel = new ArrayList<>();
		addWindowListener(controller);

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		menuBar = new JMenuBar();
		
		Border border = BorderFactory.createEtchedBorder();
		Border margin = new EmptyBorder(5,1,10,1);
		toolBar.setBorder(new CompoundBorder(border, margin));
		
		bottomBar = new JPanel();
		bottomBar.setLayout(new BoxLayout(bottomBar,BoxLayout.X_AXIS));
		bottomBar.setBackground(bottomBar.getBackground().darker());
		bottomBar.setBorder(BorderFactory.createEtchedBorder());
		bottomBar.add(clock);
		bottomBar.add(Box.createHorizontalGlue());
		bottomBar.add(nofLabel);

		settings = new JMenu("Settings");
		help = new JMenu("Help");
		list = new JMenuItem("List of elements");
		list.addActionListener(controller);
		list.setActionCommand(Statics.NEW_ELEMENTS);

		delay = new JMenuItem("Speed");
		delay.addActionListener(controller);
		delay.setActionCommand(Statics.DELAY);

		about = new JMenuItem("About " + title);
		
		about.addActionListener(controller);
		about.setActionCommand(Statics.ABOUT);

		help.add(about);
		settings.add(programmFunctions);
		settings.add(list);
		settings.add(delay);
		

		menuBar.add(settings);
		menuBar.add(help);
		setJMenuBar(menuBar);

		String names[] = new String[SortAlgorithm.length()];
		for(int i = 0; i<SortAlgorithm.length(); i++){
			names[i] = SortAlgorithm.values()[i].toString();
		}

        titleToAlg = new HashMap<>();
		titleToAlg.put("Bogo sort", SortAlgorithm.Bogosort);
        titleToAlg.put("Bubble sort", SortAlgorithm.Bubblesort);
        titleToAlg.put("Comb sort", SortAlgorithm.Combsort);
        titleToAlg.put("Merge sort", SortAlgorithm.Mergesort);
        titleToAlg.put("Insertion sort", SortAlgorithm.Insertionsort);
        titleToAlg.put("Intro sort", SortAlgorithm.Introsort);
        titleToAlg.put("Shaker sort", SortAlgorithm.Shakersort);
        titleToAlg.put("Selection sort", SortAlgorithm.Selectionsort);
        titleToAlg.put("Shell sort", SortAlgorithm.Shellsort);
        titleToAlg.put("Heap sort", SortAlgorithm.Heapsort);
        titleToAlg.put("Quick sort (Random Pivot)", SortAlgorithm.Quicksort_RANDOM);
        titleToAlg.put("Quick sort (MO3 Pivot)", SortAlgorithm.Quicksort_MO3);
        titleToAlg.put("Quick sort (Fixed Pivot)", SortAlgorithm.Quicksort_FIXED);
        //titleToAlg.put("Dual Pivot Quicksort", SortAlgorithm.Quicksort_RANDOM); // TODO Add Dual Pivot Method

        algToTitle = new HashMap<>();
        algToTitle.put(SortAlgorithm.Bogosort, "Bogo sort");
        algToTitle.put(SortAlgorithm.Bubblesort, "Bubble sort");
        algToTitle.put(SortAlgorithm.Combsort, "Comb sort");
        algToTitle.put(SortAlgorithm.Mergesort, "Merge sort");
        algToTitle.put(SortAlgorithm.Insertionsort, "Insertion sort");
        algToTitle.put(SortAlgorithm.Introsort, "Intro sort");
        algToTitle.put(SortAlgorithm.Shakersort, "Shaker sort");
        algToTitle.put(SortAlgorithm.Selectionsort, "Selection sort");
        algToTitle.put(SortAlgorithm.Shellsort, "Shell sort");
        algToTitle.put(SortAlgorithm.Heapsort, "Heap sort");
        algToTitle.put(SortAlgorithm.Quicksort_RANDOM, "Quick sort (Random Pivot)");
        algToTitle.put(SortAlgorithm.Quicksort_MO3, "Quick sort (MO3 Pivot)");
        algToTitle.put(SortAlgorithm.Quicksort_FIXED, "Quick sort (Fixed Pivot)");
        //algToTitle.put(SortAlgorithm.Quicksort_RANDOM, "Dual Pivot Quicksort"); // TODO Add Dual Pivot Method

        InfoDialog.initTitleResolver(algToTitle);

        algorithmInfo = new JLabel();
        algorithmInfo.setForeground(Color.GRAY);

        sortChooser = new GroupComboBox();

        sortChooser.addItemListener(itemEvent -> {
            String item = sortChooser.getSelectedItem().toString();

            if(item.equals("Bogo sort")) {
                algorithmInfo.setText("<html><b>Bogo sort</b> - Generate random permutations until it finds on that sorted.</html>");
            } else if (item.equals("Bubble sort")) {
                algorithmInfo.setText("<html><b>Bubble sort</b> - Compare elements pairwise and perform swaps if necessary.</html>\"");
            } else if (item.equals("Comb sort")) {
                algorithmInfo.setText("<html><b>Comb sort</b> - Improvement of bubble sort with dynamic gap sizes.</html>\"");
            } else if (item.equals("Merge sort")) {
                algorithmInfo.setText("<html><b>Merge sort</b> - Divide unsorted lists and repeatedly merge sorted sub-lists.</html>\"");
            } else if (item.equals("Insertion sort")) {
                algorithmInfo.setText("<html><b>Insertion sort</b> - In each iterations finds correct insert position of element</html>\"");
            } else if (item.equals("Intro sort")) {
                algorithmInfo.setText("<html><b>Intro sort</b> - Hybrid sorting algorithm uses both quick sort and Heapsort.</html>\"");
            } else if (item.equals("Shaker sort")) {
                algorithmInfo.setText("<html><b>Shaker sort</b> - Bidirectional bubble sort, also known as cocktail sort.</html>\"");
            } else if (item.equals("Selection sort")) {
                algorithmInfo.setText("<html><b>Selection sort</b> - Repeatedly finds the minimum element and append to sorted list.</html>\"");
            } else if (item.equals("Shell sort")) {
                algorithmInfo.setText("<html><b>Shell sort</b> - Generalization of insertion or bubble sort.</html>\"");
            }  else if (item.equals("Heap sort")) {
                algorithmInfo.setText("<html><b>Heap sort</b> - Builds a heap and repeatedly extracts the current maximum.</html>\"");
            } else if (item.equals("Quick sort (Fixed Pivot)")) {
                algorithmInfo.setText("<html><b>Quick sort</b> - Partitioning of lists by choosing a fixed pivot element.</html>\"");
            } else if (item.equals("Quick sort (Random Pivot)")) {
                algorithmInfo.setText("<html><b>Quick sort</b> - Partitioning of lists by choosing a random pivot element.</html>\"");
            } else if (item.equals("Quick sort (MO3 Pivot)")) {
                algorithmInfo.setText("<html><b>Quick sort</b> - Partitioning of lists by choosing a pivot element based on median of three.</html>\"");
            } else if (item.equals("Dual Pivot Quick sort")) {
                algorithmInfo.setText("<html><b>Quick sort</b> - Variant of quick sort with two pivot elements.</html>\"");
            } else {
                algorithmInfo.setText("-");
            }

            int prefWidth = algorithmInfo.getPreferredSize().width;
            algorithmInfo.setMaximumSize(new Dimension(prefWidth, 70));

        });

        //sortChooser.addItem("Bogo sort");
        sortChooser.addItem("Bubble sort");
        sortChooser.addItem("Comb sort");
        sortChooser.addItem("Merge sort");
        sortChooser.addItem("Insertion sort");
        sortChooser.addItem("Intro sort");
        sortChooser.addItem("Selection sort");
        sortChooser.addItem("Shaker sort");
        sortChooser.addItem("Shell sort");
        sortChooser.addItem("Heap sort");
        sortChooser.addDelimiter("Quick sort");
        sortChooser.addItem("Quick sort (Fixed Pivot)");
        sortChooser.addItem("Quick sort (Random Pivot)");
        sortChooser.addItem("Quick sort (MO3 Pivot)");


		sortChooser.setMaximumSize(new Dimension(300, 100));

        content = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0,
                        getBackground(), 0, getHeight(),
                        getBackground().darker());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

        };

		content.setLayout(new BorderLayout());

		next = new PlayPauseToggle();
		next.setPlayIcon("/resources/icons/start.png");
        next.setPauseIcon("/resources/icons/pause.png");
        next.setState(PlayPauseToggle.State.PLAY);
		next.setToolTipText("Start/Pause sorting process.");
		next.addActionListener(controller);
		next.setActionCommand(Statics.START);

		
		newSort = new VSButton("/resources/icons/add.png");
		newSort.setToolTipText("Add selected sort algorithm.");
		newSort.addActionListener(controller);
		newSort.setActionCommand(Statics.ADD_SORT);

        speed = new JSlider(0, 300, 50);

        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer( 0 ), new JLabel("Slow"));
        labelTable.put(new Integer( 300 ), new JLabel("Fast"));

		delayBtn = new VSButton("/resources/icons/speed.png");
		delayBtn.setToolTipText("Adjust the sorting speed.");
		delayBtn.addActionListener(controller);
		delayBtn.setActionCommand(Statics.DELAY);

		listBtn = new VSButton("/resources/icons/elements.png");
		listBtn.setToolTipText("Edit elements in list.");
		listBtn.addActionListener(controller);
		listBtn.setActionCommand(Statics.NEW_ELEMENTS);

		
		nextStep = new VSButton("/resources/icons/next_instruction.png");
		nextStep.setToolTipText("Execute next instruction.");
		nextStep.addActionListener(controller);
		nextStep.setActionCommand(Statics.NEXT_ITERATION);
		
		reset = new VSButton("/resources/icons/reset.png"); //Statics.getNamebyXml(Statics.COMPONENT_TITLE.RESET)
        reset.setToolTipText("Reset to unsorted state.");
		reset.addActionListener(controller);
		reset.setActionCommand(Statics.RESET);

		content.add(BorderLayout.CENTER, info);
		
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		Dimension size = new Dimension(separator.getPreferredSize().width,
					       separator.getMaximumSize().height);
		separator.setMaximumSize(size);


		algorithmInfo.setToolTipText("Short description of currently selected sort algorithm.");
        algorithmInfo.setIcon(new ImageIcon(Statics.class.getResource("/resources/icons/info_round.png")));
        algorithmInfo.setIconTextGap(10);
        //algorithmInfo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), new EmptyBorder(10, 10, 10, 10)));


		toolBar.add(Box.createHorizontalStrut(3));
		toolBar.add(next);
		toolBar.add(Box.createHorizontalStrut(15));
		toolBar.add(reset);
		toolBar.add(Box.createHorizontalStrut(15));
		toolBar.add(separator);
		toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(nextStep);
		toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(delayBtn);
		toolBar.add(Box.createHorizontalStrut(15));
		toolBar.add(listBtn);
        toolBar.add(Box.createHorizontalStrut(15));
		toolBar.add(Box.createHorizontalGlue());
        toolBar.add(algorithmInfo);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(Box.createHorizontalStrut(15));
		toolBar.add(newSort);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(sortChooser);
		toolBar.add(Box.createHorizontalStrut(3));

		reset.setEnabled(false);
		next.setEnabled(false);
		nextStep.setEnabled(false);

        java.net.URL icon = Window.class.getClassLoader().getResource(
                "resources/icons/icon.png");
		java.net.URL icon2x = Window.class.getClassLoader().getResource(
				"resources/icons/icon@2x.png");
        java.net.URL icon3x = Window.class.getClassLoader().getResource(
                "resources/icons/icon@3x.png");
        java.net.URL icon4x = Window.class.getClassLoader().getResource(
                "resources/icons/icon@4x.png");
        java.net.URL icon5x = Window.class.getClassLoader().getResource(
                "resources/icons/icon@5x.png");
        java.net.URL icon6x = Window.class.getClassLoader().getResource(
                "resources/icons/icon@6x.png");

        List<Image> icons = new ArrayList<Image>();

        if (icon != null) {
            icons.add(new ImageIcon(icon).getImage());
        }
		if (icon2x != null) {
			icons.add(new ImageIcon(icon2x).getImage());
		}
        if (icon3x != null) {
            icons.add(new ImageIcon(icon3x).getImage());
        }
        if (icon4x != null) {
            icons.add(new ImageIcon(icon4x).getImage());
        }
        if (icon5x != null) {
            icons.add(new ImageIcon(icon5x).getImage());
        }
        if (icon6x != null) {
            icons.add(new ImageIcon(icon6x).getImage());
        }

        setIconImages(icons);

		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		add(toolBar);
		add(content);
		add(bottomBar);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}


	/**
	 * Start-stop functionality for the visualisation
	 * progress.
	 */
	public void toggleStartStop() {
	    next.toggle();
		if(next.getState() == PlayPauseToggle.State.PAUSE){
			reset.setEnabled(false);
		} else {
			reset.setEnabled(true);
		}
	}
	
	/**
	 * Routine for adding a new sort algorithm 
	 * to the visualisation 
	 * 
	 * @param sort algorithm to be added
	 */
	public void addNewSort(Sort sort) {

		
		if (vsPanel.size() == 0) {
			content.remove(info);
			content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
			content.add(Box.createVerticalStrut(20));
			next.setEnabled(true);
			reset.setEnabled(true);
		}
		
		SortVisualisationPanel temp = new SortVisualisationPanel(this.getWidth(), this.getHeight());
		sort.setSortVisualisationPanel(temp,new PanelUI(controller,temp, algToTitle.get(sort.getAlgorithmName())));
		vsPanel.add(temp);
		content.add(temp);
		
		Component c = Box.createVerticalStrut(50);
		filler.add(c);
		content.add(c);
		revalidate();


	}
	/**
	 * This method updates the number of elements, by
	 * setting the component label, that is located
	 * on the bottom of the window.
	 * 
	 * @param nof number of elements to be sorted
	 */
	public void updateNumberOfElements(int nof){
		
		nofLabel.setText(String.valueOf(nof).concat(" ").concat("number of elements"));
		
	}

	/**
	 * This method updates the timer, by
	 * setting the component label, that is located
	 * on the bottom of the window. This timer checking
	 * how long the progress is taking place.
	 * 
	 * @param sec seconds of the timer
	 * @param msec milliseconds of the timer
	 */
	public void setClockParam(int sec, int msec){
		
		String smsec,ssec;
		
		if(msec < 10) smsec = "00".concat(String.valueOf(msec));
		else if(msec < 100) smsec = "0".concat(String.valueOf(msec));
		else smsec = String.valueOf(msec);
		
		if(sec < 10) ssec = "0".concat(String.valueOf(sec));
		else ssec = String.valueOf(sec);
		
		
		clock.setText(ssec.concat("s : ").concat(smsec).concat("ms"));
		
	}
	
	/**
	 * Alters the main application window's
	 * name, when realeasing the progress.
	 */
	public void appReleased(){
		this.setTitle(title);
	}
	/**
	 * Alters the main application window's
	 * name, when pausing the progress.
	 */
	public void appStopped(){
		this.setTitle(title.concat(" - Paused"));
	}

	/**
	 * 
	 * @return Returns the current selected sort,
	 * from the combobox (top of the application).
	 */
	public SortAlgorithm getSelectedSort() {
		return titleToAlg.get((String) sortChooser.getSelectedItem());
	}

    public String getSelectedSortByName() {
        return (String) sortChooser.getSelectedItem();
    }

	/**
	 * 
	 * @param lock Unlock or lock the manual iteration button
	 *  , that is responsible for executing a single 
	 *  iteration on all current pausing sort algorithms.
	 */
	public void unlockManualIteration(boolean lock) {

		nextStep.setEnabled(lock);
		listBtn.setEnabled(lock);
		
	}
	/**
	 * 
	 * @param lock Unlock or lock the button for 
	 * adding new sort algorithms.
	 */
	public void unlockAddSort(boolean lock){
		newSort.setEnabled(lock);

	}

	/**
	 * 
	 * @param index
	 */
	public void removeSort(int index) {

		content.remove(filler.get(index));
		content.remove(vsPanel.get(index));
		vsPanel.remove(index);
		filler.remove(index);

		// remove all components and show launching message
		if (vsPanel.isEmpty()) {

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
			if (i != index){
				vsPanel.get(i).updatePanelSize();
			}
		}

		// show results on frame
		revalidate();
		repaint();
		
	}
	
	/**
	 * 
	 * @param source
	 */
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
	/**
	 * 
	 * @param size
	 * @return
	 */
	public static Font getComponentFont(float size){
		return componentFont.deriveFont(size);
	}
	
	/**
	 * 
	 * @param source
	 * @param size
	 */
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
	
	
	

}
