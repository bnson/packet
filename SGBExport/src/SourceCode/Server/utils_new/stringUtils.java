/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author bnson
 */
public class stringUtils {
    
    public static String replaceLineSeparatorToSpace(String str) {
        String line_separator = "[\\n|\\r]";
        return str.trim().replaceAll("\\s+", " ").replaceAll(System.getProperty("line.separator"), " ").replaceAll(line_separator, " ");
    }
    
    public static String trimSpace(String str) {
        return str.trim().replaceAll("[ ]+", " ");
    }
    
    public static String removeSpace(String str) {
        return str.replaceAll("[ ]+", "");
    }
    
    public static String removeSpecialChar(String str) {
        String specialChar = "[`\\-=\\[\\];',./~!@#$%^&*()_={}\\\\:\\\"…«»–—”“><?!！„•《〖+]";
        return str.replaceAll(specialChar, " ");
    }
    
    public static Boolean isNumberic(String str) {
        return str.matches("\\d+");
    }    
    
    
    public static String encrypt(String strEncrypt, String key, String instance, String algorithm, String charsetName) {
        try {
            Cipher cipher = Cipher.getInstance(instance);
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeyFactory.getInstance(instance).generateSecret(new DESKeySpec(key.getBytes(charsetName))));   
            return new String(Base64.encodeBase64(cipher.doFinal(strEncrypt.getBytes(charsetName))));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Error: " + e.getMessage());      
        }
        return null;
    }
    
    public static String decrypt(String strDecrypt, String key, String instance, String algorithm, String charsetName) {
        try {
                Cipher cipher = Cipher.getInstance(instance);
                cipher.init(Cipher.DECRYPT_MODE, SecretKeyFactory.getInstance(instance).generateSecret(new DESKeySpec(key.getBytes(charsetName))));
                return new String(cipher.doFinal(Base64.decodeBase64(strDecrypt.getBytes(charsetName))));   
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Error: " + e.getMessage());      
        }
        return null;
    }     
    
}
