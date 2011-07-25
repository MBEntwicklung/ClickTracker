/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Marc Bellmann
 *
 */
public class MailServiceTest {

	/** Test  Objekt */
	private MailService mailService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mailService = new MailService();
	}

	@Test
	public void test() {
		mailService.onBind(null);
	}

}
