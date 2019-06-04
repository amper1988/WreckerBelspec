package com.belspec.app.utils;

import android.util.Base64;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Encode {
    private static String BASIC_AUTH_TEMPLATE = "Basic %1$s";
    public static String getBasicAuthTemplate(String login, String pass){
        return String.format(BASIC_AUTH_TEMPLATE, Base64.encodeToString((login+":"+pass).getBytes(), Base64.NO_WRAP));
    }

    public static String encrypt(SecretKey secret, String buffer) throws GeneralSecurityException
    {
        /* Encrypt the message. */
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

        SecureRandom rng = new SecureRandom();
        byte[] ivData = new byte[cipher.getBlockSize()];
        rng.nextBytes(ivData);

        cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivData));
        byte[] ciphertext = cipher.doFinal(buffer.getBytes());


        int aLen = ivData.length;
        int bLen = ciphertext.length;
        byte[] result = new byte[aLen + bLen];
        System.arraycopy(ivData, 0, result, 0, aLen);
        System.arraycopy(ciphertext, 0, result, aLen, bLen);


        return Base64.encodeToString(result, Base64.NO_WRAP );
    }
}