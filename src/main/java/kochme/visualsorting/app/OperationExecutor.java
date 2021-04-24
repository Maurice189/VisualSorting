package kochme.visualsorting.app;


import kochme.visualsorting.ui.FramedSortPanel;

public class OperationExecutor {
    private static final int INSTRUCTION_UNIT = 5;

    private final Controller controller;
    private volatile boolean pause;
    private volatile boolean executeNextStep;
    private long delayMs;
    private int delayNs;
    private int accesses;
    private int comparisons;
    private FramedSortPanel svp;
    private int[] elements, copyOfElements;
    private int instructionCount;
    private boolean flashing;

    public OperationExecutor(Controller controller, FramedSortPanel svp) {
        this.controller = controller;
        this.svp = svp;
    }

    public void initElements(int[] elements) {
        this.elements = elements;
        this.copyOfElements = new int[elements.length];

        System.arraycopy(elements, 0, copyOfElements, 0, elements.length);

        accesses = 0;
        comparisons = 0;
        svp.setElements(this.elements);
    }


    public void initElements() {
        this.elements = new int[copyOfElements.length];
        this.flashing = true;

        System.arraycopy(copyOfElements, 0, elements, 0, copyOfElements.length);

        accesses = 0;
        comparisons = 0;
        svp.setElements(this.elements);
    }

    public void executeNextStep() {
        this.executeNextStep = true;
    }

    public void exchange(int x[], int i, int j) throws InterruptedException {
        int tmp = x[i];
        x[i] = x[j];
        x[j] = tmp;

        manualInstruction(INSTRUCTION_UNIT);
        accesses += 3;
        svp.setInfo(accesses, comparisons);
        svp.visualExchange(i, j);
    }

    public void exchange(int i, int j) throws InterruptedException {
        exchange(elements, i, j);
    }

    public void insertByIndex(int x[], int i, int j) throws InterruptedException {
        svp.visualInsert(i, x[j]);
        svp.setInfo(accesses, comparisons++);
        accesses += 2;
        manualInstruction(INSTRUCTION_UNIT);
        x[i] = x[j];
    }

    public void insertByIndex(int i, int j) throws InterruptedException {
        insertByIndex(elements, i, j);
    }

    public void insertByValue(int x[], int i, int value) throws InterruptedException {
        svp.visualInsert(i, value);
        svp.setInfo(accesses, comparisons++);
        accesses++;
        manualInstruction(INSTRUCTION_UNIT);
        x[i] = value;
    }

    public void insertByValue(int i, int value) throws InterruptedException {
        insertByValue(elements, i, value);
    }

    public int compare(int x[], int i, int j, boolean isPivot) throws InterruptedException {
        int result = 0;

        if (x[i] > x[j])
            result = 1;
        if (x[j] > x[i])
            result = -1;

        manualInstruction(INSTRUCTION_UNIT);
        accesses += 2;
        comparisons++;
        svp.setInfo(accesses, comparisons);
        svp.visualCompare(i, j, isPivot);

        return result;
    }

    public int compare(int i, int j) throws InterruptedException {
        return compare(elements, i, j, false);
    }

    public int compareByPivot(int i, int j) throws InterruptedException {
        return compare(elements, i, j, true);
    }

    public int compareByValue(int i, int value) throws InterruptedException {
        int result = 0;

        if (elements[i] > value)
            result = 1;
        if (value > elements[i])
            result = -1;

        manualInstruction(INSTRUCTION_UNIT);
        accesses += 2;
        comparisons++;
        svp.setInfo(accesses, comparisons);

        // TODO : How to visualize svp.visualCompare(i, value, false) ?
        return result;
    }

    public void manualInstruction(int count) throws InterruptedException {
        instructionCount += count;
        if (instructionCount > INSTRUCTION_UNIT) {
            while (instructionCount >= INSTRUCTION_UNIT) {
                while (pause && !executeNextStep) {
                    Thread.sleep(100);
                }
                executeNextStep = false;
                Thread.sleep(delayMs, delayNs);
                instructionCount -= INSTRUCTION_UNIT;
            }
        }
    }

    public void manualInstructionIncrement() throws InterruptedException {
        manualInstruction(1);
    }

    public void reset() {
        this.flashing = false;
        this.pause = false;
        svp.enableRemoveButton(false);
    }

    public int getElementAtIndex(int index) throws InterruptedException {
        int result = -1;

        if (index < elements.length) {
            result = elements[index];
        }

        manualInstructionIncrement();
        accesses += 1;
        svp.setInfo(accesses, comparisons);
        return result;
    }

    public void resume() {
        pause = false;
    }
    public void pause() {
        pause = true;
    }
    public int[] getElements() {
        return elements;
    }
    public int getNumberOfElements() {
        return elements.length;
    }

    public void terminate() {
        if (flashing)
            svp.visualTermination();
        controller.terminationSignal(this);
    }

    public void setDelay(int delayMs, int delayNs) {
        this.delayMs = delayMs;
        this.delayNs = delayNs;
    }

    public FramedSortPanel getSortVisualizationPanel() {
        return svp;
    }
}
