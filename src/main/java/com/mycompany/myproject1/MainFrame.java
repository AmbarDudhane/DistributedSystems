/*
 * Author: Ambar Dudhane
 * MAV ID: 1001756592
 * CSE 5306 LAB 1
 */
package com.mycompany.myproject1;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*; 
import java.net.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Ambar
 */
public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(255, 255, 51));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("My Program");

        jButton1.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jButton1.setText("Add Client");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Enter Client Name");
        jLabel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(235, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //below method represents action listener for Add Client Button
    //It has logic of checking if the client name is correct and it will create a new client frame if connection
    //is established
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
       if(jTextField1.getText().equals("")){
           System.out.println("Empty name");
           javax.swing.JOptionPane.showMessageDialog(null, "Please enter client name in text field", "Warning", JOptionPane.INFORMATION_MESSAGE);
           return;
       }
       //clientCount++;
       int len = deserialize();
       System.out.println("------MainFrame.java clientList size="+len);
       if(len == 3){
           System.out.println("trying to create more than 3 clients");
           javax.swing.JOptionPane.showMessageDialog(null, "Maximum 3 clients can exist.", "Error", JOptionPane.ERROR_MESSAGE);
           return;
       }
       else{
           if(!jTextField1.getText().substring(0, 1).matches("[a-zA-Z]")){
               javax.swing.JOptionPane.showMessageDialog(null, "Name can start only with letters", "Error", JOptionPane.ERROR_MESSAGE);
               return;
           }

           Client cl = new Client();
           cl.setClientName(jTextField1.getText());
          
           String status = cl.connect();
           //if connection is rejected, pop up a message to user
           if(status.equals("Connection rejected")){
               javax.swing.JOptionPane.showMessageDialog(null, "Server rejected connection as client already exists. Enter different name", "Error", JOptionPane.ERROR_MESSAGE);
               return;
           }
           //if connection is established, pop up a client frame to user
           else{
                
                cl.createHomeDir();
                ClientFrame cf = new ClientFrame();
                cf.setCl(cl);
                cf.setCurrDir();
                cf.setClientName(jTextField1.getText());
                cf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                cf.setBounds(250, 250, 696, 508);
                
                //below close window method is referenced from https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
                //When user clicks the cancel button of frame, below method gets called.
                cf.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("Client Window Closing operation called...");
                        JOptionPane.showConfirmDialog(cf, 
                            "Client will get disconnected", "Close Window?", 
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE);

                            cf.getCl().terminate();
                            cf.dispose();
                        
                    }
                });
                
                cf.setVisible(true);
                jTextField1.setText("");
           }
           
       }
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    //below method is referenced from https://www.geeksforgeeks.org/serialization-in-java/#:~:text=Serialization%20is%20a%20mechanism%20of,used%20to%20persist%20the%20object.&text=To%20make%20a%20Java%20object%20serializable%20we%20implement%20the%20java.
    //it deserialoized length obj from file listlength.ser
    public int deserialize(){
        Integer len = 0;
        try{
            FileInputStream file = new FileInputStream("listlength.ser"); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            len = (Integer)in.readObject(); 
              
            in.close(); 
            file.close(); 
        }
        catch(FileNotFoundException fe){
            System.out.println("Exception thrown: It's possible that listlength can'e be found for first time.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return len;
    }
    
    public static void main(String args[]) {
        
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    public static int clientCount = 0;
    public static ArrayList<String> clientList = new ArrayList<String>();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
