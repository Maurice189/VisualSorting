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

This sort algorithm is based on:
http://www.javabeginners.de/Algorithmen/Sortieralgorithmen/Shakersort.php
(C) Jörg Czeschla

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

public class ShakerSort extends Sort {

	private int k;

	public ShakerSort() {
		// TODO Auto-generated constructor stub
		super();

	}

	public ShakerSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public int[] sort() {

		int i = 0, l = elements.length;
		while (i < l) {
			shaker1(i, l);
			l--;
			shaker2(i, l);
			i++;
		}
		return elements;
	}

	private void shaker1(int i, int l) {
		for (int j = i; j < l - 1; j++) {
			if (elements[j] > elements[j + 1]) {
				k = elements[j];
				elements[j] = elements[j + 1];
				elements[j + 1] = k;
				svp.visualCmp(j, j + 1, true);
				//svp.setInfo("Shakersort",iterates++);
				svp.setInfo("Shakersort",accesses,comparisons++);
				accesses+=3;

			}

			else {
				svp.visualCmp(j, j + 1, false);
				//svp.setInfo("Shakersort",iterates++);
				svp.setInfo("Shakersort",accesses,comparisons++);
			}

			checkRunCondition();
		}
	}

	private void shaker2(int i, int l) {
		for (int j = l - 1; j >= i; j--) {
			if (elements[j] > elements[j + 1]) {
				k = elements[j];
				elements[j] = elements[j + 1];
				elements[j + 1] = k;

				svp.visualCmp(j, j + 1, true);
				//svp.setInfo("Shakersort",iterates++);
				svp.setInfo("Shakersort",accesses,comparisons++);
				accesses+=3;

			}

			else {
				svp.visualCmp(j, j + 1, false);
				//svp.setInfo("Shakersort",iterates++);
				svp.setInfo("Shakersort",accesses,comparisons++);
			}

			checkRunCondition();
		}
	}

	public void run() {
		int i = 0, l = elements.length;

		while (i < l) {
				shaker1(i, l);
				l--;
				shaker2(i, l);
				i++;

		}

		setChanged();
		notifyObservers(svp.getID());
		
		if(flashing) svp.flashing();
		
	}
	
	@Override
	public SORTALGORITHMS getAlgorithmName() {
		// TODO Auto-generated method stub
		return SORTALGORITHMS.Shakersort;
	}

}