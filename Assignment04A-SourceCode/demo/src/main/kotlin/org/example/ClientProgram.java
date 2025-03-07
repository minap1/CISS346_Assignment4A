//package org.example;
//
//import java.net.InetAddress;
//import java.util.Scanner;
//
//public class ClientProgram
//{
//    public static void main(String[] args)
//    {
//        final int port = 50444;
//        final String GET_SIGNED_DOCUMENT = "0";
//        final String EXIT_COMMAND = "10";
//
//        try
//        {
//            Scanner keyboard = new Scanner(System.in);
//            //InetAddress inetAddress = InetAddress.getLocalHost();
//            InetAddress inetAddress = InetAddress.getLoopbackAddress();
//
//            MessengerClient messengerClient = new MessengerClient(inetAddress, port);
//
//            System.out.print("Enter 0-Get Signed Document, 10-Exit: ");
//            String response = keyboard.nextLine();
//
//            while(response.equals(EXIT_COMMAND) == false)
//            {
//                if (response.equals(GET_SIGNED_DOCUMENT))
//                {
//                    System.out.print("Enter document name: ");
//                    String documentName = keyboard.nextLine();
//
//                    String digitalSignaturePackage = messengerClient.sendMessage(documentName);
//
//                    //Output the package
//                    DigitalSignaturePackage.outputPackageComponents(digitalSignaturePackage);
//
//                    //Verify package
//                    boolean result =  DigitalSignaturePackage.verifyPackage(digitalSignaturePackage);
//                    System.out.println("Digital signature is valid: " + result + "\n");
//                }
//                else System.out.println("Input error...");
//
//                System.out.print("Enter 0-Get Signed Document, 10-Exit: ");
//                response = keyboard.nextLine();
//            }
//
//            keyboard.close();
//        }
//        catch (Exception ex)
//        {
//            System.out.println(ex.getMessage());
//        }
//    }
//}
