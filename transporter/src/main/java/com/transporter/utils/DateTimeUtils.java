package com.transporter.utils;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


/**
 * @author JSALDANHA
 */
@SuppressWarnings("unchecked")
public class DateTimeUtils {

    private static final String SPACES = "\\s+";
    private static final String SEPARATORS = "[-/.]";
    private static final String DEF_SEPARATOR = "/";
    
    private static final Map datePatterns;
    private static final Map timePatterns;
    
    private static final Map dateFormats;
    private static final Map timesFormats;
    
    public static final String PARAMETER_DATE_PATTERN = "datePattern";
    public final static long DAY_IN_MILLISECONDS = 86400000;
    
    
    static {
        datePatterns = getDatePatterns();
        timePatterns = getTimePatterns();
        
        dateFormats = getDateFormats();
        timesFormats = getTimeFormats();
    }
    
    
    private DateTimeUtils() {
    }

    /**
     * This method returns a current time calendar. 
     * @return
     */
    public static Calendar getCurrentCalendar() {
    	return Calendar.getInstance();
    }
    
    /**
     * This method returns a current time calendar. 
     * @return
     */
    public static Calendar getCalendar(long timestamp) {
    	Calendar calendar =  Calendar.getInstance();
    	calendar.setTimeInMillis(timestamp);
    	return calendar;
    }
    
    /**
     * This method returns a current date calendar. 
     * @return
     */
    public static Calendar getCurrentDateCalendar() {
    	Calendar calendar = getCurrentCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    	return calendar;
    }

    /**
     * This method returns a current date time calendar. 
     * @return
     */
    public static Calendar getCurrentDateTimeCalendar() {
    	Calendar calendar = getCurrentCalendar();
        calendar.set(Calendar.MILLISECOND, 0);
    	return calendar;
    }
    
    /**
     * This method returns a Calendar with day precision, clearing HOUR_OF_DAY, MINUTE_OF_DAY and SECOND_OF_DAY (00:00:00) 
     * @return
     */
    public static Calendar getCurrentDay() {
    	Calendar calendar =  Calendar.getInstance();
    	calendar.set(Calendar.HOUR_OF_DAY,0);
    	calendar.set(Calendar.MINUTE,0);
    	calendar.set(Calendar.SECOND,0);
    	return calendar;
    }

    
    public static Date getCurrentDateTime() {
    	return new Date(System.currentTimeMillis());
    }
    
    
    public static Date getCurrentDate() {
        return getCurrentDateCalendar().getTime();
    }
    
    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public static Calendar removeTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
    
	public static Calendar deserializeCalendar(String value) {
		if (!StringUtils.hasText(value)) {
			return null;
		}
		
		Date date = null;
		
		try {
			date = parseDate(value);
		}
		catch (ParseException pe) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar;
	}
    
    public static Timestamp getCurrentTimestamp() {
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.MILLISECOND, 0);
        
