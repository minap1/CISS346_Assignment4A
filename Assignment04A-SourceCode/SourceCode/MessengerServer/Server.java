import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Server 
{
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

            String documentName = bufferedReader.readLine();

            System.out.println("Document name received: " + documentName);

            String digitalSignaturePackage = createDigitalSignaturePackage(documentName);

            System.out.println("Sending signed document...\n");
        
            printWriter.print(digitalSignaturePackage);
        
            printWriter.close();
            bufferedReader.close();
            clientSocket.close();
        }

        //Unreachable code
        serverSocket.close();
    }

    static String createDigitalSignaturePackage(String documentName) throws Exception
    {
        final String COMPANY_NAME = "Never Crash Software Services";
        final String COMPANY_DOMAIN_NAME = "NCSS.com";
        final String COMPANY_EMAIL = "support@ncss.com";
        String publicKeyFilename = "public.key";
        String privateKeyFilename = "private.key";

        //Read public key from file
        PublicKey publicKey = RsaEncryptor.readPublicKeyFromFile(publicKeyFilename);
        
        //Read private key from file
        PrivateKey privateKey = RsaEncryptor.readPrivateKeyFromFile(privateKeyFilename);
        
        //Create Digital Certificate
        DigitalCertificate digitalCertificate = new DigitalCertificate(COMPANY_NAME, COMPANY_DOMAIN_NAME, COMPANY_EMAIL, publicKey);
        
        //Message to sign
        String message = DigitalSignature.readMessageFromFile(documentName);
        byte[] messageAsBytes = message.getBytes();

		//Create digital signature 
        byte[] signature = DigitalSignature.create(messageAsBytes, privateKey); 
        
        //Create the package
        String digitalSignaturePackage = DigitalSignaturePackage.createPackage(message, signature, digitalCertificate);

        return digitalSignaturePackage;
    }
}
