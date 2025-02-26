/* 
 * Parker Mina
 * 1/30/2025
 * This class creates/verifies a digital signature as well as reads a message from a provided file.
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Scanner;

public class DigitalSignature 
{
    //The SHA256 RSA signature algorithm uses the SHA256 data digest with the 
    //RSASSA-PKCS1-v1_5 signature scheme as defined in PKCS #1 v2.2.
	private static final String SIGNING_ALGORITHM = "SHA256withRSA"; 
	
    //Signs provided message (as an array of bytes) with private key and then encodes the signature and returns it as an array of bytes.
	public static byte[] create(byte[] message, PrivateKey privateKey) throws Exception 
	{ 
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(message);
        
        byte[] signatureAsBytes = Base64.getEncoder().encode(signature.sign());

        return signatureAsBytes; 
	} 

    //Signs provided message (as a String) with private key and then encodes the signature and returns it as an array of bytes.
	public static byte[] create(String message, PrivateKey privateKey) throws Exception 
	{ 
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        
        byte[] signatureAsBytes = Base64.getEncoder().encode(signature.sign());

        return signatureAsBytes; 
	} 

    //Decodes provided signature (as an array of bytes) and verifies it with public key.
	public static boolean verify(byte[] message, byte[] signatureToVerify, PublicKey publicKey) throws Exception 
	{ 
        byte[] signatureToVerifyDecoded = Base64.getDecoder().decode(signatureToVerify);

        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(message);
        boolean result = signature.verify(signatureToVerifyDecoded);
        return result; 
	} 

    //Decodes provided signature (as a String) and verifies it with public key.
    public static boolean verify(String message, String signatureToVerify, PublicKey publicKey) throws Exception 
	{ 
        byte[] signatureToVerifyDecoded = Base64.getDecoder().decode(signatureToVerify);
        
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(message.getBytes());
        boolean result = signature.verify(signatureToVerifyDecoded);
        return result; 
	} 

    //This method is never called.
	static void writeMessageToFile(String message, String filename) throws Exception
    {
        
    }

    //Reads a message from the provided file. Scanner reads file line by line appending to a String Builder then outputs the read file.
    static String readMessageFromFile(String filename) throws Exception
    {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(filename);
        Scanner lineReader = new Scanner(file).useDelimiter("\n");
        while(lineReader.hasNext()) {
            stringBuilder.append(lineReader.nextLine() + "\n");
        }
        lineReader.close();
       

        return stringBuilder.toString();
    }
}
