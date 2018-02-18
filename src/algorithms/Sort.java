package algorithms;

import java.util.Observable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import main.PanelUI;
import main.SortVisualisationPanel;
import main.Statics.SortAlgorithm;

/*
 * This software is licensed under the MIT License.
 * Copyright 2018, Maurice Koch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

public abstract class Sort extends Observable implements Runnable {

    protected static int gElement[];
    protected static long delayMs = 5;
    protected static int delayNs = 0;
    protected static int INSTRUCTION_UNIT = 5;
    protected volatile boolean stop = false, pause = false, flashing = true, executeNextStep = false;

    protected String name;
    protected int elements[];
    protected int iterates, accesses, comparisons;
    protected SortVisualisationPanel svp;
    protected PanelUI panelUI;
    private int instructionCount = 0;


    /*
     * Each object hold its own SortVisualisationPanel and redirect every
     * changes that are done on the sortlist
     */
    public Sort(SortVisualisationPanel svp) {
        this.svp = svp;
    }

    /*
     * a copy of the static sortlist is created
     */
    public Sort() {
        this.elements = new int[Sort.gElement.length];
        System.arraycopy(Sort.gElement, 0, elements, 0, Sort.gElement.length);
    }

    /**
     *
     */
    public void initElements() {
        this.elements = new int[Sort.gElement.length];
        System.arraycopy(Sort.gElement, 0, elements, 0, Sort.gElement.length);

        iterates = 0;
        accesses = 0;
        comparisons = 0;
        svp.setElements(this.elements);
    }

    /*
     * This method is called in the run method from every algorithm that is running
     * This is needed to provide the start/stop functionality that is based
     * on locks and condition.
     *
     */
    protected void checkRunCondition() throws InterruptedException {
        while (pause && !executeNextStep) {
            Thread.sleep(100);
        }
        executeNextStep = false;
        Thread.sleep(delayMs, delayNs);
    }

    public void executeNextStep() {
        this.executeNextStep = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void pause() {
        pause = true;
    }

    public void setTerminationAnimationEnabled(boolean terminationAnimationEnabled) {
        this.flashing = terminationAnimationEnabled;
    }

    public void exchange(int x[], int i, int j) throws InterruptedException {
        int tmp = x[i];
        x[i] = x[j];
        x[j] = tmp;

        checkRunCondition();
        accesses += 3;
        panelUI.setInfo(accesses, comparisons);
        svp.visualExchange(i, j);
    }

    public void exchange(int i, int j) throws InterruptedException {
        exchange(elements, i, j);
    }

    public void insertByIndex(int x[], int i, int j) throws InterruptedException {
        svp.visualInsert(i, x[j]);
        panelUI.setInfo(accesses, comparisons++);
        accesses += 2;
        checkRunCondition();
        x[i] = x[j];
    }

    public void insertByIndex(int i, int j) throws InterruptedException {
        insertByIndex(elements, i, j);
    }

    public void insertByValue(int x[], int i, int value) throws InterruptedException {
        svp.visualInsert(i, value);
        panelUI.setInfo(accesses, comparisons++);
        accesses++;
        checkRunCondition();
        x[i] = value;
    }

    public void insertByValue(int i, int value) throws InterruptedException {
        insertByValue(elements, i, value);
    }


    public void manualInstruction(int count) throws InterruptedException {
        instructionCount += count;
        if (instructionCount > INSTRUCTION_UNIT) {
            while (instructionCount >= INSTRUCTION_UNIT) {
                checkRunCondition();
                instructionCount -= INSTRUCTION_UNIT;
            }
        }
    }

    public void manualInstructionIncrement() throws InterruptedException {
        manualInstruction(1);
    }

    public int compare(int x[], int i, int j) throws InterruptedException {
        int result = 0;

        if (x[i] > x[j])
            result = 1;
        if (x[j] > x[i])
            result = -1;

        checkRunCondition();
        accesses += 2;
        comparisons++;
        panelUI.setInfo(accesses, comparisons);
        svp.visualCmp(i, j, false);

        return result;
    }

    public int compare(int i, int j) throws InterruptedException {
        return compare(elements, i, j);
    }


    /**
     *
     */
    public void resume() {
        pause = false;

    }

    /*
     * is especially needed for the controller
     */
    public boolean isStopped() {
        return stop;
    }

    /**
     * @param svp
     * @param panelUI
     */
    public void setSortVisualisationPanel(SortVisualisationPanel svp, PanelUI panelUI) {
        this.svp = svp;
        this.panelUI = panelUI;
        svp.setElements(elements);
    }

    /**
     * @return
     */
    public PanelUI getPanelUI() {
        return panelUI;
    }

    /*
     * the specific algorithm name (identifying is needed for the info dialog)
     */
    public abstract SortAlgorithm getAlgorithmName();

    /**
     * is used to apply modifications on the 'SortVisualisationPanel' object
     * for e.g enable the remove button
     */
    public SortVisualisationPanel getSortVisualisationPanel() {
        return svp;
    }

    /*
     * Sorting list, that can accessed from every object, in order to create a copy
     */
    public static void setElements(int elements[]) {
        Sort.gElement = elements;

    }

    /*
     * delayNs  set the delay(nanoseconds) for all threads
     */
    public static void setDelayNs(int delayNs) {
        Sort.delayNs = delayNs;
    }

    /*
     * delayNs  set the delay(milliseconds) for all threads
     */
    public static void setDelayMs(long delayMs) {
        Sort.delayMs = delayMs;
    }

    /*
     * is used for displaying the sorting list in 'EnterDialog'
     * and for saving the number of elements in the configuration file
     */
    public static int[] getElements() {
        return Sort.gElement;
    }

    /*
     *  The delay is saved in the configuration file
     */
    public static long getDelayMs() {
        return Sort.delayMs;
    }

    /*
     *  The delay is saved in the configuration file
     */
    public static long getDelayNs() {
        return Sort.delayNs;
    }

}
