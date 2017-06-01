/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter.Component;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author bnson
 * @param <E>
 */
public class CheckBoxCellRenderer<E extends CheckBoxNode> extends MouseAdapter implements ListCellRenderer<E> {

    private final JCheckBox checkBox = new JCheckBox();
    private int rollOverRowIndex = -1;

    @Override
    public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean selected, boolean cellHasFocus) {
        checkBox.setOpaque(true);
        if (selected) {
            checkBox.setBackground(list.getSelectionBackground());
            checkBox.setForeground(list.getSelectionForeground());
        } else {
            checkBox.setBackground(list.getBackground());
            checkBox.setForeground(list.getForeground());
        }
        checkBox.setSelected(value.selected);
        checkBox.getModel().setRollover(index == rollOverRowIndex);
        checkBox.setText(value.text);
        return checkBox;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (rollOverRowIndex >= 0) {
            JList l = (JList) e.getComponent();
            l.repaint(l.getCellBounds(rollOverRowIndex, rollOverRowIndex));
            rollOverRowIndex = -1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            JList l = (JList) e.getComponent();
            Point p = e.getPoint();
            int index = l.locationToIndex(p);
            if (index >= 0) {
                @SuppressWarnings("unchecked")
                DefaultListModel<CheckBoxNode> m = (DefaultListModel<CheckBoxNode>) l.getModel();
                CheckBoxNode n = m.get(index);
                m.set(index, new CheckBoxNode(n.text, !n.selected));
                l.repaint(l.getCellBounds(index, index));
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        JList l = (JList) e.getComponent();
        int index = l.locationToIndex(e.getPoint());
        if (index != rollOverRowIndex) {
            rollOverRowIndex = index;
            l.repaint();
        }
    }
}
