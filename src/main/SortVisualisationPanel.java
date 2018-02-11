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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JPanel;

import algorithms.Sort;
import com.sun.deploy.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;

/**
 * This class is used to display the animation in a panel. The animation is
 * based on bars with different heights. Each bar is representing a different
 * value in the sorting list.
 *
 * @author maurice koch
 * @version beta
 * @category graphics
 **/
public class SortVisualisationPanel extends JPanel {

    private static Color backgroundColor = Color.GREEN;
    private static final int preferredGapSize = 1, offsetY = 20;
    private static final int visualTerminationTime = 800;   // ms

    private int width, height;
    private int refWidth, refHeight;
    private int margin = 7, gapSize = 0, marginTop = 25;
    private int lstIndex1 = -1, lstIndex2 = -1, lstInsert = -1, lstPivot = -1;

    private BufferedImage buffer;
    private Graphics2D gbuffer;
    private int elements[];
    private int maxElement;

    public SortVisualisationPanel(ActionListener listener, String selectedSort,
                                  int width, int height) {

        this.width = width;
        this.height = height;
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        gbuffer = (Graphics2D) buffer.getGraphics();
        gbuffer.setFont(Window.getComponentFont(12f));
        gbuffer.setBackground(backgroundColor);
        gbuffer.clearRect(0, 0, width, height);
        this.setOpaque(false);
    }

