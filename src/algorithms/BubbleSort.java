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
 * Implementation of the respective sort algorithm.
 *
 * @author maurice
 * @version BETA
 * @category Sort
 */

import main.SortVisualisationPanel;
import main.Statics.SortAlgorithm;

public class BubbleSort extends Sort {

    public void run() {
        boolean swapped;

        do {
            swapped = false;
            for (int i = 0; i < elements.length - 1; i++) {
                if (compare(i, i + 1) == 1) {
                    exchange(i, i + 1);
                    swapped = true;
                }
                checkRunCondition();
            }
        } while (swapped);

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing) svp.visualTermination();

    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Bubblesort;
    }

}
