package kochme.visualsorting.algorithms;

import kochme.visualsorting.instruction.InstructionMediator;
import kochme.visualsorting.app.Constants;

public class InsertionSort extends SortAlgorithm {

    public InsertionSort(InstructionMediator instructionMediator) {
        super(instructionMediator);
    }

    public void run() {
        for (int i = 1; i < instructionMediator.getNumberOfElements(); i++) {
            int value = instructionMediator.getElementAtIndex(i);
            int j = i - 1;
            while (j >= 0 && instructionMediator.compareByValue(j, value) == 1) {
                instructionMediator.insertByIndex(j + 1, j);
                j = j - 1;
            }
            instructionMediator.insertByValue(j + 1, value);
        }
    }

    @Override
    public Constants.SortAlgorithm getAlgorithmName() {
        return Constants.SortAlgorithm.Insertionsort;
    }

}
