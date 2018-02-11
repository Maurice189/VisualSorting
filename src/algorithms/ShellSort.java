package algorithms;

// copied from http://rosettacode.org/wiki/Sorting_algorithms/Shell_sort#Java

/**
 * Implementation of the respective sort algorithm.
 *
 * @author maurice
 * @version BETA
 * @category Sort
 */

import main.SortVisualisationPanel;
import main.Statics.SORTALGORITHMS;

public class ShellSort extends Sort {

    public ShellSort(SortVisualisationPanel svp) {
        super(svp);
    }

    public ShellSort() {
        super();
    }

    public void run() {
        int increment = elements.length / 2;
        while (increment > 0) {
            for (int i = increment; i < elements.length; i++) {
                int j = i;
                int temp = elements[i];
                while (j >= increment && elements[j - increment] > temp) {
                    svp.visualInsert(j, elements[j - increment]);
                    //svp.setInfo("Shellsort",iterates++);
                    panelUI.setInfo(accesses, comparisons++);
                    accesses += 2;

                    checkRunCondition();
                    elements[j] = elements[j - increment];
                    j = j - increment;
                }

                svp.visualInsert(j, temp);
                panelUI.setInfo(accesses++, comparisons++);

                checkRunCondition();

                elements[j] = temp;
            }
            if (increment == 2) {
                increment = 1;
            } else {
                increment *= (5.0 / 11);
            }
        }

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing)
            svp.visualTermination();


    }

    @Override
    public SORTALGORITHMS getAlgorithmName() {
        return SORTALGORITHMS.Shellsort;
    }
}
