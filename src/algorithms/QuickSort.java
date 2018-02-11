package algorithms;


// copied from http://www.java-uni.de/index.php?Seite=86

/**
 * Implementation of the respective sort algorithm.
 *
 * @author maurice
 * @version BETA
 * @category Sort
 */

import main.SortVisualisationPanel;
import main.Statics.SORTALGORITHMS;

import java.util.function.Function;

public class QuickSort extends Sort {

    public static enum PivotStrategy {FIXED, RANDOM, MO3}

    private int pivotIndex;
    private PivotStrategy pivotStrategy;

    public QuickSort(SortVisualisationPanel svp) {
        super(svp);
    }

    public QuickSort(PivotStrategy pivotStrategy) {
        super();
        this.pivotStrategy = pivotStrategy;
    }

    private void sort(int x[], int left, int right) throws InterruptedException {
        if (left < right) {
            int i = partition(elements, left, right);
            sort(x, left, i - 1);
            sort(x, i + 1, right);
        }
    }


    private int getPivotByRandom(int left, int right) {
        return left + (int)(Math.random() * (right - left));
    }
    private int getPivotByMedianOfThree(int x[], int left, int right) {
        int center = (left + right) / 2;

        int lv = x[left];
        int rv = x[right];
        int cv = x[center];

        if (lv <= rv && rv <= cv) {
            return right;
        }
        if (rv <= lv && lv <= cv) {
            return left;
        }
        return center;
    }

    private int partition(int x[], int left, int right) throws InterruptedException {
        int pivot, i, j, tmp;

        if (pivotStrategy == PivotStrategy.FIXED) {
            pivotIndex = right;
        } else if (pivotStrategy == PivotStrategy.RANDOM) {
            pivotIndex = getPivotByRandom(left, right);
        } else  if (pivotStrategy == PivotStrategy.MO3) {
            pivotIndex = getPivotByMedianOfThree(x, left, right);
        }

        tmp = x[pivotIndex];
        x[pivotIndex] = x[right];
        x[right] = tmp;

        svp.visualCmp(pivotIndex, right, true);
        accesses += 3;

        pivot = x[right];

        i = left;
        j = right - 1;

        while (i <= j) {
            panelUI.setInfo(accesses, comparisons++);
            checkRunCondition();

            if (x[i] > pivot) {
                tmp = x[i];
                x[i] = x[j];
                x[j] = tmp;

                svp.visualCmp(i, j, true);
                svp.visualPivot(right);
                panelUI.setInfo(accesses, comparisons++);
                accesses += 3;
                j--;
            } else {
                i++;
            }

            checkRunCondition();
        }

        tmp = x[i];
        x[i] = x[right];
        x[right] = tmp;

        svp.visualCmp(i, right, true);
        svp.visualPivot(right);
        panelUI.setInfo(accesses, comparisons++);
        accesses += 3;
        checkRunCondition();

        return i;
    }

    public void run() {

        try {
            sort(elements, 0, elements.length - 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing) svp.visualTermination();
    }

    @Override
    public SORTALGORITHMS getAlgorithmName() {
        if(pivotStrategy == PivotStrategy.FIXED) {
            return SORTALGORITHMS.Quicksort_FIXED;
        }
        if(pivotStrategy == PivotStrategy.RANDOM) {
            return SORTALGORITHMS.Quicksort_RANDOM;
        }
        return SORTALGORITHMS.Quicksort_MO3;
    }

}
