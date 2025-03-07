package org.example;/*
 * Parker Mina
 * 1/30/2025
 * This class defines a Digital Certificate object and its attributes. These attributes can be returned as a String.
*/

import java.security.PublicKey;

public class DigitalCertificate 
{
    String companyName;
    String companyDomainName;
    String companyEmail;
    PublicKey publicKey;

    public DigitalCertificate(String companyName, String comanyDomainName, String companyEmail, PublicKey publicKey)
    {
        this.companyName = companyName;
        this.companyDomainName = comanyDomainName;
        this.companyEmail = companyEmail;
        this.publicKey = publicKey;
    }

    //Returns digital certificate (containing company name, company domain name, company email, and public key) as a concatenated String.
    public String getDigitalCertificateAsString() throws Exception
    {
        String digitalCertificateAsString = companyName + "\n" + companyDomainName + "\n" + companyEmail + "\n" + RsaEncryptor.getPublicKeyAsString(publicKey);

        return digitalCertificateAsString;
    }
}
