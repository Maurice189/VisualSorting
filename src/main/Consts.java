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

public class Consts {

    private Consts() {}

    public enum SortAlgorithm {
        Bogosort, Bubblesort, Combsort, Heapsort, Insertionsort,
        //Introsort,
        Mergesort,
        Quicksort_FIXED, Quicksort_RANDOM, Quicksort_MO3, Selectionsort, Shakersort, Shellsort;


        @Override
        public String toString() {
            String result = "";

            switch (this) {
                case Bogosort:
                    result = "Bogo sort";
                    break;
                case Bubblesort:
                    result = "Bubble sort";
                    break;
                case Combsort:
                    result = "Comb sort";
                    break;
                case Heapsort:
                    result = "Heap sort";
                    break;
                case Insertionsort:
                    result = "Insertion sort";
                    break;
                //case Introsort:
                //    result = "Intro sort";
                //    break;
                case Mergesort:
                    result = "Merge sort";
                    break;
                case Quicksort_FIXED:
                    result = "Quick sort (Fixed)";
                    break;
                case Quicksort_RANDOM:
                    result = "Quick sort (Random)";
                    break;
                case Quicksort_MO3:
                    result = "Quick sort (Mo3)";
                    break;
                case Selectionsort:
                    result = "Selection sort";
                    break;
                case Shakersort:
                    result = "Shaker sort";
                    break;
                case Shellsort:
                    result = "Shell sort";
                    break;
            }
            return result;
        }

        public static int length() {
            return SortAlgorithm.values().length;
        }
    }

    public static final String ADD_SORT = "action_add";
    public static final String REMOVE_SORT = "action_remove";
    public static final String START = "action_start";
    public static final String NEW_ELEMENTS = "action_setElements";
    public static final String NEXT_ITERATION = "action_nextIter";
    public static final String RESET = "action_reset";
    public static final String ELEMENTS_SET = "action_elements";

    public static final String DELAY = "action_delay";
    public static final String ABOUT = "action_about";
    public static final String INFO = "action_info";
    public static final String AUTO_PAUSE = "action_auto";

}
