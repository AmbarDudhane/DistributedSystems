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
import java.util.Iterator;
import java.util.Scanner; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

//Client class 
public class Client 
{ 
        public static ArrayList<String> commandList = new ArrayList<String>(Arrays.asList("mkdir", "list", "rmdir", "rename", "move", "cd"));
        private String baseDir = "C:\\Users\\Ambar\\OneDrive\\Documents\\NetBeansProjects\\MyProject1\\clientDir";
        //private String baseDir = "clientDir";
        private String currDir;
        private String clientName;
        private Socket clientSocket;
        private DataInputStream dis;
        private DataOutputStream dos;

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
            System.out.println("Client.java executeCommand method command:"+command);
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Client connection terminated");
        }

} 


