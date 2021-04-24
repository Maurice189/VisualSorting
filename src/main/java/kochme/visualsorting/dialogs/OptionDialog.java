package kochme.visualsorting.dialogs;

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

import kochme.visualsorting.app.Consts;

import java.awt.*;

import javax.swing.*;


public abstract class OptionDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    protected String title;

    public OptionDialog(String title, int width, int height, boolean enableLoadingAnimation) {
        this.title = title;

        final JLabel loadGif = new JLabel(new ImageIcon(
                Consts.class.getResource("/icons/loading.gif")),
                JLabel.CENTER);

        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        if (enableLoadingAnimation) {
            add(loadGif);
            setVisible(true);
            new Thread(() -> {
                initComponents();
                remove(loadGif);
                EventQueue.invokeLater(() -> setVisible(true));

            }).start();
        } else {
            initComponents();
        }

    }

    protected abstract void initComponents();

}
