/*
 * Author: Ambar Dudhane
 * MAV ID: 1001756592
 * CSE 5306 LAB 1
 */
package com.mycompany.myproject1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.JList;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Ambar
 */
public class ServerFrame extends javax.swing.JFrame {

    public static ArrayList<String> serverClientList = new ArrayList<>();
    public static ArrayList<String> logOperations = new ArrayList<>();   //Lab 3 arraylist created to maintain logged operations
    public static ArrayList<DumpMap> mapList = new ArrayList<>();   //Lab 3
    
    //default constructor
    public ServerFrame() {
        initComponents();
        
    }
    
    //below method responsible for getting client list size
    public int getListSize(){
        System.out.println("ServerFrame clientList size="+ServerFrame.serverClientList.size());
        return ServerFrame.serverClientList.size();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Server Window");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Client List");

        jButton1.setFont(new java.awt.Font("Calibri", 1, 13)); // NOI18N
        jButton1.setText("Refresh List");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Calibri", 1, 13)); // NOI18N
        jButton2.setText("UNDO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //This method represents action listner of Refresh List button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jTextField1.setText("");
        System.out.println("ServerFrame refresh button clientList:"+ServerFrame.serverClientList.toString());
        jTextField1.setText(ServerFrame.serverClientList.toString());
    }//GEN-LAST:event_jButton1ActionPerformed

    //Lab 3 Below method represents action listner for UNDO button.
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String[] list = (String[]) logOperations.toArray(new String[logOperations.size()]);
        
