package main;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import algorithms.BogoSort;
import algorithms.BubbleSort;
import algorithms.CombSort;
import algorithms.GnomeSort;
import algorithms.HeapSort;
import algorithms.InsertionSort;
import algorithms.IntroSort;
import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.RadixSort;
import algorithms.ShakerSort;
import algorithms.ShellSort;
import algorithms.Sort;
import dialogs.AboutDialog;
import dialogs.DelayDialog;
import dialogs.EnterDialog;
import dialogs.InfoDialog;
import dialogs.OptionDialog;
import main.InternalConfig.LANG;
import main.Statics.SORTALGORITHMS;

public class Controller implements Observer, ComponentListener, ActionListener, WindowListener {

    private enum State {RUNNING, PAUSED, RESET}

    private State state = State.RESET;
    private ArrayList<Sort> sortList;
    private LinkedList<OptionDialog> dialogs;

    private Window window;
    private LanguageFileXML langXMLInterface;
    private ExecutorService executor;
    private javax.swing.Timer appTimer;
    private int leftMs, leftSec, threadsAlive;

    /**
     * @param langXMLInterface
     */
    public Controller(LanguageFileXML langXMLInterface) {

        this.langXMLInterface = langXMLInterface;

        sortList = new ArrayList<Sort>();
        dialogs = new LinkedList<OptionDialog>();

        executor = Executors.newCachedThreadPool();
        createTimer();

    }

    /**
     * @param window
     */
    public void setView(Window window) {
        this.window = window;
        SortVisualisationPanel.setBackgroundColor(window.getBackground());
        window.addComponentListener(this);
        window.updateNumberOfElements(Sort.getElements().length);
    }

    /**
     *
     */
    public void reset() {
        for (Sort temp : sortList) {
            temp.deleteObservers();
            temp.getPanelUI().enableRemoveButton(true);
            temp.resume();
        }

        try {
            executor.shutdownNow();
            executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!executor.isTerminated())
            System.out.println("executor service isn't terminated !");

        for (Sort temp : sortList) {
            temp.initElements();
            temp.addObserver(this);
        }

        createTimer();

        window.unlockAddSort(true);
        threadsAlive = 0;
        state = State.RESET;
    }

    /**
     *
     */
    private void createTimer() {
        leftMs = 0;
        leftSec = 0;

        appTimer = new javax.swing.Timer(10, e -> {
            leftMs += 10;
            if (leftMs == 1000) {
                leftMs = 0;
                leftSec++;
            }
            window.setClockParam(leftSec, leftMs);
        });
        if (window != null) window.setClockParam(0, 0);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == Statics.ADD_SORT) {
            Sort sort;
            String selectedSort;

            for (Sort temp : sortList) {
                temp.initElements();
                threadsAlive = 0;
            }

            selectedSort = window.getSelectedSort();

            if (selectedSort.equals(SORTALGORITHMS.Heapsort.toString()))
                sort = new HeapSort();
            else if (selectedSort.equals(SORTALGORITHMS.Bubblesort.toString()))
                sort = new BubbleSort();
            else if (selectedSort.equals(SORTALGORITHMS.Quicksort.toString()))
                sort = new QuickSort();
            else if (selectedSort.equals(SORTALGORITHMS.Combsort.toString()))
                sort = new CombSort();
            else if (selectedSort.equals(SORTALGORITHMS.Gnomesort.toString()))
                sort = new GnomeSort();
            else if (selectedSort.equals(SORTALGORITHMS.Shakersort.toString()))
                sort = new ShakerSort();
            else if (selectedSort.equals(SORTALGORITHMS.Mergesort.toString()))
                sort = new MergeSort();
            else if (selectedSort.equals(SORTALGORITHMS.Radixsort.toString()))
                sort = new RadixSort();
            else if (selectedSort.equals(SORTALGORITHMS.Shellsort.toString()))
                sort = new ShellSort();
            else if (selectedSort.equals(SORTALGORITHMS.Insertionsort
                    .toString()))
                sort = new InsertionSort();
            else if (selectedSort.equals(SORTALGORITHMS.Bogosort
                    .toString()))
                sort = new BogoSort();
            else if (selectedSort.equals(SORTALGORITHMS.Introsort
                    .toString()))
                sort = new IntroSort();
            else
                sort = new HeapSort();

            sort.addObserver(this);
            sortList.add(sort);
            window.addNewSort(sort);
            resize();

        } else if (e.getActionCommand() == Statics.START) {
            if (state == State.RUNNING) {
                sortList.forEach(Sort::pause);
                window.unlockManualIteration(true);
                appTimer.stop();
                state = State.PAUSED;
            } else if (state == State.PAUSED) {
                sortList.forEach(Sort::resume);
                state = State.RUNNING;
                appTimer.start();
            } else if (state == State.RESET) {
                if (executor.isTerminated()) {
                    executor = Executors.newCachedThreadPool();
                }
                for (Sort temp : sortList) {
                    temp.initElements();
                    temp.getPanelUI().enableRemoveButton(false);
                    executor.execute(temp);
                    threadsAlive++;
                }

                window.unlockManualIteration(false);
                window.unlockAddSort(false);

                createTimer();
                appTimer.start();
                state = State.RUNNING;
            }
            window.toggleStartStop();
        } else if (e.getActionCommand() == Statics.RESET) {
            reset();
        } else if (e.getActionCommand() == Statics.AUTO_PAUSE) {
            InternalConfig.toggleAutoPause();
        } else if (e.getActionCommand() == Statics.NEW_ELEMENTS) {
            dialogs.add(EnterDialog.getInstance(this, 500, 300));
        } else if (e.getActionCommand() == Statics.ABOUT) {
            dialogs.add(AboutDialog.getInstance(400, 500));
        } else if (e.getActionCommand() == Statics.INFO) {

            SORTALGORITHMS selAlgorithm = sortList.get(
                    PanelUI.getReleasedID()).getAlgorithmName();

            dialogs.add(new InfoDialog(selAlgorithm, selAlgorithm
                    .toString(), 600, 370));
        } else if (e.getActionCommand() == Statics.LANG_DE) {

            InternalConfig.setLanguage(LANG.de);
            langXMLInterface.readXML(InternalConfig.getLanguageSetPath());
            window.updateLanguage();
            for (OptionDialog temp : dialogs)
                temp.updateComponentsLabel(); // update language on every open
            // dialog

        } else if (e.getActionCommand() == Statics.LANG_EN) {

            InternalConfig.setLanguage(LANG.en);
            langXMLInterface.readXML(InternalConfig.getLanguageSetPath());
            window.updateLanguage();
            // update language on every open dialog
            for (OptionDialog temp : dialogs)
                temp.updateComponentsLabel();


        } else if (e.getActionCommand() == Statics.LANG_FR) {

            InternalConfig.setLanguage(LANG.fr);
            langXMLInterface.readXML(InternalConfig.getLanguageSetPath());
            window.updateLanguage();
            for (OptionDialog temp : dialogs)
                temp.updateComponentsLabel(); // update language on every open
            // dialog

        }

