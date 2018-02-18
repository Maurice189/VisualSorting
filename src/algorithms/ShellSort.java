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
import main.Statics.SortAlgorithm;

public class ShellSort extends Sort {

    public ShellSort() {
        super();
    }

    public void run() {

        try {
            int increment = elements.length / 2;
            while (increment > 0) {
                for (int i = increment; i < elements.length; i++) {
                    int j = i;
                    int temp = elements[i];
                    while (j >= increment && elements[j - increment] > temp) {
                        checkRunCondition();
                        insertByIndex(j, j - increment);
                        j = j - increment;
                    }
                    insertByValue(j, temp);
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

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Shellsort;
    }
}
