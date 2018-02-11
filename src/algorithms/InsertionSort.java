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

import main.SortVisualisationPanel;
import main.Statics.SORTALGORITHMS;

public class InsertionSort extends Sort {

    public InsertionSort(SortVisualisationPanel svp) {
        super(svp);

    }

    public InsertionSort() {
        super();
    }

    public void run() {

        for (int i = 1; i < elements.length; i++) {
            int value = elements[i];
            int j = i - 1;
            while (j >= 0 && elements[j] > value) {

                svp.visualInsert(j + 1, elements[j]);
                //svp.setInfo("Insertionsort", iterates++);
                panelUI.setInfo(accesses, comparisons++);
                accesses += 2;

                checkRunCondition();
                elements[j + 1] = elements[j];
                j = j - 1;
            }

            svp.visualInsert(j + 1, value);
            //svp.setInfo("Insertionsort", iterates++);
            panelUI.setInfo(accesses, comparisons++);
            accesses += 2;

            checkRunCondition();
            elements[j + 1] = value;
        }

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing)
            svp.visualTermination();


    }

    @Override
    public SORTALGORITHMS getAlgorithmName() {
        return SORTALGORITHMS.Insertionsort;
    }

}
