package sorting_algorithms;

import java.util.Observable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import main.SortVisualtionPanel;
import main.Statics.SORTALGORITHMS;

/**
 * 
 * <h3>Used Design Patterns</h3></br>
 * <ul>
 * 		<li><b>Model</b>-View-Controller</br></li>
 * 		<li>Strategy Design Pattern</li>
 * 		<li>Observer Design Pattern</li>
 * </ul>
 * 
 * </br><h3>Abstract</h3></br>
 * 
 * As the Strategy Design Pattern declares, every single sort algorithmn is
 * implemented as a own class. This abstract class defines the interface and
 * implements Runnable in order to execute every single sort algorithmn
 * in a own thread. Moreover every sort object has its reference to a own
 * canvas 
 * Even we need to know(for the GUI) when the sort proceedure has terminated.
 * Thats the reason, why we need the Observable interface 
 * After a thread has terminated, it notifys the Controller 
 * 
 * 
 * 
 * @author maurice
 * @version BETA
 * @category Strategy
 * @see main.Controller
 * @see java.util.Observable
 * @see main.SortVisualtionPanel
 * 
 */

public abstract class Sort extends Observable implements Runnable {

	protected static int gElement[];
	protected static long delayMs = 5;
	protected static int delayNs = 0;
	protected static boolean stop = true,flashing = true;
	

	protected int elements[];
	protected int iterates;
	protected SortVisualtionPanel svp;
	protected Lock lock = new ReentrantLock();
	protected Condition condition = lock.newCondition();


	/** 
	 * @param svp
	 * Each object hold its own SortVisualtionPanel and redirect every 
	 * changes that are done on the sortlist
	 */
	public Sort(SortVisualtionPanel svp) {

		this.svp = svp;


	}

	public void initElements() {

		this.elements = new int[Sort.gElement.length];
		System.arraycopy(Sort.gElement, 0, elements, 0, Sort.gElement.length);

		iterates = 0;
		svp.setElements(this.elements);
		// svp.drawElements();
	}

	/**
	 * 
	 * @param elements
	 * sortling list, that can accessed from every object, in order to create a copy
	 */
	public static void setElements(int elements[]) {
		Sort.gElement = elements;

	}

	/**
	 * a copy of the static sortlist is created 
	 * @see setElements(int elements[])
	 */
	public Sort() {
		// TODO Auto-generated constructor stub

		this.elements = new int[Sort.gElement.length];
		System.arraycopy(Sort.gElement, 0, elements, 0, Sort.gElement.length);

	}
	
	/**
	 * The paused thread resume
	 */
	public void unlockSignal(){
		try {
			lock.lock();
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * This method is called in the run method from every algorithm that is running
	 * This is needed to provide the start/stop functionality that is based
	 * on locks and condition. 
	 * 
	 */
	protected void checkRunCondition() {

		try {
			if (Sort.stop) {
				lock.lock();
				condition.await();
				lock.unlock();
			}

			else
				Thread.sleep(Sort.delayMs, Sort.delayNs);

		} catch (InterruptedException e) {
			System.out.println("INFO: INTERRUPTED WHILE SLEEPING"); //e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 
	 * @return is used for displaying the sorting list in 'EnterDialog'
	 * and for saving the number of elements in the configuration file
	 * 
	 * @see OptionDialogs.EnterDialog
	 * @see main.Controller
	 * @see main.InternalConfig
	 */
	public static int[] getElements() {
		return Sort.gElement;
	}

	public void setSortVisualtionPanel(SortVisualtionPanel svp) {

		this.svp = svp;
		svp.setElements(elements);

	}
	
	/**
	 * @return the specific algorithm name (identifying is needed for the info dialog)
	 * @see OptionDialogs.InfoDialog
	 */
	public abstract SORTALGORITHMS getAlgorithmName();

	public static void stop() {
		stop = true;

	}
	public static void resume() {
		stop = false;

	}
	
	/**
	 * 
	 * @param flashing decide wheather to animated the ending of a sorting proceedure
	 */
	public static void setFlashingAnimation(boolean flashing){
		
		Sort.flashing = flashing;
	}
	
	/**
	 * 
0	 * @return is especially needed for the controller
	 */
	public static boolean isStopped() {
		return stop;
	}

	/**
	 * 
	 * @return is used to apply modifications on the 'SortVisualtionPanel' object
	 * for e.g enable the remove button
	 */
	public SortVisualtionPanel getSortVisualtionPanel() {

		return svp;
	}

	/**
	 * 
	 * @param delayNs  set the delay(nanoseconds) for all threads
	 */
	public static void setDelayNs(int delayNs) {

		Sort.delayNs = delayNs;

	}


	/**
	 * 
	 * @param delayNs  set the delay(milliseconds) for all threads
	 */
	public static void setDelayMs(long delayMs) {

		Sort.delayMs = delayMs;

	}

	/**
	 * 
	 * @return The delay is saved in the configuartion file
	 */
	public static long getDelayMs() {
		return Sort.delayMs;
	}

	/**
	 * 
	 * @return The delay is saved in the configuartion file
	 */
	public static long getDelayNs() {
		return Sort.delayNs;
	}

}
