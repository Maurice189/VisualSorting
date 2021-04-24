package kochme.visualsorting.app;


import java.util.LinkedList;
import java.util.List;

public class Utils {

    public static int getRandomNumber(int low, int high) {
        return (int) (Math.random() * (high - low) + low);
    }

    public static int[] getRandomSequence(int n) {
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

    public static int[] getSortedSequence(int n) {
        int[] sortedSequence = new int[n];

        for (int i = 0; i < n; i++) {
            sortedSequence[i] = i;
        }

        return sortedSequence;
    }

    public static int[] getReversedSequence(int n) {
        int[] reversedSequence = new int[n];

        for (int i = 0; i < n; i++) {
            reversedSequence[i] = n - 1 - i;
        }

        return reversedSequence;
    }
}
