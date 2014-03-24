/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PksZadanie;

import PksZadanie.equip.TcpCommunication;
import PksZadanie.equip.UdpCommunication;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Mathis
 */
public class AnalyserGUI extends javax.swing.JFrame {

     
    private Integer arpTabExists = null;
    private Integer icmpTabExists = null;
 
    private final ArrayList<JTabbedPane> udpTabs = new ArrayList<>();
    private ArrayList<String> udpTabHelper = new ArrayList<>();
    private final ArrayList<JTabbedPane> tcpTabs = new ArrayList<>();
    private final ArrayList<String> tcpTabHelper = new ArrayList<>();

    private File pcapFile;
    private JTabbedPane jTabbedPane4;
    private JTabbedPane jTabbedPane5;

    public AnalyserGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChoose = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane3 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1300, 700));
        setPreferredSize(new java.awt.Dimension(1300, 700));

        fileChoose.setText("Choose file");
        fileChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooseActionPerformed(evt);
            }
        });

        jLabel1.setText("Pcap file Analyser");

        jTabbedPane1.setVisible(false);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1260, 600));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1260, 600));
        jTabbedPane1.addTab("tab1", jTabbedPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileChoose)
                .addGap(418, 418, 418))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fileChoose))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooseActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileNameExtensionFilter("only .pcap", "pcap"));
        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                System.out.println("You chose to open this directory: "
                        + chooser.getSelectedFile().getAbsolutePath());
                pcapFile = chooser.getSelectedFile();
                // jLabel2.setText("LOADING FILE : ");
                //    jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("F:\\Moje dokumenty\\Martin HUdec\\škola\\FIIT\\4. sem\\PKS\\pkspkspks\\src\\main\\java\\images\\loading.gif")));
                // jTextArea1.setText("LOADING file" + pcapFile.getPath());
                
                AnalyserMainCheck panel = new AnalyserMainCheck(pcapFile, this);

                jTabbedPane1.addTab("File content", jTabbedPane3);
                jTabbedPane3.addTab(pcapFile.getName(), panel);
                jTabbedPane3.addTab("Scan Result", panel.getAn().getResult());
                if (panel.getAn().getArpPanel() != null) {

                    if (arpTabExists == null) {
                        jTabbedPane4 = new JTabbedPane();
                        jTabbedPane1.addTab("ARP", jTabbedPane4);
                        arpTabExists = 1;
                    }
                    jTabbedPane4.addTab(pcapFile.getName(), panel.getAn().getArpPanel());
                }

                if (panel.getAn().getIcmpPanel() != null) {
                    if (icmpTabExists == null) {
                        jTabbedPane5 = new JTabbedPane();
                        jTabbedPane1.addTab("ICMP", jTabbedPane5);
                        icmpTabExists = 1;
                    }
                    jTabbedPane5.addTab(pcapFile.getName(), panel.getAn().getIcmpPanel());
                }
             
                Integer index = 0;
                for (TcpCommunication temp : panel.getAn().getTcpCommunicationList()) {
                    Integer found = 0;
                    int i = 0;
                    for (String tab : tcpTabHelper) {
                        if (temp.getList().size() > 0) {
                            if (tab.equalsIgnoreCase(temp.getList().get(0).getApplicationProtocol())) {
                                tcpTabs.get(i).addTab(pcapFile.getName(), temp.getPanel());
                                found = 1;
                            }
                        }
                        i++;
                    }
                    if (found.equals(0)) {
                        JTabbedPane newPane = new JTabbedPane();
                        jTabbedPane1.addTab(temp.getList().get(0).getApplicationProtocol(), newPane);
                        tcpTabs.add(newPane);
                        tcpTabHelper.add(temp.getList().get(0).getApplicationProtocol());
                        newPane.addTab(pcapFile.getName(), temp.getPanel());
                    }
                }

                for (UdpCommunication temp : panel.getAn().getUdpCommunicationList()) {
                    Integer found = 0;
                    int i = 0;
                    for (String tab : udpTabHelper) {
                        if (temp.getList().size() > 0) {
                            if (tab.equalsIgnoreCase(temp.getList().get(0).getApplicationProtocol())) {
                                udpTabs.get(i).addTab(pcapFile.getName(), temp.getPanel());
                                found = 1;
                            }
                        }
                        i++;
                    }
                    if (found.equals(0)) {
                        JTabbedPane newPane = new JTabbedPane();
                        jTabbedPane1.addTab(temp.getList().get(0).getApplicationProtocol() + " [UDP]", newPane);
                        udpTabs.add(newPane);
                        udpTabHelper.add(temp.getList().get(0).getApplicationProtocol());
                        newPane.addTab(pcapFile.getName(), temp.getPanel());

                    }
                }
                jTabbedPane1.setVisible(true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AnalyserGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AnalyserGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_fileChooseActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // AnalyzerGUI gui = new AnalyzerGUI();
                // AnalyzerGuiController guicontroller = new AnalyzerGuiController();
                new AnalyserGUI().setVisible(true);
            }
        });
    }

    public File getPcapFile() {
        return pcapFile;
    }

    public JButton getFileChoose() {
        return fileChoose;
    }

    public JTabbedPane getjTabbedPane3() {
        return jTabbedPane3;
    }

    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fileChoose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    // End of variables declaration//GEN-END:variables
}
