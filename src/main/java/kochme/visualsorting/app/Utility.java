package kochme.visualsorting.app;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utility {
    public static int[] getRandomSequence(int n) {
        List<Integer> sequence = IntStream.range(0, n).boxed().collect(Collectors.toList());
        Collections.shuffle(sequence);
        return sequence.stream().mapToInt(i->i).toArray();
    }

    public static int[] getSortedSequence(int n) {
        return IntStream.range(0, n).toArray();
    }

    public static int[] getReversedSequence(int n) {
        List<Integer> sequence = IntStream.range(0, n).boxed().collect(Collectors.toList());
        Collections.reverse(sequence);
        return sequence.stream().mapToInt(i->i).toArray();
    }
}
