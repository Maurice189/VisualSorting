package kochme.visualsorting.algorithms;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import kochme.visualsorting.instruction.*;
import kochme.visualsorting.algorithms.*;

import java.util.Arrays;
import java.util.Random;

public class SortTest {
    private final static int SEQUENCE_LENGTH = 16;
    private InstructionMediator instructionMediator;
    private SortAlgorithm[] sorts;

    @Before
    public void setup() {
        instructionMediator = new InstructionMediator();

        sorts = new SortAlgorithm[]{
                new CombSort(instructionMediator),
                new HeapSort(instructionMediator),
                new InsertionSort(instructionMediator),
                new MergeSort(instructionMediator),
                new QuickSort(QuickSort.PivotStrategy.FIXED, instructionMediator),
                new QuickSort(QuickSort.PivotStrategy.RANDOM, instructionMediator),
                new QuickSort(QuickSort.PivotStrategy.MO3, instructionMediator),
                new SelectionSort(instructionMediator),
                new ShakerSort(instructionMediator),
                new ShellSort(instructionMediator)
        };
    }

    @Test
    public void randomizedSortTest() {
        int[] randomSequence = generateRandomSequence(SEQUENCE_LENGTH);
        int[] sortedSequence = new int[SEQUENCE_LENGTH];

        System.arraycopy(randomSequence, 0, sortedSequence, 0, SEQUENCE_LENGTH);
        Arrays.sort(sortedSequence);

        for (SortAlgorithm sort : sorts) {
            instructionMediator.initElements(randomSequence);
            sort.run();

            int[] outputSequence = instructionMediator.getElements();
            assertArrayEquals("At " + sort.getAlgorithmName(), outputSequence, sortedSequence);
        }
    }

    public int[] generateRandomSequence(int size) {
        Random rd = new Random();
        int[] randomSequence = new int[size];
        for (int i = 0; i < size; i++) {
            randomSequence[i] = rd.nextInt();
        }
        return randomSequence;
    }
}