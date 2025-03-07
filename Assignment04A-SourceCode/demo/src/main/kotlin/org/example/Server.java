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

                String documentName = bufferedReader.readLine();

                WRITEHERE.write(("Document name received: " + documentName + "\n").getBytes());

                String digitalSignaturePackage = createDigitalSignaturePackage(documentName);

                WRITEHERE.write("Sending signed document...\n".getBytes());

                printWriter.print(digitalSignaturePackage);

                printWriter.close();
                bufferedReader.close();
                clientSocket.close();
            } catch (SocketTimeoutException _) {
                // Timeout is expected, continue running
            } catch (FileNotFoundException e) {
                WRITEHERE.write(("File not found...try checking your directories, aborting send\n").getBytes());
            }
        }

        WRITEHERE.write("Server stopped! Exiting the process\n".getBytes());
        serverSocket.close();
    }

    private String createDigitalSignaturePackage(String documentName) throws Exception {
        final String COMPANY_NAME = "Never Crash Software Services";
        final String COMPANY_DOMAIN_NAME = "NCSS.com";
        final String COMPANY_EMAIL = "support@ncss.com";
        String publicKeyFilename = "public.key";
        String privateKeyFilename = "private.key";

        // Read public key from file
        PublicKey publicKey = RsaEncryptor.readPublicKeyFromFile(publicKeyFilename);

        // Read private key from file
        PrivateKey privateKey = RsaEncryptor.readPrivateKeyFromFile(privateKeyFilename);

        // Create Digital Certificate
        DigitalCertificate digitalCertificate = new DigitalCertificate(COMPANY_NAME, COMPANY_DOMAIN_NAME, COMPANY_EMAIL, publicKey);

        // Message to sign
        String message = DigitalSignature.readMessageFromFile(documentName);
        byte[] messageAsBytes = message.getBytes();

        // Create digital signature
        byte[] signature = DigitalSignature.create(messageAsBytes, privateKey);

        // Create the package
        String digitalSignaturePackage = DigitalSignaturePackage.createPackage(message, signature, digitalCertificate);

        return digitalSignaturePackage;
    }
}