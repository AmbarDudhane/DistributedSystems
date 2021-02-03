/*
 * Author: Ambar Dudhane
 * MAV ID: 1001756592
 * CSE 5306 LAB 2
 */
package com.mycompany.myproject1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
;


public class DirWatcher {
    
    //below method is referenced from https://www.geeksforgeeks.org/serialization-in-java/#:~:text=Serialization%20is%20a%20mechanism%20of,used%20to%20persist%20the%20object.&text=To%20make%20a%20Java%20object%20serializable%20we%20implement%20the%20java.
    //it deserialized HashMap from file hashmap.ser
    public HashMap deserialize(){
        HashMap map = new HashMap();
        try{
            FileInputStream file = new FileInputStream("hashmap.ser"); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            map = (HashMap)in.readObject(); 
              
            in.close(); 
            file.close(); 
        }
        catch(FileNotFoundException fe){
            System.out.println("Exception thrown: It's possible that HashMap can't be found for first time.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }
    
    //Below method is referenced from https://www.baeldung.com/java-nio2-watchservice
    public static void main(String[] args){
        DirWatcher dw = new DirWatcher();
        
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            
            Path path = Paths.get("clientDir");
            HashMap<String, String> hm;
            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            
            WatchKey key;
            File src, dest;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    hm = dw.deserialize();
                    System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                    System.out.println("Client hm:"+hm.toString());

                    for (Map.Entry<String,String> entry : hm.entrySet()) {
                        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
                        if(entry.getValue().contains(event.context().toString())){
                            System.out.println("---Need to Sync "+event.context()+" at LDs");
                            src = new File("clientDir//"+event.context());
                            //delete old home dir in LDs/ID
                            FileUtils.deleteDirectory(new File("LDs//"+entry.getKey()+"//copyOF"+event.context()));
                            dest = new File("LDs//"+entry.getKey()+"//copyOF"+event.context());
                            FileUtils.copyDirectory(src, dest);
                            System.out.println("---Sync Complete");
                        }
                    } 
                        
                }
                key.reset();
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
