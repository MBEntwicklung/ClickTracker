/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import org.junit.Before;
import org.junit.Ignore;
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
	@Ignore
	public void testSendMail() {
		new MailSender().withTarget("spam-test@spambog.com").withPostionLink("Test Mail").send();
	}

}
