/**
 * 
 */
package com.transporter.utils;

import java.net.URLDecoder;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.BeanUtils;

/**
 * @author Dev
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CaAppUtils {
	public static <Source, Destination> Destination copyBeanProperties(Source source, Class target) {
		Destination destination = null;
		try {
			destination = (Destination) target.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(source, target);
		return destination;
	}

	public static boolean isListNotNullAndEmpty(List list) {
		return (list != null && !list.isEmpty());
	}

	public static boolean isNotNull(Object object) {
		return (null != object);
	}

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public static boolean isValidPhoneNumber(String phoneNo) {
		// validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}"))
			return true;
		// validating phone number with -, . or spaces
		else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
			return true;
		// validating phone number with extension length from 3 to 5
		else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
			return true;
		// validating phone number where area code is in braces ()
		else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
			return true;
		// return false if nothing matches the input
		else
			return false;

	}
	
	public static String getFileUploadDirectory() {
		String currentPath = CaAppUtils.class.getClassLoader().getResource("./").getPath();
		
//		currentPath = URLDecoder.decode(currentPath, "UTF-8");
		String pathArr[] = currentPath.split("/classes/");
		
		String fileUploadDirectory = pathArr[0] + "/userVerificationDocs/";
		fileUploadDirectory = fileUploadDirectory.substring(1);
		
		System.out.println("fileUploadDirectory >>> " + fileUploadDirectory);

		return fileUploadDirectory;
	}
}
