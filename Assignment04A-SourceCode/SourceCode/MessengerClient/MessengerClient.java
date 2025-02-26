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

    public String sendMessage(String messageType)
    {
        String serverResponse = null;

        try 
        {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(messageType);
            stringBuilder.append(System.lineSeparator());

            String packet = stringBuilder.toString();

            System.out.println();
            System.out.println("Connecting to server...");
            String address = inetAddress.getHostAddress();
            Socket socket = new Socket(address, port);

            System.out.println("Sending packet to remote server...");
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.println(packet);

            System.out.println("Reading response from remote server...");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            serverResponse = getServerResponse(bufferedReader);

            socket.close();
        } 
        catch (Exception ex)
        {
            String message = ex.getMessage();

            System.out.println(message);
        }
       
        return serverResponse;
    }

    private static String getServerResponse(BufferedReader bufferedReader) throws Exception 
    {
        StringBuilder stringBuilder = new StringBuilder();

        String fileline = bufferedReader.readLine();
        
        while (fileline != null)
        {
            stringBuilder.append(fileline + "\n");

            fileline = bufferedReader.readLine();
        }
        
        String fileContent = stringBuilder.toString();

        return fileContent;
    }
}