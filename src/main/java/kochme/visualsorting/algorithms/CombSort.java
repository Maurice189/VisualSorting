package kochme.visualsorting.algorithms;


import kochme.visualsorting.instruction.InstructionMediator;
import kochme.visualsorting.app.Constants;

public class CombSort extends SortAlgorithm {

    public CombSort(InstructionMediator instructionMediator) {
        super(instructionMediator);
    }

    public void run() {
        float shrink = 1.3f;
        int i;
        int gap = instructionMediator.getNumberOfElements();
        boolean swapped = false;

        while (gap > 1 || swapped) {
            if (gap > 1) {
                gap = (int) ((float) gap / shrink);
            }
            swapped = false;
            for (i = 0; gap + i < instructionMediator.getNumberOfElements(); ++i) {
                if (instructionMediator.compare(i, i + gap) == 1) {
                    instructionMediator.swap(i, i + gap);
                    swapped = true;
                }
            }
        }
    }

    @Override
    public Constants.SortAlgorithm getAlgorithmName() {
        return Constants.SortAlgorithm.Combsort;
    }


}