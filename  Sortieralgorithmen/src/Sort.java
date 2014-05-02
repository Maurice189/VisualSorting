import java.util.Observable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Diese Klasse dient als Abstraktion der Sortierverfahren.
 * <br>Jedes Sortierverfahren ist als Klasse implementiert.
 *   
 * 
 * 
 * @author maurice
 * @version BETA
 *  
 */



public abstract class Sort extends Observable implements Runnable{
	
	
	protected static int gElement[]; /** Statisches Referenzelement fuer die Sortierelemente*/
	protected int elements[];	
	protected int iterates;
	protected SortVisualtionPanel svp;
	protected static int delay = 110; // TODO mache delay statisch 
	
	
	protected Lock lock = new ReentrantLock();
	protected Condition condition = lock.newCondition();
	protected static boolean stop = true;
	
	/** @param <b>svp</b>  */
	public Sort(SortVisualtionPanel svp) { // permite write only per copy
		// TODO Auto-generated constructor stub
		
	
		this.elements = new int[Sort.gElement.length];
		System.arraycopy( Sort.gElement, 0, elements, 0, Sort.gElement.length );
		this.svp = svp;
		
		svp.setElements(this.elements);
		svp.drawElements();

	}
	
	public void resetElements(){
		
		this.elements = new int[Sort.gElement.length];
		System.arraycopy( Sort.gElement, 0, elements, 0, Sort.gElement.length );
		
		iterates = 0;
		svp.setElements(this.elements);
		svp.drawElements();
	}
	
	public static void setElements(int elements[]){
		Sort.gElement = elements;	
	}

	public Sort() {
		// TODO Auto-generated constructor stub
		
		this.elements = new int[Sort.gElement.length];
		System.arraycopy( Sort.gElement, 0, elements, 0, Sort.gElement.length );
		

	}
	
	public static int[] getElements(){
		return Sort.gElement;
	}
	
	
	
	public void setSortVisualtionPanel(SortVisualtionPanel svp){
		
		this.svp = svp;
		svp.setElements(elements);
		
	}
	
	public static void stop(){
		stop = true;
		
		
	}
	
	public static void resume(){
		stop = false;
		
	}
	
	public static boolean isStopped(){
		return stop;
	}
	
	public SortVisualtionPanel getSortVisualtionPanel(){
		
		return svp;
	}
	
	
	public Condition getCondition(){
		return condition;
	}
	
	public Lock getLock(){
		return lock;
	}
	
	public static void setDelay(int delay){
		Sort.delay = delay;
	}
	
	public static int getDelay(){
		return Sort.delay;
	}


}
