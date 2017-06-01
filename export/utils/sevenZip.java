/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.SevenZipNativeInitializationException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;

/**
 *
 * @author bnson
 */
public class sevenZip {

    public sevenZip() {
        try {
            SevenZip.initSevenZipFromPlatformJAR();
            System.out.println("Message: 7-Zip-JBinding library was initialized.");

        } catch (SevenZipNativeInitializationException e) {
        }
    }

    
    public boolean extractFiles(String pathFile, String pathUnpacking,String pass) {
        RandomAccessFile randomAccessFile = null;
        IInArchive inArchive = null;

        try {
            File zFile = new File(pathFile);
            randomAccessFile = new RandomAccessFile(zFile, "r");
            inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));

            // Getting simple interface of the archive inArchive
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();

//            System.out.println("   Hash   |    Size    | Filename");
//            System.out.println("----------+------------+---------");

            for (ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                final int[] hash = new int[]{0};
                if (!item.isFolder()) {
                    ExtractOperationResult result;

                    final File file = new File(pathUnpacking + File.separator + item.getPath());
                    file.getParentFile().mkdirs();
                    final FileOutputStream fos = new FileOutputStream(file);
                    final long[] sizeArray = new long[1];
                    result = item.extractSlow((byte[] data) -> {
                        try {
                            fos.write(data);                            
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        hash[0] ^= Arrays.hashCode(data); // Consume data
                        sizeArray[0] += data.length;
                        return data.length; // Return amount of consumed data
                        
                    }, pass);

                    fos.flush();
                    fos.close();                 
                    
                    if (result == ExtractOperationResult.OK) {
//                        System.out.println(String.format("%9X | %10s | %s", hash[0], sizeArray[0], item.getPath()));
                    } else {
                        System.err.println("Error: extracting item - " + result);
                        return false;
                    }
                }
            }

        } catch (FileNotFoundException | SevenZipException ex) {
            Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                if (inArchive != null) {
                    inArchive.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

        return true;
    }    
    
    
    public boolean extractHere(String pathFile, String pass) {
        RandomAccessFile randomAccessFile = null;
        IInArchive inArchive = null;

        try {
            File zFile = new File(pathFile);
            randomAccessFile = new RandomAccessFile(zFile, "r");
            inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));

            // Getting simple interface of the archive inArchive
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();

//            System.out.println("   Hash   |    Size    | Filename");
//            System.out.println("----------+------------+---------");

            for (ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                final int[] hash = new int[]{0};
                if (!item.isFolder()) {
                    ExtractOperationResult result;

                    final File file = new File(zFile.getParent() + File.separator + item.getPath());
                    file.getParentFile().mkdirs();
                    final FileOutputStream fos = new FileOutputStream(file);
                    final long[] sizeArray = new long[1];
                    result = item.extractSlow((byte[] data) -> {
                        try {
                            fos.write(data);                            
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        hash[0] ^= Arrays.hashCode(data); // Consume data
                        sizeArray[0] += data.length;
                        return data.length; // Return amount of consumed data
                        
                    }, pass);

                    fos.flush();
                    fos.close();                 
                    
                    if (result == ExtractOperationResult.OK) {
//                        System.out.println(String.format("%9X | %10s | %s", hash[0], sizeArray[0], item.getPath()));
                    } else {
//                        System.err.println("Error extracting item: " + result);
                    }
                }
            }

        } catch (FileNotFoundException | SevenZipException ex) {
            Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                if (inArchive != null) {
                    inArchive.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

        return true;
    }

    public int getNumberOfItemsInArchive(String archiveFile) {
        RandomAccessFile randomAccessFile = null;
        int numberOfItems = -1;

        try {
            IInArchive archive;
            randomAccessFile = new RandomAccessFile(archiveFile, "r");

            //ArchiveFormat = null -> autodetect
            archive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
            numberOfItems = archive.getNumberOfItems();
            archive.close();

        } catch (FileNotFoundException | SevenZipException ex) {
            Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(sevenZip.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return numberOfItems;
    }

}
