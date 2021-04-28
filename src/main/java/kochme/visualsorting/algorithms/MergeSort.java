package kochme.visualsorting.algorithms;

import kochme.visualsorting.instruction.InstructionMediator;
import kochme.visualsorting.app.Constants;


public class MergeSort extends SortAlgorithm {
    public MergeSort(InstructionMediator instructionMediator) {
        super(instructionMediator);
    }

    public void run() {
        sort(0, instructionMediator.getNumberOfElements() - 1);
    }

    public void sort(int l, int r) {
        if (l < r) {
            int q = (l + r) / 2;

            sort(l, q);
            sort(q + 1, r);
            merge(l, q, r);
        }
    }

    private void merge(int l, int q, int r) {
        int[] arr = new int[instructionMediator.getNumberOfElements()];
        int i, j;

        for (i = l; i <= q; i++) {
            instructionMediator.insertOutOfPlace(arr, i, i);
        }

        for (j = q + 1; j <= r; j++) {
            instructionMediator.insertOutOfPlace(arr, r + q + 1 - j, j);
        }

        i = l;
        j = r;

        for (int k = l; k <= r; k++) {
            if (instructionMediator.compare(arr, i, j, false) != 1) {
                instructionMediator.insertByValue(k, arr[i]);
                i++;
            } else {
                instructionMediator.insertByValue(k, arr[j]);
                j--;
            }
        }
    }


    @Override
    public Constants.SortAlgorithm getAlgorithmName() {
        return Constants.SortAlgorithm.Mergesort;
    }

}
