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
http://rosettacode.org/wiki/Sorting_algorithms/Insertion_sort#Java
(C) Ingy döt Net 

*/

/**
 * Implementation of the respective sort algorithm.
 *
 * @author maurice
 * @version BETA
 * @category Sort
 */

import main.Statics.SortAlgorithm;

public class InsertionSort extends Sort {

    public void run() {

        try {
            for (int i = 1; i < elements.length; i++) {
                int value = elements[i];
                int j = i - 1;
                while (j >= 0 && elements[j] > value) {
                    checkRunCondition();
                    insertByIndex(j + 1, j);
                    j = j - 1;
                }
                insertByValue(j + 1, value);
            }

            setChanged();
            notifyObservers(panelUI.getID());

            if (flashing)
                svp.visualTermination();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Insertionsort;
    }

}
