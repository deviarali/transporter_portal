package com.transporter.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    public static Date getCurrentDateTime() {
    	return new Date(System.currentTimeMillis());
    }
    
    public static Date getCurrentDate() {
        return getCurrentDateCalendar().getTime();
    }
    
    public static Calendar getCurrentDateCalendar() {
    	Calendar calendar = getCurrentCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    	return calendar;
    }

    public static Calendar getCurrentCalendar() {
    	return Calendar.getInstance();
    }

    public static String formatDate(Calendar calendar, String datePattern) {
    	if (calendar == null) return "";
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		String formattedDate = null;
		if(calendar != null) {
			formattedDate = format.format(calendar.getTime());
		}
		return formattedDate;
    }

    public static String formatDateTime(Calendar calendar) {
		return formatDate(calendar, "dd/MM/yyyy HH:mm:ss");
    }
    
    public static String formatDateTime() {
		return formatDate(getCurrentCalendar(), "dd/MM/yyyy");
    }
    
    public static String formatDate() {
		return formatDate(CalendarUtils.getCurrentDateCalendar(), "dd/MM/yyyy");
    }

    public static Calendar convertStringToCalendar(String strDate) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
    	Date date = sdf.parse(strDate); 
    	Calendar cal = Calendar.getInstance(); cal.setTime(date);
    	return cal;
    }
    
    public static Calendar convertStringToCalendar(String strDate, String format) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat(format); 
    	Date date = sdf.parse(strDate); 
    	Calendar cal = Calendar.getInstance(); cal.setTime(date);
    	return cal;
    }
    
    public static int getHoursDifference(Date date1, Date date2) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }
    
    public static int getMinutesDifference(Calendar cal1, Calendar cal2) {
        final int SECS_TO_HOUR = 1000 * 60;
        
        int minutes = (int) (cal2.getTime().getTime() - cal1.getTime().getTime()) / SECS_TO_HOUR;
        
        return minutes; 
    }
    
}
