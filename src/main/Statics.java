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

public class Statics {
	

	public static enum SORTALGORITHMS {
		Bogosort,Bubblesort,Combsort,Heapsort,Insertionsort,Introsort,Mergesort,
		Quicksort_FIXED, Quicksort_RANDOM, Quicksort_MO3, DualPivotQuicksort, Selectionsort,Shakersort,Shellsort;
		
		public static int length(){
			return SORTALGORITHMS.values().length;
		}
	};

	// actionlistener use actioncommands 
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

	public static final String LANG_DE = "action_de", LANG_EN = "action_en", LANG_FR = "action_fr";
	public static final String DIALOG_EXIT = "action_exitDialog";


	

}