    public SortVisualisationPanel(int width, int height) {

        this.width = width;
        this.height = height;
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        gbuffer = (Graphics2D) buffer.getGraphics();
        gbuffer.setFont(Window.getComponentFont(12f));
        gbuffer.setBackground(backgroundColor);
        gbuffer.clearRect(0, 0, width, height);
        this.setOpaque(false);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, width, height, this);

    }

    public void visualPivot(int pivotIndex) {

        int x = (pivotIndex * (refWidth + gapSize)) + margin;

        int y = (height - (refHeight * elements[pivotIndex])) - offsetY;
        int h = refHeight * elements[pivotIndex];

        gbuffer.setColor(Color.CYAN);
        gbuffer.fillRect(x, y, refWidth, h);

        if (lstPivot != -1 && lstPivot != pivotIndex) {
            x = (lstPivot * (refWidth + gapSize)) + margin;

            y = (height - (refHeight * elements[lstPivot])) - offsetY;
            h = refHeight * elements[lstPivot];

            float b = ((float) elements[lstPivot]) / maxElement;
            gbuffer.setColor(getBarColor(b));
            gbuffer.fillRect(x, y, refWidth, h);
        }

        lstPivot = pivotIndex;

    }

    public void visualInsert(int c, int value) {

        if (lstInsert >= 0) {
            float b = ((float) elements[lstInsert]) / maxElement;
            gbuffer.setColor(getBarColor(b));
            gbuffer.fillRect((lstInsert * (refWidth + gapSize)) + margin,
                    (height - (refHeight * elements[lstInsert])) - offsetY,
                    refWidth, refHeight * elements[lstInsert]);

        }

        gbuffer.setColor(Color.darkGray);
        gbuffer.setBackground(new Color(0, 0, 0, 0));
        gbuffer.clearRect((c * (refWidth + gapSize)) + margin,
                (height - (refHeight * elements[c])) - offsetY, refWidth,
                (refHeight * elements[c]) + 5);

        gbuffer.setColor(Color.GREEN);
        gbuffer.fillRect((c * (refWidth + gapSize)) + margin,
                (height - (refHeight * value)) - offsetY, refWidth, refHeight
                        * value);

        lstInsert = c;

        repaint();

    }

    public Color getBarColor(float relativePosition) {
        if (relativePosition <=  0.33) {
            float hsb[] = new float[3];
            Color.RGBtoHSB(207,26,203, hsb);
            return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        }
        if (relativePosition <= 0.66) {
            float hsb[] = new float[3];
            Color.RGBtoHSB(86,0,234, hsb);
            return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        }

        float hsb[] = new float[3];
        Color.RGBtoHSB(43,144,245, hsb);
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

    public void visualCmp(int c1, int c2, boolean changed) {

        // long t = System.currentTimeMillis();
        int x1, x2, y1, y2, h1, h2;

        // nur unteren bereich loeschen
        gbuffer.clearRect(0, height - offsetY, width, height);

        if (lstIndex1 >= 0 && lstIndex2 >= 0) {

            x1 = (lstIndex1 * (refWidth + gapSize)) + margin;
            x2 = (lstIndex2 * (refWidth + gapSize)) + margin;
            y1 = (height - (refHeight * elements[lstIndex1])) - offsetY;
            y2 = (height - (refHeight * elements[lstIndex2])) - offsetY;
            h1 = refHeight * elements[lstIndex1];
            h2 = refHeight * elements[lstIndex2];


            float b = ((float) elements[lstIndex1]) / maxElement;
            gbuffer.setColor(getBarColor(b));

            gbuffer.fillRect(x1, y1, refWidth, h1);

            b = ((float) elements[lstIndex2]) / maxElement;
            gbuffer.setColor(getBarColor(b));

            gbuffer.fillRect(x2, y2, refWidth, h2);

        }

        x1 = (c1 * (refWidth + gapSize)) + margin;
        x2 = (c2 * (refWidth + gapSize)) + margin;
        y1 = (height - (refHeight * elements[c1])) - offsetY;
        y2 = (height - (refHeight * elements[c2])) - offsetY;
        h1 = refHeight * elements[c1];
        h2 = refHeight * elements[c2];

        if (changed) {

            gbuffer.setColor(Color.darkGray);
            gbuffer.setBackground(new Color(0, 0, 0, 0));
            gbuffer.clearRect(x1, y2, refWidth, h2 + 5);
            gbuffer.clearRect(x2, y1, refWidth, h1 + 5);

            signalExchangedElements(c1, c2, refWidth);
        }

        gbuffer.setColor(Color.RED);
        gbuffer.fillRect(x1, y1, refWidth, h1);
        gbuffer.fillRect(x2, y2, refWidth, h2);

        lstIndex1 = c1;
        lstIndex2 = c2;
        // System.out.println("TIME: "+(System.currentTimeMillis()-t));
        repaint();

    }

    public void visualTermination() {

        // nur unteren bereich loeschen
        gbuffer.clearRect(0, height - offsetY, width, height);
        gbuffer.setColor(new Color(100, 100, 100));

        double delayNs = (visualTerminationTime * 1000.0 / elements.length);

        for (int i = 0; i < elements.length; i++) {

            gbuffer.fillRect((i * (refWidth + gapSize)) + margin,
                    (height - (refHeight * elements[i])) - offsetY, refWidth,
                    refHeight * elements[i]);

            try {
                Thread.sleep((long) (delayNs / 1000.), ((int) delayNs) % 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();

        }

    }

    public void updatePanelSize() {

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        gbuffer = (Graphics2D) buffer.getGraphics();
        gbuffer.setBackground(backgroundColor);
        gbuffer.clearRect(0, 0, width, height);
        gbuffer.setFont(Window.getComponentFont(14f));
        drawElements();

    }

    public void updateBarSize() {

        int elements[] = Sort.getElements();

        if (MathFunc.getMax(elements) > 0) {

            refHeight = (height - offsetY - marginTop)
                    / MathFunc.getMax(elements);
            refWidth = (width - (elements.length * preferredGapSize))
                    / elements.length;

            if (refHeight <= 0)
                refHeight = 1;
            if (refWidth <= 0) {

                double newBorder = ((elements.length - width) / ((double) (-1) * elements.length));
                if (newBorder > 0)
                    gapSize = (int) newBorder;
                else
                    gapSize = 1;
                refWidth = 1;
            } else
                gapSize = 1;

            margin = (width - (elements.length * (refWidth + gapSize))) / 2;

        } else {
            System.err.println("Bar size invalid");
        }
    }

    public void updateSize() {

        this.width = this.getWidth();
        this.height = this.getHeight();
        updateBarSize();
        updatePanelSize();

    }

    public void setElements(int elements[]) {

        this.elements = elements;
        this.maxElement = Arrays.stream(elements).max().getAsInt();

        lstIndex1 = -1;
        lstIndex2 = -1;
        lstInsert = -1;
        lstPivot = -1;

        drawElements();

    }

    private void drawElements() {

        gbuffer.setBackground(new Color(0, 0, 0, 0));
        gbuffer.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
        gbuffer.setColor(new Color(100, 100, 100));


        for (int i = 0; i < elements.length; i++) {
            float b = ((float) elements[i]) / maxElement;
            gbuffer.setColor(getBarColor(b));
            gbuffer.fillRect((i * (refWidth + gapSize)) + margin,
                    (height - (refHeight * elements[i])) - offsetY, refWidth,
                    refHeight * elements[i]);

        }

        repaint();
    }

    private void signalExchangedElements(int i1, int i2, int refWidth) {

        int x1 = (int) (i1 * (refWidth + gapSize) + (refWidth * 0.5)) + margin;
        int x2 = (int) (i2 * (refWidth + gapSize) + (refWidth * 0.5)) + margin;

        gbuffer.setColor(Color.BLUE);
        gbuffer.setStroke(new BasicStroke(2));
        gbuffer.drawLine(x1, height - offsetY + 5, x1,
                (int) (height - (offsetY * 0.70)));
        gbuffer.drawLine(x2, height - offsetY + 5, x2,
                (int) (height - (offsetY * 0.70)));
        gbuffer.drawLine(x1, (int) (height - (offsetY * 0.70)), x2,
                (int) (height - (offsetY * 0.70)));

    }

    public static void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

}