        // just execute one more step
        else if (e.getActionCommand() == Statics.NEXT_ITERATION) {
            sortList.forEach(Sort::executeNextStep);
        }
        /**
         * It is quite inconvenient to figure out which
         * panel was removed. It's even not a good idea, to create
         * for each new panel a new handler. So all remove requests (fired events)
         * are redirected to this handler.
         */
        else if (e.getActionCommand() == Statics.REMOVE_SORT) {
            if (sortList.size() > 0) {

                int selPanel = PanelUI.getReleasedID();
                window.removeSort(selPanel);
                sortList.remove(selPanel);

                for (Sort temp : sortList) {
                    temp.getPanelUI().updateID();
                }
                PanelUI.updateCounter();
                resize();
            }
        } else if (e.getActionCommand() == Statics.DELAY) {
            dialogs.add(DelayDialog.getInstance(320, 150));
        } else if (e.getActionCommand() == Statics.ELEMENTS_SET) {

            window.updateNumberOfElements(Sort.getElements().length);
            for (Sort temp : sortList) {
                temp.getSortVisualisationPanel().updateBarSize();
            }
            reset();
        }
    }


    /*
     * Called when a algorithm terminates
     * (Oberserver)
     */
    @Override
    public void update(Observable o, Object arg) {

        /*
         * When all proccess has been terminated,
         * then the gui has to be updated (enable buttons etc.).
         *
         * Hence we're using swing (no sync. , no concurrent operations),
         * we need to invoke the gui update later,
         * instead of using the calling thread.
         */
        if (--threadsAlive == 0) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    window.toggleStartStop();
                    window.unlockAddSort(true);
                    window.unlockManualIteration(true);
                }
            });
            appTimer.stop();

            for (Sort temp : sortList) {
                temp.getPanelUI().enableRemoveButton(true);
            }

            state = State.RESET;
        }


        sortList.get((int) arg).getPanelUI().setDuration(leftSec, leftMs);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        appTimer.stop();

        for (OptionDialog temp : dialogs) {
            temp.dispose();
        }
        InternalConfig.saveChanges();
    }

    @Override
    public void windowActivated(WindowEvent e) {

        if (InternalConfig.isAutoPauseEnabled()) {
            if (threadsAlive != 0) {
                appTimer.start();
                for (Sort temp : sortList) {
                    temp.resume();
                }
            }
            window.appReleased();
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

        if (InternalConfig.isAutoPauseEnabled()) {
            sortList.forEach(sort -> sort.pause());
            if (threadsAlive != 0) {
                window.appStopped();
                appTimer.stop();
            }
        }
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

    /**
     *
     */
    private void resize() {
        if (!sortList.isEmpty()) {
            for (Sort tmp : sortList) {
                tmp.getSortVisualisationPanel().updateSize();
            }
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        resize();
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
}
