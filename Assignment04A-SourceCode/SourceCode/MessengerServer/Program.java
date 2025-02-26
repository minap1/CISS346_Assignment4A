import java.net.ServerSocket;

public class Program
{
    public static void main(String[] args)
    {
        try 
        {
            //int port = findAvailablePort();
            int port = 50444;

            Server.start(port);
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }

        System.out.println("Shutting down server...");
    }

    public static int findAvailablePort()
    {
        int localPort = -1;

        try (ServerSocket serverSocket = new ServerSocket(0)) 
        {
            localPort = serverSocket.getLocalPort();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return localPort;
    }
}
