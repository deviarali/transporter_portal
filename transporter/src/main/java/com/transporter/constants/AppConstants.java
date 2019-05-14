package com.transporter.constants;

/**
 * @author devappa_arali
 *
 */
public interface AppConstants 
{
	public static final String PARAMETER_DELIVERY_EMAIL_FOR_PARTIALLY_COMPLETE_NOTIFICATION = "deliveryEmailForPartiallyCompleteNotification";
	public static final String PARAMETER_NOTIFICATION_MORQUE = "notification_morque";
	public static final String PARAMETER_SMTP_GMAIL_USERNAME = "smtp_gmail_username";
	public static final String PARAMETER_SMTP_GMAIL_PASSWORD = "smtp_gmail_password";
	public static final String PARAMETER_MAXIMUM_RECORDS_ALLOWED_TO_FETCH = "maximum_records_allowed_to_fetch";
	
	/* NOTE: Creating Database Constraints */
	public static final String DB_NAME = "transporter";
	public static final String DB_USER = "root";
	public static final String DB_PASS = "root";


	public static final String LOGIN = "/login";
	
	public static final String LOGIN_PROCESS = "/loginProcess";
	
	public static final String REGISTER_PROCESS = "/registerProcess";
	
	public static final String EXCLUDE_PROPERTIES = "EXCLUDE_PROPERTIES";
	
	public static final String MENU_ITEM_CLIENT = "/client";
	
}
