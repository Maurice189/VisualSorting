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
import main.Statics.SortAlgorithm;

import java.util.function.Function;

public class QuickSort extends Sort {

    public enum PivotStrategy {FIXED, RANDOM, MO3}

    private int pivotIndex;
    private PivotStrategy pivotStrategy;

    public QuickSort(PivotStrategy pivotStrategy) {
        super();
        this.pivotStrategy = pivotStrategy;
    }

    private void sort(int x[], int left, int right) {
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
        int cmpLR = compare(left, right);
        int cmpRC = compare(right, center);

        if (cmpLR == -1 && cmpRC == -1) {
            return right;
        }

        int cmpLC = compare(left, center);

        if (cmpLR == 1 && cmpLC == -1) {
            return left;
        }

        return center;
    }

    private int partition(int x[], int left, int right) {
        int i, j;

        if (pivotStrategy == PivotStrategy.FIXED) {
            pivotIndex = right;
        } else if (pivotStrategy == PivotStrategy.RANDOM) {
            pivotIndex = getPivotByRandom(left, right);
        } else  if (pivotStrategy == PivotStrategy.MO3) {
            pivotIndex = getPivotByMedianOfThree(x, left, right);
        }

        exchange(pivotIndex, right);

        i = left;
        j = right - 1;

        while (i <= j) {
            if (compare(i, right) == 1) {
                exchange(i, j);
                j--;
            } else {
                i++;
            }
        }

        exchange(i, right);
        return i;
    }

    public void run() {

        sort(elements, 0, elements.length - 1);

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing) svp.visualTermination();
    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        if(pivotStrategy == PivotStrategy.FIXED) {
            return SortAlgorithm.Quicksort_FIXED;
        }
        if(pivotStrategy == PivotStrategy.RANDOM) {
            return SortAlgorithm.Quicksort_RANDOM;
        }
        return SortAlgorithm.Quicksort_MO3;
    }

}
