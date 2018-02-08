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
import main.Statics.SORTALGORITHMS;


public class MergeSort extends Sort {

    public MergeSort(SortVisualisationPanel svp) {
        super(svp);

    }

    public MergeSort() {
        super();
    }

    public void run() {
        try {
            sort(0, elements.length - 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing) svp.visualTermination();
    }


    public void sort(int l, int r) throws InterruptedException {
        if (l < r) {
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
            panelUI.setInfo("Mergesort", accesses++, comparisons);
        }
        for (j = q + 1; j <= r; j++) {
            arr[r + q + 1 - j] = elements[j];
            panelUI.setInfo("Mergesort", accesses++, comparisons);

        }
        i = l;
        j = r;
        for (int k = l; k <= r; k++) {
            if (arr[i] <= arr[j]) {
                svp.visualInsert(k, arr[i]);
                elements[k] = arr[i];
                //svp.setInfo("Mergesort",iterates++);
                panelUI.setInfo("Mergesort", accesses++, comparisons++);

                i++;
            } else {

                svp.visualInsert(k, arr[j]);
                elements[k] = arr[j];
                //svp.setInfo("Mergesort",iterates++);
                panelUI.setInfo("Mergesort", accesses++, comparisons++);

                j--;
            }
            checkRunCondition();
        }
    }


    @Override
    public SORTALGORITHMS getAlgorithmName() {
        return SORTALGORITHMS.Mergesort;
    }


}
