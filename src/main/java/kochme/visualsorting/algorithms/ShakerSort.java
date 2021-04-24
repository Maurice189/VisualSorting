package kochme.visualsorting.algorithms;

import kochme.visualsorting.app.OperationExecutor;
import kochme.visualsorting.algorithms.SortAlgorithm;
import kochme.visualsorting.app.Consts;

public class ShakerSort extends SortAlgorithm {


    public ShakerSort(OperationExecutor operationExecutor) {
        super(operationExecutor);
    }

    private void shaker1(int i, int l) throws InterruptedException {
        for (int j = i; j < l - 1; j++) {
            if (operationExecutor.compare(j, j + 1) == 1) {
                operationExecutor.exchange(j, j + 1);
            }
        }
    }

    private void shaker2(int i, int l) throws InterruptedException {
        for (int j = l - 1; j >= i; j--) {
            if (operationExecutor.compare(j, j + 1) == 1) {
                operationExecutor.exchange(j, j + 1);
            }
        }
    }

    public void run() {
        try {
            int i = 0, l = operationExecutor.getNumberOfElements();
            while (i < l) {
                shaker1(i, l);
                l--;
                shaker2(i, l);
                i++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        operationExecutor.terminate();
    }

    @Override
    public Consts.SortAlgorithm getAlgorithmName() {
        return Consts.SortAlgorithm.Shakersort;
    }

}