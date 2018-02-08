package algorithms;

import java.util.Observable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import main.PanelUI;
import main.SortVisualisationPanel;
import main.Statics.SORTALGORITHMS;

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
    protected volatile boolean stop = false, pause = false, flashing = true, executeNextStep = false;

    protected int elements[];
    protected int iterates, accesses, comparisons;
    protected SortVisualisationPanel svp;
    protected PanelUI panelUI;


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

    public void pause() {
        pause = true;
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
    public abstract SORTALGORITHMS getAlgorithmName();

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
