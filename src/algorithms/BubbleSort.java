package algorithms;

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
 * Implementation of the respective sort algorithm.
 * 
 * @author maurice
 * @version BETA
 * @category Sort
 */

import main.SortVisualtionPanel;
import main.Statics.SORTALGORITHMS;

public class BubbleSort extends Sort {

	public BubbleSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public BubbleSort() {
		// TODO Auto-generated constructor stub
		super();


	}

	public void run() {

		int tmp = 0;
		boolean swapped;
		
		do{
			swapped = false;
			for (int i = 0; i < elements.length - 1; i++) {

				
				if (elements[i] > elements[i + 1]) {

					tmp = elements[i];
					elements[i] = elements[i + 1];
					elements[i + 1] = tmp;
					svp.visualCmp(i, i + 1, true);
					//svp.setInfo("Bubblesort",iterates++);
					svp.setInfo("Bubblesort",accesses,comparisons++);
					accesses+=3;
					swapped = true;

				} else {
					svp.visualCmp(i, i + 1, false);
					//svp.setInfo("Bubblesort",iterates++);
					svp.setInfo("Bubblesort",accesses,comparisons++);
					
				}
				
				checkRunCondition();

			}
			
		}while(swapped);
		
		setChanged();
		notifyObservers(svp.getID());
		
		if(flashing) svp.flashing();
	

	}
	@Override
	public SORTALGORITHMS getAlgorithmName() {
		// TODO Auto-generated method stub
		return SORTALGORITHMS.Bubblesort;
	}

}
