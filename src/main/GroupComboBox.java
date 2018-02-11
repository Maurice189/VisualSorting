package main;

import javax.swing.*;
import java.awt.*;

public class GroupComboBox extends JComboBox {

    public GroupComboBox() {
        setModel(new ExtendedComboBoxModel());
        setRenderer(new ExtendedListCellRenderer());
    }

    public void addDelimiter(String text) {
        this.addItem(new Delimiter(text));
    }

    private static class ExtendedComboBoxModel extends DefaultComboBoxModel {
        @Override
        public void setSelectedItem(Object anObject) {
            if (!(anObject instanceof Delimiter)) {
                super.setSelectedItem(anObject);
            } else {
                int index = getIndexOf(anObject);
                if (index < getSize()) {
                    setSelectedItem(getElementAt(index+1));
                }
            }
        }
    }

    private static class ExtendedListCellRenderer
            extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            if (!(value instanceof Delimiter)) {
                return super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);
            } else {
                JLabel label = new JLabel(value.toString());
                Font f = label.getFont();
                label.setFont(f.deriveFont(f.getStyle()
                        | Font.BOLD));
                return label;
            }
        }
    }

    private static class Delimiter {
        private String text;

        private Delimiter(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text.toString();
        }
    }
}
