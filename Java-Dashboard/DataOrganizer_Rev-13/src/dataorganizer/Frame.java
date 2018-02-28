/** ***********************************************
 *Title: DataOrganizerRev-13
 *Date: 2/26/2018
 *Author: Robert Warner (If you really need it: (610-425-2245))
 *Last Edited by: Andrew McEntee
 *
 * Description: Newly Update GUI allows user to read and write data
 *              to the URI module.
 *              Creates multiple .CSV files in documents folder
 *              Sends program configurations to PIC Microcontroller
 ************************************************* */
package dataorganizer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import gnu.io.*;
import java.io.OutputStream;
import java.util.Date;
import javax.swing.JOptionPane;

public class Frame extends javax.swing.JFrame implements Runnable, SerialPortEventListener {

    int magSampleRate;          //instance variable for holding the magnetometer sample rate
    int accelGyroSampleRate;    //instance variable for holding the Accerometer and Gyroscope Sample Rate
    int accelSensitivity;       //Accelerometer Sensitivity (8G)
    int gyroSensitivity;        //Gyroscope Sensitivity (1000 DPS)
    int accelFilter;            //Accelerometer Filter (92 Hz)
    int gyroFilter;             //Gyroscope Filter     (92 Hz)
    boolean readMode = true;    //Flag that tracks if the program is in read or write mode
    String NameOfFile = "";     //Sets the name of file to an empty string to start
    int lengthOfTest = 25;      //Defaults the length of the test to 25 seconds
    static int percent = 0;     //Varaiable used to track the progress of creating the .CSV file
    public boolean error;       //If an error occurs while creating the .CSV files, this will be set to false
    SerialPort serialPort;      //Object for the serial port class
    Thread readThread;          //Thread that the serial Port is on
    static CommPortIdentifier portId;       //Object used for opening a comm port
    static Enumeration portList;            //Object used for finding comm ports
    InputStream inputStream;                //Object used for reading serial data 
    OutputStream outputStream;              //Object used for writing serial data
    Organizer Organizer = new Organizer();  //Object used for creating .CSV files 
    boolean incommingData = false;          //Boolean that tracks if more Data is in the serial buffer
    int testNum = 0;                        //Current Test Number that is being read from the serial buffer
    int expectedTestNum = 0;                //The number of test that are expected to be recieved 
    