        JList logList = new JList(list);
        //Below line referenced from: https://stackoverflow.com/questions/8899605/multiple-choices-from-a-joptionpane
        JOptionPane.showMessageDialog(null, logList, "Choose operations to undo", JOptionPane.INFORMATION_MESSAGE);
        if(logList.getSelectedValuesList().size() == 0){
            return;
        }
        if(logList.getSelectedValuesList().size()>1){
            javax.swing.JOptionPane.showMessageDialog(null, "Only 1 operation can be selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println("Selected operation:"+logList.getSelectedValuesList());
        String res = handleUNDO(logList.getSelectedValuesList());
        if(res!=null || !res.equals("")){
            javax.swing.JOptionPane.showMessageDialog(null, res, "Status", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            javax.swing.JOptionPane.showMessageDialog(null, "UNDO Failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Lab 3 Below method handles undo operations.
    public String handleUNDO(List selected){
        String selectedElement = (String) selected.get(0);
        String[] splitArray = selectedElement.split(" ");
        
        if(splitArray[0].equals("rename")){
            System.out.println("UNDO rename");
            
            File source = new File(splitArray[2]);
            File dest = new File(splitArray[1]);

            if(source.renameTo(dest)){
                ServerFrame.jTextArea1.append("UNDO Rename successful From "+splitArray[2]+" to "+splitArray[1]+"\n");
                logOperations.remove(selectedElement);
                return "UNDO rename successful";
            }
            else{
                ServerFrame.jTextArea1.append("UNDO Failed to rename from "+splitArray[2]+" to "+splitArray[1]+"\n");
                return "UNDO rename failed";
            }
        }
        else if(splitArray[0].equals("create")){
            System.out.println("UNDO create");
            try {
                ClientHandlerNew.deleteDirectory(splitArray[1]);
                ServerFrame.jTextArea1.append("UNDO Create successful of "+splitArray[1]+"\n");
                logOperations.remove(selectedElement);
                checkChildOperations(splitArray[1]);
                return "UNDO create successful";
            } catch (IOException ex) {
                ServerFrame.jTextArea1.append("UNDO Create failed of "+splitArray[1]+"\n");
                ex.printStackTrace();
                return "UNDO create Failed. Seems like invalid path";
            }
            
        }
        else if(splitArray[0].equals("delete")){
            System.out.println("UNDO delete: "+splitArray[1]);
            //Lets split the filePath and get client name
            String temp = splitArray[1].replace("\\", "\\\\");
            
            String[] splits = splitArray[1].split("\\\\");
            String clientName = splits[1];
            boolean result = restoreFileObject(splitArray[1], clientName);
            if(result){
                logOperations.remove(selectedElement);
                ServerFrame.jTextArea1.append("UNDO Delete successful of "+splitArray[1]+"\n");
                return "UNDO Delete Successful";
            }
            else{
               return "UNDO Delete Failed"; 
            } 
        }
        else if(splitArray[0].equals("move")){
            try{
                //rearranging strings
                String temp = splitArray[1].replace("\\", "\\\\");
                System.out.println("temp: "+temp);
                String[] splits = splitArray[1].split("\\\\");
                String source = splitArray[2] + "\\" + splits[splits.length-1];
                String destination = splitArray[1].replace(splits[splits.length-1], "");
                
                System.out.println("ServerFrame handleUNDO source:"+source+" dest:"+destination);
                FileUtils. moveDirectoryToDirectory(new File(source), new File(destination), false);
                ServerFrame.logOperations.remove(selectedElement);
                ServerFrame.jTextArea1.append("UNDO move successful from "+source+" to "+destination+"\n");
                return "UNDO move successful";
            } catch(Exception e){
                ServerFrame.jTextArea1.append("UNDO move failed"+"\n");
                e.printStackTrace();
            }
        }
        return "";
    }
    
    //Lab 3 Below method deletes any child dir operations while undoing "create".
    //Reference: https://www.java67.com/2018/12/how-to-remove-objects-or-elements-while-iterating-Arraylist-java.html
    public void checkChildOperations(String parentDir){
        
        Iterator<String> itr = logOperations.iterator(); 
        while (itr.hasNext()) { 
            String path = itr.next(); 
            if (path.contains(parentDir)) { 
                    itr.remove(); 
            } 
        }

    }
    
    //Lab 3 Below method deserializes file object from trash folder.
    public boolean restoreFileObject(String filePath, String clientName){
        System.out.println("ServerFrame restoreFileObject filePath: "+filePath+", client: "+clientName);
        
        int temp = 0;
        DumpMap dmObj = null;
        for(DumpMap dm: ServerFrame.mapList){
            System.out.println("*** DM: "+ dm.toString());
            if(dm.filePath.equals(filePath) && dm.clientName.equals(clientName)){
                temp = dm.uniqueId;
                dmObj = dm;
                System.out.println("File Found! with id:"+dm.uniqueId);
                break;
            }
        }
        
        //reference: https://www.geeksforgeeks.org/serialization-in-java/#:~:text=Serialization%20is%20a%20mechanism%20of,used%20to%20persist%20the%20object.&text=To%20make%20a%20Java%20object%20serializable%20we%20implement%20the%20java.
        File restore = null, backupObj = new File("trash//"+Integer.toString(temp));
        try{
            FileInputStream file = new FileInputStream(backupObj); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            restore = (File)in.readObject(); 
            ServerFrame.mapList.remove(dmObj);
            
            if(restore.mkdir()){
                backupObj.delete();
                return true;
            }  
            in.close(); 
            file.close(); 
        }catch(Exception e){
            e.printStackTrace();
        } 
        return false;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        ServerFrame sf = new ServerFrame();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                sf.setVisible(true);
            }
        });
        
        /*
            below code represents accepting client connections and creating a thread for the corresponding client.
            Reference: https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/ 
        */
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
				sf.jTextArea1.append("A new client is connected : " + s +"\n");
                                
				// obtaining input and out streams 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                                ObjectOutputStream oos = new ObjectOutputStream(dos);   //Lab 2
				
				System.out.println("Assigning new thread for this client"); 
                                sf.jTextArea1.append("Assigning new thread for this client"+"\n");
				// create a new thread object 
				Thread t = new ClientHandlerNew(s, dis, dos, oos); 

				// Invoking the start() method 
				t.start(); 
				
			} 
			catch (Exception e){ 
				s.close(); 
				e.printStackTrace(); 
			} 
		}
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

//ClientHandlerNew class 
//ClientHandlerNew class referenced from: https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
class ClientHandlerNew extends Thread 
{ 
	final DataInputStream dis; 
	final DataOutputStream dos; 
        final ObjectOutputStream oos;   //Lab 2
        
	final Socket s; 
	public static HashMap<String, String> idMap = new HashMap<>();

	// Constructor which accepts client socket, input dtream and output stream.
	public ClientHandlerNew(Socket s, DataInputStream dis, DataOutputStream dos, ObjectOutputStream oos) 
	{ 
		this.s = s; 
		this.dis = dis; 
		this.dos = dos; 
                this.oos = oos; //Lab 2
	} 
        
        //Lab 3 Below method serializes file object in trash folder while deleting so that it can be restored while performig undo.
        //Below code is referenced from: https://www.geeksforgeeks.org/serialization-in-java/#:~:text=Serialization%20is%20a%20mechanism%20of,used%20to%20persist%20the%20object.&text=To%20make%20a%20Java%20object%20serializable%20we%20implement%20the%20java.
        public void backupFileObject(String filePath, String clientName){
            System.out.println("backupFileObject keeping back for client: "+clientName);
            File backup = new File(filePath);
            try{
                DumpMap dm = new DumpMap();
                File f;
                int temp = 0;
                while(true){
                   temp = DumpMap.generateRandom();
                   f = new File("trash//"+temp);
                   if(!f.exists()){
                       break;
                   }
                   
                }
                dm.uniqueId = temp;
                dm.clientName = clientName;
                dm.filePath = filePath;
                ServerFrame.mapList.add(dm);
                
                FileOutputStream file = new FileOutputStream("trash\\"+Integer.toString(temp)); 
                ObjectOutputStream out = new ObjectOutputStream(file); 
              
            
                out.writeObject(backup); 
              
                out.close(); 
                file.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        
        /*
            Below method serializes the object containing client list length into file listlength.ser.
            Reference: https://www.geeksforgeeks.org/serialization-in-java/#:~:text=Serialization%20is%20a%20mechanism%20of,used%20to%20persist%20the%20object.&text=To%20make%20a%20Java%20object%20serializable%20we%20implement%20the%20java.
        */
        public void serialize(int n){
            Integer obj = n;
            try{
                FileOutputStream file = new FileOutputStream("listlength.ser"); 
                ObjectOutputStream out = new ObjectOutputStream(file); 
              
                // Method for serialization of object 
                out.writeObject(obj); 
              
                out.close(); 
                file.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        

        //Below method is responsible for deleting a directory.
        //Reference:https://mkyong.com/java/how-to-delete-directory-in-java/
        public static void deleteDirectory(String filePath)
        throws IOException {

        Path path = Paths.get(filePath);

        Files.walkFileTree(path,
            new SimpleFileVisitor<>() {

                // delete directories or folders
                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                                                          IOException exc)
                                                          throws IOException {
                    Files.delete(dir);
                    System.out.printf("Directory is deleted : %s%n", dir);
                    return FileVisitResult.CONTINUE;
                }

                // delete files
                @Override
                public FileVisitResult visitFile(Path file,
                                                 BasicFileAttributes attrs)
                                                 throws IOException {
                    Files.delete(file);
                    System.out.printf("File is deleted : %s%n", file);
                    return FileVisitResult.CONTINUE;
                }
            }
        );

    }
        
        //Run method creates thread for each client connection.
	@Override
	public void run() 
	{ 
		String received; 
		String toreturn; 
		while (true) 
		{ 
			try {  
				// receive the answer from client 
				received = dis.readUTF(); 
				String[] splitted = received.split(" ");
                                //System.out.println("ServerFrame received="+received+" splitted="+splitted[0]+","+splitted[1]);
                                
				if(received.contains("Exit")) 
				{ 
                                    System.out.println("Client " + this.s + " sends exit..."); 
                                    System.out.println("Closing this connection."); 
                                    ServerFrame.jTextArea1.append("Client " + this.s + " "+splitted[1]+ " sends exit...\n");
                                    ServerFrame.jTextArea1.append("Closing this connection.\n");
                                    this.s.close(); 
                                    ServerFrame.serverClientList.remove(splitted[1]);
                                    serialize(ServerFrame.serverClientList.size());
                                    System.out.println("Connection closed"); 
                                    break; 
				}
                                //Lab 3 sending looged operations to client
                                else if(splitted[0].equals("getLoggedOperations")){
                                    //oos.writeObject(logOperations);
                                    oos.writeUnshared(ServerFrame.logOperations);
                                }
                                //Lab 2 getting server dirs
                                else if(splitted[0].equals("getServerDirs")){
                                    System.out.println("ServerFrame request for server dirs");
                                    File f = new File("clientDir");
                                    String[] pathnames;
                                    pathnames = f.list();
                                    StringBuffer sb = new StringBuffer();
                                    for (String pathname : pathnames) {
                                        // Print the names of files and directories
                                        sb.append(pathname+",");
                                    }
                                    dos.writeUTF(sb.toString());
                                    
                                }
                                //Lab 2 check If ID Exists
                                else if(splitted[0].equals("checkIfExists")){
                                    dos.writeUTF(Boolean.toString(idMap.containsKey(splitted[1])));
                                }
                                //Lab 2 add ID
                                else if(splitted[0].equals("addID")){
                                    idMap.put(splitted[1], splitted[2]);
                                    dos.writeUTF("ID "+splitted[1]+" assigned to "+splitted[2]);
                                }
                                //Lab 2 remove id
                                else if(splitted[0].equals("removeID")){
                                    idMap.remove(splitted[1]);
                                    dos.writeUTF("ID "+splitted[1]+" removed from server hashmap");
                                }
                                //Lab 2 sync
                                else if(splitted[0].equals("sync")){
                                    //ObjectOutputStream oos = new ObjectOutputStream(dos);
                                    ArrayList<String> dirList = new ArrayList<String>();
                                    for(int i=0; i< splitted.length; i++){
                                        if(i!=0){
                                            dirList.add(splitted[i]);
                                        }
                                    }
                                    File f1;
                                    for(String dir: dirList){
                                        f1 = new File("clientDir//"+dir);
                                        oos.writeObject(f1);
                                    }
                                    System.out.println("ServerFrame sync dirList:"+dirList.toString());
                                    //oos.close();
                                }
                                else if(splitted[0].equals("name")){
                                    System.out.println("ServerFrame.java In name condition");
                                    if(ServerFrame.serverClientList.contains(splitted[1])){
                                        dos.writeUTF("Connection rejected");
                                        ServerFrame.jTextArea1.append("Rejecting conection for "+this.s+"\n");
                                        this.s.close();
                                        break;
                                    }
                                    else{
                                        ServerFrame.serverClientList.add(splitted[1]);
                                        serialize(ServerFrame.serverClientList.size());
                                        dos.writeUTF("Connection established");
                                    }
                                }
                                else if(splitted[0].equals("createHomeDir")){
                                    
                                    System.out.println("Creating home dir for client: "+received);
                                    //String[] splitted = received.split(" ");
                                    File file = new File(splitted[1]);
                                    if(file.mkdir()){
                                        ServerFrame.jTextArea1.append("Creating Home Dir "+splitted[1]+"\n");
                                        System.out.println("Server.java Line 107 "+splitted[1]+" is created.");
                                    }
                                    else{
                                        ServerFrame.jTextArea1.append("Client Directory "+splitted[1]+" already exists."+"\n");
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
                                        ServerFrame.logOperations.add("create "+splitted[1]);   //Lab 3
                                        ServerFrame.jTextArea1.append(splitted[1]+" is created.\n");
                                        System.out.println("Server.java "+splitted[1]+" is created.");
                                        dos.writeUTF("Directory created successfully!");
                                    }
                                    else{
                                        ServerFrame.jTextArea1.append(splitted[1]+" Directory already exists.\n");
                                        System.out.println("Server.java "+splitted[1]+" Directory already exists.");
                                        dos.writeUTF("Directory already exists!");
                                    }
                                }
                                else if(splitted[0].equals("rmdir")){
                                    System.out.println("Server.java Deleting directory: "+splitted[1]);

                                   try{
                                       backupFileObject(splitted[1], splitted[2]);
                                       deleteDirectory(splitted[1]);
                                       ServerFrame.jTextArea1.append("Delete: "+splitted[1]+"\n");
                                       ServerFrame.logOperations.add("delete "+splitted[1]);    //Lab 3
                                       dos.writeUTF("Directory deleted successfully.");
                                   }catch(Exception e){
                                       e.printStackTrace();
                                       dos.writeUTF("Directory deletion unsuccessful.");
                                   }
                                }
                                else if(splitted[0].equals("rename")){
                                    File source = new File(splitted[1]);
                                    File dest = new File(splitted[2]);
                                    
                                    if(source.renameTo(dest)){
                                        ServerFrame.logOperations.add("rename "+source+" "+dest);   //Lab 3
                                        ServerFrame.jTextArea1.append("Rename successful From "+splitted[1]+" to "+splitted[2]+"\n");
                                        dos.writeUTF("Rename successful.");
                                    }
                                    else{
                                        ServerFrame.jTextArea1.append("Failed to rename of "+splitted[1]+" to "+splitted[2]+"\n");
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
                                        ServerFrame.logOperations.add("move "+source+" "+newdir);    //Lab 3
                                        ServerFrame.jTextArea1.append(splitted[1]+" moved successfully to "+splitted[2]+"\n");
                                        dos.writeUTF("Directory moved successfully");
                                    } catch(Exception e){
                                        ServerFrame.jTextArea1.append("Failed to move directory from "+splitted[1]+" to "+splitted[2]+"\n");
                                        dos.writeUTF("Failed to move directory.");
                                        e.printStackTrace();
                                    }
                                    
                                }
                                else if(splitted[0].equals("cd")){
                                    //changing one directory back
                                    File file = new File(splitted[2]+"/..");
                                    ServerFrame.jTextArea1.append("changed one dir back to:"+file.getCanonicalPath()+"\n");
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
                        this.oos.close();
			
		}catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 
} 
