package com.deloitte.mulesoft.helperclasses;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * <p>
 * This is a helper class, with static methods for working with various aspects
 * of exceptions:
 * </p>
 * 
 * <ul>
 * <li>Getting an error code for a specific exception class, and</li>
 * <li>"Escaping" a string, such that it is ready to be used as a JSON string
 * property</li>
 * </ul>
 * 
 * <p>
 * This class is heavily based on the {@link org.mule.config.ExceptionHelper}
 * class from MuleSoft, and uses that class under the covers for getting codes
 * for Mule-defined errors.
 * </p>
 * 
 * @author David Hunter (Deloitte)
 * @apiviz.landmark
 * @apiviz.uses org.apache.commons.lang3.StringEscapeUtils
 * @apiviz.uses org.mule.config.ExceptionHelper
 */
public final class CustomExceptionHelper {
	/**
	 * Used by the <code>initialize()</code> method, to ensure that the class is
	 * only initialized once.
	 */
	private static boolean initialized = false;

	/**
	 * The property file with error codes is loaded into this member.
	 */
	private static Properties errorCodes = new Properties();

	/**
	 * Name of the property file to use for loading the codes
	 */
	private static final String PROPERTY_FILE_NAME = "error-properties.properties";

	// Causes the class to initialize itself -- especially the static members
	static {
		initialize();
	}

	/**
	 * Initializes the static members, which consists of loading the property
	 * file into the <code>errorCodes</code> member.
	 */
	private static void initialize() {
		if (initialized) {
			return;
		}

		try {
			InputStream is = CustomExceptionHelper.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
			if (is == null) {
				throw new IllegalArgumentException("Failed to load resource: " + PROPERTY_FILE_NAME);
			}

			errorCodes.load(is);
			is.close();

			initialized = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Static method that takes in an exception class, finds the error code for
	 * that class, and returns it.
	 * </p>
	 * 
	 * <p>
	 * If no error code is found, the default MuleSoft
	 * <code>ExceptionHelper</code> is used. The MuleSoft class will return a
	 * default code of -1, which this class passes on if no error is found.
	 * </p>
	 * 
	 * @param exception
	 *            The exception for which we want the error code
	 * @return Error code for the exception.
	 */
	public static int getErrorCode(@SuppressWarnings("rawtypes") Class exception) {
		String codeString = "";
		int codeInt = -1;

		codeString = errorCodes.getProperty(exception.getName(), "-1");
		codeInt = Integer.parseInt(codeString);

		if (codeInt != -1) {
			return codeInt;
		}

		// if the method hasn't returned by this point, return the default
		// MuleSoft implementation's error code (if any)
		codeInt = org.mule.config.ExceptionHelper.getErrorCode(exception);
		return codeInt;
	}

	/**
	 * <p>
	 * Helper method for 'escaping' a Java string so that it can be used as a
	 * JSON string property.
	 * </p>
	 * 
	 * <p>
	 * Uses Apache's <code>StringEscapeUtils</code> to do the heavy lifting; the
	 * <code>StringEscapeUtils.escapeJson()</code> method doesn't exist in the
	 * version included in Mule, so <code>escapeEcmaScript()</code> was used
	 * instead.
	 * </p>
	 * 
	 * @param input
	 *            The string to be escaped
	 * @return a string that is ready for use as a JSON property.
	 */
	public static String escapeStringForJSON(String input) {
		String escapedValue = StringEscapeUtils.escapeJavaScript(input);
		// single quotes aren't supposed to be escaped in JSON
		escapedValue = escapedValue.replaceAll("\\\\'", "'");
		return escapedValue;
	}
}