    public static void main(String args[]) {
        //sets up Swing and makes a new thread to run it
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {            //creates new Runnable that runs the JFrame
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }
    
    public Frame() {            //Constructor of Class; adds components to GUI
        initComponents();
    }

    @SuppressWarnings("unchecked")  //GUI code is automatically generated with Netbeand Auto-GUI builder
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        magSampleField = new javax.swing.JTextField();
        magSampleLabel = new javax.swing.JLabel();
        accelGyroSampleLabel = new javax.swing.JLabel();
        accelGyroSampleField = new javax.swing.JTextField();
        nameOfFileLabel = new javax.swing.JLabel();
        timeStampCheck = new java.awt.Checkbox();
        lenOfTestField = new javax.swing.JTextField();
        lenOfTestLabel = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        saveOnly9AxisCheck = new java.awt.Checkbox();
        comPortsComboBox = new javax.swing.JComboBox<>();
        disconnectBtn = new java.awt.Button();
        comPortsBtn = new java.awt.Button();
        stateLabel = new javax.swing.JLabel();
        debugLabel = new javax.swing.JLabel();
        accelSensiLabel = new javax.swing.JLabel();
        gyroSensiLabel = new javax.swing.JLabel();
        accelFilterLabel = new javax.swing.JLabel();
        gyroFilterLabel = new javax.swing.JLabel();
        nameOfFileField = new javax.swing.JTextField();
        writeBtn = new java.awt.Button();
        saveTestParamsCheck = new java.awt.Checkbox();
        writeModeLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        writeModeStateLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        readModeLabel = new javax.swing.JLabel();
        readModeStateLabel = new javax.swing.JLabel();
        gyroSensiLabelR = new javax.swing.JLabel();
        gyroSensiFieldR = new javax.swing.JTextField();
        accelFilterLabelR = new javax.swing.JLabel();
        accelFilterFieldR = new javax.swing.JTextField();
        gyroFilterLabelR = new javax.swing.JLabel();
        gyroFilterFieldR = new javax.swing.JTextField();
        magSampleFieldR = new javax.swing.JTextField();
        magSampleLabelR = new javax.swing.JLabel();
        accelGyroSampleLabelR = new javax.swing.JLabel();
        accelGyroSampleFieldR = new javax.swing.JTextField();
        accelSensiLabelR = new javax.swing.JLabel();
        accelSensiFieldR = new javax.swing.JTextField();
        lenOfTestFieldR = new javax.swing.JTextField();
        lenOfTestLabelR = new javax.swing.JLabel();
        numTestsLabelR = new javax.swing.JLabel();
        numTestFieldR = new javax.swing.JTextField();
        serialToggleBtn = new java.awt.Button();
        accelSensiComboBox = new javax.swing.JComboBox<>();
        gyroSensiComboBox = new javax.swing.JComboBox<>();
        accelFilterComboBox = new javax.swing.JComboBox<>();
        gyroFilterComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Organizer Rev-13 (2/26/2018)");
        setMinimumSize(new java.awt.Dimension(450, 550));
        setResizable(false);

        magSampleField.setEditable(false);
        magSampleField.setText("120");
        magSampleField.setToolTipText("Must be equal to Accel/Gyro Sample or 1/10 of Accel/Gyro Sample " + "\n" + "NOTE: A Sample Rate over 100Hz might cause some data points to be saved multiple times");

        magSampleLabel.setText("Magnetometer Sample Rate: ");

        accelGyroSampleLabel.setText("Accel/Gyro Sample Rate:");

        accelGyroSampleField.setEditable(false);
        accelGyroSampleField.setText("120");
        accelGyroSampleField.setToolTipText("Maximum Value: 1000");

        nameOfFileLabel.setText("Name of File:");

        timeStampCheck.setLabel("Time Stamp Data");

        lenOfTestField.setEditable(false);
        lenOfTestField.setText("25");
        lenOfTestField.setToolTipText("Max Value: 65534");

        lenOfTestLabel.setText("Length of Test (Sec):");

        jProgressBar1.setToolTipText("Shows the progress on creating the .CSV file");

        saveOnly9AxisCheck.setLabel("Save Only 9 Axis Data");

        comPortsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "", "", "" }));
        comPortsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comPortsComboBoxActionPerformed(evt);
            }
        });

        disconnectBtn.setBackground(new java.awt.Color(216, 25, 25));
        disconnectBtn.setLabel("Disconnect");
        disconnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectBtnActionPerformed(evt);
            }
        });

        comPortsBtn.setBackground(new java.awt.Color(153, 153, 153));
        comPortsBtn.setLabel("Search Available Ports");
        comPortsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comPortsBtnActionPerformed(evt);
            }
        });

        stateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stateLabel.setText("Port Closed");

        debugLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        accelSensiLabel.setText("Accel Sensitivity (G):");

        gyroSensiLabel.setText("Gyroscope Sensitivity (dps):");

        accelFilterLabel.setText("Accelerometer Filter (Hz):");

        gyroFilterLabel.setText("Gyroscope Filter (Hz):");

        nameOfFileField.setToolTipText("Do not add .CSV at the end of the file name; it is automatically added");

        writeBtn.setBackground(new java.awt.Color(153, 153, 153));
        writeBtn.setEnabled(false);
        writeBtn.setLabel("Write Data");
        writeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                writeBtnActionPerformed(evt);
            }
        });

        saveTestParamsCheck.setLabel("Save Test Parameters in Name of File");
        saveTestParamsCheck.setState(true);

        writeModeLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        writeModeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        writeModeLabel.setText("Write Mode");

        writeModeStateLabel.setForeground(new java.awt.Color(255, 0, 0));
        writeModeStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        writeModeStateLabel.setText("Currently Disabled");

        readModeLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        readModeLabel.setForeground(new java.awt.Color(102, 0, 102));
        readModeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        readModeLabel.setText("Read Mode");

        readModeStateLabel.setForeground(new java.awt.Color(0, 0, 204));
        readModeStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        readModeStateLabel.setText("Currently Enabled");

        gyroSensiLabelR.setText("Gyroscope Sensitivity (dps):");

        gyroSensiFieldR.setEditable(false);

        accelFilterLabelR.setText("Accelerometer Filter (Hz):");

        accelFilterFieldR.setEditable(false);

        gyroFilterLabelR.setText("Gyroscope Filter (Hz):");

        gyroFilterFieldR.setEditable(false);

        magSampleFieldR.setEditable(false);

        magSampleLabelR.setText("Magnetometer Sample Rate: ");

        accelGyroSampleLabelR.setText("Accel/Gyro Sample Rate:");

        accelGyroSampleFieldR.setEditable(false);

        accelSensiLabelR.setText("Accel Sensitivity (G):");

        accelSensiFieldR.setEditable(false);

        lenOfTestFieldR.setEditable(false);

        lenOfTestLabelR.setText("Length of Test (Seconds):");

        numTestsLabelR.setText("Number of Tests:");

        numTestFieldR.setEditable(false);

        serialToggleBtn.setBackground(new java.awt.Color(204, 0, 204));
        serialToggleBtn.setLabel("Read Mode");
        serialToggleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serialToggleBtnActionPerformed(evt);
            }
        });

        accelSensiComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "4", "8", "16" }));
        accelSensiComboBox.setSelectedIndex(2);
        accelSensiComboBox.setEnabled(false);

        gyroSensiComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "250", "500", "1000", "2000" }));
        gyroSensiComboBox.setSelectedIndex(2);
        gyroSensiComboBox.setEnabled(false);

        accelFilterComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "20", "41", "92", "184", "460", "1130(OFF)" }));
        accelFilterComboBox.setSelectedIndex(4);
        accelFilterComboBox.setEnabled(false);

        gyroFilterComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "20", "41", "92", "184", "250", "3600", "8800(OFF)" }));
        gyroFilterComboBox.setSelectedIndex(4);
        gyroFilterComboBox.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(stateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(saveOnly9AxisCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(debugLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nameOfFileLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nameOfFileField)))
                        .addGap(17, 17, 17))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(writeModeStateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(timeStampCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(saveTestParamsCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lenOfTestLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(accelGyroSampleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(accelSensiLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(accelFilterLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(accelGyroSampleField, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(magSampleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                        .addComponent(magSampleField, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lenOfTestField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(writeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(accelFilterComboBox, 0, 1, Short.MAX_VALUE)
                                            .addComponent(accelSensiComboBox, 0, 60, Short.MAX_VALUE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(gyroSensiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addComponent(gyroFilterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(gyroSensiComboBox, 0, 66, Short.MAX_VALUE)
                                            .addComponent(gyroFilterComboBox, 0, 1, Short.MAX_VALUE))
                                        .addGap(1, 1, 1)))
                                .addGap(7, 7, 7))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(serialToggleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comPortsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comPortsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(accelSensiLabelR, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(accelFilterLabelR, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(accelFilterFieldR, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(gyroFilterLabelR, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(accelSensiFieldR, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(gyroSensiLabelR, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gyroSensiFieldR)
                            .addComponent(gyroFilterFieldR))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(accelGyroSampleLabelR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numTestsLabelR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(numTestFieldR)
                            .addComponent(accelGyroSampleFieldR, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(magSampleLabelR, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lenOfTestLabelR)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(magSampleFieldR, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                            .addComponent(lenOfTestFieldR))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(disconnectBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(writeModeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(readModeStateLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(readModeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(17, 17, 17))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(comPortsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comPortsComboBox))
                    .addComponent(serialToggleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(stateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disconnectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(writeModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(writeModeStateLabel)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(magSampleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(magSampleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accelGyroSampleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accelGyroSampleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(accelSensiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(gyroSensiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(accelSensiComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(gyroSensiComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accelFilterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gyroFilterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(accelFilterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gyroFilterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lenOfTestLabel)
                        .addComponent(lenOfTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(writeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(readModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(readModeStateLabel)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameOfFileLabel)
                    .addComponent(nameOfFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeStampCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(saveOnly9AxisCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(saveTestParamsCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(debugLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lenOfTestLabelR)
                    .addComponent(lenOfTestFieldR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numTestsLabelR)
                    .addComponent(numTestFieldR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(magSampleLabelR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(magSampleFieldR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accelGyroSampleFieldR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accelGyroSampleLabelR, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(accelSensiLabelR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(accelSensiFieldR)
                        .addComponent(gyroSensiLabelR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(gyroSensiFieldR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accelFilterLabelR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(accelFilterFieldR)
                    .addComponent(gyroFilterFieldR)
                    .addComponent(gyroFilterLabelR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comPortsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comPortsComboBoxActionPerformed
        //Method for selecting serial Port from comboBox and opening it

        Object selectedItem = comPortsComboBox.getSelectedItem();      //creates a string of selected item; Name of the com port as a string
        String com = selectedItem.toString();                           // " "

        try {
            SimpleRead(com);                                        //opens the serial port with the selected Com Port
            comPortsBtn.setLabel("Port Opened Successfully!");      //sets the Button Label to notify the user that the port is open
            comPortsBtn.setBackground(new java.awt.Color(124, 252, 0));
            serialToggleBtn.setEnabled(false);                      //Turns the changing mode button off because the mode cannot be changed after a serial port is open
            if (readMode) {                                         //If the program is set to read mode
                stateLabel.setForeground(new java.awt.Color(0, 0, 0));  //sets the font as black, it might gave been set as red from before
                stateLabel.setText("Waiting for Data!");                //Read mode is on, so the program is waing for data
            } else {
                stateLabel.setForeground(new java.awt.Color(0, 0, 0));     //sets the font as black, it might gave been set as red from before 
                stateLabel.setText("Press Button Below to Write Data!");   //write mode is on, so the program is waiting to send data
            }
        } catch (IOException ex) {      //if something went wrong
            comPortsBtn.setLabel("Port Did Not OPEN!");     //Port did not open because something is wrong
            comPortsBtn.setBackground(new java.awt.Color(255, 30, 20));
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comPortsComboBoxActionPerformed

    private void disconnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectBtnActionPerformed
        //If the disconnect button is pressed: disconnects from the serial port and resets the UI    
        serialPort.close();     //closes the serial port
        comPortsBtn.setLabel("Search Available Ports!");                //resets the serial label for the serial
        comPortsBtn.setBackground(new java.awt.Color(153, 153, 153));   //sets the button back to grey
        stateLabel.setForeground(new java.awt.Color(0, 0, 0));          //turns the font black
        stateLabel.setText("Port Closed");                              //says the port closed
        serialToggleBtn.setEnabled(true);                               //turns the button back on so the mode can be cahanged
    }//GEN-LAST:event_disconnectBtnActionPerformed

    private void comPortsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comPortsBtnActionPerformed
        //Creates a list of all available serial Ports and adds them to a comboBox    
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();   //creats a list of all the available serial ports
        int i = 0;
        String[] r = new String[5];
        while (portEnum.hasMoreElements() && i < 5) {                   //adds the serial ports to a string array
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            r[i] = portIdentifier.getName();
            i++;
        }
        comPortsComboBox.setModel(new javax.swing.DefaultComboBoxModel(r)); //adds the string array of serial ports to the comboBox
    }//GEN-LAST:event_comPortsBtnActionPerformed

    private void writeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_writeBtnActionPerformed
        //Method for writing data to URI module. Sends programming config to URI module    
        String parameters = "          Z";    //Adds 10 spaces for UART Calibration followed by Z for the delimeter that the URI module is expecting

        int[] writeData = new int[7];
        writeData[0] = Integer.parseInt(accelGyroSampleField.getText());//accelGyroSampleRate
        writeData[1] = Integer.parseInt(magSampleField.getText());    //magSampleRate
        writeData[2] = Integer.parseInt(lenOfTestField.getText());     //lengthOfTest
        writeData[3] = Integer.parseInt(accelSensiComboBox.getSelectedItem().toString());  //accelSensitivity
        writeData[4] = Integer.parseInt(gyroSensiComboBox.getSelectedItem().toString());   //gyroSensitivity
        writeData[5] = Integer.parseInt(accelFilterComboBox.getSelectedItem().toString());  //accelFilter
        writeData[6] = Integer.parseInt(gyroFilterComboBox.getSelectedItem().toString());  //gyroFilter

        if ((writeData[0] == writeData[1] || writeData[0] / 10 == writeData[1]) && writeData[2] < 65534 && writeData[0] <= 1000) {  //Checks to make sure that the data being sent to the URI module is valid
            //Adds the correct amount of zeros to the write data in order to send 4 digits per parameter
            for (int i = 0; i < writeData.length; i++) {
                if (writeData[i] < 16) {
                    parameters += "000" + Integer.toHexString(writeData[i]);    //Adds the parameter to the string that will be sent
                } else if (writeData[i] < 256) {
                    parameters += "00" + Integer.toHexString(writeData[i]);     //Adds the parameter to the string that will be sent
                } else if (writeData[i] < 4096) {
                    parameters += "0" + Integer.toHexString(writeData[i]);      //Adds the parameter to the string that will be sent
                } else {
                    parameters += Integer.toHexString(writeData[i]);            //Adds the parameter to the string that will be sent
                }
            }

            try {
                outputStream = serialPort.getOutputStream();                    //creates Output stream to write data
                outputStream.write(parameters.toUpperCase().getBytes());        //sends the string
            } catch (IOException ex) {                                          //If there is an IOException
                stateLabel.setForeground(new java.awt.Color(255, 0, 0));
                stateLabel.setText("Data was not sent! Something is Wrong");    //Notify the user that something broke
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {                                  //If there is a NullPointer
                stateLabel.setForeground(new java.awt.Color(255, 0, 0));
                stateLabel.setText("Data was not sent! Serial Port was not Opened!!");  //The serial port was not open; notifies the user about the mistake
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, e);
            }
            serialPort.close();                                             //If data was sent successfully, the port is closed
            comPortsBtn.setLabel("Search Available Ports!");                //Resets the Btn
            comPortsBtn.setBackground(new java.awt.Color(153, 153, 153));   //Resets the Btn
            stateLabel.setText("Successfully Wrote Data: Port Closed");     //Sets the label to notify the user that the data was successfully sent
            serialToggleBtn.setEnabled(true);                               //Re-enables the serial button
        } else {        //if something is wrong about the settings, notify the user instead of sending data
            JOptionPane.showMessageDialog(null, "Something is wrong with your settings: \nMagnetometer Sample Rate must match the Accel/Gyro Sample Rate "
                    + "or be the Accel/Gyro Sample Rate divided by 10 \nThe length of the test must be less than 65534 Seconds"
                    + "\nThe Sample Rate of the Accelerometer and Gyroscope must be less than or equal to 1000Hz");
        }
    }//GEN-LAST:event_writeBtnActionPerformed

    private void serialToggleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serialToggleBtnActionPerformed
        //Method that controls whether the program is in read or write mode
        if (readMode) { //If the program was in readMode, it will now be swapped to write mode
            readMode = !readMode;   //changes the mode    
            //Everything else is for disabling and enabling components and changing the colors
            serialToggleBtn.setLabel("Write Mode");
            serialToggleBtn.setBackground(new java.awt.Color(0, 204, 0));
            writeModeStateLabel.setForeground(new java.awt.Color(0, 0, 204));
            writeModeStateLabel.setText("Currently Enabled");
            readModeStateLabel.setForeground(new java.awt.Color(255, 0, 0));
            readModeStateLabel.setText("Currently Disabled");
            readModeLabel.setForeground(new java.awt.Color(0, 0, 0));
            writeModeLabel.setForeground(new java.awt.Color(0, 153, 0));
            writeBtn.setBackground(new java.awt.Color(124, 252, 0));
            accelGyroSampleField.setEditable(true);
            magSampleField.setEditable(true);
            lenOfTestField.setEditable(true);
            accelSensiComboBox.setEnabled(true);
            gyroSensiComboBox.setEnabled(true);
            accelFilterComboBox.setEnabled(true);
            gyroFilterComboBox.setEnabled(true);
            writeBtn.setEnabled(true);
            nameOfFileField.setEditable(false);
            timeStampCheck.setEnabled(false);
            saveOnly9AxisCheck.setEnabled(false);
            saveTestParamsCheck.setEnabled(false);
        } else {    //if the program was in write mode, it will now be swapped 
            readMode = !readMode;   //swaps the mode
            //Everything else is for disabling and enabling components and changing the colors
            serialToggleBtn.setLabel("Read Mode");
            serialToggleBtn.setBackground(new java.awt.Color(204, 0, 204));
            readModeStateLabel.setForeground(new java.awt.Color(0, 0, 204));
            readModeStateLabel.setText("Currently Enabled");
            writeModeStateLabel.setForeground(new java.awt.Color(255, 0, 0));
            writeModeStateLabel.setText("Currently Disabled");
            readModeLabel.setForeground(new java.awt.Color(102, 0, 102));
            writeModeLabel.setForeground(new java.awt.Color(0, 0, 0));
            writeBtn.setBackground(new java.awt.Color(153, 153, 153));
            accelGyroSampleField.setEditable(false);
            magSampleField.setEditable(false);
            lenOfTestField.setEditable(false);
            accelSensiComboBox.setEnabled(false);
            gyroSensiComboBox.setEnabled(false);
            accelFilterComboBox.setEnabled(false);
            gyroFilterComboBox.setEnabled(false);
            writeBtn.setEnabled(false);
            nameOfFileField.setEditable(true);
            timeStampCheck.setEnabled(true);
            saveOnly9AxisCheck.setEnabled(true);
            saveTestParamsCheck.setEnabled(true);
        }
    }//GEN-LAST:event_serialToggleBtnActionPerformed

    public static String getMonth(int month) {  //Method for changing the data in int form to a string
        switch (month) {
            case (0):
                return "JAN";
            case (1):
                return "FEB";
            case (2):
                return "MAR";
            case (3):
                return "APR";
            case (4):
                return "MAY";
            case (5):
                return "JUN";
            case (6):
                return "JUL";
            case (7):
                return "AUG";
            case (8):
                return "SEP";
            case (9):
                return "OCT";
            case (10):
                return "NOV";
            case (11):
                return "DEC";
        }
        return "NOP";
    }

    public static void updateProgress(int progress) {   //Method that updates the progress with the percentage that has been completed so far in making the .CSV file
        jProgressBar1.setValue(progress);
    }

   

    public void SimpleRead(String com) throws IOException {                     //Method that creates the serial port
        portList = CommPortIdentifier.getPortIdentifiers();                     //creates list of avaiable com ports

        while (portList.hasMoreElements()) {                                    //Loops through the com ports
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(com)) {                             //If the avaliable Comm Port equals the comm port that aws selected earlier
                    try {
                        serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
                    } catch (PortInUseException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        inputStream = serialPort.getInputStream();              //creates input stream
                        outputStream = serialPort.getOutputStream();            //creates output stream
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        serialPort.addEventListener(this);                      //adds the listner to the serial port
                    } catch (TooManyListenersException e) {
                        System.out.println(e.getMessage());
                    }
                    serialPort.notifyOnDataAvailable(true);                     //adds an interupt so the port can constantly check is serial data is on the buffer
                    try {
                        if (readMode) {                                 //If read mode is enabled
                            serialPort.setSerialPortParams(115200,      //Opens the serial port at 115200 Baud for high speed reading
                                    SerialPort.DATABITS_8,
                                    SerialPort.STOPBITS_1,
                                    SerialPort.PARITY_NONE);
                        } else {                                        //If write mode is enable
                            serialPort.setSerialPortParams(9600,       //Opens serial port at 9600 Baud so the URI module can read the data
                                    SerialPort.DATABITS_8,
                                    SerialPort.STOPBITS_1,
                                    SerialPort.PARITY_NONE);
                        }
                    } catch (UnsupportedCommOperationException e) {
                        System.out.println(e);
                    }
                    readThread = new Thread(this);          //creates new thread that runs the serial port
                    readThread.start();
                }
            }
        }

    }

    @Override
    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event) {                                //serial Port listner. Checks for incomming data 
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:                //If data is recieved
                incommingData = true;                           
                if (incommingData) {
                    jProgressBar1.setStringPainted(true);       //Sets the progress bar up to display a percentage
                    updateProgress(0);                          //sets the progress bat to 0 percent
                } else {
                    stateLabel.setText("All Data Recieved: Port Closed");
                    serialPort.close();
                    comPortsBtn.setLabel("Search Available Ports!");
                    comPortsBtn.setBackground(new java.awt.Color(153, 153, 153));
                }
                int[] testParams = new int[14];                //Saves parameters that were recieved from URI module. Setting that were used during the test     
                int[][] Tests = null;                          //Stores all of the data that was recieved  
                boolean startCondition = false;                //Flag that determines if the start condtion was recieved 
                int dataByte;                                  //reads data from the input stream and stores in momentarilty. Just for comparison 
                int i = 0;                                     //tracks the current index the test is on 
                try {
                    testNum = 0;                               //Sets the current test that is being recorded to zero 
                    expectedTestNum = 255;                     //Sets the number of tests that are going to be recieved to 255 temporarily 
                    //Checks for the number of tests
                    while (expectedTestNum == 255) {        
                        if (inputStream.available() > 0) {
                            dataByte = (int) (inputStream.read());
                            if (dataByte > 0) { //checks to see if the data that was read is a null character or real data
                                expectedTestNum = dataByte;//if it is real then it is saved in expectedTestNum
                                stateLabel.setText("Collecting Data for " + expectedTestNum + " Tests");//Tells the user how many tests are being transmitted
                            }
                        }
                    }
                    //reads the parameters for the test that are sent from the URI module
                    int param = 0;
                    while (param < testParams.length) {
                        if (inputStream.available() > 0) {
                            testParams[param] = (int) (inputStream.read());
                            param++;
                        }
                    }
                    //each parameter is sent as two bytes. The higher byte is multiplied by 256 and the bottom byte is added on
                    accelGyroSampleRate = (testParams[0] * 256) + testParams[1];
                    magSampleRate = (testParams[2] * 256) + testParams[3];
                    lengthOfTest = (testParams[4] * 256) + testParams[5];
                    accelSensitivity = (testParams[6] * 256) + testParams[7];
                    gyroSensitivity = (testParams[8] * 256) + testParams[9];
                    accelFilter = (testParams[10] * 256) + testParams[11];
                    gyroFilter = (testParams[12] * 256) + testParams[13];
                    //sets the GUI to display the new parameters
                    numTestFieldR.setText(Integer.toString(expectedTestNum));
                    accelGyroSampleFieldR.setText(Integer.toString(accelGyroSampleRate)); //accelGyroSampleRate
                    magSampleFieldR.setText(Integer.toString(magSampleRate));           //magSampleRate
                    lenOfTestFieldR.setText(Integer.toString(lengthOfTest));            //lengthOfTest
                    accelSensiFieldR.setText(Integer.toString(accelSensitivity));       //accelSensitivity
                    gyroSensiFieldR.setText(Integer.toString(gyroSensitivity));         //gyroSensitivity
                    accelFilterFieldR.setText(Integer.toString(accelFilter));           //accelFilter
                    gyroFilterFieldR.setText(Integer.toString(gyroFilter));             //gyroFilter 
                    Tests = new int[expectedTestNum][(accelGyroSampleRate * 12 * lengthOfTest) + (magSampleRate * 6 * lengthOfTest)];//Creates the test array to have the correct amount of elements for all of the tests
                    NameOfFile = nameOfFileField.getText();
                    if (saveTestParamsCheck.getState()) {
                        //Adds the parameters and date to the end of the file Name
                        Date date = new Date();
                        NameOfFile += (" " + accelGyroSampleRate + "-" + magSampleRate + " " + accelSensitivity + "G-" + accelFilter + " " + gyroSensitivity + "dps-" + gyroFilter + " MAG-N " + date.getDate() + getMonth(date.getMonth()) + (date.getYear() - 100));
                        nameOfFileField.setText(NameOfFile);
                    }
                    //Loops until it all of the tests are collected
                    while (testNum < expectedTestNum) {
                        //Start Condition test, The program is expecting to receive "1-2-3-4-5-6-7-8-9" as the start condition
                        int counter = 0;
                        while (!startCondition) {
                            if (inputStream.available() > 0) {
                                if ((int) (inputStream.read()) == counter) {    
                                    counter++;
                                } else {
                                    counter = 0;
                                }
                                if (counter == 10) {    //if the start condition was received correctly
                                    startCondition = true;                      //start condition flag is set to true so data collection will begin
                                    debugLabel.setText("Found the Start Condition For Test " + (testNum + 1) + ". Now Collecting Data");    //display to the user where the program is
                                    updateProgress(0);      //Update the progress bar so the last test is no longer being dispalyed
                                }
                            }
                        }
                        while (inputStream.available() > 0 && i < Tests[0].length) {    //read all of the data on the serial buffer and store it in the test array
                            Tests[testNum][i] = (int) (inputStream.read());
                            i++;
                        }
                        if (i == Tests[0].length) {                             //if all of the data has been collected
                            int[] finalData = Tests[testNum];                   //store all of the data from the single collected test in another array so it is final
                            jProgressBar1.setStringPainted(true);
                            NameOfFile = "(#" + (testNum + 1) + ") " + nameOfFileField.getText() + ".CSV";  //Add a number and .CSV to the file name
                            Thread t = new Thread(() -> {   //create a new thread for creating a .CSV
                                error = Organizer.sortData(finalData, NameOfFile, (accelGyroSampleRate / magSampleRate), (1 / accelGyroSampleRate), timeStampCheck.getState(), saveOnly9AxisCheck.getState());  //create the .CSV with neccessary parameters
                            });
                            t.start();      //start the new thread
                            startCondition = false;     //set the start condtion flag to false so the next test needs to send the start condtion in order to delimit the data
                            i = 0;                  //i is reset for the next test
                            testNum++;              //The test number is incremented to collect data for the next test
                            debugLabel.setText("Creating CSV for " + (testNum));        //Tell the user a new .CSV has been created.
                        }
                    }
                    //after all of the data has been sent, reset the GUI
                    incommingData = false;      
                    debugLabel.setText("");
                    serialPort.close();
                    serialToggleBtn.setEnabled(true);
                    stateLabel.setText("All Data Recieved: Port Closed");
                    comPortsBtn.setLabel("Search Available Ports!");
                    comPortsBtn.setBackground(new java.awt.Color(153, 153, 153));

                } catch (IOException e) {
                	System.out.println(e.getMessage());
                }
                break;

        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> accelFilterComboBox;
    private javax.swing.JTextField accelFilterFieldR;
    private javax.swing.JLabel accelFilterLabel;
    private javax.swing.JLabel accelFilterLabelR;
    private javax.swing.JTextField accelGyroSampleField;
    private javax.swing.JTextField accelGyroSampleFieldR;
    private javax.swing.JLabel accelGyroSampleLabel;
    private javax.swing.JLabel accelGyroSampleLabelR;
    private javax.swing.JComboBox<String> accelSensiComboBox;
    private javax.swing.JTextField accelSensiFieldR;
    private javax.swing.JLabel accelSensiLabel;
    private javax.swing.JLabel accelSensiLabelR;
    private java.awt.Button comPortsBtn;
    private javax.swing.JComboBox<String> comPortsComboBox;
    private javax.swing.JLabel debugLabel;
    private java.awt.Button disconnectBtn;
    private javax.swing.JComboBox<String> gyroFilterComboBox;
    private javax.swing.JTextField gyroFilterFieldR;
    private javax.swing.JLabel gyroFilterLabel;
    private javax.swing.JLabel gyroFilterLabelR;
    private javax.swing.JComboBox<String> gyroSensiComboBox;
    private javax.swing.JTextField gyroSensiFieldR;
    private javax.swing.JLabel gyroSensiLabel;
    private javax.swing.JLabel gyroSensiLabelR;
    public static javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField lenOfTestField;
    private javax.swing.JTextField lenOfTestFieldR;
    private javax.swing.JLabel lenOfTestLabel;
    private javax.swing.JLabel lenOfTestLabelR;
    private javax.swing.JTextField magSampleField;
    private javax.swing.JTextField magSampleFieldR;
    private javax.swing.JLabel magSampleLabel;
    private javax.swing.JLabel magSampleLabelR;
    private javax.swing.JTextField nameOfFileField;
    private javax.swing.JLabel nameOfFileLabel;
    private javax.swing.JTextField numTestFieldR;
    private javax.swing.JLabel numTestsLabelR;
    private javax.swing.JLabel readModeLabel;
    private javax.swing.JLabel readModeStateLabel;
    private java.awt.Checkbox saveOnly9AxisCheck;
    private java.awt.Checkbox saveTestParamsCheck;
    private java.awt.Button serialToggleBtn;
    private static javax.swing.JLabel stateLabel;
    private java.awt.Checkbox timeStampCheck;
    private java.awt.Button writeBtn;
    private javax.swing.JLabel writeModeLabel;
    private javax.swing.JLabel writeModeStateLabel;
    // End of variables declaration//GEN-END:variables

}
