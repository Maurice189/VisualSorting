package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import OptionDialogs.DelayDialog;
import OptionDialogs.EnterDialog;
import OptionDialogs.AboutDialog;
import OptionDialogs.InfoDialog;
import OptionDialogs.ManualDialog;
import OptionDialogs.OptionDialog;
import sorting_algorithms.BinaryTreeSort;
import sorting_algorithms.BitonicSort;
import sorting_algorithms.BubbleSort;
import sorting_algorithms.CombSort;
import sorting_algorithms.GnomeSort;
import sorting_algorithms.HeapSort;
import sorting_algorithms.InsertionSort;
import sorting_algorithms.MergeSort;
import sorting_algorithms.QuickSort;
import sorting_algorithms.RadixSort;
import sorting_algorithms.ShakerSort;
import sorting_algorithms.ShellSort;
import sorting_algorithms.Sort;

/**
 * @author Maurice Koch
 * @version BETA
 * 
 * 
 *          This class respresents, as the name implies, the controller in the
 *          MVC pattern. Moreover this class implements the observer pattern, so
 *          the controller can be informed, if a visualsation thread ends
 */


public class Controller implements Observer, ActionListener, WindowListener {

	private ArrayList<Sort> sortList; // dynamic storage for the sort algorithmns
	private LinkedList<OptionDialog> dialogs; // references to the open dialogs, like settings etc.

	private Window window;
	private ConfigXML langXMLInterface;

	private int runningThreads, vspIndex; // number of running sortthreads, index of current clicked vsp-panel

	private boolean byUserStopped = false; 
	private ExecutorService executor; // we use executor service, because it's more memory efficient

	public Controller(ConfigXML langXMLInterface, final int nofelements) {

		this.langXMLInterface = langXMLInterface;
		int[] elements = new int[nofelements];

		sortList = new ArrayList<Sort>();
		dialogs = new LinkedList<OptionDialog>();

		executor = Executors.newCachedThreadPool();

		// fill random numbers to the sort list
		for (int i = 0; i < nofelements; i++)
			elements[i] = Controller.getRandomNumber(0, nofelements/3);

		Sort.setElements(elements);

	}

	public ArrayList<Sort> getList() {
		return sortList;
		
	}

	public void setView(Window window) {

		this.window = window;
		SortVisualtionPanel.setBackgroundColor(window.getBackground());
	}

