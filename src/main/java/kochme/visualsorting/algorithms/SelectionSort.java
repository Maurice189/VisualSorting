package kochme.visualsorting.algorithms;

import kochme.visualsorting.app.OperationExecutor;
import kochme.visualsorting.algorithms.SortAlgorithm;
import kochme.visualsorting.app.Consts;

public class SelectionSort extends SortAlgorithm {


    public SelectionSort(OperationExecutor operationExecutor) {
        super(operationExecutor);
    }

    @Override
    public Consts.SortAlgorithm getAlgorithmName() {
        return Consts.SortAlgorithm.Selectionsort;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < operationExecutor.getNumberOfElements() - 1; i++) {
                int indexMin = i;
                for (int j = i + 1; j < operationExecutor.getNumberOfElements(); j++) {
                    if (operationExecutor.compare(j, indexMin) == -1) {
                        indexMin = j;
                    }
                }
                operationExecutor.exchange(i, indexMin);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        operationExecutor.terminate();
    }
}