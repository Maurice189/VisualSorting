package algorithms;

import main.OperationExecutor;
import main.Consts.SortAlgorithm;

public class ShakerSort extends Sort {


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
            operationExecutor.terminate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Shakersort;
    }

}