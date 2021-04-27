package kochme.visualsorting.algorithms;

import kochme.visualsorting.instruction.InstructionMediator;
import kochme.visualsorting.app.Constants;

public class ShakerSort extends SortAlgorithm {


    public ShakerSort(InstructionMediator instructionMediator) {
        super(instructionMediator);
    }

    private void shaker1(int i, int l) {
        for (int j = i; j < l - 1; j++) {
            if (instructionMediator.compare(j, j + 1) == 1) {
                instructionMediator.swap(j, j + 1);
            }
        }
    }

    private void shaker2(int i, int l) {
        for (int j = l - 1; j >= i; j--) {
            if (instructionMediator.compare(j, j + 1) == 1) {
                instructionMediator.swap(j, j + 1);
            }
        }
    }

    public void run() {
        int i = 0, l = instructionMediator.getNumberOfElements();
        while (i < l) {
            shaker1(i, l);
            l--;
            shaker2(i, l);
            i++;
        }
    }

    @Override
    public Constants.SortAlgorithm getAlgorithmName() {
        return Constants.SortAlgorithm.Shakersort;
    }

}