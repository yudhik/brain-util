package com.brainmaster.util.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLDateFormatter {
	private static final Logger log = LoggerFactory.getLogger(XMLDateFormatter.class);
	private static DatatypeFactory datatypeFactory;

	static {
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			LoggerFactory.getLogger(XMLDateFormatter.class).error("Error initializing DatatypeFactory", e);
		}
	}

	protected static String format(Date date, String format) {
		return format(date, format, null, null);
	}

	protected static String format(Date date, String format, String locale) {
		return format(date, format, locale, null);
	}

	protected static String format(Date date, String format, String locale, String timezone) {
		DateFormat df = (locale != null) ? new SimpleDateFormat(format, new Locale(locale)) : new SimpleDateFormat(
				format);
		if (timezone != null) {
			df.setTimeZone(TimeZone.getTimeZone(timezone));
		}
		return df.format(date);
	}

	public static String format(String date, String format) {
		return format(date, format, null, null);
	}

	public static String format(String date, String format, String locale) {
		return format(date, format, locale, null);
	}

	public static String format(String date, String format, String locale, String timezone) {
		assert (date != null);
		assert (format != null);
		assert (datatypeFactory != null);
		Calendar cal = datatypeFactory.newXMLGregorianCalendar(date).toGregorianCalendar();
		if (cal == null) return date;
		return format(cal.getTime(), format, locale, timezone);
	}

	public static String getTimestamp(String format) {
		return getTimestamp(format, null, null);
	}

	public static String getTimestamp(String format, String locale) {
		return getTimestamp(format, locale, null);
	}

	public static String getTimestamp(String format, String locale, String timezone) {
		assert (format != null);
		return format(new Date(), format, locale, timezone);
	}

	public static String getXMLFormat(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		if (date != null) cal.setTime(date);
		return toXMLFormat(cal);
	}

	public static String toXMLFormat(GregorianCalendar cal) {
		String result = datatypeFactory.newXMLGregorianCalendar(cal).toXMLFormat();
		if (result.length() > 29) {
			log.error("Invalid date transformation to XML format: " + cal + " -> " + result);
		}
		return result;
	}

	public static String getXMLFormat(String date, String format) throws ParseException {
		return getXMLFormat(date, format, null);
	}

	public static String getXMLFormat(String date, String format, String timezone) throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		if (timezone != null) df.setTimeZone(TimeZone.getTimeZone(timezone));
		return getXMLFormat(df.parse(date));
	}

	public static String getXMLTimestamp() {
		return getXMLFormat(null);
	}

	public static Date parse(String source, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(source);
	}

	public static Date parseXMLDate(String source) {
		try {
			return (source == null) ? null : datatypeFactory.newXMLGregorianCalendar(source).toGregorianCalendar()
					.getTime();
		} catch (Exception e) {
			if (log.isDebugEnabled()) log.warn("Could not convert XML date: '" + source + "'", e);
			else
				log.warn("Could not convert XML date: '" + source + "'");
			return null;
		}
	}

}