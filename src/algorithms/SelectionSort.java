package algorithms;

import main.Consts;
import main.OperationExecutor;

public class SelectionSort extends Sort {


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

            operationExecutor.terminate();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}