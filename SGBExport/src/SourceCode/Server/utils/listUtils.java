/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 *
 * @author bnson
 */
public class listUtils {

    
    public static void print(java.util.List<String> list) {
        list.forEach((_item) -> {
            //System.out.println("list: " + _item);
            System.out.println(_item);
        });        
    }      
    
    public static List<String> parseList(String str, String separator) {
        List<String> rs = new ArrayList<>(Arrays.asList(str.split(separator)));
        return rs;
    }
    
    public static List<String> removeDuplication(List<String> list) {
            Set<String> hs = new HashSet<>();
            hs.addAll(list);
            list.clear();
            list.addAll(hs);  
            
            return list;
    }
    
    public static void trimSpace(List<String> listString) {
        
        ListIterator<String> it = listString.listIterator();
        while (it.hasNext()) {
            it.set(stringUtils.trimSpace(it.next()));
        }

    }    
    
    public static void replace(List<String> listString, String oldText, String newText) {
        
        ListIterator<String> it = listString.listIterator();
        while (it.hasNext()) {
            it.set(it.next().replace(oldText,newText));
        }

    }     
    
    public static void removeNumberic(List<String> listString) {
        for (Iterator<String> iter = listString.listIterator(); iter.hasNext();) {
            String tmp = iter.next();
            if (stringUtils.isNumberic(tmp)) {
                iter.remove();
            }
        }
       
    }    

    public static java.util.Comparator<java.io.File> COMPARATOR_SORT_FILE_NATURAL = new java.util.Comparator<java.io.File>() {
        @Override
        @SuppressWarnings("empty-statement")
        public int compare(java.io.File o1, java.io.File o2) {;
            return COMPARATOR_SORT_STRING_NATURAL.compare(o1.getName(), o2.getName());
        }
        
        private final java.util.Comparator<String> COMPARATOR_SORT_STRING_NATURAL = new stringComparator_WindowsExplorer();
    };
    
    public static java.util.Comparator<String> COMPARATOR_SORT_STRING_NATURAL = new stringComparator_WindowsExplorer();
    public static java.util.Comparator<String> COMPARATOR_SORT_STRING_LENGTH = new stringComparator_Length();
    
    
            


}
