/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter.Component;

import java.util.Vector;

/**
 *
 * @author bnson
 * @param <E>
 */
public class TreeNodeVector<E> extends Vector<E> {

    String name;

    TreeNodeVector(String name) {
        this.name = name;
    }

    TreeNodeVector(String name, E elements[]) {
        this.name = name;
        for (int i = 0, n = elements.length; i < n; i++) {
            add(elements[i]);
        }
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }
}
