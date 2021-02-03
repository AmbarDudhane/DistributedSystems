/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myproject1;

/*
 * This code is referenced from: https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
 */

import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;


//Server class 
public class Server 
{ 
    
	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 5056 
		ServerSocket ss = new ServerSocket(5056); 
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			Socket s = null; 
			
			try
			{ 
				// socket object to receive incoming client requests 
				s = ss.accept(); 
				
				System.out.println("A new client is connected : " + s); 
				
				// obtaining input and out streams 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
				
				System.out.println("Assigning new thread for this client"); 

				// create a new thread object 
				Thread t = new ClientHandler(s, dis, dos); 

				// Invoking the start() method 
				t.start(); 
				
			} 
			catch (Exception e){ 
				s.close(); 
				e.printStackTrace(); 
			} 
		} 
	} 
} 

//ClientHandler class 
class ClientHandler extends Thread 
{ 
	final DataInputStream dis; 
	final DataOutputStream dos; 
	final Socket s; 
	

	// Constructor 
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
	{ 
		this.s = s; 
		this.dis = dis; 
		this.dos = dos; 
	} 

	@Override
	public void run() 
	{ 
		String received; 
		String toreturn; 
		while (true) 
		{ 
			try { 

				// Ask user what he wants 
				//dos.writeUTF("Enter the command in client GUI..\n"+ 
							//"Type Exit to terminate connection."); 
				
				// receive the answer from client 
				received = dis.readUTF(); 
				String[] splitted = received.split(" ");
                                 
				if(received.equals("Exit")) 
				{ 
					System.out.println("Client " + this.s + " sends exit..."); 
					System.out.println("Closing this connection."); 
					this.s.close(); 
					System.out.println("Connection closed"); 
					break; 
				} 
                                else if(splitted[0].equals("createHomeDir")){
                                    System.out.println("Creating home dir for client: "+received);
                                    //String[] splitted = received.split(" ");
                                    File file = new File(splitted[1]);
                                    if(file.mkdir()){
                                        System.out.println("Server.java Line 107 "+splitted[1]+" is created.");
                                    }
                                    else{
                                        System.out.println("Server.java Line 110 "+ splitted[1]+" Directory already exists.");
                                    }
                                }
                                else if(splitted[0].equals("list")) {
                                    //below code snippet is referenced from https://stackabuse.com/java-list-files-in-a-directory/#:~:text=list(),an%20array%20of%20String%20s.&text=Using%20a%20simple%20for%2Deach,print%20out%20the%20String%20s.
                                     String[] pathnames;

                                    File f = new File(splitted[1]);

                                    pathnames = f.list();
                                    StringBuffer sb = new StringBuffer("The directory contents are:\n");
                                    for (String pathname : pathnames) {
                                        // Print the names of files and directories
                                        sb.append(pathname+"\n");
                                    }
                                    dos.writeUTF(sb.toString());
                                }
                                else if(splitted[0].equals("mkdir")){
                                    File file = new File(splitted[1]);
                                    if(file.mkdir()){
                                        System.out.println("Server.java "+splitted[1]+" is created.");
                                        dos.writeUTF("Directory created successfully!");
                                    }
                                    else{
                                        System.out.println("Server.java "+splitted[1]+" Directory already exists.");
                                        dos.writeUTF("Directory already exists!");
                                    }
                                }
                                else if(splitted[0].equals("rmdir")){
                                    System.out.println("Server.java Deleting directory: "+splitted[1]);
                                    boolean result = FileUtils.deleteQuietly(new File(splitted[1]));
                                    if(result){
                                        dos.writeUTF("Directory deleted successfully.");
                                    }
                                    else{
                                        System.out.println("Server.java deletion is unsuccessful");
                                        dos.writeUTF("Directory deletion unsuccessful.");
                                    }   
                                }
                                else if(splitted[0].equals("rename")){
                                    File source = new File(splitted[1]);
                                    File dest = new File(splitted[2]);
                                    
                                    if(source.renameTo(dest)){
                                        dos.writeUTF("Rename successful.");
                                    }
                                    else{
                                        dos.writeUTF("Failed to rename.");
                                    }
                                }
                                else if(splitted[0].equals("move")){
                                    System.out.println("Server.java moving dir "+splitted[1]+" to "+splitted[2]);
                                    // below code is referenced from https://www.codejava.net/java-se/file-io/how-to-rename-move-file-or-directory-in-java
                                    try{
                                        Path source = Paths.get(splitted[1]);
                                        Path newdir = Paths.get(splitted[2]);
                                        Files.move(source, newdir.resolve(source.getFileName()));
                                        dos.writeUTF("Directory moved successfully");
                                    } catch(Exception e){
                                        dos.writeUTF("Failed to move directory.");
                                        e.printStackTrace();
                                    }
                                    
                                }
                                else if(splitted[0].equals("cd")){
                                    //changing one directory back
                                    File file = new File(splitted[2]+"/..");
                                    System.out.println("Server.java changed one dir back to:"+file.getCanonicalPath());
                                    dos.writeUTF(file.getCanonicalPath());
                                }
				
//				} 
			} catch (IOException ex) { 
                            ex.printStackTrace(); 
			} catch(Exception e){
                            e.printStackTrace();
                        }
		} 
		
		try
		{ 
			// closing resources 
			this.dis.close(); 
			this.dos.close(); 
			
		}catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 
} 

