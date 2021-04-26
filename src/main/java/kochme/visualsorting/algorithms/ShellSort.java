package kochme.visualsorting.algorithms;

import kochme.visualsorting.app.OperationExecutor;
import kochme.visualsorting.algorithms.SortAlgorithm;
import kochme.visualsorting.app.Consts;

public class ShellSort extends SortAlgorithm {

    public ShellSort(OperationExecutor operationExecutor) {
        super(operationExecutor);
    }

    public void run() {
        try {
            int increment = operationExecutor.getNumberOfElements() / 2;
            while (increment > 0) {
                for (int i = increment; i < operationExecutor.getNumberOfElements(); i++) {
                    int j = i;
                    int temp = operationExecutor.getElementAtIndex(i);
                    while (j >= increment && operationExecutor.compareByValue(j - increment, temp) == 1) {
                        operationExecutor.insertByIndex(j, j - increment);
                        j = j - increment;
                    }
                    operationExecutor.insertByValue(j, temp);
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
        operationExecutor.terminate();
    }

    @Override
    public Consts.SortAlgorithm getAlgorithmName() {
        return Consts.SortAlgorithm.Shellsort;
    }
}
