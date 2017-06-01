/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter.Component;

/**
 *
 * @author bnson
 */
public class CheckBoxNode {

    public final String text;
    public final boolean selected;

    public CheckBoxNode(String text, boolean selected) {
        this.text = text;
        this.selected = selected;
    }

    @Override
    public String toString() {
        return text;
    }
}
