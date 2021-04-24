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

import java.util.HashMap;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;
//import com.sun.tools.javac.comp.Enter;

import kochme.visualsorting.dialogs.DelayDialog;
import kochme.visualsorting.dialogs.EnterDialog;
import kochme.visualsorting.app.Consts.SortAlgorithm;
import kochme.visualsorting.dialogs.InfoDialog;
import kochme.visualsorting.ui.Window;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException
                    | InstantiationException
                    | IllegalAccessException
                    | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }

        System.setProperty("apple.laf.useScreenMenuBar", "true");

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-configdir:")) {
                InternalConfig.setConfigFileDirectory(args[i].subSequence("-configdir:".length(), args[i].length()).toString());
            }
        }

        InternalConfig.loadConfigFile();

        int[] initElements = Utils.getRandomSequence(InternalConfig.getNumberOfElements());

        DelayDialog.setDelayMs(InternalConfig.getExecutionSpeedDelayMs());
        DelayDialog.setDelayNs(InternalConfig.getExecutionSpeedDelayNs());
        EnterDialog.setElements(initElements);

        // this font is used under the GPL from google fonts under 'OpenSans'
        Window.setComponentFont("/Fonts/OpenSans-Regular.ttf");
        Window.setInfoFont("/Fonts/Oxygen-Regular.ttf", 30f);

        UIManager.put("OptionPane.messageFont", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("Button.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("EditorPane.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("ComboBox.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("Label.font", new FontUIResource(Window.getComponentFont(12f)));
        UIManager.put("MenuBar.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("MenuItem.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("RadioButton.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("CheckBox.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("RadioButtonMenuItem.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("CheckBoxMenuItem.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("TextField.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("TitledBorder.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("Menu.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("Spinner.font", new FontUIResource(Window.getComponentFont(13f)));
        UIManager.put("Slider.font", new FontUIResource(Window.getComponentFont(13f)));

        // hashmap for resolving sort into the respective infopage file
        HashMap<SortAlgorithm, String> map = new HashMap<>();
        map.put(SortAlgorithm.Bubblesort, "infopage_bubblesort.html");
        map.put(SortAlgorithm.Combsort, "infopage_combsort.html");
        map.put(SortAlgorithm.Heapsort, "infopage_heapsort.html");
        map.put(SortAlgorithm.Insertionsort, "infopage_insertionsort.html");
        map.put(SortAlgorithm.Mergesort, "infopage_mergesort.html");
        map.put(SortAlgorithm.Shakersort, "infopage_shakersort.html");
        map.put(SortAlgorithm.Shellsort, "infopage_shellsort.html");
        map.put(SortAlgorithm.Bogosort, "infopage_bogosort.html");
        //map.put(SortAlgorithm.Introsort, "infopage_introsort.html");
        map.put(SortAlgorithm.Selectionsort, "infopage_selectionsort.html");
        map.put(SortAlgorithm.Quicksort_FIXED, "infopage_quicksort.html");
        map.put(SortAlgorithm.Quicksort_RANDOM, "infopage_quicksort.html");
        map.put(SortAlgorithm.Quicksort_MO3, "infopage_quicksort.html");

        InfoDialog.initInfoPageResolver(map);
        Controller controller = new Controller(initElements);
        Window window = new Window(controller, "Visual Sorting - ".concat(InternalConfig.getVersion()), 1200, 800);
        controller.setView(window);
    }
}
