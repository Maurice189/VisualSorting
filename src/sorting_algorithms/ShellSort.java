package sorting_algorithms;

// copied from http://forgetcode.com/Java/1327-Shell-Sort

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

	public void run() {
		// TODO Auto-generated method stub

		int i, j, increment, temp, number_of_elements = elements.length;

		/* Shell Sort Program */
		for (increment = number_of_elements / 2; increment > 0; increment /= 2) {
			for (i = increment; i < number_of_elements; i++) {
				temp = elements[i];
				for (j = i; j >= increment; j -= increment) {

					if (temp < elements[j - increment]) {
					
						svp.visualInsert(j, elements[j - increment]);
						svp.setInfo("Shellsort",iterates++);
						
						elements[j] = elements[j - increment];
						checkRunCondition();

					} else {

						svp.visualCmp(i, j - increment, false);
						svp.setInfo("Shellsort",iterates++);
						
						checkRunCondition();
						break;
					}
				}
				
				
				svp.visualInsert(j, temp);
				elements[j] = temp;
				
				checkRunCondition();

			}
		}

		if(flashing) svp.flashing();
		setChanged();
		notifyObservers();

	}
	
	@Override
	public SORTALGORITHMS getAlgorithmName() {
		// TODO Auto-generated method stub
		return SORTALGORITHMS.Shellsort;
	}
}