	public static int getRandomNumber(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == Statics.ADD_SORT) {

			Sort sort;
			String selectedSort;

			Sort.resume();
			for (Sort temp : sortList) {
				temp.initElements();
				runningThreads = 0;
			}

			selectedSort = window.getSelectedSort();

			if (selectedSort.equals(Statics.SORT_ALGORITHMNS[0]))
				sort = new HeapSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[1]))
				sort = new BubbleSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[2]))
				sort = new QuickSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[3]))
				sort = new BinaryTreeSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[4]))
				sort = new CombSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[5]))
				sort = new GnomeSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[6]))
				sort = new ShakerSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[7]))
				sort = new MergeSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[8]))
				sort = new BitonicSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[9]))
				sort = new RadixSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[10])) 
				sort = new ShellSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[11]))
				sort = new InsertionSort();
			else
				sort = new HeapSort();

			sort.addObserver(this);
			sortList.add(sort);
			window.addNewSort(sort, selectedSort);

		}

		else if (e.getActionCommand() == Statics.START) {

			if (runningThreads != 0) {
				if (Sort.isStopped()) {

					Sort.resume();
					for (Sort temp : sortList) {
						temp.unlockSignal();
						temp.getSortVisualtionPanel().enableRemoveButton(false);
					}

					byUserStopped = false;
					window.unlockManualIteration(false);
				} else {
					Sort.stop();
					for (Sort temp : sortList) {
						temp.getSortVisualtionPanel().enableRemoveButton(true);
					}
					window.unlockManualIteration(true);
					byUserStopped = true;
				}

			}

			else {

				if (executor.isTerminated())
					executor = Executors.newCachedThreadPool();

				Sort.resume();
				for (Sort temp : sortList) {

					temp.initElements();
					temp.getSortVisualtionPanel().enableRemoveButton(false);
					executor.execute(temp);
					runningThreads++;

				}

				if (byUserStopped)
					byUserStopped = false; // if app was reseted, start routine
											// continues here
				window.unlockManualIteration(false);
				window.unlockAddSort(false);
			}

			window.toggleStartStop();
		}

		else if (e.getActionCommand() == Statics.RESET) {

			if (Sort.isStopped()) {
				
				Sort.resume();
				Sort.setFlashingAnimation(false);
				
				for (Sort temp : sortList) {

					temp.deleteObservers();
					temp.unlockSignal();

				}

				executor.shutdownNow();
				
				try {
					executor.awaitTermination(2000, TimeUnit.MILLISECONDS); 
				} catch (InterruptedException e1) {e1.printStackTrace();}

				if (!executor.isTerminated())
					System.out.println("executor service isn't terminated !");
				
				for (Sort temp : sortList) {
					temp.initElements();
					temp.addObserver(this);

				}
				
				Sort.setFlashingAnimation(true);

			}

			else{
				for (Sort temp : sortList) {
					temp.initElements();

				}
			}

			window.unlockAddSort(true);
			runningThreads = 0;
		}

		else if (e.getActionCommand() == Statics.MANUAL) {

			dialogs.add(new ManualDialog(this, "Bedienungsanleitung", 300, 700));

		}

		else if (e.getActionCommand() == Statics.NEW_ELEMENTS) {

			dialogs.add(EnterDialog.getInstance(this, 500, 300));
		}

		else if (e.getActionCommand() == Statics.ABOUT) {

			dialogs.add(new AboutDialog(this, 300, 190));
		}
		
		else if (e.getActionCommand() == Statics.INFO) {

			dialogs.add(new InfoDialog(this,"Name", 400, 200));
		}

		else if (e.getActionCommand() == Statics.LANG_DE) {

			Statics.setLanguage("lang_de.xml");
			langXMLInterface.readXML("/resources/lang_de.xml",true);
			window.updateLanguage();
			for (OptionDialog temp : dialogs)
				temp.updateComponentsLabel(); // update language on every open
												// dialog

		}

		else if (e.getActionCommand() == Statics.LANG_EN) {

			Statics.setLanguage("lang_en.xml");
			langXMLInterface.readXML("/resources/lang_en.xml",true);
			window.updateLanguage();
			for (OptionDialog temp : dialogs)
				temp.updateComponentsLabel(); // update language on every open
												// dialog

		}

		else if (e.getActionCommand() == Statics.LANG_FR) {

			Statics.setLanguage("lang_fr.xml");
			langXMLInterface.readXML("/resources/lang_fr.xml",true);
			window.updateLanguage();
			for (OptionDialog temp : dialogs)
				temp.updateComponentsLabel(); // update language on every open
												// dialog

		}

		// just execute one more step
		else if (e.getActionCommand() == Statics.NEXT_ITERATION) {

			for (Sort temp : sortList) {
				temp.unlockSignal();
			}

		}

		else if (e.getActionCommand() == Statics.POPUP_REMOVE) {

			if (sortList.size() > 0) {

				Sort.setFlashingAnimation(false);
				sortList.get(vspIndex).unlockSignal();
				sortList.get(vspIndex).deleteObservers();
				
				Future<?> f = executor.submit(sortList.get(vspIndex));  
				f.cancel(true);
				window.removeSort(vspIndex);
				sortList.remove(vspIndex);
				runningThreads--;
				Sort.setFlashingAnimation(true);
				

			}
		}

		else if (e.getActionCommand() == Statics.REPORT) {

			if (sortList.size() > 0) {

				window.removeSort(vspIndex);
				sortList.remove(vspIndex);

			}
		}

		else if (e.getActionCommand() == Statics.DELAY) {

			dialogs.add(DelayDialog.getInstance(this, 320, 150));
		}

	}

	// this method will be called by an EDT foreign thread
	@Override
	public void update(Observable o, Object arg) {

		if (--runningThreads == 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					window.toggleStartStop();
					System.out.println("ALL THREADS ENDED");
				}
			});
		}

	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		for (OptionDialog temp : dialogs)
			temp.dispose();
		
		InternalConfig.setValue("delayms",Sort.getDelayMs());
		InternalConfig.setValue("delayns",Sort.getDelayNs());
		InternalConfig.setValue("language",Statics.getLanguageSet());
		InternalConfig.setValue("nofelements",Sort.getElements().length);
		InternalConfig.saveChanges();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	// animation will be released after the user reactivate the window
	@Override
	public void windowActivated(WindowEvent e) {

		if (runningThreads != 0 && byUserStopped == false) {
			Sort.resume();
			for (Sort temp : sortList) {
				temp.unlockSignal();
			}

		}
		window.appReleased();

	}

	// animation will be paused if the user deactivate the window
	@Override
	public void windowDeactivated(WindowEvent e) {
		Sort.stop();
		if (runningThreads != 0 && byUserStopped == false) {
			window.appStopped();
		}
	}

}
