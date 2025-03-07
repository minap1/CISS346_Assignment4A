package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Server extends Thread {

    private int port;
    private volatile boolean running = true;
    private PipedOutputStream WRITEHERE = null;
    static final String NO_PRIORITY_1_MESSAGES = "NO_PRIORITY_1_MESSAGES";
    static final String NO_PRIORITY_2_MESSAGES = "NO_PRIORITY_2_MESSAGES";
    static final String NO_PRIORITY_3_MESSAGES = "NO_PRIORITY_3_MESSAGES";
    static final String PRODUCE_MESSAGE_STRING = "PRODUCE_MESSAGE";
    static final String CONSUME_MESSAGE_STRING = "CONSUME_MESSAGE";
    static final String PRODUCE_MESSAGE = "0";
    static final String CONSUME_MESSAGE = "1";

    static final String textFilename = "EncryptedMessages.txt";
    public Server(int port) {
        this.port = port;
    }

    public Server(int port, PipedOutputStream p) {
        this.port = port;
        this.WRITEHERE = p;
    }

    @Override
    public void run() {
        try {
            startServer(port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stopThread() {
        running = false;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private void startServer(int port) throws Exception {


        String publicKeyFilename = "public.key";
        String privateKeyFilename = "private.key";
        PublicKey publicKey;
        PrivateKey privateKey;

        // Read public key from file
        try {
            publicKey = RsaEncryptor.readPublicKeyFromFile(publicKeyFilename);
        } catch (Exception e) {
            WRITEHERE.write("Error reading public key from file\n".getBytes());
            throw new RuntimeException(e);
        }
        // Read private key from file
        try {
            privateKey = RsaEncryptor.readPrivateKeyFromFile(privateKeyFilename);
        } catch (Exception e) {
            WRITEHERE.write("Error reading private key from file\n".getBytes());
            throw new RuntimeException(e);
        }

        WRITEHERE.write(("Attempting to server on port " + port + "...\n").getBytes());

        ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getLoopbackAddress());
        serverSocket.setSoTimeout(1000);
        serverSocket.setReuseAddress(true);
        WRITEHERE.write("Server creation successful!\n".getBytes());
        WRITEHERE.write("Waiting for the client connection...\n".getBytes());

        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                InetAddress inetAddress = clientSocket.getInetAddress();
                String hostAddress = inetAddress.getHostAddress();

                WRITEHERE.write(("Client connection received from " + hostAddress + "\n").getBytes());

                InputStream inputStream = clientSocket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);

                String messageType = bufferedReader.readLine();
                if (messageType.equals(PRODUCE_MESSAGE))
                {
                    System.out.println("Message type received: " + PRODUCE_MESSAGE_STRING);
                    System.out.println();

                    String priority = bufferedReader.readLine();
                    String encryptedMessage = bufferedReader.readLine();

                    Utilities.writeEncryptedMessageToFile(priority, encryptedMessage, textFilename);

                    printWriter.println("Message type received: " + PRODUCE_MESSAGE_STRING);
                }
                else if (messageType.equals(CONSUME_MESSAGE))
                {
                    System.out.println("Message type received: " + CONSUME_MESSAGE_STRING);
                    System.out.println();

                    String priority = bufferedReader.readLine();

                    String consumedRecord = Utilities.consumeEncryptedMessageFromFile(priority, textFilename);

                    printWriter.println(consumedRecord);
                }
                else System.out.println("INVALID MESSAGE TYPE: " + messageType);
                printWriter.close();
                bufferedReader.close();
                clientSocket.close();

                //printWriter.print(digitalSignaturePackage);

                printWriter.close();
                bufferedReader.close();
                clientSocket.close();
            } catch (SocketTimeoutException _) {
                // Timeout is expected, continue running
            } catch (FileNotFoundException e) {
                WRITEHERE.write(("File not found...try checking your directories, aborting send\n").getBytes());
            }
        }
//write something too
        WRITEHERE.write("Server stopped! Exiting the process\n".getBytes());
        serverSocket.close();
    }

//    private String createDigitalSignaturePackage(String documentName) throws Exception {
//        final String COMPANY_NAME = "Never Crash Software Services";
//        final String COMPANY_DOMAIN_NAME = "NCSS.com";
//        final String COMPANY_EMAIL = "support@ncss.com";
//
//
//        // Create Digital Certificate
//        DigitalCertificate digitalCertificate = new DigitalCertificate(COMPANY_NAME, COMPANY_DOMAIN_NAME, COMPANY_EMAIL, publicKey);
//
//        // Message to sign
//        String message = DigitalSignature.readMessageFromFile(documentName);
//        byte[] messageAsBytes = message.getBytes();
//
//        // Create digital signature
//        byte[] signature = DigitalSignature.create(messageAsBytes, privateKey);
//
//        // Create the package
//        String digitalSignaturePackage = DigitalSignaturePackage.createPackage(message, signature, digitalCertificate);
//
//        return digitalSignaturePackage;
//    }
}