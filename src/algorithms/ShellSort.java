package algorithms;

// copied from http://rosettacode.org/wiki/Sorting_algorithms/Shell_sort#Java

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

public class ShellSort extends Sort {

	public ShellSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public ShellSort() {
		// TODO Auto-generated constructor stub
		super();

	}

	public static void shell(int[] a) {
		int increment = a.length / 2;
		while (increment > 0) {
			for (int i = increment; i < a.length; i++) {
				int j = i;
				int temp = a[i];
				while (j >= increment && a[j - increment] > temp) {
					a[j] = a[j - increment];
					j = j - increment;
				}
				a[j] = temp;
			}
			if (increment == 2) {
				increment = 1;
			} else {
				increment *= (5.0 / 11);
			}
		}
	}

	public void run() {
		// TODO Auto-generated method stub

		int increment = elements.length / 2;
		while (increment > 0) {
			for (int i = increment; i < elements.length; i++) {
				int j = i;
				int temp = elements[i];
				while (j >= increment && elements[j - increment] > temp) {
					

					svp.visualInsert(j, elements[j - increment]);
					//svp.setInfo("Shellsort",iterates++);
					svp.setInfo("Shellsort",accesses,comparisons++);
					accesses+=2;
					
					checkRunCondition();
					elements[j] = elements[j - increment];
					j = j - increment;
				}
				
				svp.visualInsert(j, temp);
				//svp.setInfo("Shellsort",iterates++);
				svp.setInfo("Shellsort",accesses++,comparisons++);
				checkRunCondition();
				elements[j] = temp;
			}
			if (increment == 2) {
				increment = 1;
			} else {
				increment *= (5.0 / 11);
			}
		}


		if (flashing)
			svp.flashing();
		setChanged();
		notifyObservers(svp.getID());

	}

	@Override
	public SORTALGORITHMS getAlgorithmName() {
		// TODO Auto-generated method stub
		return SORTALGORITHMS.Shellsort;
	}
}
