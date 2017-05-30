/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author bnson
 */
public class systemUtils {

    public static String getHostName() {
        try {
            java.net.InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostName();
        } catch (UnknownHostException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return "unknow";
    }
    
    public static void pressAnyKeyToContinue() {
        System.out.println("Press any key to exit...");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }    

}
