package algorithms;

import main.OperationExecutor;
import main.Consts.SortAlgorithm;

public class InsertionSort extends Sort {

    public InsertionSort(OperationExecutor operationExecutor) {
        super(operationExecutor);
    }

    public void run() {

        try {
            for (int i = 1; i < operationExecutor.getNumberOfElements(); i++) {
                int value = operationExecutor.getElementAtIndex(i);
                int j = i - 1;
                while (j >= 0 && operationExecutor.compareByValue(j, value) == 1) {
                    operationExecutor.insertByIndex(j + 1, j);
                    j = j - 1;
                }
                operationExecutor.insertByValue(j + 1, value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        operationExecutor.terminate();
    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Insertionsort;
    }

}
