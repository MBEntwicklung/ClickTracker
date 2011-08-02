/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import org.junit.Before;
import org.junit.Test;

/**
 * @author marc
 *
 */
public class MailTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSendMail() {
		new Mail().to("spam-test@spambog.com").with("Test Mail").send();
	}

}
