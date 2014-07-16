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
 * <h3>Used Design Patterns</h3></br>
 * <ul>
 * 		<li>Model-View-<b>Controller</li>
 * 		<li>Observer Design Pattern</li>
 * </ul>
 * 
 * </br><h3>Abstract</h3></br>
 * 
 * This class respresents, as the name implies, the controller in the
 * MVC pattern. Moreover this class implements the observer pattern,
 * so the controller can be informed, if a visualsation thread ends
 * 
 * @author Maurice Koch
 * @version BETA
 * @category MVC
 * 
 */


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
import algorithms.BinaryTreeSort;
import algorithms.BitonicSort;
import algorithms.BogoSort;
import algorithms.BubbleSort;
import algorithms.CombSort;
import algorithms.GnomeSort;
import algorithms.HeapSort;
import algorithms.InsertionSort;
import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.RadixSort;
import algorithms.ShakerSort;
import algorithms.ShellSort;
import algorithms.Sort;
import dialogs.AboutDialog;
import dialogs.DelayDialog;
import dialogs.EnterDialog;
import dialogs.InfoDialog;
import dialogs.OptionDialog;
import main.Statics.SORTALGORITHMS;



public class Controller implements Observer, ActionListener, WindowListener {

	private ArrayList<Sort> sortList; 
	private LinkedList<OptionDialog> dialogs; 

	private Window window;
	private LanguageFileXML langXMLInterface;
	private int threadsAlive; 
	private boolean byUserStopped = false;
	private ExecutorService executor; 
	private javax.swing.Timer appTimer;
	

	/**
	 * 
	 * @param langXMLInterface interface for language
	 * @param nofelements number of elements that are supposed to generate
	 * (this is determined by the value in the config.txt)
	 * 
	 * 
	 */
	public Controller(LanguageFileXML langXMLInterface) {

		this.langXMLInterface = langXMLInterface;


		sortList = new ArrayList<Sort>();
		dialogs = new LinkedList<OptionDialog>();

		executor = Executors.newCachedThreadPool();

		createTimer();
	
	}
	
	
	private void createTimer(){
		
		appTimer = new javax.swing.Timer(100, new ActionListener() {
			 
			  private int leftMs,leftSec;

			  public void actionPerformed( ActionEvent e ) {
				  
				  leftMs+=100;
				  if (leftMs == 1000) {
						leftMs = 0;
						leftSec++;
				  }
				  
				  window.setClockParam(leftSec, leftMs);
			  }
			  
		}); 
		if(window!=null) window.setClockParam(0,0);
	}

	public void showNumberOfElements(){
		window.updateNumberOfElements(Sort.getElements().length);
	}

	public void setView(Window window) {

		this.window = window;
		SortVisualtionPanel.setBackgroundColor(window.getBackground());
		window.updateNumberOfElements(Sort.getElements().length);
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
				threadsAlive = 0;
			}

			selectedSort = window.getSelectedSort();

			if (selectedSort.equals(SORTALGORITHMS.Heapsort.toString()))
				sort = new HeapSort();
			else if (selectedSort.equals(SORTALGORITHMS.Bubblesort.toString()))
				sort = new BubbleSort();
			else if (selectedSort.equals(SORTALGORITHMS.Quicksort.toString()))
				sort = new QuickSort();
			else if (selectedSort.equals(SORTALGORITHMS.BST.toString()))
				sort = new BinaryTreeSort();
			else if (selectedSort.equals(SORTALGORITHMS.Combsort.toString()))
				sort = new CombSort();
			else if (selectedSort.equals(SORTALGORITHMS.Gnomesort.toString()))
				sort = new GnomeSort();
			else if (selectedSort.equals(SORTALGORITHMS.Shakersort.toString()))
				sort = new ShakerSort();
			else if (selectedSort.equals(SORTALGORITHMS.Mergesort.toString()))
				sort = new MergeSort();
			else if (selectedSort.equals(SORTALGORITHMS.Bitonicsort.toString()))
				sort = new BitonicSort();
			else if (selectedSort.equals(SORTALGORITHMS.Radixsort.toString()))
				sort = new RadixSort();
			else if (selectedSort.equals(SORTALGORITHMS.Shellsort.toString()))
				sort = new ShellSort();
			else if (selectedSort.equals(SORTALGORITHMS.Insertionsort
					.toString()))
				sort = new InsertionSort();
			else if (selectedSort.equals(SORTALGORITHMS.Bogosort
					.toString()))
				sort = new BogoSort();
			else
				sort = new HeapSort();

