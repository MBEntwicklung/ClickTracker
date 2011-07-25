/**
 * 
 */
package de.mbentwicklung.android.clickTracker.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import de.mbentwicklung.android.clickTracker.positioning.Position;

/**
 * @author marc
 * 
 */
public class Mail {

	private String toAddr;
	private Position withPosition;

	public Mail() {
	}

	public Mail to(final String addr) {
		this.toAddr = addr;
		return this;
	}

	public Mail with(final Position position) {
		this.withPosition = position;
		return this;
	}

	public void send() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.mb-entwicklung.de");
		props.put("mail.debug", "true");
		props.put("mail.from", "mail@clicktracker.mb-entwicklung.de");
		Session session = Session.getInstance(props);
		MimeMessage message = null;
		try {
			message = new MimeMessage(session, null);
			message.setFrom();
			message.setRecipients(Message.RecipientType.TO,
					"winhasser@gmail.com");
			message.setSubject("JavaMail hello world example");
			message.setSentDate(new Date());
			message.setText("Hello, world!\n");
			Transport.send(message);
		} catch (final MessagingException e) {
			System.out.println("send failed, exception: " + message);
		}
	}
}
