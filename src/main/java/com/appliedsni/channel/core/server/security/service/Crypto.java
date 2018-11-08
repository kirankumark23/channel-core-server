
package com.appliedsni.channel.core.server.security.service;
import javax.crypto.Cipher;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Crypto {
	private static final Logger LOGGER = LoggerFactory.getLogger(Crypto.class);
	/**
	 * Utility class - Not for instantiation.
	 */
	private Crypto() {
		
		
	}	

    private static final String INITVECTOR = "00GauriShankar00";
    
    private static final String UTF_WORD= ("UTF-8");

    public static String encrypt(final String pValue) {
        try {
            SecretKeySpec skeySpec = null;
            {
                String key = "829629gauri54173";
                skeySpec = new SecretKeySpec(key.getBytes(UTF_WORD), "AES");

            }
            IvParameterSpec iv = new IvParameterSpec(INITVECTOR.getBytes(UTF_WORD));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(pValue.getBytes());

            return new String(Base64.encodeBase64(encrypted));
        }
        catch (Exception ex) {
        	LOGGER.debug(ex.getStackTrace().toString());
        }
        return "";
    }

    public static String decrypt(final String pEncrypted) {
        try {
            SecretKeySpec skeySpec = null;
            {
                String key = "829629gauri54173";
                skeySpec = new SecretKeySpec(key.getBytes(UTF_WORD), "AES");
            }
            IvParameterSpec iv = new IvParameterSpec(INITVECTOR.getBytes(UTF_WORD));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(pEncrypted.getBytes()));

            return new String(original);
        }
        catch (Exception ex) {
        	LOGGER.debug(ex.getStackTrace().toString());
        }
        return "";
    }
}