        return new Timestamp(calendar.getTime().getTime());
    }    

    public static Date getDate(int year, int month, int day) {
    	Calendar calendar = Calendar.getInstance();
    	
    	calendar.setLenient(false);
    	calendar.set(year, month - 1,day);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	
        return calendar.getTime();
    }

    /**
     * Gets a java.sql.Date from a Calendar
     * @param calendar
     * @return
     */
    public static java.sql.Date getSqlDate(Calendar calendar) {
    	if (calendar == null) {
    		return null;    		
    	}
        return getSqlDate(calendar.getTime());
    }
    

    
    
    public static java.sql.Date getSqlDate(int year, int month, int day) {
    	return new java.sql.Date(getCalendar(year, month, day).getTimeInMillis()); 
    }
    
    
    /**
     * Gets a java.sql.Date from a java.util.Date.
     * @param date
     * @return
     */
    public static java.sql.Date getSqlDate(Date date) {
    	if (date == null) {
    		return null;    		
    	}
    	return new java.sql.Date(date.getTime());
    }
    

    public static Timestamp getTimestamp(int year, int month, int day) {
    	Calendar calendar = Calendar.getInstance();
    	
    	calendar.setLenient(false);
    	calendar.set(year, month - 1, day);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
        
    	return new Timestamp(calendar.getTime().getTime());
    }

    public static Timestamp getTimestamp(int year, int month, int day, int hour, int minute, int second) {
    	Calendar calendar = Calendar.getInstance();
    
    	calendar.setLenient(false);
    	calendar.set(year, month - 1, day, hour, minute, second);
    	calendar.set(Calendar.MILLISECOND, 0);
        
    	return new Timestamp(calendar.getTime().getTime());
    }
    
    /**
     * Use when we need a second precision Calendar value 
     * @param year
     * @param month (1 to 12 value)
     * @param day
     * @return
     */
    @Deprecated
    public static Calendar getCalendar(int year, int month, int day) {
    	return getCalendar(year, month, day, 0, 0, 0, 0);
    }
    
    /**
     * Use when we need a second precision Calendar value 
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    @Deprecated
    public static Calendar getCalendar(int year, int month, int day, int hour, int minute, int second) {
    	return getCalendar(year, month, day, hour, minute, second, 0);
    }
    
    
    /**
     * Use when we need a millisecond precision Calendar value
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @param millisecond
     * @return
     * Set latient property shoudl be reviewd.
     */
    @Deprecated  
    public static Calendar getCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond) {
    	Calendar calendar = Calendar.getInstance();
    
    	calendar.set(year, month - 1, day, hour, minute, second);
    	calendar.set(Calendar.MILLISECOND, millisecond);
        
    	return calendar;
    }
    

    public static Date getFirstDateOfMonth(Date date) {
    	if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        return calendar.getTime();
    }
    
    public static Date getFirstDateOfMonth(int year, int month) {
        int firstDayOfMonth = DateTimeUtils.getFirstDayOfMonth(year, month) ;
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month - 1, firstDayOfMonth);
        
        return gregorianCalendar.getTime();
    }

    public static Calendar getCalendar(Date date) {
    	if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        return calendar;
    }
    
    public static Calendar getCalendar(Calendar date) {
    	if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.getTime());
        
        return calendar;
    }
    
    public static int getLastDayOfMonth(int year, int month) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month - 1, 1);
        
        return gregorianCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    }

    public static int getFirstDayOfMonth(int year, int month) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month - 1, 1);
        
        return gregorianCalendar.getActualMinimum(GregorianCalendar.DAY_OF_MONTH);
    }

    public static Date getLastDateOfMonth(int year, int month) {
        int maxDayOfMonth = DateTimeUtils.getLastDayOfMonth(year, month) ;
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month - 1, maxDayOfMonth );
        
        return gregorianCalendar.getTime();
    }

    public static int getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getLastDateOfMonth(Date date) {
    	if (date == null) return null;
        int maxDayOfMonth = DateTimeUtils.getLastDayOfMonth( date ) ;
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);
        
        return calendar.getTime();
    }

    public static int getNumberOfDays(Date date1, Date date2) {
        return (getOffsetInDays(date1, date2) + 1);
    }

    /**
     * This methdo has errors. If you test 2 dates on same day butwith different times
     * You will get 1 month of difference between them.
     * TODO
     * @param date1
     * @param date2
     * @return
     */
    @Deprecated
    public static int getNumberOfMonths(Date date1, Date date2) {
    	Calendar calendar1 = Calendar.getInstance();
        
    	calendar1.setTime(date1);
        
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
                
        boolean setDateToLastDayOfMonth = false;

        if (getLastDayOfMonth(date1) == getDayOfMonth(date1)) {
        	setDateToLastDayOfMonth = true;
        }
        
        int initialDay = getDayOfMonth(date1);
        
        int monthCount = 0;
        
        while(calendar1.before(calendar2)) {
        	calendar1.add(Calendar.MONTH, 1);
        	
        	if (setDateToLastDayOfMonth) {
        		calendar1.set(Calendar.DAY_OF_MONTH, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
        	}
        	else {
        		calendar1.set(Calendar.DAY_OF_MONTH, getSafeDay(calendar1, initialDay));
        	}

        	monthCount++;
        } 
        
        return monthCount;
    }

    public static long getDateInMillisecondsUntilMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime().getTime();
    }

    public static long getTimeInMillisecondsSinceMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, 0);

        return calendar.getTime().getTime();
    }

    /**
     * Gets the day of month from a Date
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getDayOfMonth(calendar);
    }
    
    /**
     * Gets the day of month from a Calendar
     * @param calendar
     * @return
     */
    public static int getDayOfMonth(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    

    /**
     * This method returns month of calendar (1-12)
     * @param calendar
     * @return
     */
    public static int getMonth(Calendar calendar) {
        return (calendar.get(Calendar.MONTH) + 1);
    }
    

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        
        return (calendar.get(Calendar.MONTH) + 1);
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * Gets calendar year
     * @param calendar
     * @return
     */
    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }
    
    

    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getHours(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutes(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        
        return calendar.get(Calendar.MINUTE);
    }

    public static int getSeconds(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        
        return calendar.get(Calendar.SECOND);
    }

    public static int getMilliseconds(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        
        return calendar.get(Calendar.MILLISECOND);
    }

    public static int getOffsetInDays(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        int adjust = calendar1.get(Calendar.DST_OFFSET);
        
        if (adjust != 0) {
        	calendar1.add(Calendar.MILLISECOND, adjust);
        }

        adjust = calendar2.get(Calendar.DST_OFFSET);

        if (adjust != 0) {
        	calendar2.add(Calendar.MILLISECOND, adjust);
        }

        long offset = calendar2.getTime().getTime() - calendar1.getTime().getTime();

        return ((int) (offset / 86400000));
    }

    public static Date addDay(Date date, int numberOfDays) {
    	if (date == null) return null;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);

        return calendar.getTime();
    }

    /**
     * Adds Days to a new calendar based on calendar argument
     * @param calendar
     * @param numberOfDays
     * @return
     */
    public static Calendar addDay(final Calendar calendar, int numberOfDays) {
        Calendar newCalendar = (Calendar)calendar.clone();

        newCalendar.add(Calendar.DAY_OF_MONTH, numberOfDays);

        return newCalendar;
    }
    

    
    
    public static Date addMonth(Date date, int numberOfMonths) {
    	if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, numberOfMonths);

        return calendar.getTime();
    }
    
    
    public static Date addYear(Date date, int numberOfYears) {
    	if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, numberOfYears);
        
        return calendar.getTime();
    }
    
    /**
     * Add Years to a new calendar based on calendar argument
     * @param calendar
     * @param numberOfYEars
     * @return
     */
    public static Calendar addYear(final Calendar calendar, int numberOfYears) {
        Calendar newCalendar = (Calendar)calendar.clone();
        newCalendar.add(Calendar.YEAR, numberOfYears);
        return newCalendar;
    }
    

    public static Date addHour(Date date, int numberOfHours) {
    	if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, numberOfHours);

        return calendar.getTime();
    }
    
    public static Date addMinute(Date date, int numberOfMinutes) {
    	if (date == null) return null;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, numberOfMinutes);

        return calendar.getTime();
    }

    public static Date addSecond(Date date, int numberOfSeconds) {
    	if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, numberOfSeconds);
        
        return calendar.getTime();
    }
    
    public static String format(Calendar calendar) {
    	if (calendar == null) return "";
        return format(calendar.getTime());
    }
    
    
    public static String format(Date date) {
    	if (date == null) return "";
        return format(null, date);
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
        
    public static String formatDate(Calendar calendar, String datePattern, String locale) {
    	if (calendar == null) return "";
		SimpleDateFormat format = new SimpleDateFormat(datePattern, new Locale(locale, locale.toUpperCase()));
		String formattedDate = null;
		if(calendar != null) {
			formattedDate = format.format(calendar.getTime());
		}
		return formattedDate;
    }

    public static String formatDate(Date date, String datePattern) {
    	if (date == null) return "";
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		String formattedDate = null;
		if(date != null) {
			formattedDate = format.format(date);
		}
		return formattedDate;
    }
    
    public static String format(TimeZone timeZone, Date date) {
    	if (date == null) return "";
    	return format(date, timeZone, Locale.getDefault(), DateFormat.SHORT, DateFormat.LONG);
    }

    public static String formatDate() {
    	return formatDate(DateTimeUtils.getCurrentDate());    	
    }
    
    /**
     * Default format for date
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
    	if (date == null) return "";
    	return DateTimeUtils.format(date, null, new Locale("en"), DateFormat.SHORT);    	
    }
    
    /**
     * Default format for date and time
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
    	if (date == null) return "";
    	return DateTimeUtils.format(date,null, new Locale("en"), DateFormat.SHORT, DateFormat.LONG);
    }
    
    
    /**
     * Date (just date) format method
     * @param timeZone
     * @param date
     * @param locale
     * @param dateStyle
     * @return
     */
    public static String format(Date date, TimeZone timeZone, Locale locale, int dateStyle) {
    	if (date == null) return "";
        DateFormat dateFormat = DateFormat.getDateInstance(dateStyle, locale);

        if (timeZone == null) {
            dateFormat.setTimeZone(TimeZone.getDefault());
        }
        else {
            dateFormat.setTimeZone(timeZone);
        }

        return dateFormat.format(date);    	
    }    
    
    /**
     * Full Date (date and time) format method
     * @param timeZone
     * @param date
     * @param locale
     * @param dateStyle
     * @param timeStyle
     * @return
     */
    public static String format(Date date, TimeZone timeZone, Locale locale, int dateStyle, int timeStyle) {
    	if (date == null) return "";
        DateFormat dateFormat = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);

        if (timeZone == null) {
            dateFormat.setTimeZone(TimeZone.getDefault());
        }
        else {
            dateFormat.setTimeZone(timeZone);
        }

        return dateFormat.format(date);    	
    }

    public static String getDateYYYYMMDD(Date date) {
    	if (date == null) return "";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getYear(date));

        int month = getMonth(date);
        
        if (month < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(month);

        int day = getDayOfMonth(date);
        
        if (day < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(day);

        return stringBuffer.toString();
    }
    
    public static String getDateDDMMYYYY(Date date) {
    	if (date == null) return "";
        StringBuffer stringBuffer = new StringBuffer();
        
        int day = getDayOfMonth(date);
        
        if (day < 10){
        	stringBuffer.append("0");
        }
        
        stringBuffer.append(day);
        
        int month = getMonth(date);
        
        if (month < 10){
        	stringBuffer.append("0");
        }
        
        stringBuffer.append(month);

        stringBuffer.append(getYear(date));

        return stringBuffer.toString();
    }
    
    public static String getDateDD_MM_YYYY(Date date) {
    	if (date == null) return "";
        StringBuffer stringBuffer = new StringBuffer();
        
        int day = getDayOfMonth(date);
        
        if (day < 10){
        	stringBuffer.append("0");
        }
        
        stringBuffer.append(day);
        stringBuffer.append("/");
        
        int month = getMonth(date);
        
        if (month < 10){
        	stringBuffer.append("0");
        }
        stringBuffer.append(month);
        stringBuffer.append("/");

        stringBuffer.append(getYear(date));

        return stringBuffer.toString();
    }
    
    public static String getDateMMDDYYYY(Date date) {
    	if (date == null) return "";
        StringBuffer stringBuffer = new StringBuffer();

        int month = getMonth(date);
        
        if (month < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(month);
        stringBuffer.append(" ");

        int day = getDayOfMonth(date);
        
        if (day < 10) {
            stringBuffer.append("0");
        }
        
        stringBuffer.append(day);
        stringBuffer.append("  ");

        stringBuffer.append(getYear(date));
        
        return stringBuffer.toString();
    }
    

    public static String getDateMMDDYYYY_HHMMSS(Date date) {
    	if (date == null) return "";
        StringBuffer stringBuffer = new StringBuffer();

        int month = getMonth(date);
        
        if (month < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(month);

        int day = getDayOfMonth(date);
        
        if (day < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(day);

        stringBuffer.append(getYear(date));
        
        stringBuffer.append("_");
        
        int hour = getHours(date);
        if (hour < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(hour);

        int minute = getMinutes(date);
        if (minute < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(minute);

        int second = getSeconds(date);
        if (second < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(second);

        
        return stringBuffer.toString();
    }
    
    public static String getDateYYYYMMDD_HHMMSS(Date date) {
    	if (date == null) return "";
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(getYear(date));
        
        int month = getMonth(date);
        
        if (month < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(month);

        int day = getDayOfMonth(date);
        
        if (day < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(day);
        
        stringBuffer.append("_");
        
        int hour = getHours(date);
        if (hour < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(hour);

        int minute = getMinutes(date);
        if (minute < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(minute);

        int second = getSeconds(date);
        if (second < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(second);

        
        return stringBuffer.toString();
    }
    
    public static String getDateYYYYMMDDHHMMSS(Date date) {
    	if (date == null) return "";
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(getYear(date));
        
        int month = getMonth(date);
        
        if (month < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(month);

        int day = getDayOfMonth(date);
        
        if (day < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(day);
        
//        stringBuffer.append("_");
        
        int hour = getHours(date);
        if (hour < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(hour);

        int minute = getMinutes(date);
        if (minute < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(minute);

        int second = getSeconds(date);
        if (second < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(second);

        
        return stringBuffer.toString();
    }
    
    public static String getDateYYYYMMDDSPACEHHMMSS(Date date) {
    	if (date == null) return "";
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(getYear(date));
        
        int month = getMonth(date);
        
        if (month < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(month);

        int day = getDayOfMonth(date);
        
        if (day < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(day);
        
        stringBuffer.append(" ");
        
        int hour = getHours(date);
        if (hour < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(hour);

        int minute = getMinutes(date);
        if (minute < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(minute);

        int second = getSeconds(date);
        if (second < 10) {
            stringBuffer.append("0");
        }

        stringBuffer.append(second);

        
        return stringBuffer.toString();
    }
    
    public static Date setDay(Date date, int day) {
    	if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        
        return setDay(calendar, date, day);
    }
    
    public static Date setDay(Calendar calendar, Date date, int day) {
    	if (date == null) return null;
    	calendar.setLenient(false);
    	calendar.setTime(date);
    	calendar.set(Calendar.DAY_OF_MONTH, day);
        
    	return calendar.getTime();
    }    
    
	public static Date setSafeDay(Date date, int day) {
		if (date == null) return null;
		Date newDate = null;
		int lastDayOfMonth = DateTimeUtils.getLastDayOfMonth(date);

		if (lastDayOfMonth <= day) {
			newDate = DateTimeUtils.setDay(date, lastDayOfMonth);
		} else {
			newDate = DateTimeUtils.setDay(date, day);
		}

		return newDate;
	}
    
    public static int getSafeDay(Calendar c, int day)
    {	
    	int safeDay = day;
    	int lastDayOfMonth	= c.getActualMaximum(Calendar.DAY_OF_MONTH);
    	
    	if (lastDayOfMonth < day) {
    		safeDay = lastDayOfMonth;
    	}
    	
    	return safeDay;
    }
    
    public static java.util.Date parseDateYYYYMMDD(String date) throws ParseException {
    	if (date == null) return null;
    	DateFormat defaultDateFormat = new SimpleDateFormat("yyyyMMdd");
        defaultDateFormat.setLenient(false);

        return defaultDateFormat.parse(date);
    }

    /**
     * Parses a date using a date format argument.
     * @param date
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    public static java.util.Date parseDate(String date, String dateFormat) throws ParseException {
    	DateFormat defaultDateFormat = new SimpleDateFormat(dateFormat);
        defaultDateFormat.setLenient(false);
        return defaultDateFormat.parse(date);
    }

    /**
     * 
     * @param date
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    public static Calendar parseCalendar(String date, String dateFormat) throws ParseException {
    	Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(parseDate(date, dateFormat));
        return calendar;
    }
    

    public static java.sql.Timestamp parseTimestampYYYYMMDDHHMMSS(String dateTime) throws ParseException {
        if(dateTime.length()==14) {
        	DateFormat defaultTimestampFormat = new SimpleDateFormat("yyyyMMddkkmmss");
            defaultTimestampFormat.setLenient(false);
       		return new java.sql.Timestamp(defaultTimestampFormat.parse(dateTime).getTime());
        }
        else  {
        	DateFormat defaultDateFormat = new SimpleDateFormat("yyyyMMdd");
            defaultDateFormat.setLenient(false);
       		return new java.sql.Timestamp(defaultDateFormat.parse(dateTime).getTime());
        }
    }
    
    
    public static java.util.Date parseDate(String strDate) throws ParseException {
        String pattern = findDateFormatPattern(strDate);
        Assert.notNull(pattern, "Unable to find date format pattern for: ["+strDate+"]");
        
        DateFormat dateFormat = (DateFormat) dateFormats.get(pattern);
        Assert.notNull(dateFormat, "Unable to find date format for: ["+strDate+"]");
        
        String dateWithDftSeps = strDate.replaceAll(SEPARATORS, DEF_SEPARATOR);

        return dateFormat.parse(dateWithDftSeps);
    }
    
    public static java.util.Date parseDateTimeYYYYMMDDHHMMSS(String strDateTime) throws ParseException {
    	if (!StringUtils.hasText(strDateTime)) {
    		return null;    		
    	}
    	
        String newStrDateTime = strDateTime.replaceAll(SPACES, " ");
        
        String pattern = findTimestampFormatPattern(newStrDateTime);
        if(pattern == null) {
        	pattern = findDateFormatPattern(newStrDateTime);
        }
        Assert.notNull(pattern, "Unable to find dateTimestamp format pattern for: ["+newStrDateTime+"]");
        
        DateFormat dateTimeFormat = new SimpleDateFormat(pattern);
        
        String dateWithDftSeps = newStrDateTime.replaceAll(SEPARATORS, DEF_SEPARATOR);
        
        return dateTimeFormat.parse(dateWithDftSeps);
    }
    
    public static java.sql.Time parseTime(String strTime) throws ParseException {
    	if (!StringUtils.hasText(strTime)) {
    		return null;    		
    	}
    	
        String pattern = findTimeFormatPattern(strTime);
        Assert.notNull(pattern, "Unable to find time format pattern for: ["+strTime+"]");
        
        DateFormat timeFormat = (DateFormat) timesFormats.get(pattern);
        Assert.notNull(timeFormat, "Unable to find time format for: ["+strTime+"]");
        
        Date tmpDate = timeFormat.parse(strTime);
        return new Time(tmpDate.getTime());
    }    
    
    public static java.sql.Timestamp parseTimestamp(String strTimestamp) throws ParseException {
    	if (!StringUtils.hasText(strTimestamp)) {
    		return null;    		
    	}
    	
    	
        String newStrTimestamp = strTimestamp.replaceAll(SPACES, " ");
        
        String pattern = findTimestampFormatPattern(newStrTimestamp);
        Assert.notNull(pattern, "Unable to find timestamp format pattern for: ["+newStrTimestamp+"]");
        
        DateFormat timestampFormat = new SimpleDateFormat(pattern);
        
        Date tmpDate = timestampFormat.parse(newStrTimestamp.replaceAll(SEPARATORS, DEF_SEPARATOR));
        return new Timestamp(tmpDate.getTime());
    }      
    
    
    public static int getNumberOfDaysUsingA360DaysYear(Date date1, Date date2)
    {    	
    	int nrOfDays = 0;
    	
    	if	(date1 != null && date2 != null) {
    		int nrOfMonths = getNumberOfMonths(date1, date2);
    		
    		if (nrOfMonths == 0) {
    			nrOfDays = getNumberOfDays(date1, date2) - 1;
    		}
    		else {
    			int day1 = getDayOfMonth(date1);
    			int day2 = getDayOfMonth(date2); 	
    			int lastDay1 = getLastDayOfMonth(date1); 
    			int lastDay2 = getLastDayOfMonth(date2); 

    			int correctedDay1 = day1;
    			int correctedDay2 = day2;		
    			
    			if (day1 == lastDay1) {
    				correctedDay1 = 30;
    				if (day2 >= day1) {
    					correctedDay2 = 30;
    				}
    			}
    		
    			if (day2 == lastDay2) {
    				correctedDay2 = 30;
    				if (day1 >= day2) {
    					correctedDay1 = 30;
    				}    			
    			}
    			
    			nrOfDays = 30 * nrOfMonths + correctedDay2 - correctedDay1;    		
    		}
    	}
    	
    	return nrOfDays;
    }
    
          
    private static Map getDatePatterns() {
        Map datePatterns = new HashMap(); 
        datePatterns.put("(0[1-9]|[12][0-9]|3[01])[-/.](0[1-9]|1[012])[-/.](19|20)\\d\\d", "dd/MM/yyyy");
        datePatterns.put("(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])(19|20)\\d\\d", "ddMMyyyy");
        datePatterns.put("(0[1-9]|[12][0-9]|3[01])[-/.](0[1-9]|1[012])[-/.]\\d\\d", "dd/MM/yy");
        datePatterns.put("(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])\\d\\d", "ddMMyy");
        datePatterns.put("(19|20)\\d\\d[-/.](0[1-9]|1[012])[-/.](0[1-9]|[12][0-9]|3[01])", "yyyy/MM/dd");
        datePatterns.put("(19|20)\\d\\d(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])", "yyyyMMdd");
        datePatterns.put("\\d\\d[-/.]?(0[1-9]|1[012])[-/.](0[1-9]|[12][0-9]|3[01])", "yy/MM/dd");
        datePatterns.put("\\d\\d(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])", "yyMMdd");
        
        return datePatterns;
    }
    
    private static Map getDateFormats() {
        Map dateFormats = new HashMap(); 
        
        DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat1.setLenient(false);
        dateFormats.put("dd/MM/yyyy", dateFormat1);
        
        DateFormat dateFormat2 = new SimpleDateFormat("ddMMyyyy");
        dateFormat2.setLenient(false);
        dateFormats.put("ddMMyyyy", dateFormat2);
        
        DateFormat dateFormat3 = new SimpleDateFormat("dd/MM/yy");
        dateFormat3.setLenient(false);
        dateFormats.put("dd/MM/yy", dateFormat3);
        
        DateFormat dateFormat4 = new SimpleDateFormat("ddMMyy");
        dateFormat4.setLenient(false);
        dateFormats.put("ddMMyy", dateFormat4);
        
        DateFormat dateFormat5 = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat5.setLenient(false);
        dateFormats.put("yyyy/MM/dd", dateFormat5);
        
        DateFormat dateFormat6 = new SimpleDateFormat("yyyyMMdd");
        dateFormat6.setLenient(false);
        dateFormats.put("yyyyMMdd", dateFormat6);
        
        DateFormat dateFormat7 = new SimpleDateFormat("yy/MM/dd");
        dateFormat7.setLenient(false);
        dateFormats.put("yy/MM/dd", dateFormat7);
        
        DateFormat dateFormat8 = new SimpleDateFormat("yyMMdd");
        dateFormat8.setLenient(false);
        dateFormats.put("yyMMdd", dateFormat8);
        
        return dateFormats;
    }    
    
    private static Map getTimePatterns() {
        Map timePatterns = new HashMap();
        
        String kk = "(0[0-9]|1[0-9]|2[0-3])";
        String mm = "[0-5][0-9]";
        String ss = "[0-5][0-9]";
        timePatterns.put("((24:00:00)|("+kk+":"+mm+":"+ss+"))", "kk:mm:ss");
        timePatterns.put("((240000)|("+kk+mm+ss+"))", "kkmmss");
        timePatterns.put("((24:00)|("+kk+":"+mm+"))", "kk:mm");
        timePatterns.put("((2400)|("+kk+mm+"))", "kkmm");

        return timePatterns;
    }
    
    private static Map getTimeFormats() {
        Map dateFormats = new HashMap(); 
        
        DateFormat dateFormat1 = new SimpleDateFormat("kk:mm:ss");
        dateFormat1.setLenient(false);
        dateFormats.put("kk:mm:ss", dateFormat1);
        
        DateFormat dateFormat2 = new SimpleDateFormat("kkmmss");
        dateFormat2.setLenient(false);
        dateFormats.put("kkmmss", dateFormat2);
        
        DateFormat dateFormat3 = new SimpleDateFormat("kk:mm");
        dateFormat3.setLenient(false);
        dateFormats.put("kk:mm", dateFormat3);
        
        DateFormat dateFormat4 = new SimpleDateFormat("kkmm");
        dateFormat4.setLenient(false);
        dateFormats.put("kkmm", dateFormat4);
        
        return dateFormats;
    }     
    
    public static String findDateFormatPattern(String expression) {
        for (Iterator i = datePatterns.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry)i.next();
            Pattern pattern = Pattern.compile((String)entry.getKey());
            Matcher matcher = pattern.matcher(expression);
            
            if (matcher.matches()) {
                return (String)entry.getValue();
            }
        }
        return null;
    }
    
    public static String findTimeFormatPattern(String expression) {
        for (Iterator i = timePatterns.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry)i.next();
            Pattern pattern = Pattern.compile((String)entry.getKey());
            Matcher matcher = pattern.matcher(expression);
            
            if (matcher.matches()) {
                return (String)entry.getValue();
            }
        }
        return null;
    }    
    
    public static String findTimestampFormatPattern(String expression) {
        for (Iterator d = datePatterns.entrySet().iterator(); d.hasNext();) {
            Map.Entry dateEntry = (Map.Entry)d.next();
            for (Iterator t = timePatterns.entrySet().iterator(); t.hasNext();) {
                Map.Entry timeEntry = (Map.Entry)t.next();
                String patt = (String)dateEntry.getKey() + SPACES + (String)timeEntry.getKey();
                Pattern pattern = Pattern.compile(patt);
                Matcher matcher = pattern.matcher(expression);
                
                if (matcher.matches()) {
                    return (String)dateEntry.getValue() + " " + (String)timeEntry.getValue();
                }
            }
        }
        return null;
    }    

	 public static Time calculateTimeDifference(Time firstTime, Time secondTime) {
		Time totalTimeDifference = null;
		if (firstTime != null && secondTime != null) {
			if (firstTime.after(secondTime)) {
				
				int firstTimeTotalSeconds = (DateTimeUtils.getHours(firstTime) * 60 * 60 )+ (DateTimeUtils.getMinutes(firstTime) * 60 )+ DateTimeUtils.getSeconds(firstTime);
				int secondTimeTotalSeconds = (DateTimeUtils.getHours(secondTime) * 60 * 60) + (DateTimeUtils.getMinutes(secondTime) *60 ) + DateTimeUtils.getSeconds(secondTime);

				totalTimeDifference = convertSecondsIntoHoursMinutesSeconds(firstTimeTotalSeconds - secondTimeTotalSeconds);
			} else {
				java.sql.Time twentyFourHourTime = java.sql.Time.valueOf("23:59:59" );
				
				int twentyFourHoursTotalSeconds = (DateTimeUtils.getHours(twentyFourHourTime) * 60 * 60 )+ (DateTimeUtils.getMinutes(twentyFourHourTime) * 60 ) + DateTimeUtils.getSeconds(twentyFourHourTime);
				int secondTotalSeconds = (DateTimeUtils.getHours(secondTime) * 60 * 60) + (DateTimeUtils.getMinutes(secondTime) * 60 ) + DateTimeUtils.getSeconds(secondTime);
				
				int differenceInSeconds = twentyFourHoursTotalSeconds - secondTotalSeconds;
				Time differenceUpto24  = convertSecondsIntoHoursMinutesSeconds(differenceInSeconds);
				
				int totalDifferenceUpto24Seconds = (differenceUpto24.getHours() * 60 * 60 ) + (DateTimeUtils.getMinutes(differenceUpto24) * 60 ) + DateTimeUtils.getSeconds(differenceUpto24);
				int totalDifferenceAfter24Seconds = (DateTimeUtils.getHours(firstTime) * 60 * 60  ) +  ( DateTimeUtils.getMinutes(firstTime) * 60 ) + DateTimeUtils.getSeconds(firstTime);

				totalTimeDifference = convertSecondsIntoHoursMinutesSeconds(totalDifferenceUpto24Seconds + totalDifferenceAfter24Seconds + 1);
			}
		}
		return totalTimeDifference;
	}

	 public static Time convertSecondsIntoHoursMinutesSeconds(int totalSeconds){
			Time convertedTime = null;
			
			int hours = totalSeconds / 3600,
			remainder = totalSeconds % 3600,
			minutes = remainder / 60,
			seconds = remainder % 60;

			String disHour = (hours < 10 ? "0" : "") + hours,
			disMinu = (minutes < 10 ? "0" : "") + minutes ,
			disSec = (seconds < 10 ? "0" : "") + seconds ;
			String total = disHour+":" +disMinu+ ":"+disSec;
			convertedTime = java.sql.Time.valueOf(total);

			return convertedTime;
		 }

	public static int convertTimeInToHoursAndRoundup(Time time, Boolean upperRoundOff) {
		int hours = 0;
		if (time != null) {
			hours = DateTimeUtils.getHours(time);
			if (upperRoundOff == null) {
				if (DateTimeUtils.getMinutes(time) >= 30) {
					hours = hours + 1;
				}
			} else if (upperRoundOff) {
				hours = hours + 1;
			}
		}
		return hours;
	}

	public static Time addTime(Time actualTime, Time addTime) {
		Time totalTime = null;
		int actualTimeinSeconds = 0;
		if (actualTime != null) {
			actualTimeinSeconds = (DateTimeUtils.getHours(actualTime) * 60 *60) + (DateTimeUtils.getMinutes(actualTime) * 60) + (DateTimeUtils.getSeconds(actualTime));
		}
		int addTimeinSeconds = 0;
		if (addTime != null) {
			addTimeinSeconds = (DateTimeUtils.getHours(addTime) * 60 *60) + (DateTimeUtils.getMinutes(addTime) * 60) + (DateTimeUtils.getSeconds(addTime));
		}
		int totalTimeInSeconds = actualTimeinSeconds + addTimeinSeconds ;
		
		totalTime = convertSecondsIntoHoursMinutesSeconds(totalTimeInSeconds);
		
		return totalTime;
	}
	

	public static Integer convertTimeDifferenceIntoHours (Time firstTime, Time secondTime) {
		Integer hoursWorked = null;
		Time timeDifference = calculateTimeDifference (firstTime, secondTime);
		hoursWorked = convertTimeInToHoursAndRoundup(timeDifference, false);
		return hoursWorked;
	}

	public static String getTimeHH_MM(Calendar date) {
        StringBuffer stringBuffer = new StringBuffer();
        
        stringBuffer.append(date.get(Calendar.HOUR_OF_DAY));
        stringBuffer.append(":");
        stringBuffer.append(date.get(Calendar.MINUTE));
        
        return stringBuffer.toString();
    }
	
	public static Integer convertTimeInToMinutes(Time time) {
		int minutes = 0;
		if (time != null) {
			minutes = DateTimeUtils.getHours(time) * 60 + DateTimeUtils.getMinutes(time);
		}
		return minutes;
	}
		
	public static BigDecimal getHoursBetween(Time firstTime, Time secondTime) {
		BigDecimal hoursBetween = null;
		Time timeDifference = calculateTimeDifference(firstTime, secondTime);
		if (timeDifference != null) {
			hoursBetween = new BigDecimal(DateTimeUtils.getHours(timeDifference));
			double remainder = (DateTimeUtils.getMinutes(timeDifference)*60 + DateTimeUtils.getSeconds(timeDifference)) / 3600.00;
			hoursBetween = hoursBetween.add(BigDecimal.valueOf(remainder));
		}
		return hoursBetween;
	}
	
	/**
	 * Returns the number of milliseconds until today midnight
	 * @return
	 */
	public static long getMillisecondsToMidnight() {
		Calendar now = DateTimeUtils.getCurrentDateTimeCalendar();
		long secondsToMidnight = (24*60*60) - (now.get(Calendar.HOUR_OF_DAY)*60*60 + now.get(Calendar.MINUTE)*60 + now.get(Calendar.SECOND));
		return secondsToMidnight * 1000;
	}
	
    public static Calendar getDateCalendar(Calendar calendar) {
		if(calendar != null){
	        calendar.set(Calendar.HOUR_OF_DAY, 0);
	        calendar.set(Calendar.MINUTE, 0);
	        calendar.set(Calendar.SECOND, 0);
	        calendar.set(Calendar.MILLISECOND, 0);
		}
    	return calendar;
    }
    
    public static String formatTime(Time time, String pattern) {
		String timeString = null;
		if ("hh:mm".equalsIgnoreCase(pattern)) {
			timeString = time.toString().substring(0, time.toString().lastIndexOf(":"));
		} else if ("hh:mm:ss".equalsIgnoreCase(pattern)) {
			timeString = time.toString();
		}
		return timeString;
	}
    
    public static String formatTime(String pattern,Date date){
    	String timeString = null;
    	if("HH:mm:ss a".equalsIgnoreCase(pattern)){
			SimpleDateFormat parser = new SimpleDateFormat(pattern);
			return parser.format(date);
		}
    	return timeString;
    }
    
    /**
     * This method will test if current time is between initialPeriod and finalPeriod 
     * @param initialPeriod String on format HH:mm
     * @param finalPeriod String on format HH:mm
     * @return
     */
    public static boolean isCurrentTimeInPeriodHHmm(String initialPeriod, String finalPeriod) {
    	final String TIME_FORMAT = "HH:mm";
    	SimpleDateFormat parser = new SimpleDateFormat(TIME_FORMAT);
    	Date initialTime = null;
		try {
			initialTime = parser.parse(initialPeriod);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("Invalid time format ["+ initialPeriod +"]. The expected format is ["+ TIME_FORMAT +"] ");
		}
    	
    	Date finalTime = null;

    	try {
    		finalTime = parser.parse(finalPeriod);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("Invalid time format ["+ finalPeriod +"]. The expected format is ["+ TIME_FORMAT +"] ");
		}
   	    
    	Calendar currentCalendar = DateTimeUtils.getCurrentDateTimeCalendar(); 
    	
    	Date currentTime = null;
    	
    	String currentTimeString = currentCalendar.get(Calendar.HOUR_OF_DAY) + ":" + currentCalendar.get(Calendar.MINUTE);
    	try {
    		currentTime = parser.parse(currentTimeString);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("Invalid time format ["+ currentTimeString +"]. The expected format is ["+ TIME_FORMAT +"] ");
		}
 
	    if (currentTime.after(initialTime) && currentTime.before(finalTime)) {
	        return true;
	    }
    	return false;
    }


	public static List<Calendar> findCalendarEntriesBetween(Calendar start, Calendar end) {
		List<Calendar> calendarsInBetween = new ArrayList<Calendar>();
		if(start != null && end != null && start.compareTo(end) == -1){
			Calendar tempCalendar = start;
			while(tempCalendar.compareTo(end) == -1){
				calendarsInBetween.add(tempCalendar);
				tempCalendar = addDay(tempCalendar, 1);
			}
		}
		calendarsInBetween.add(end);
		return calendarsInBetween;
	}
	
	public static Calendar setTimeComponentForDate(Calendar date, int hour, int min, int sec) {
		if(date != null){
			date.set(Calendar.HOUR_OF_DAY, hour);
			date.set(Calendar.MINUTE, min);
			date.set(Calendar.SECOND, sec);
		}
		return date;
	}
	
	public static Calendar setTimeComponentForDate(Calendar date, int hour, int min, int sec, int milliSec) {
		if(date != null){
			date.set(Calendar.HOUR_OF_DAY, hour);
			date.set(Calendar.MINUTE, min);
			date.set(Calendar.SECOND, sec);
			date.set(Calendar.MILLISECOND, milliSec);
		}
		return date;
	}
	
	public static Calendar getMaxBetweenCalendars(Calendar ...dates) {
		Calendar maxCalendar = null;
		for (Calendar date : dates) {
			if (date != null) {
				if (maxCalendar == null) {
					maxCalendar = date;
				} else {
					maxCalendar = maxCalendar.compareTo(date) > 0 ? maxCalendar : date; 
				}
			}
		}
		
		return getCalendar(maxCalendar);
	}

	public static Calendar getMinBetweenCalendars(Calendar ...dates) {
		Calendar minCalendar = null;
		for (Calendar date : dates) {
			if (date != null) {
				if (minCalendar == null) {
					minCalendar = date;
				} else {
					minCalendar = minCalendar.compareTo(date) < 0 ? minCalendar : date; 
				}
			}
		}
		
		return getCalendar(minCalendar);
	}

	/**
	 * This method returns a 
	 * @return
	 */
	public static DateFormat getISO8601DateFormat() {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
	}
		
		
	public static boolean equalsDates(Calendar calendar1, Calendar calendar2) {
		return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
				&& calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
	}

	public static Date getZeroTimeDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		return date;
	}
	/* Converter nº de working days entre duas datas.
$$jourstotal = $$dtfin - $$dtdebut + 1  ; 
$$int        = $$jourstotal / 7
$$nbsem      = $$int[trunc]
$$modulo     = $$jourstotal - ($$nbsem * 7)
$$nbjours    = $$nbsem * 5              ; 
$$dttemp     = $$dtfin - $$modulo + 1	 
	 */
}
