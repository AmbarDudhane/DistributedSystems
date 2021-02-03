/*
 * Author: Ambar Dudhane
 * MAV ID: 1001756592
 * CSE 5306 LAB 1
 */
package com.mycompany.myproject1;


import java.io.*; 
import java.net.*; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

//Client class 
public class Client 
{ 
        public static ArrayList<String> commandList = new ArrayList<String>(Arrays.asList("mkdir", "list", "rmdir", "rename", "move", "cd"));
        private String baseDir = "clientDir";
        //private String baseDir = "clientDir";
        private String currDir;
        private String clientName;
        private Socket clientSocket;
        private DataInputStream dis;
        private DataOutputStream dos;
        private ObjectInputStream ois;  //Lab 2
        private String identifier;  //Lab 2 identifier
        public static HashMap<String, String> hm = new HashMap<String, String>();  //Lab 2

        public static HashMap<String, String> getHm() {
            return hm;
        }

        
        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }


        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getClientName() {
            return clientName;
        }

        public String getCurrDir() {
            return currDir;
        }

        public void setCurrDir(String currDir) {
            this.currDir = currDir;
        }
        
        //connect the client with server by creating the socket and returns status of the connection
        //connect method is referenced from: https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
        public String connect(){
            String status = "";
            try{
                Scanner scn = new Scanner(System.in); 
			
                // getting localhost ip 
		InetAddress ip = InetAddress.getByName("localhost"); 
	
		// establish the connection with server port 5056 
		Socket s = new Socket(ip, 5056); 
                clientSocket = s;
		// obtaining input and out streams 
		dis = new DataInputStream(s.getInputStream()); 
		dos = new DataOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(dis);
                
                dos.writeUTF("name "+this.clientName);
                status = dis.readUTF();
                System.out.println("Client.java status for "+this.clientName+" is "+status);
                
            }catch(Exception e){
                e.printStackTrace();
            }
            return status;
        }
        
        //This method creates home directory for client
        public void createHomeDir(){
            String dir = baseDir+"\\"+this.clientName;
            
            try {
                dos.writeUTF("createHomeDir "+dir);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            currDir = dir;
        }
        
        //This method forwards client's command to server and returns respective output
        public String executeCommand(String command){
            //System.out.println("Client.java executeCommand method command:"+command);
            String output="Please enter correct command\n"+
                    "Correct command syntax is:\n"+
                    "1. list\n"+
                    "2. mkdir <dir-name>\n"+
                    "3. rmdir <dir-name>\n"+
                    "4. rename <dir1> <dir2>\n"+
                    "5. move <source-dir> <destination-dir-path>\n"+
                    "6. cd <dir-name>";
         
            try{
                //if condition for list command
                if(command.equals("list")){
                    dos.writeUTF("list "+currDir);
                    output = dis.readUTF(); 
                }
                //else if condition for mkdir command
                else if(command.contains("mkdir")){
                    String[] res = command.split(" ");
                    System.out.println("Client.java creating "+currDir+"\\"+res[1]);
                    dos.writeUTF("mkdir "+currDir+"\\"+res[1]);
                    output = dis.readUTF();
                }
                //else if condition for rmdir command
                else if(command.contains("rmdir")){
                    String[] res = command.split(" ");
                    System.out.println("Client.java deleting "+currDir+"\\"+res[1]);
                    dos.writeUTF("rmdir "+currDir+"\\"+res[1]);
                    output = dis.readUTF();
                }
                //else if condition for rename command
                else if(command.contains("rename")){
                    String[] res = command.split(" ");
                    dos.writeUTF("rename "+currDir+"\\"+res[1]+" "+currDir+"\\"+res[2]);
                    output = dis.readUTF();
                }
                //else if condition for move command
                else if(command.contains("move")){
                    String[] res = command.split(" ");
                    dos.writeUTF("move "+currDir+"\\"+res[1]+" "+currDir+"\\"+res[2]);
                    output = dis.readUTF();
                }
                //else if condition for cd command
                else if(command.contains("cd")){
                    if(command.contains("..")){
                        System.out.println("Client.java changing one directory back");
                        dos.writeUTF("cd .. "+currDir);
                        String path = dis.readUTF();
                        if(path.equals(baseDir)){
                            output = "You can't go beyond your parent DIR.";
                        }
                        else{
                            output = "Directory changed one level back.";
                            setCurrDir(path);
                        }    
                        
                    }
                    else{
                        String[] dir = command.split(" ");
                        System.out.println("Client.java changing directory to "+dir[1]);
                        setCurrDir(currDir+"\\"+dir[1]);
                        output = "directory changed to "+dir[1];
                    }
                }
                //Lab 2 else if for synchronization
                else if(command.contains("sync")){
                    System.out.println("Client "+command);
                    int listCount = command.split(" ").length - 1;
                    dos.writeUTF(command);
                    
                    String dirList = "";
                    //reading object from server
                    //ObjectInputStream ois = new ObjectInputStream(dis);
                    File ld;
                    File f1;
                    for(int i=0; i<listCount; i++){
                        f1 = (File)ois.readObject();    //src
                        ld = new File("LDs//"+identifier+"//copyOF"+f1.getName());    //dest
                        System.out.println("* File obj received of "+f1.getName());
                        FileUtils.copyDirectory(f1, ld);
                        dirList = dirList + ", " + f1.getName();    //creating string value for hashmap
                    }
                    
                    hm.put(identifier, dirList);
                    serialize(hm);
                    output = "Synchronization complete!";
                    System.out.println("Client sync hashmap:"+hm.toString());
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            
            return output;
        }
        
        // This method terminates the connection with server and closes socket, inputstream and outputstream
        public void terminate(){
            try {
                dos.writeUTF("Exit "+this.clientName);
                clientSocket.close();
                dis.close();
                dos.close();
                ois.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Client connection terminated");
        }
        
        //Lab 2 below method fetches home dir from server
        public ArrayList getServerDir() {
            ArrayList<String> dirList = new ArrayList();
            try {
                dos.writeUTF("getServerDirs");
                String output = dis.readUTF();
          
                String[] tokens = output.split(",");
                for(String dir: tokens){
                    dirList.add(dir);
                }
                System.out.println("Client.java server Dirs:"+dirList.toString());
                return dirList;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return dirList;
        }
        
        //Lab 2 below method desynchronizes the dirs
        public boolean desync(List<String> dirList){
            boolean result = false;
            //deleting dirs from HashMap hm
            String dirValue = hm.get(identifier);
            System.out.println("Client.java desync BEFORE DELETE dirValue:"+dirValue);
            for(String dir: dirList){
                 
                result = FileUtils.deleteQuietly(new File("LDs//"+identifier+"//"+dir));
                //Also deleting from hashmap
                dir = dir.replace("copyOF", "");
                dirValue = dirValue.replace(dir, "");
            }
            System.out.println("Client.java desync AFTER DELETE dirValue:"+dirValue);
            hm.put(identifier, dirValue);
            serialize(hm);
            System.out.println("Client.java desync HashMap:"+hm.toString());
            
            return result;
        }
        
        //Lab 2 below method checks if id exists at server
        public boolean checkIfExists(String id){
            try {
                dos.writeUTF("checkIfExists "+id);
                String output = dis.readUTF();
                if(output.equals("true"))
                    return true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return false;
        }
        
        //Lab 2 below method adds id to server hashmap
        public void addID(String id){
            try {
                dos.writeUTF("addID "+id+" "+getClientName());
                String output = dis.readUTF();
                System.out.println("Client.java "+output);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        //Lab 2 below method removes id from server
        public void removeID(String id){
            try {
                dos.writeUTF("removeID "+id);
                String output = dis.readUTF();
                System.out.println("Client.java "+output);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        /*
            Below method serializes the object containing client list length into file listlength.ser.
            Reference: https://www.geeksforgeeks.org/serialization-in-java/#:~:text=Serialization%20is%20a%20mechanism%20of,used%20to%20persist%20the%20object.&text=To%20make%20a%20Java%20object%20serializable%20we%20implement%20the%20java.
        */
        public void serialize(HashMap map){
            
            try{
                FileOutputStream file = new FileOutputStream("hashmap.ser"); 
                ObjectOutputStream out = new ObjectOutputStream(file); 
              
                // Method for serialization of object 
                out.writeObject(map); 
              
                out.close(); 
                file.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }

} 


