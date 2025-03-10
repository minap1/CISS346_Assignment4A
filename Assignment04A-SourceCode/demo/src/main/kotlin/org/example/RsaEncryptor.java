package org.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

class RsaEncryptor
{
    //Generates keypairs for the RSA algorithm (Signature/Cipher).
    private static final String ENCRYPTION_ALGORITHM = "RSA";
    
    //keysize is an algorithm-specific metric, such as modulus length, 
    //specified in number of bits.
    private static final int KEYSIZE = 2048;
    
    //API Note: It is recommended to use a transformation that fully specifies the algorithm, 
    //mode, and padding. By not doing so, the provider will use a default for the mode and 
    //padding which may not meet the security requirements of your application.
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    static void createPublicPrivateKeyPair(String publicKeyFilename, String privateKeyFilename) throws Exception
    {
        //Create public-private key pair
        KeyPair keyPair = getKeyPair();

        //Generate public and private keys
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        //Write public key to file
        writePublicKeyToFile(publicKey , publicKeyFilename);

        //Write private key to file
        writePrivateKeyToFile(privateKey, privateKeyFilename);        
    }
    
    static KeyPair getKeyPair() throws Exception 
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM);
        keyPairGenerator.initialize(KEYSIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        return keyPair;
    }

    static void writePublicKeyToFile(PublicKey publicKey, String filename) throws Exception 
    {
        byte[] encodedKey = publicKey.getEncoded();

        writeBytesToFile(encodedKey, filename);
    }

    static void writePrivateKeyToFile(PrivateKey privateKey, String filename) throws Exception
    {
        byte[] encodedKey = privateKey.getEncoded();

        writeBytesToFile(encodedKey, filename);
    }

    static PublicKey readPublicKeyFromFile(String filename) throws Exception
    {
        byte[] keyBytes = readBytesFromFile(filename);

        PublicKey publicKey = getPublicKeyFromBytes(keyBytes);
        
        return publicKey;
    }

    static PrivateKey readPrivateKeyFromFile(String filename) throws Exception
    {
        byte[] keyBytes = readBytesFromFile(filename);
        
        PrivateKey privateKey = getPrivateKeyFromBytes(keyBytes);

        return privateKey;
    }

    static PublicKey getPublicKeyFromBytes(byte[] publicKeyAsBytes) throws Exception
    {
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(publicKeyAsBytes);
        PublicKey publicKey = keyFactory.generatePublic(encodedKeySpec);

        return publicKey;
    }

    static PrivateKey getPrivateKeyFromBytes(byte[] privateKeyAsBytes) throws Exception
    {
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyAsBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }

    static PublicKey getPublicKeyFromString(String publicKeyAsString) throws Exception
    {
        byte[] publicKeyAsBytes = Base64.getDecoder().decode(publicKeyAsString);

        PublicKey publicKey = getPublicKeyFromBytes(publicKeyAsBytes);

        return publicKey;
    }

    static PrivateKey getPrivateKeyFromString(String privateKeyAsString) throws Exception
    {
        byte[] privateKeyAsBytes = Base64.getDecoder().decode(privateKeyAsString);

        PrivateKey privateKey = getPrivateKeyFromBytes(privateKeyAsBytes);

        return privateKey;
    }

    static String getPublicKeyAsString(PublicKey publicKey) throws Exception
    {
        byte[] keyAsBytes = publicKey.getEncoded();

		String keyAsString = Base64.getEncoder().encodeToString(keyAsBytes);
		
        return keyAsString;
    }

    static String getPrivateKeyAsString(PrivateKey privateKeyKey) throws Exception
    {
        byte[] keyAsBytes = privateKeyKey.getEncoded();
        
		String keyAsString = Base64.getEncoder().encodeToString(keyAsBytes);
		
        return keyAsString;
    }
    
    //Encrypt using public key
    public static byte[] encryptMessageAsBytes(byte[] message, PublicKey publicKey) throws Exception 
    {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedMessage = cipher.doFinal(message);

        return encryptedMessage;
    }
    //Encrypt using public key
    public static byte[] encryptMessageAsBytes(String message, PublicKey publicKey) throws Exception 
    {
        byte[] messageBytes = message.getBytes();

        byte[] encryptedMessage = encryptMessageAsBytes(messageBytes, publicKey); //cipher.doFinal(messageBytes);

        return encryptedMessage;
    }

    //Encrypt using public key
    public static String encryptMessageAsString(byte[] message, PublicKey publicKey) throws Exception 
    {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedMessageAsBytes = cipher.doFinal(message);

        String encryptedMessage = Base64.getEncoder().encodeToString(encryptedMessageAsBytes);

        return encryptedMessage;
    }

    //Encrypt using public key
    public static String encryptMessageAsString(String message, PublicKey publicKey) throws Exception 
    {
        byte[] messageBytes = message.getBytes();
       
        String encryptedMessage = encryptMessageAsString(messageBytes, publicKey);

        return encryptedMessage;
    }

    //Encrypt using private key
    public static byte[] encryptMessageAsBytes(byte[] message, PrivateKey privateKey) throws Exception 
    {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] encryptedMessage = cipher.doFinal(message);

        return encryptedMessage;
    } 

    //Encrypt using private key
    public static byte[] encryptMessageAsBytes(String message, PrivateKey privateKey) throws Exception 
    {
        byte[] messageBytes = message.getBytes();

        byte[] encryptedMessage = encryptMessageAsBytes(messageBytes, privateKey);

        return encryptedMessage;
    } 

     //Encrypt using private key
     public static String encryptMessageAsString(byte[] message, PrivateKey privateKey) throws Exception 
     {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] encryptedMessageAsBytes = cipher.doFinal(message);

        String encryptedMessage = Base64.getEncoder().encodeToString(encryptedMessageAsBytes);

        return encryptedMessage;
     } 
 
     //Encrypt using private key
     public static String encryptMessageAsString(String message, PrivateKey privateKey) throws Exception 
     {
        byte[] messageBytes = message.getBytes();
       
        String encryptedMessage = encryptMessageAsString(messageBytes, privateKey);

        return encryptedMessage;
     } 
 
    //Decrypt using private key
    public static byte[] decryptMessageAsBytes(byte[] encryptedMessage, PrivateKey privateKey) throws Exception 
    {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
        
        return decryptedMessage;
    }

    //Decrypt using private key
    public static byte[] decryptMessageAsBytes(String encryptedMessage, PrivateKey privateKey) throws Exception 
    {
        byte[] messageBytes = encryptedMessage.getBytes();

        byte[] decryptedMessage = decryptMessageAsBytes(messageBytes, privateKey);
        
        return decryptedMessage;
    }

    //Decrypt using private key
    public static String decryptMessageAsString(byte[] encryptedMessage, PrivateKey privateKey) throws Exception 
    {
        byte[] decryptedMessagAsBytes = decryptMessageAsBytes(encryptedMessage, privateKey); 
        
        String decryptedMessage = new String(decryptedMessagAsBytes);

        return decryptedMessage;
    }

    //Decrypt using private key
    public static String decryptMessageAsString(String encryptedMessage, PrivateKey privateKey) throws Exception 
    {
        byte[] encryptedMessageAsBytes = Base64.getDecoder().decode(encryptedMessage);

        String decryptedMessage = decryptMessageAsString(encryptedMessageAsBytes, privateKey); 
   
        return decryptedMessage;
    }

    //Decrypt using public key
    public static byte[] decryptMessageAsBytes(byte[] encryptedMessage, PublicKey publicKey) throws Exception 
    {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
        
        return decryptedMessage;
    }

    //Decrypt using public key
    public static byte[] decryptMessageAsBytes(String encryptedMessage, PublicKey publicKey) throws Exception 
    {
        byte[] messageBytes = encryptedMessage.getBytes();

        byte[] decryptedMessage = decryptMessageAsBytes(messageBytes, publicKey);
        
        return decryptedMessage;
    }

    //Decrypt using public key
    public static String decryptMessageAsString(byte[] encryptedMessage, PublicKey publicKey) throws Exception 
    {
        byte[] decryptedMessagAsBytes = decryptMessageAsBytes(encryptedMessage, publicKey); 
        
        String decryptedMessage = new String(decryptedMessagAsBytes);

        return decryptedMessage;
    }

    //Decrypt using public key
    public static String decryptMessageAsString(String encryptedMessage, PublicKey publicKey) throws Exception 
    {
        byte[] encryptedMessageAsBytes = Base64.getDecoder().decode(encryptedMessage);

        String decryptedMessage = decryptMessageAsString(encryptedMessageAsBytes, publicKey); 
       
        return decryptedMessage;
    }

    public static void writeEncryptedMessageToFile(byte[] encryptedMessageBytes, String filename) throws Exception
    {
        writeBytesToFile(encryptedMessageBytes, filename);
    }

    public static void writeEncryptedMessageToFile(String encryptedMessage, String filename, boolean append) throws Exception
    {
        writeStringToFile(encryptedMessage, filename, append);
    }

    public static byte[] readEncryptedMessageFromFileAsBytes(String filename) throws Exception
    {
        byte[] encryptedFileBytes = readBytesFromFile(filename);
        
        return encryptedFileBytes;
    }

    public static String readEncryptedMessageFromFileAsString(String filename) throws Exception
    {
        String encryptedMessage = readLineFromFile(filename);

        return encryptedMessage;
    }

    private static void writeBytesToFile(byte[] bytes, String filename) throws Exception
    {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }
    
    private static void writeStringToFile(String string, String filename, boolean append) throws Exception
    {
        FileWriter fileWriter = new FileWriter(filename, append);

        fileWriter.write(string);
        fileWriter.write(System.lineSeparator());

        fileWriter.close();
    }

    private static byte[] readBytesFromFile(String filename) throws Exception
    {
        FileInputStream fileInputStream = new FileInputStream(filename);
        byte[] bytes = fileInputStream.readAllBytes();
        fileInputStream.close();

        return bytes;
    }

    public static String readLineFromFile(String filename) throws Exception
    {
        FileReader fileReader = new FileReader(filename);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String fileline = bufferedReader.readLine();

        bufferedReader.close();

        return fileline;
    }
}