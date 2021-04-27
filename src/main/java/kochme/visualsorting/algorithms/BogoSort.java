package kochme.visualsorting.algorithms;

import kochme.visualsorting.instruction.InstructionMediator;
import kochme.visualsorting.app.Constants;

public class BogoSort extends SortAlgorithm {

    public BogoSort(InstructionMediator instructionMediator) {
        super(instructionMediator);
    }

    void bogo() {
        while (!isSorted()) // we need to check the interrupt flag here
            shuffle();
    }

    void shuffle() {
        int i = instructionMediator.getNumberOfElements() - 1;
        while (i > 0)
            swap(i--, (int) (Math.random() * i));
    }

    void swap(int i, int j) {
        instructionMediator.swap(i, j);
    }

    boolean isSorted() {
        for (int i = 1; i < instructionMediator.getNumberOfElements(); i++) {
            if (instructionMediator.compare(i, i - 1) == -1) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void run() {
        bogo();
    }

    @Override
    public Constants.SortAlgorithm getAlgorithmName() {
        return Constants.SortAlgorithm.Bogosort;
    }

}
