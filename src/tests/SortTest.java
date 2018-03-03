package tests;

import algorithms.*;
import gui.SortVisualisationPanel;
import main.Consts;
import main.Controller;
import main.OperationExecutor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SortTest {
    private final static int NUMBER_OF_RANDOM_ROUNDS = 5;

    private OperationExecutor executor;
    private Sort[] sorts;

    @Before
    public void setup() {
        Controller controller = new Controller(new int[1]);
        SortVisualisationPanel panel = new SortVisualisationPanel(Consts.SortAlgorithm.Heapsort, 1, 1);

        executor = new OperationExecutor(controller, panel);

        sorts = new Sort[]{
                new BubbleSort(executor),
                new CombSort(executor),
                new HeapSort(executor),
                new InsertionSort(executor),
                new MergeSort(executor),
                new QuickSort(QuickSort.PivotStrategy.FIXED, executor),
                new QuickSort(QuickSort.PivotStrategy.RANDOM, executor),
                new QuickSort(QuickSort.PivotStrategy.MO3, executor),
                new SelectionSort(executor),
                new ShakerSort(executor),
                new ShellSort(executor)
        };
    }

    @Test
    public void randomizedSortTest() {
        for (int r = 0; r < NUMBER_OF_RANDOM_ROUNDS; r++) {
            int[] randomSequence = getRandomSequence(getRandomNumber(0, 1000));

            int[] randomSequenceI1 = new int[randomSequence.length];
            System.arraycopy(randomSequence, 0, randomSequenceI1, 0, randomSequence.length);
            Arrays.sort(randomSequenceI1);

            for (Sort sort : sorts) {
                int[] randomSequenceI2 = new int[randomSequence.length];
                System.arraycopy(randomSequence, 0, randomSequenceI2, 0, randomSequence.length);

                try {
                    execute(sort, randomSequenceI2);
                    assertArrayEquals("At " + sort.getAlgorithmName(), randomSequenceI1, randomSequenceI2);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int[] execute(Sort c, int[] sequence) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        executor.initElements(sequence);
        c.run();
        return executor.getElements();
    }

    private static int getRandomNumber(int low, int high) {
        return (int) (Math.random() * (high - low) + low);
    }

    private static int[] getRandomSequence(int n) {
        List<Integer> s1 = new LinkedList<>();
        int[] randomSequence = new int[n];

        for (int i = 0; i < n; i++) {
            s1.add(i);
        }

        for (int i = 0; i < n; i++) {
            int r = getRandomNumber(0, n - i);
            randomSequence[i] = s1.remove(r);
        }

        return randomSequence;
    }
}