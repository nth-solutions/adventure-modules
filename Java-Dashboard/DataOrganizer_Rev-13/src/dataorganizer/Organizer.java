package dataorganizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.filechooser.FileSystemView;

public class Organizer {        //Class for Creating .CSV files

    public boolean sortData(int[] data, String NameOfFile, int magInterval, double period, boolean timeStamp, boolean only9Axis) {
    //Method to create .CSV
        PrintWriter DataFile = null;    //Object used to create .CSV file

        try {
            DataFile = new PrintWriter(new File((FileSystemView.getFileSystemView()     //Creates .CSV file in default directory which is documents
                    .getDefaultDirectory().toString()
                    + File.separator + NameOfFile)));
        } catch (FileNotFoundException e) {
            return false;
        }

        StringBuilder builder = new StringBuilder();    //creates new string builder which is used for adding data to the .CSV

        int word = 0;                                   //Variable used to hold data
        int i = 0;                                      //Index used to iterate through data array
        int lineNumber = 0;                             //the current line number or sample number
        while (i < data.length) {                       //While there is more data that needs to be processed
            Frame.updateProgress((int) ((((double) (i) / (double) (data.length)) * 100) + 0.5));    //Updates the progress bar with a progress of creating teh .CSV file
            for (int newLine = 0; newLine < magInterval; newLine++) {   //tracks if mag data should be written to the .CSV for just Accel/Gyro
                if (timeStamp) {   //If the Time Stamp Boolean is true, then add the time stamp on the first column of the .CSV
                    builder.append(lineNumber * period).append(",");
                }
                if (newLine == 0) {     //if new line is zero, then the mag data should is in this block of data so it needs to written to the .CSV
                    for (int wrCounter = 0; wrCounter < 9; wrCounter++) {   //Writes 9 words (18 bytes) to the .CSV file
                        word = (data[i] * 256) + data[i + 1];   //multiples the High byte by 256 and adds the lower byte to the word
                        builder.append(word).append(",");       //adds to the .CSV with a comma
                        i += 2;                                 //iterate I by 2
                    }
                    builder.append("\n");               //After the nine bytes add a new line to .CSV
                    lineNumber++;                           
                } else {
                    for (int wrCounter = 0; wrCounter < 6; wrCounter++) {//Writes 6 word (12 bytes) to the .CSV (No Mag!)
                        word = (data[i] * 256) + data[i + 1];           //multiples the High byte by 256 and adds the lower byte to the word
                        if (!only9Axis) {   //If the user only wants to save data that has a magnetometer reading with it
                            builder.append(word).append(",");   //adds to the .CSV with a comma
                        }
                        i += 2;
                    }
                    builder.append("\n");       //After the nine bytes add a new line to .CSV
                    lineNumber++;
                }
            }
        }
        DataFile.write(builder.toString());     //writes the string buffer to the .CSV creating the file
        DataFile.close();                       //close the .CSV
        return true;        
    }
}


