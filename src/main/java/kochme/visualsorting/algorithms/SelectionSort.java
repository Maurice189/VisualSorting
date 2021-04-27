package kochme.visualsorting.algorithms;

import kochme.visualsorting.instruction.InstructionMediator;
import kochme.visualsorting.app.Constants;

public class SelectionSort extends SortAlgorithm {


    public SelectionSort(InstructionMediator instructionMediator) {
        super(instructionMediator);
    }

    @Override
    public Constants.SortAlgorithm getAlgorithmName() {
        return Constants.SortAlgorithm.Selectionsort;
    }

    @Override
    public void run() {
        for (int i = 0; i < instructionMediator.getNumberOfElements() - 1; i++) {
            int indexMin = i;
            for (int j = i + 1; j < instructionMediator.getNumberOfElements(); j++) {
                if (instructionMediator.compare(j, indexMin) == -1) {
                    indexMin = j;
                }
            }
            instructionMediator.swap(i, indexMin);
        }
    }
}