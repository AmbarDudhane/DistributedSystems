/*
 * This class keeps mapping for all deleted objects in terms of client name and file path. uniqueId is a filename 
for backup object in trash folder.
 */
package com.mycompany.myproject1;

import java.util.Random;

/**
 *
 * @author Ambar
 */
public class DumpMap {
    int uniqueId;
    String clientName;
    String filePath;
    
    //reference: https://www.educative.io/edpresso/how-to-generate-random-numbers-in-java
    public static int generateRandom(){
        Random rand = new Random(); //instance of random class
        int upperbound = 10000;
        //generate random values from 0-24
        int int_random = rand.nextInt(upperbound);
        return int_random;
    }

    @Override
    public String toString() {
        return "DumpMap{" + "uniqueId=" + uniqueId + ", clientName=" + clientName + ", filePath=" + filePath + '}';
    }
    
}
