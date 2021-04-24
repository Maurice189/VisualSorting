package kochme.visualsorting.algorithms;

import kochme.visualsorting.app.OperationExecutor;
import kochme.visualsorting.algorithms.SortAlgorithm;
import kochme.visualsorting.app.Consts;


public class MergeSort extends SortAlgorithm {

    public MergeSort(OperationExecutor operationExecutor) {
        super(operationExecutor);
    }

    public void run() {
        try {
            sort(0, operationExecutor.getNumberOfElements() - 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        operationExecutor.terminate();
    }


    public void sort(int l, int r) throws InterruptedException {
        if (l < r) {
            operationExecutor.manualInstructionIncrement();
            int q = (l + r) / 2;

            sort(l, q);
            sort(q + 1, r);
            merge(l, q, r);
        }
    }

    private void merge(int l, int q, int r) throws InterruptedException {
        int[] arr = new int[operationExecutor.getNumberOfElements()];
        int i, j;

        for (i = l; i <= q; i++) {
            arr[i] = operationExecutor.getElementAtIndex(i);
        }

        for (j = q + 1; j <= r; j++) {
            arr[r + q + 1 - j] = operationExecutor.getElementAtIndex(j);
        }

        i = l;
        j = r;

        for (int k = l; k <= r; k++) {
            if (operationExecutor.compare(arr, i, j, false) != 1) {
                operationExecutor.insertByValue(k, arr[i]);
                i++;
            } else {
                operationExecutor.insertByValue(k, arr[j]);
                j--;
            }
        }
    }


    @Override
    public Consts.SortAlgorithm getAlgorithmName() {
        return Consts.SortAlgorithm.Mergesort;
    }

}
