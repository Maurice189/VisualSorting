package sorting_algorithms;



// copied from http://www.java-uni.de/index.php?Seite=86

/**
 * 
 * Implementation of the respective sort algorithm.
 * 
 * @author maurice
 * @version BETA
 * @category Sort
 */

import main.SortVisualtionPanel;
import main.Statics.SORTALGORITHMS;

public class QuickSort extends Sort {

	public QuickSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public QuickSort() {
		// TODO Auto-generated constructor stub
		super();

	}

	private void qSort(int x[], int links, int rechts){
		if (links < rechts) {
			int i = partition(elements, links, rechts);
			qSort(x, links, i - 1);
			qSort(x, i + 1, rechts);
		}

	}

	private int partition(int x[], int links, int rechts){
		int pivot, i, j, help;
		pivot = x[rechts];
		i = links;
		j = rechts - 1;
		while (i <= j) {
			
			if (x[i] > pivot) {
				// tausche x[i] und x[j]
				help = x[i];
				x[i] = x[j];
				x[j] = help;
				
				//svp.drawElements(i, j, true);
				svp.visualCmp(i, j,true);
				//svp.setInfo("Quicksort",iterates++);
				svp.setInfo("Quicksort",accesses,comparisons++);
				accesses+=3;

				j--;
			} else{
				
				
				//svp.drawElements(i, rechts, false);
				svp.visualCmp(i, rechts,false);
				//svp.setInfo("Quicksort",iterates++);
				svp.setInfo("Quicksort",accesses,comparisons++);
				
				
				i++;
			}
			
			checkRunCondition();
				
		}
		// tausche x[i] und x[rechts]
		help = x[i];
		x[i] = x[rechts];
		x[rechts] = help;

		//svp.drawElements(i, rechts, true);
		svp.visualCmp(i, rechts,true);
		//svp.setInfo("Quicksort",iterates++);
		svp.setInfo("Quicksort",accesses,comparisons++);
		accesses+=3;
		

		checkRunCondition();

		return i;
	}

	public void run() {
		// TODO Auto-generated method stub

		qSort(elements, 0, elements.length - 1);
		if(flashing) svp.flashing();
		
		setChanged();
		notifyObservers();
		
		

	}
	
	@Override
	public SORTALGORITHMS getAlgorithmName() {
		// TODO Auto-generated method stub
		return SORTALGORITHMS.Quicksort;
	}

}
