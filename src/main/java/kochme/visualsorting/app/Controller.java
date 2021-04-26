package kochme.visualsorting.app;

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


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import kochme.visualsorting.algorithms.*;
import kochme.visualsorting.dialogs.*;

import kochme.visualsorting.ui.FramedElementsCanvas;
import kochme.visualsorting.ui.MainWindow;


public class Controller implements ComponentListener, ActionListener, WindowListener, OperationExecutorListener {
    private boolean pausedByUser = false;
    private final List<OperationExecutor> operationExecutors;
    private final BlockingQueue<Runnable> tasks;
    private final Timer timer;

    private MainWindow mainWindow;
    private ExecutorService executor;
    private volatile int threadsAlive;

    private int[] elements;
    private MainWindow.State state;

    public Controller(int[] initElements) {
        state = MainWindow.State.EMPTY;
        elements = initElements;
        tasks = new LinkedBlockingQueue<>();
        operationExecutors = new ArrayList<>();
        executor = Executors.newCachedThreadPool();
        timer = new Timer();
    }

    public void setView(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        mainWindow.addComponentListener(this);
    }

    public void reset() {
        operationExecutors.forEach(OperationExecutor::reset);
        try {
            executor.shutdownNow();
            executor.awaitTermination(10000, TimeUnit.MILLISECONDS);

            if (!executor.isTerminated()) {
                System.err.println("Attempt to interrupt active threads failed (after 2000 ms timeout). Try again, please.");
                return;
            }

            timer.reset();
            operationExecutors.forEach(OperationExecutor::initElements);
            pausedByUser = false;

            state = MainWindow.State.STOPPED;
            mainWindow.setState(state);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case Consts.ADD_SORT:
                Consts.SortAlgorithm selectedSort = mainWindow.getSelectedSort();
                FramedElementsCanvas sortPanel = new FramedElementsCanvas(selectedSort, mainWindow.getWidth(), mainWindow.getHeight());

                final OperationExecutor operationExecutor = new OperationExecutor(sortPanel);
                final SortAlgorithm sort;

                operationExecutor.setOperationExecutorListener(this);
                operationExecutor.initElements(elements);
                operationExecutor.setDelay(InternalConfig.getExecutionSpeedDelayMs(), InternalConfig.getExecutionSpeedDelayNs());

                if (selectedSort.equals(Consts.SortAlgorithm.Heapsort))
                    sort = new HeapSort(operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Bubblesort))
                    sort = new BubbleSort(operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Quicksort_FIXED))
                    sort = new QuickSort(QuickSort.PivotStrategy.FIXED, operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Quicksort_RANDOM))
                    sort = new QuickSort(QuickSort.PivotStrategy.RANDOM, operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Quicksort_MO3))
                    sort = new QuickSort(QuickSort.PivotStrategy.MO3, operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Combsort))
                    sort = new CombSort(operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Selectionsort))
                    sort = new SelectionSort(operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Shakersort))
                    sort = new ShakerSort(operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Mergesort))
                    sort = new MergeSort(operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Shellsort))
                    sort = new ShellSort(operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Insertionsort))
                    sort = new InsertionSort(operationExecutor);
                else if (selectedSort.equals(Consts.SortAlgorithm.Bogosort))
                    sort = new BogoSort(operationExecutor);
                else
                    sort = new HeapSort(operationExecutor);

                sortPanel.setActionListenerForRemoveAction(
                        e1 -> {
                            if (operationExecutors.size() > 0) {
                                mainWindow.removeSortVisualizationPanel(((FramedElementsCanvas) e1.getSource()));
                                operationExecutors.remove(operationExecutor);
                                tasks.remove(sort);
                            }
                            if (operationExecutors.isEmpty()) {
                                state = MainWindow.State.EMPTY;
                                mainWindow.setState(state);
                            }
                        });

                operationExecutors.add(operationExecutor);
                mainWindow.addSortVisualizationPanel(sortPanel);
                tasks.add(sort);
                mainWindow.updateSize();
                state = MainWindow.State.STOPPED;
                mainWindow.setState(state);
                break;
            case Consts.START:
                if (state == MainWindow.State.RUNNING) {
                    operationExecutors.forEach(OperationExecutor::pause);
                    timer.stop();
                    pausedByUser = true;
                    state = MainWindow.State.PAUSED;
                    mainWindow.setState(state);
                } else if (state == MainWindow.State.PAUSED) {
                    operationExecutors.forEach(OperationExecutor::resume);
                    state = MainWindow.State.RUNNING;
                    mainWindow.setState(state);
                    timer.start();
                    pausedByUser = false;
                } else if (state == MainWindow.State.STOPPED) {
                    if (executor.isTerminated()) {
                        executor = Executors.newCachedThreadPool();
                    }
                    operationExecutors.forEach(OperationExecutor::initElements);
                    tasks.forEach(executor::execute);

                    threadsAlive = tasks.size();

                    timer.reset();
                    timer.start();
                    state = MainWindow.State.RUNNING;
                    mainWindow.setState(state);
                }
                break;
            case Consts.RESET:
                reset();
                break;
            case Consts.AUTO_PAUSE:
                InternalConfig.toggleAutoPause();
                break;
            case Consts.NEW_ELEMENTS:
                EnterDialog.getInstance(this, 500, 300);
                break;
            case Consts.ABOUT:
                AboutDialog.getInstance(400, 470);
                break;
            case Consts.NEXT_ITERATION:
                operationExecutors.forEach(OperationExecutor::executeNextStep);
                break;
            case Consts.DELAY:
                DelayDialog.getInstance(this, 320, 150);
                break;
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        timer.stop();
        InternalConfig.saveChanges();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        if (InternalConfig.isAutoPauseEnabled() && !pausedByUser && state == MainWindow.State.PAUSED) {
            if (threadsAlive != 0) {
                timer.start();
                operationExecutors.forEach(OperationExecutor::resume);
            }
            state = MainWindow.State.RUNNING;
            mainWindow.setState(state);
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        if (InternalConfig.isAutoPauseEnabled() && state == MainWindow.State.RUNNING) {
            if (threadsAlive != 0) {
                operationExecutors.forEach(OperationExecutor::pause);
                timer.stop();
                state = MainWindow.State.PAUSED;
                mainWindow.setState(state);
            }
        }
    }

    public void executionSpeedChanged(int delayMs, int delayNs) {
        InternalConfig.setExecutionSpeedParameters(delayMs, delayNs);
        operationExecutors.forEach(v -> v.setDelay(delayMs, delayNs));
    }

    public void elementsChanged(int[] elements) {
        this.elements = elements;
        InternalConfig.setNumberOfElements(elements.length);
        mainWindow.updateNumberOfElements(elements.length);
        operationExecutors.forEach(v -> v.initElements(elements));
        reset();
    }


    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        mainWindow.updateSize();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public long terminated() {
        if (--threadsAlive == 0) {
            EventQueue.invokeLater(() -> {
                state = MainWindow.State.STOPPED;
                mainWindow.setState(state);
            });
            timer.stop();
        }
        return timer.getLeftMs();
    }


    private class Timer {
        private final static int TIMER_DELAY_MS = 100;
        private final javax.swing.Timer appTimer;
        private int leftMs;

        public Timer() {
            leftMs = 0;
            appTimer = new javax.swing.Timer(TIMER_DELAY_MS, e -> {
                leftMs += TIMER_DELAY_MS;
                mainWindow.setExecutionTime(leftMs);
            });
            if (mainWindow != null) mainWindow.setExecutionTime(0);
        }

        public int getLeftMs() {
            return leftMs;
        }

        public void reset() {
            leftMs = 0;
        }

        public void start() {
            appTimer.start();
        }

        public void stop() {
            appTimer.stop();
        }
    }
}
