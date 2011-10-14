/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marc
 * 
 */
public class MailSender extends Authenticator {

	private static final String STD_SUBJECT = "Position from ClickTracker App";
	private static final String SMTP = "smtp";
	private static final String SMTP_PASS = "nAGzRGWrYeqs6ns6";
	private static final String SMTP_USER = "m0209ea8";
	private static final int SMTP_PORT = 25;
	private static final String SMTP_SERVER = "smtp.mb-entwicklung.de";
	private static final String FROM_MAIL_ADDR = "clicktracker@mb-entwicklung.de";
	
	private Logger logger = LoggerFactory.getLogger(MailSender.class);
	
	private final Session session;
	
	private String toAddr;
	private String withPositionLink;

    static {   
        Security.addProvider(new MailProvider());   
    }  

	public MailSender() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(props, this);   
	}

	public MailSender to(final String addr) {
		this.toAddr = addr;
		return this;
	}

	public MailSender with(final String positionLink) {
		this.withPositionLink = positionLink;
		return this;
	}

	public void send() {
		logger.info("Send mail to " + toAddr + " with " + withPositionLink);

		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(FROM_MAIL_ADDR));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toAddr));
			message.setSubject(STD_SUBJECT);
			message.setSentDate(new Date());
			message.setText("Hello " + toAddr + ",\n"
					+ "ClickTracker App send the position " + withPositionLink);

			Transport transport = session.getTransport(SMTP);
			transport.connect(SMTP_SERVER, SMTP_PORT, SMTP_USER, SMTP_PASS);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
