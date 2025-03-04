/* 
 * Parker Mina
 * 1/30/2025
 * This class creates a Digital Signature Package retrieving all the necessary information, packing it, extracting
 * its components, printing out said components, and verifying its contents.
*/

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Scanner;

public class DigitalSignaturePackage 
{
    private static final int PACKAGE_MESSAGE_INDEX = 0;
    private static final int PACKAGE_DIGITAL_SIGNATURE_INDEX = 1;
	private static final int PACKAGE_CERTIFICATE_COMPANY_NAME_INDEX = 2;
	private static final int PACKAGE_CERTIFICATE_COMPANY_DOMAIN_NAME_INDEX = 3;
	private static final int PACKAGE_CERTIFICATE_COMPANY_EMAIL_INDEX = 4;
	private static final int PACKAGE_CERTIFICATE_PUBLIC_KEY_INDEX = 5;
	
	//Returns provided message, digital signature and digital certificate as a concatenated String.
	public static String createPackage(String message, byte[] digitalSignature,  DigitalCertificate digitalCertificate) throws Exception 
	{ 
		String digitalSignatureString = new String(digitalSignature);
		String digitalSignaturePackage = message + "\n/\n" + digitalSignatureString + "\n/\n" + digitalCertificate.getDigitalCertificateAsString();
        
		return digitalSignaturePackage; 
	} 

	//Reads digitalSignaturePackage and calls DigitalSignature.verify() to verify provided package. Returns True if package is valid.
	public static boolean verifyPackage(String digitalSignaturePackage) throws Exception 
	{ 
		Scanner scanner = new Scanner(digitalSignaturePackage).useDelimiter("\n/\n");
		String message = scanner.next();
		String digitalSignatureAsString = scanner.next();
		String digitalCertificateAsString = scanner.next();
		Scanner certificateScanner = new Scanner(digitalCertificateAsString);
		certificateScanner.nextLine();
		certificateScanner.nextLine();
		certificateScanner.nextLine();
		String publicKeyEncoded = certificateScanner.next();
		PublicKey publicKeyDecoded = RsaEncryptor.getPublicKeyFromString(publicKeyEncoded);
		
		boolean result = DigitalSignature.verify(message, digitalSignatureAsString, publicKeyDecoded);
		scanner.close();
		certificateScanner.close();
		return result;
	} 

	//Prints out provided digitalSignaturePackage contents.
	public static void outputPackageComponents(String digitalSignaturePackage) throws Exception 
	{ 
		String[] digitalSignaturePackageComponents = extractPackageComponents(digitalSignaturePackage); 

		String message = digitalSignaturePackageComponents[PACKAGE_MESSAGE_INDEX];
		String digitalSignatureAsString = digitalSignaturePackageComponents[PACKAGE_DIGITAL_SIGNATURE_INDEX];
		String digitalCertificateCompanyName = digitalSignaturePackageComponents[PACKAGE_CERTIFICATE_COMPANY_NAME_INDEX];
		String digitalCertificateCompanyDomainName = digitalSignaturePackageComponents[PACKAGE_CERTIFICATE_COMPANY_DOMAIN_NAME_INDEX];
		String digitalCertificateCompanyEmail = digitalSignaturePackageComponents[PACKAGE_CERTIFICATE_COMPANY_EMAIL_INDEX];
		String digitalCertificatePublicKey = digitalSignaturePackageComponents[PACKAGE_CERTIFICATE_PUBLIC_KEY_INDEX];

		System.out.println();
        System.out.println("Package Message:\n\n" + message);
        System.out.println();
        System.out.println("Signed Message:\n\n" + digitalSignatureAsString);
        System.out.println();
        System.out.println("Digital Certificate: Company Name: " + digitalCertificateCompanyName);
        System.out.println();
		System.out.println("Digital Certificate: Company Domain Name: " + digitalCertificateCompanyDomainName);
        System.out.println();
		System.out.println("Digital Certificate: Company Email: " + digitalCertificateCompanyEmail);
        System.out.println();
		System.out.println("Digital Certificate: Public Key:\n\n" + digitalCertificatePublicKey);
        System.out.println();
	} 

	//Returns a String array containing the contents of the provided digitalSignaturePackage.
	private static String[] extractPackageComponents(String digitalSignaturePackage) throws Exception 
	{ 
		String[] digitalSignaturePackageComponents = new String[6];
		Scanner scanner = new Scanner(digitalSignaturePackage).useDelimiter("\n/\n");
		
		digitalSignaturePackageComponents[0] = scanner.next();
		digitalSignaturePackageComponents[1] = scanner.next();
		String digitalCertificateString = scanner.next();
		Scanner certificateReader = new Scanner(digitalCertificateString);
		digitalSignaturePackageComponents[2] = certificateReader.nextLine();
		digitalSignaturePackageComponents[3] = certificateReader.nextLine();
		digitalSignaturePackageComponents[4] = certificateReader.nextLine();
		digitalSignaturePackageComponents[5] = certificateReader.nextLine();

        scanner.close();
		certificateReader.close();
		return digitalSignaturePackageComponents; 
	} 
}
