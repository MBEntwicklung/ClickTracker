/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author marc
 *
 */
public class MailTest {

	private Mail mail;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mail = new Mail();
	}

	@Test
	public void test() {
		mail.send();
	}

}
