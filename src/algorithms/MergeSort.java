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
http://www.javabeginners.de/Algorithmen/Sortieralgorithmen/Mergesort.php
(C) Jörg Czeschla

*/


/**
 * Implementation of the respective sort algorithm.
 *
 * @author maurice
 * @version BETA
 * @category Sort
 */

import main.SortVisualisationPanel;
import main.Statics.SortAlgorithm;


public class MergeSort extends Sort {

    public void run() {
        try {
            sort(0, elements.length - 1);

            setChanged();
            notifyObservers(panelUI.getID());

            if (flashing) svp.visualTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void sort(int l, int r) throws InterruptedException {
        if (l < r) {
            manualInstructionIncrement();
            int q = (l + r) / 2;

            sort(l, q);
            sort(q + 1, r);
            merge(l, q, r);
        }
    }

    private void merge(int l, int q, int r) throws InterruptedException {
        int[] arr = new int[elements.length];
        int i, j;

        for (i = l; i <= q; i++) {
            arr[i] = elements[i];
            panelUI.setInfo(accesses++, comparisons);
        }

        for (j = q + 1; j <= r; j++) {
            arr[r + q + 1 - j] = elements[j];
            panelUI.setInfo(accesses++, comparisons);
        }

        i = l;
        j = r;

        for (int k = l; k <= r; k++) {
            if (compare(arr, i, j) != 1) {
                insertByValue(k, arr[i]);
                i++;
            } else {
                insertByValue(k, arr[j]);
                j--;
            }
            panelUI.setInfo(accesses++, comparisons);
        }
    }


    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Mergesort;
    }


}
