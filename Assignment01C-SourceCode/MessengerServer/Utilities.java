/* 
 * Parker Mina
 * 1/28/2025
 * The following program contains methods for use in Program and helper methods for those methods. 
 * Including generating a timestamp, writing encrypted records to a file, and consuming and returning the first file of a given priority.
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Utilities 
{
    //Returns the current date, time, and am/pm as a String.
    static String getDateTimeStamp() 
	{
        LocalDateTime date = LocalDateTime.now(); //Current date and time info
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.hh.mm.ss.a"); //Puts date and time info into appropriate format
        return date.format(dateFormatter);
	}

    //Takes encrypted message and writes it to the provided file, attaches a time stamp, and assigns it a given priority. Calls getDateTimeStamp().
    static void writeEncryptedMessageToFile(String priority, String encryptedMessage, String filename) throws Exception
    {
        FileWriter writer = new FileWriter(filename, true); //Appends given encyrpted message, message priority, and associated timestamp to provided file
        writer.write(getDateTimeStamp() + "\n" + priority + "\n" + encryptedMessage);
        writer.close();
    }

    /*
     * For every record in provided file, method checks its priority against the requested priority, 
     * if the current record doesn't have the right priority then it's added to a StringBuilder. 
     * If the current record matches the priority then it is stored as an output and is not added to the
     * StringBuilder. Additionally after the desired record is found, messagePriority is changed to 
     * -1 to prevent multiple records from being pulled. Once the file has been read fully then 
     * the StringBuilder overwrites the file contents without including the desired record and 
     * the desired record is returned as a String.
     */
    static String consumeEncryptedMessageFromFile(String messagePriority, String filename) throws Exception
    {
        File file = new File(filename);
        Scanner lineReader = new Scanner(file);
        StringBuilder newFileContent = new StringBuilder();
        String curr_date = "";
        String curr_priority = "";
        String curr_message = "";
        String encryptedMessage = "";
        while(lineReader.hasNext()) {
            curr_date = lineReader.next();
            curr_priority = lineReader.next();
            curr_message = lineReader.next();
            if(!curr_priority.equals(messagePriority)) {
                newFileContent.append(curr_date + "\n" + curr_priority + "\n" + curr_message + "\n\n");
            }
            else {
                encryptedMessage = curr_message;
                messagePriority = "-1";
            }
        }
        lineReader.close();
        FileWriter writer = new FileWriter(filename);
        writer.write(newFileContent.toString());
        writer.close();
        return encryptedMessage;
    }
}
