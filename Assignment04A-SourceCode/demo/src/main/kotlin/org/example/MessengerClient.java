package org.example;/*
 * Parker Mina
 * 1/28/2025
 * Opens a connection to a Server and sends encrypted messages and receives encrypted messages
 * with sendMessage and getMessage respectively.
*/

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MessengerClient
{
    private InetAddress inetAddress;
    private int port;

    public MessengerClient(InetAddress inetAddress, int port)
    {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    //Sends a message to the Server script. Takes given parameters messageType, priority, and encryptedMessage.
    //These get sent to the Server sepertaed by new line characters.
    //Then it reads the response from the Server and returns it as a String.
    public String sendMessage(String messageType, String priority, String encryptedMessage)
    {
        String serverResponse = null;

        try 
        {
            System.out.println("\nConnecting to server...");
            Socket socket = new Socket(inetAddress, port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader input = new BufferedReader(inputStreamReader);
            
            System.out.println("Sending packet to remote server...");
            printWriter.println(messageType + "\n" + priority + "\n" + encryptedMessage);
            System.out.println("Reading response from remote server...");
            serverResponse = input.readLine() + "\n";
            socket.close();
        } 
        catch (Exception ex)
        {
            String message = ex.getMessage();

            System.out.println(message);
        }
       
        return serverResponse;
    }

    //Sends a message to the Server script. Takes given parameters messageType and priority.
    //These get sent to the Server sepertaed by new line characters.
    //Then it reads the response from the Server and returns it as a String.
    public String getMessage(String messageType, String priority)
    {
        String serverResponse = null;

        try 
        {
            System.out.println("\nConnecting to server...");
            Socket socket = new Socket(inetAddress, port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader input = new BufferedReader(inputStreamReader);
            
            System.out.println("Sending packet to remote server...");
            printWriter.println(messageType + "\n" + priority);
            System.out.println("Reading response from remote server...");
            serverResponse = input.readLine();
            socket.close();
        } 
        catch (Exception ex)
        {
            String message = ex.getMessage();
            System.out.println(message);
        }
       
        return serverResponse;
    }
}