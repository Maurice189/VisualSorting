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
http://rosettacode.org/wiki/Sorting_algorithms/Gnome_sort#Java
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

public class GnomeSort extends Sort {

    public GnomeSort(SortVisualisationPanel svp) {
        super(svp);
    }

    public GnomeSort() {
        super();
    }


    public void run() {
        int i = 1;
        int j = 2;

        while (i < elements.length) {
            if (elements[i - 1] <= elements[i]) {


                svp.visualCmp(i, i - 1, false);
                //svp.setInfo("Gnomesort", iterates++);
                panelUI.setInfo("Gnomesort", accesses, comparisons++);

                i = j;
                j++;

            } else {

                int tmp = elements[i - 1];
                elements[i - 1] = elements[i];
                elements[i] = tmp;

                svp.visualCmp(i, i - 1, true);
                //svp.setInfo("Gnomesort", iterates++);
                panelUI.setInfo("Gnomesort", accesses, comparisons++);
                accesses += 3;

                i--;
                i = (i == 0) ? j++ : i;
            }

            checkRunCondition();
        }

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing)
            svp.visualTermination();


    }

    @Override
    public SORTALGORITHMS getAlgorithmName() {
        return SORTALGORITHMS.Gnomesort;
    }

}