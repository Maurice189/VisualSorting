package kochme.visualsorting.ui.dialogs;

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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import kochme.visualsorting.app.Controller;
import kochme.visualsorting.app.Configuration;

public final class ExecutionSpeedDialog extends JDialog implements ActionListener {
    private static ExecutionSpeedDialog instance;
    private final Controller controller;
    private JLabel delay;
    private JSlider slider;
    private JRadioButton ms, ns;
    private boolean active = true;

    private static int delayMs = 0, delayNs = 0;

    private ExecutionSpeedDialog(Controller controller, int width, int height) {
        this.controller = controller;
        setTitle("Execution Speed");
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initComponents();
    }

    protected void initComponents() {
        delay = new JLabel();
        slider = new JSlider(0, 100, 50);
        ms = new JRadioButton("ms");
        ms.addActionListener(this);
        ns = new JRadioButton("ns");
        ns.addActionListener(this);


        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));

        panel2.add(ms);
        panel2.add(ns);

        slider.setValue(Configuration.getExecutionSpeedDelayMs());
        delay.setText((String.valueOf(Configuration.getExecutionSpeedDelayMs())).concat(" ms : ")
                .concat(String.valueOf(Configuration.getExecutionSpeedDelayNs())).concat(" ns"));
        ms.setSelected(true);

        panel3.add(Box.createHorizontalGlue());
        panel3.add(delay);
        panel3.add(Box.createHorizontalGlue());
        panel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        setLayout(new BoxLayout(getContentPane(),
                BoxLayout.Y_AXIS));

        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        slider.addChangeListener(e -> {

            if (active) {
                if (ms.isSelected()) {
                    delayMs = slider.getValue();
                } else {
                    delayNs = slider.getValue();
                }
                delay.setText((String
                        .valueOf(delayMs)
                        .concat(" ms : ")
                        .concat(String.valueOf(delayNs))
                        .concat(" ns")));
                controller.executionSpeedChanged(delayMs, delayNs);
            }

        });


        add(panel3);
        add(Box.createVerticalStrut(4));
        add(panel2);
        add(Box.createVerticalStrut(8));
        add(slider);
        add(Box.createVerticalStrut(25));

        setResizable(false);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ns) {
            active = false;
            ms.setSelected(false);
            slider.setMajorTickSpacing(10);
            slider.setMaximum(999);
            slider.setMinimum(0);
            slider.setValue(delayNs);
            active = true;
        } else if (e.getSource() == ms) {
            active = false;
            ns.setSelected(false);
            slider.setMajorTickSpacing(5);
            slider.setMaximum(100);
            slider.setMinimum(0);
            slider.setValue(delayMs);
            active = true;
        }
    }

    public static void setDelayMs(int delayMs) {
        ExecutionSpeedDialog.delayMs = delayMs;
    }

    public static void setDelayNs(int delayNs) {
        ExecutionSpeedDialog.delayNs = delayNs;
    }

    public static ExecutionSpeedDialog getInstance(Controller controller, int width, int height) {
        if (instance == null) {
            instance = new ExecutionSpeedDialog(controller, width, height);
        }
        instance.setVisible(true);
        return instance;
    }
}