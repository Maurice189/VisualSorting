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
http://rosettacode.org/wiki/Sorting_algorithms/Comb_sort
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
import main.Statics.SortAlgorithm;

public class CombSort extends Sort {

    public void run() {
        try {
            float shrink = 1.3f;
            int i;
            int gap = elements.length;
            boolean swapped = false;

            while (gap > 1 || swapped) {
                if (gap > 1) {
                    gap = (int) ((float) gap / shrink);
                }
                swapped = false;
                for (i = 0; gap + i < elements.length; ++i) {
                    if (compare(i, i + gap) == 1) {
                        exchange(i, i + gap);
                        swapped = true;
                    }
                }
            }

            setChanged();
            notifyObservers(panelUI.getID());

            if (flashing) svp.visualTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Combsort;
    }


}