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

    public static void shell(int[] a) {
        int increment = a.length / 2;
        while (increment > 0) {
            for (int i = increment; i < a.length; i++) {
                int j = i;
                int temp = a[i];
                while (j >= increment && a[j - increment] > temp) {
                    a[j] = a[j - increment];
                    j = j - increment;
                }
                a[j] = temp;
            }
            if (increment == 2) {
                increment = 1;
            } else {
                increment *= (5.0 / 11);
            }
        }
    }

    public void run() {
        try {
            int increment = elements.length / 2;
            while (increment > 0) {
                for (int i = increment; i < elements.length; i++) {
                    int j = i;
                    int temp = elements[i];
                    while (j >= increment && elements[j - increment] > temp) {
                        svp.visualInsert(j, elements[j - increment]);
                        //svp.setInfo("Shellsort",iterates++);
                        panelUI.setInfo("Shellsort", accesses, comparisons++);
                        accesses += 2;

                        checkRunCondition();
                        elements[j] = elements[j - increment];
                        j = j - increment;
                    }

                    svp.visualInsert(j, temp);
                    panelUI.setInfo("Shellsort", accesses++, comparisons++);

                    checkRunCondition();

                    elements[j] = temp;
                }
                if (increment == 2) {
                    increment = 1;
                } else {
                    increment *= (5.0 / 11);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
