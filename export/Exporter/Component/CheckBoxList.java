/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter.Component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

/**
 *
 * @author bnson
 * @param <E>
 */
public class CheckBoxList<E extends CheckBoxListNode> extends JList<E> {

    private transient CheckBoxListCellRenderer<E> renderer;

    public CheckBoxList() {
        
    }
    
    public CheckBoxList(ListModel<E> model) {
        super(model);
    }

    @Override
    public void updateUI() {
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        
        setSelectionForeground(Color.BLACK);
        setSelectionBackground(Color.WHITE);
        removeMouseListener(renderer);
        removeMouseMotionListener(renderer);
        super.updateUI();
        renderer = new CheckBoxListCellRenderer<>();
        setCellRenderer(renderer);
        addMouseListener(renderer);
        addMouseMotionListener(renderer);
        putClientProperty("List.isFileList", Boolean.TRUE);
    }

    //@see SwingUtilities2.pointOutsidePrefSize(...)
    private boolean pointOutsidePrefSize(Point p) {
        int i = locationToIndex(p);
        DefaultListModel<E> m = (DefaultListModel<E>) getModel();
        E n = m.get(i);
        ListCellRenderer<? super E> renderers = getCellRenderer();
        Component c = renderers.getListCellRendererComponent(this, n, i, false, false);
        Rectangle r = getCellBounds(i, i);
        r.width = c.getPreferredSize().width;
        return i < 0 || !r.contains(p);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (!pointOutsidePrefSize(e.getPoint())) {
            super.processMouseEvent(e);
        }
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        if (pointOutsidePrefSize(e.getPoint())) {
            MouseEvent ev = new MouseEvent(e.getComponent(), MouseEvent.MOUSE_EXITED, e.getWhen(),
                    e.getModifiers(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(),
                    e.getClickCount(), e.isPopupTrigger(), MouseEvent.NOBUTTON);
            super.processMouseEvent(ev);
        } else {
            super.processMouseMotionEvent(e);
        }
    }
}
