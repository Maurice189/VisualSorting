package kochme.visualsorting.instruction;

public class Instruction {
    private final int accessCosts, compareCosts;
    private final boolean flag;
    private final int first;
    private final int second;

    public Instruction(int first, int second, boolean flag, int accessCosts, int compareCosts) {
        this.first = first;
        this.second = second;
        this.flag = flag;
        this.accessCosts = accessCosts;
        this.compareCosts = compareCosts;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public boolean getFlag() {
        return flag;
    }

    public int accessCosts() {
        return accessCosts;

    }
    public int compareCosts() {
        return compareCosts;
    }

    public static Instruction insertInstance(int index, int value) {
        return new Instruction(index, value, false, 1, 0);
    }
    public static Instruction compareInstance(int firstIndex, int secondIndex, boolean isPivot) {
        return new Instruction(firstIndex, secondIndex, isPivot, 2, 1);
    }
    public static Instruction swapInstance(int firstIndex, int secondIndex) {
        return new Instruction(firstIndex, secondIndex, false, 3, 0);
    }

}