			sort.addObserver(this);
			sortList.add(sort);
			window.addNewSort(sort, selectedSort);

		}

		else if (e.getActionCommand() == Statics.START) {

			if (threadsAlive != 0) {
				if (Sort.isStopped()) {

					Sort.resume();
					for (Sort temp : sortList) {
						temp.unlockSignal();
						temp.getSortVisualtionPanel().enableRemoveButton(false);
					}

					byUserStopped = false;
					window.unlockManualIteration(false);
					appTimer.start();

					
				} else {
					Sort.stop();
					for (Sort temp : sortList) {
						temp.getSortVisualtionPanel().enableRemoveButton(true);
					}
					window.unlockManualIteration(true);
					byUserStopped = true;
					appTimer.stop();
					

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
					threadsAlive++;

				}

				if (byUserStopped)
					byUserStopped = false; // if app was reseted, start routine
											// continues here
				window.unlockManualIteration(false);
				window.unlockAddSort(false);
				
				createTimer();
				appTimer.start();
				
	
			}

			window.toggleStartStop();
			
		}

		else if (e.getActionCommand() == Statics.RESET) {

			reset();
		}
		
		else if (e.getActionCommand() == Statics.AUTO_PAUSE) {

			InternalConfig.toggleAutoPause();
			System.out.println("PRESSED");
		}


		else if (e.getActionCommand() == Statics.NEW_ELEMENTS) {

			dialogs.add(EnterDialog.getInstance(this,500, 300));
		}

		else if (e.getActionCommand() == Statics.ABOUT) {

			dialogs.add(AboutDialog.getInstance(400, 415));
		}

		else if (e.getActionCommand() == Statics.INFO) {

			SORTALGORITHMS selAlgorithm = sortList.get(
					SortVisualtionPanel.getReleasedID()).getAlgorithmName();

			dialogs.add(new InfoDialog(selAlgorithm, selAlgorithm
					.toString(), 600, 400));
		}

		else if (e.getActionCommand() == Statics.LANG_DE) {

			InternalConfig.setLanguage("lang_de.xml");
			langXMLInterface.readXML("/resources/lang_de.xml");
			window.updateLanguage();
			for (OptionDialog temp : dialogs)
				temp.updateComponentsLabel(); // update language on every open
												// dialog

		}

		else if (e.getActionCommand() == Statics.LANG_EN) {

			InternalConfig.setLanguage("lang_en.xml");
			langXMLInterface.readXML("/resources/lang_en.xml");
			window.updateLanguage();
			for (OptionDialog temp : dialogs)
				temp.updateComponentsLabel(); // update language on every open
												// dialog

		}

		else if (e.getActionCommand() == Statics.LANG_FR) {

			InternalConfig.setLanguage("lang_fr.xml");
			langXMLInterface.readXML("/resources/lang_fr.xml");
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

		else if (e.getActionCommand() == Statics.REMOVE_SORT) {

			if (sortList.size() > 0) {

				int selPanel = SortVisualtionPanel.getReleasedID();

				if (threadsAlive != 0) {
					Sort.setFlashingAnimation(false);
					sortList.get(selPanel).unlockSignal();
					sortList.get(selPanel).deleteObservers();
					Future<?> f = executor.submit(sortList.get(selPanel));
					f.cancel(true);
					threadsAlive--;
					

				}

				window.removeSort(selPanel);
				sortList.remove(selPanel);
				for (Sort temp : sortList)
					temp.getSortVisualtionPanel().updateID();
				SortVisualtionPanel.updateCounter();
				Sort.setFlashingAnimation(true);

			}
		}


		else if (e.getActionCommand() == Statics.DELAY) {

			dialogs.add(DelayDialog.getInstance(320, 150));
		}

	}
	
	public void reset(){
		
		
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
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if (!executor.isTerminated())
				System.out.println("executor service isn't terminated !");

			for (Sort temp : sortList) {

				temp.initElements();
				temp.addObserver(this);

			}

			Sort.setFlashingAnimation(true);
			createTimer();

		}
		
		else {

			for (Sort temp : sortList) {
				temp.getSortVisualtionPanel().enableRemoveButton(true);
				temp.initElements();

			}

		}

		
		window.unlockAddSort(true);
		threadsAlive = 0;
		
		
	}
	

	
	

	/**
	 *  This is part of the Observer Pattern
	 *  this method will be called by an EDT foreign thread
	 *  
	 *  @Override
	 */
	
	public void update(Observable o, Object arg) {

		if (--threadsAlive == 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					window.toggleStartStop();
				}
			});
			
			appTimer.stop();
			
			for (Sort temp : sortList) 
				temp.getSortVisualtionPanel().enableRemoveButton(true);

			
		}

	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	/**
	 * If the window is closing the program values are set in the configuartion file
	 * @see main.InternalConfig
	 * @Override
	 */
	
	public void windowClosing(WindowEvent e) {
		
		appTimer.stop();

		for (OptionDialog temp : dialogs)
			temp.dispose();

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

	/**
	 *  animation will be released after the user reactivate the window
	 *  @Override
	 */
	public void windowActivated(WindowEvent e) {

		if(InternalConfig.isAutoPauseEnabled()){
			if (threadsAlive != 0 && byUserStopped == false) {
			 	Sort.resume();
			 	appTimer.start();
			 	for (Sort temp : sortList) {
			 		temp.unlockSignal();
			 	}

			}
			
			window.appReleased();
		 
	 	}
		
	}

	/**
	 *  animation will be paused if the user deactivate the window
	 *  @Override
	 */
	
	public void windowDeactivated(WindowEvent e) {
		
		if(InternalConfig.isAutoPauseEnabled()){
			Sort.stop();
			if (threadsAlive != 0 && byUserStopped == false) {
				window.appStopped();
				appTimer.stop();
			}
		}
	}
	

	
	

}
