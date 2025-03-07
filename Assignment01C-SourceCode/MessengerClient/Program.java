import java.net.InetAddress;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

public class Program 
{
    public static void main(String[] args) 
    {
        final int port = 50444;
        final String EXIT_COMMAND = "10";
        final String PRODUCE_MESSAGE = "0";
        final String CONSUME_MESSAGE = "1"; 
        
        try
        {
            Scanner keyboard = new Scanner(System.in);
            InetAddress inetAddress = InetAddress.getLoopbackAddress();
            MessengerClient messengerClient = new MessengerClient(inetAddress, port);
            
            String publicKeyFilename = "public.key";
            String privateKeyFilename = "private.key";
            
            //Read public key from file
            PublicKey publicKey = RsaEncryptor.readPublicKeyFromFile(publicKeyFilename);
        
            //Read private key from file
            PrivateKey privateKey = RsaEncryptor.readPrivateKeyFromFile(privateKeyFilename);
            
            System.out.print("Enter 0-Produce Message, 1-Consume Message, 10-Exit): ");
            String response = keyboard.nextLine();

            while(response.equals(EXIT_COMMAND) == false)
            {
                if (response.equals(PRODUCE_MESSAGE))
                {
                    System.out.print("Enter message priority (0-Low, 1-Medium, 2-High): ");
                    String priority = keyboard.nextLine();
    
                    System.out.print("Enter message: ");
                    String message = keyboard.nextLine();
    
                    //Use public key to encrypt the message as string
                    String encryptedMessage = RsaEncryptor.encryptMessageAsString(message, publicKey);
    
                    String serverResponse = messengerClient.sendMessage(PRODUCE_MESSAGE, priority, encryptedMessage);
                
                    System.out.print("Server response: " + serverResponse);
                    System.out.println();
                }
                else if (response.equals(CONSUME_MESSAGE))
                {
                    System.out.print("Enter message priority (0-Low, 1-Medium, 2-High): ");
                    String priority = keyboard.nextLine();
                    
                    String encryptedMessage = messengerClient.getMessage(CONSUME_MESSAGE, priority);
                    String decryptedMessage = RsaEncryptor.decryptMessageAsString(encryptedMessage, privateKey);
                    System.out.print("Decrypted message: ");
                    System.out.println(decryptedMessage);
                    System.out.println();
                }
                else System.out.println("Input error...");
               
                System.out.print("Enter 0-Produce Message, 1-Consume Message, 10-Exit): ");
                response = keyboard.nextLine();
            }

            keyboard.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
