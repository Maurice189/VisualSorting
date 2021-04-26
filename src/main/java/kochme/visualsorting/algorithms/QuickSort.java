package kochme.visualsorting.algorithms;

import kochme.visualsorting.app.OperationExecutor;
import kochme.visualsorting.algorithms.SortAlgorithm;
import kochme.visualsorting.app.Consts;

public class QuickSort extends SortAlgorithm {

    public enum PivotStrategy {FIXED, RANDOM, MO3}

    private int pivotIndex;
    private PivotStrategy pivotStrategy;

    public QuickSort(PivotStrategy pivotStrategy, OperationExecutor operationExecutor) {
        super(operationExecutor);
        this.pivotStrategy = pivotStrategy;
    }

    private void sort(int left, int right) throws InterruptedException {
        if (left < right) {
            int i = partition(left, right);
            sort(left, i - 1);
            sort(i + 1, right);
        }
    }


    private int getPivotByRandom(int left, int right) {
        return left + (int) (Math.random() * (right - left));
    }

    private int getPivotByMedianOfThree(int left, int right) throws InterruptedException {
        int center = (left + right) / 2;
        int cmpLR = operationExecutor.compare(left, right);
        int cmpRC = operationExecutor.compare(right, center);

        if (cmpLR == -1 && cmpRC == -1) {
            return right;
        }

        int cmpLC = operationExecutor.compare(left, center);

        if (cmpLR == 1 && cmpLC == -1) {
            return left;
        }
        return center;
    }

    private int partition(int left, int right) throws InterruptedException {
        int i, j;

        if (pivotStrategy == PivotStrategy.FIXED) {
            pivotIndex = right;
        } else if (pivotStrategy == PivotStrategy.RANDOM) {
            pivotIndex = getPivotByRandom(left, right);
        } else if (pivotStrategy == PivotStrategy.MO3) {
            pivotIndex = getPivotByMedianOfThree(left, right);
        }

        operationExecutor.exchange(pivotIndex, right);

        i = left;
        j = right - 1;

        while (i <= j) {
            if (operationExecutor.compareByPivot(i, right) == 1) {
                operationExecutor.exchange(i, j);
                j--;
            } else {
                i++;
            }
        }

        operationExecutor.exchange(i, right);
        return i;
    }

    public void run() {
        try {
            sort(0, operationExecutor.getNumberOfElements() - 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        operationExecutor.terminate();
    }

    @Override
    public Consts.SortAlgorithm getAlgorithmName() {
        if (pivotStrategy == PivotStrategy.FIXED) {
            return Consts.SortAlgorithm.Quicksort_FIXED;
        }
        if (pivotStrategy == PivotStrategy.RANDOM) {
            return Consts.SortAlgorithm.Quicksort_RANDOM;
        }
        return Consts.SortAlgorithm.Quicksort_MO3;
    }

}
