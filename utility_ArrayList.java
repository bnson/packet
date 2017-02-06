/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comRobot;

import java.util.ArrayList;

/**
 *
 * @author bnson
 */
public class utility_ArrayList {
    public utility_ArrayList() {
        
    }
    
    
    public void printArrayListString(ArrayList<String> arrList) {
        arrList.forEach((_item) -> {
            System.out.println("arrList: " + _item);
        });        
    }    
    
}