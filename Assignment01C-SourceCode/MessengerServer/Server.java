import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
    static final String NO_PRIORITY_1_MESSAGES = "NO_PRIORITY_1_MESSAGES";
    static final String NO_PRIORITY_2_MESSAGES = "NO_PRIORITY_2_MESSAGES";
    static final String NO_PRIORITY_3_MESSAGES = "NO_PRIORITY_3_MESSAGES";
    static final String PRODUCE_MESSAGE_STRING = "PRODUCE_MESSAGE";
    static final String CONSUME_MESSAGE_STRING = "CONSUME_MESSAGE";

    static final String PRODUCE_MESSAGE = "0";
    static final String CONSUME_MESSAGE = "1"; 

    static final String textFilename = "EncryptedMessages.txt";

    public static void start(int port) throws Exception
    {
        System.out.println("Running server on port " + port + "...\n");

        boolean listenForConnections = true;

        ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getLoopbackAddress());
        serverSocket.setReuseAddress(true);
       
        while(listenForConnections)
        {
            System.out.println("Waiting for the client connection...\n");
            
            Socket clientSocket = serverSocket.accept();

            InetAddress inetAddress = clientSocket.getInetAddress();
            String hostAddress = inetAddress.getHostAddress();

            System.out.println("Client connection received from " + hostAddress);
          
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
        }

        //Unreachable code
        serverSocket.close();
    }
}
