package com.transporter.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordUtils {

	private static final Logger LOG = LoggerFactory
	        .getLogger(PasswordUtils.class);

	private static final byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8,
	        (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

	/**
	 * Hash the given password for given salt on SHA-256
	 * 
	 * @param passwordToHash
	 * @param salt
	 */
	public static String generateSecurePassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash
			        .getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
				        .substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Error while encryption", e);
		}
		return generatedPassword;
	}
}
