package kochme.visualsorting.ui;
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
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.swing.*;


public class ElementsCanvas extends JPanel {
    private static final int preferredGapSize = 1, offsetY = 20;
    private static final int visualTerminationTime = 800;   // ms

    private int width, height;
    private int refWidth, refHeight;
    private int margin = 7;
    private int gapSize = 0;
    private int lstIndex1 = -1;
    private int lstIndex2 = -1;
    private int lstInsert = -1;

    private BufferedImage buffer;
    private Graphics2D graphics2D;
    private int[] elements;
    private int maxElement;

    private Color[] colors;
    private final GraphicsEnvironment graphicsEnvironment;

    public ElementsCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

        try {
            buffer = createOptimizedBufferedImage(width, height);
            graphics2D = (Graphics2D) buffer.getGraphics();
            graphics2D.setFont(MainWindow.getComponentFont(12f));
            graphics2D.clearRect(0, 0, width, height);
            this.setOpaque(false);
            initColors();
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, width, height, this);
    }

    private void initColors() {
        float[] hsb = new float[3];
        colors = new Color[3];

        Color.RGBtoHSB(207, 26, 203, hsb);
        colors[0] = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);

        Color.RGBtoHSB(86, 0, 234, hsb);
        colors[1] = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);

        Color.RGBtoHSB(43, 144, 245, hsb);
        colors[2] = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

    // See : http://www.oracle.com/technetwork/java/perf-graphics-135933.html - Tips for Achieving Better Performance
    private BufferedImage createOptimizedBufferedImage(int width, int height) throws HeadlessException {
        GraphicsDevice gs = graphicsEnvironment.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        return gc.createCompatibleImage(
                width, height, Transparency.BITMASK);
    }

    private void undoLastHighlight() {
        if (lstIndex1 >= 0 && lstIndex2 >= 0) {
            int x1 = (lstIndex1 * (refWidth + gapSize)) + margin;
            int x2 = (lstIndex2 * (refWidth + gapSize)) + margin;
            int y1 = (height - (refHeight * elements[lstIndex1])) - offsetY;
            int y2 = (height - (refHeight * elements[lstIndex2])) - offsetY;
            int h1 = refHeight * elements[lstIndex1];
            int h2 = refHeight * elements[lstIndex2];

            float b = ((float) elements[lstIndex1]) / maxElement;
            graphics2D.setColor(getBarColor(b));
            graphics2D.fillRect(x1, y1, refWidth, h1);

            b = ((float) elements[lstIndex2]) / maxElement;
            graphics2D.setColor(getBarColor(b));
            graphics2D.fillRect(x2, y2, refWidth, h2);
        }
    }

    public void visualExchange(int c1, int c2) {
        undoLastHighlight();

        int tmp = elements[c1];
        elements[c1] = elements[c2];
        elements[c2] = tmp;

        int x1 = (c1 * (refWidth + gapSize)) + margin;
        int x2 = (c2 * (refWidth + gapSize)) + margin;
        int y1 = (height - (refHeight * elements[c1])) - offsetY;
        int y2 = (height - (refHeight * elements[c2])) - offsetY;
        int h1 = refHeight * elements[c1];
        int h2 = refHeight * elements[c2];

        graphics2D.clearRect(x1, y2, refWidth, h2 + 5);
        graphics2D.clearRect(x2, y1, refWidth, h1 + 5);

        graphics2D.setColor(Color.RED);
        graphics2D.fillRect(x1, y1, refWidth, h1);
        graphics2D.fillRect(x2, y2, refWidth, h2);

        // Draw lines, indicating exchange operation
        int xi1 = (int) (c1 * (refWidth + gapSize) + (refWidth * 0.5)) + margin;
        int xi2 = (int) (c2 * (refWidth + gapSize) + (refWidth * 0.5)) + margin;

        graphics2D.setColor(Color.BLUE);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawLine(xi1, height - offsetY + 5, xi1,
                (int) (height - (offsetY * 0.70)));
        graphics2D.drawLine(xi2, height - offsetY + 5, xi2,
                (int) (height - (offsetY * 0.70)));
        graphics2D.drawLine(xi1, (int) (height - (offsetY * 0.70)), xi2,
                (int) (height - (offsetY * 0.70)));

        lstIndex1 = c1;
        lstIndex2 = c2;
        repaint();
    }

    public void visualInsert(int c, int value) {
        if (lstInsert >= 0) {
            float b = ((float) elements[lstInsert]) / maxElement;
            graphics2D.setColor(getBarColor(b));
            graphics2D.fillRect((lstInsert * (refWidth + gapSize)) + margin,
                    (height - (refHeight * elements[lstInsert])) - offsetY,
                    refWidth, refHeight * elements[lstInsert]);
        }

        graphics2D.setColor(Color.darkGray);
        graphics2D.clearRect((c * (refWidth + gapSize)) + margin,
                (height - (refHeight * elements[c])) - offsetY, refWidth,
                (refHeight * elements[c]) + 5);

        graphics2D.setColor(Color.GREEN);
        graphics2D.fillRect((c * (refWidth + gapSize)) + margin,
                (height - (refHeight * value)) - offsetY, refWidth, refHeight
                        * value);

        lstInsert = c;
        elements[c] = value;
        repaint();
    }

    public void visualCompare(int c1, int c2, boolean isPivot) {
        graphics2D.clearRect(0, height - offsetY, width, height);
        undoLastHighlight();

        int x1 = (c1 * (refWidth + gapSize)) + margin;
        int x2 = (c2 * (refWidth + gapSize)) + margin;
        int y1 = (height - (refHeight * elements[c1])) - offsetY;
        int y2 = (height - (refHeight * elements[c2])) - offsetY;
        int h1 = refHeight * elements[c1];
        int h2 = refHeight * elements[c2];

        graphics2D.setColor(Color.RED);
        graphics2D.fillRect(x1, y1, refWidth, h1);

        if (isPivot) {
            graphics2D.setColor(Color.CYAN);
        }
        graphics2D.fillRect(x2, y2, refWidth, h2);

        lstIndex1 = c1;
        lstIndex2 = c2;
        repaint();
    }

    public void visualTermination() {
        graphics2D.clearRect(0, height - offsetY, width, height);
        graphics2D.setColor(new Color(100, 100, 100));

        for (int i = 0; i < elements.length; i++) {
            graphics2D.fillRect((i * (refWidth + gapSize)) + margin,
                    (height - (refHeight * elements[i])) - offsetY, refWidth,
                    refHeight * elements[i]);

        }
        repaint();
    }

    public Color getBarColor(float relativePosition) {
        if (relativePosition <= 0.33) {
            return colors[0];
        }
        if (relativePosition <= 0.66) {
            return colors[1];
        }
        return colors[2];
    }

    public void updatePanelSize() {
        try {
            buffer = createOptimizedBufferedImage(width, height);
            graphics2D = (Graphics2D) buffer.getGraphics();
            graphics2D.setBackground(new Color(0, 0, 0, 0));
            graphics2D.clearRect(0, 0, width, height);
            graphics2D.setFont(MainWindow.getComponentFont(14f));
            drawElements();
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }

    public void updateBarSize() {
        if (maxElement > 0) {
            int marginTop = 25;
            refHeight = (height - offsetY - marginTop)
                    / maxElement;
            refWidth = (width - (elements.length * preferredGapSize))
                    / elements.length;

            if (refHeight <= 0)
                refHeight = 1;
            if (refWidth <= 0) {
                double newBorder = ((elements.length - width) / ((double) (-1) * elements.length));
                gapSize = (int) newBorder;
                refWidth = 1;
            } else
                gapSize = preferredGapSize;
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

    public void setElements(int[] elements) {
        this.elements = new int[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
        this.maxElement = Arrays.stream(elements).max().getAsInt();

        lstIndex1 = -1;
        lstIndex2 = -1;
        lstInsert = -1;
        int lstPivot = -1;

        updateBarSize();
        drawElements();
    }

    private void drawElements() {
        graphics2D.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
        graphics2D.setColor(new Color(100, 100, 100));

        for (int i = 0; i < elements.length; i++) {
            float b = ((float) elements[i]) / maxElement;
            graphics2D.setColor(getBarColor(b));
            graphics2D.fillRect((i * (refWidth + gapSize)) + margin,
                    (height - (refHeight * elements[i])) - offsetY, refWidth,
                    refHeight * elements[i]);

        }
        repaint();
    }
}
