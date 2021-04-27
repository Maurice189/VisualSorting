package kochme.visualsorting.algorithms;

import kochme.visualsorting.instruction.InstructionMediator;
import kochme.visualsorting.app.Constants;

public class ShellSort extends SortAlgorithm {

    public ShellSort(InstructionMediator instructionMediator) {
        super(instructionMediator);
    }

    public void run() {
        int increment = instructionMediator.getNumberOfElements() / 2;
        while (increment > 0) {
            for (int i = increment; i < instructionMediator.getNumberOfElements(); i++) {
                int j = i;
                int temp = instructionMediator.getElementAtIndex(i);
                while (j >= increment && instructionMediator.compareByValue(j - increment, temp) == 1) {
                    instructionMediator.insertByIndex(j, j - increment);
                    j = j - increment;
                }
                instructionMediator.insertByValue(j, temp);
            }
            if (increment == 2) {
                increment = 1;
            } else {
                increment *= (5.0 / 11);
            }
        }
    }

    @Override
    public Constants.SortAlgorithm getAlgorithmName() {
        return Constants.SortAlgorithm.Shellsort;
    }
}
