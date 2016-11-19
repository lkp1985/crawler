/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.donghang;

/**
 * <p>class function description.<p>
 *
 * create  2016年3月11日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Hex;

/**
 * @author TonyJ
 * @Time 2013-7-1 上午02:12:46
 *
 */
public class BigIntegerRSA {

	public String rsaCrypt(String modeHex, String exponentHex, String messageg)

			throws IllegalBlockSizeException, BadPaddingException,

			NoSuchAlgorithmException, InvalidKeySpecException,

			NoSuchPaddingException, InvalidKeyException,

			UnsupportedEncodingException {

		KeyFactory factory = KeyFactory.getInstance("RSA");

		BigInteger m = new BigInteger(modeHex, 16);

		BigInteger e = new BigInteger(exponentHex, 16);

		RSAPublicKeySpec spec = new RSAPublicKeySpec(m, e);

		RSAPublicKey pub = (RSAPublicKey) factory.generatePublic(spec);

		Cipher enc = Cipher.getInstance("RSA");

		enc.init(Cipher.ENCRYPT_MODE, pub);

		byte[] encryptedContentKey = enc.doFinal(messageg.getBytes("GB2312"));

		return new String(Hex.encodeHex(encryptedContentKey));

	}
}
