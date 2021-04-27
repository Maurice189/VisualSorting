package kochme.visualsorting.algorithms;

import kochme.visualsorting.instruction.InstructionMediator;
import kochme.visualsorting.app.Constants;

public class QuickSort extends SortAlgorithm {

    public enum PivotStrategy {FIXED, RANDOM, MO3}

    private int pivotIndex;
    private PivotStrategy pivotStrategy;

    public QuickSort(PivotStrategy pivotStrategy, InstructionMediator instructionMediator) {
        super(instructionMediator);
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
        int cmpLR = instructionMediator.compare(left, right);
        int cmpRC = instructionMediator.compare(right, center);

        if (cmpLR == -1 && cmpRC == -1) {
            return right;
        }

        int cmpLC = instructionMediator.compare(left, center);

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

        instructionMediator.swap(pivotIndex, right);

        i = left;
        j = right - 1;

        while (i <= j) {
            if (instructionMediator.compareByPivot(i, right) == 1) {
                instructionMediator.swap(i, j);
                j--;
            } else {
                i++;
            }
        }

        instructionMediator.swap(i, right);
        return i;
    }

    public void run() {
        try {
            sort(0, instructionMediator.getNumberOfElements() - 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Constants.SortAlgorithm getAlgorithmName() {
        if (pivotStrategy == PivotStrategy.FIXED) {
            return Constants.SortAlgorithm.Quicksort_FIXED;
        }
        if (pivotStrategy == PivotStrategy.RANDOM) {
            return Constants.SortAlgorithm.Quicksort_RANDOM;
        }
        return Constants.SortAlgorithm.Quicksort_MO3;
    }

}
