/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import org.apache.commons.validator.EmailValidator;

/**
 * @author marc
 *
 */
public class MailValidator {

	public static boolean isMailAddressValid(final String address) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(address);
	}
	
}
