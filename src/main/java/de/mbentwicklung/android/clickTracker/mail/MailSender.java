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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Marc Bellmann <marc.bellmann@mb-entwicklung.de>
 */
public class MailSender extends Authenticator {

	private static final String SMTP = "smtp";
	private static final String SMTP_PASS = "nAGzRGWrYeqs6ns6";
	private static final String SMTP_USER = "m0209ea8";
	private static final int SMTP_PORT = 25;
	private static final String SMTP_SERVER = "smtp.mb-entwicklung.de";
	private static final String FROM_MAIL_ADDR = "clicktracker@mb-entwicklung.de";

	private final Session session;

	private InternetAddress[] targetAddr;
	private String positionLink;
	private String subject;
	private String text;
	private Date date;

	static {
		Security.addProvider(new MailProvider());
	}

	public MailSender() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");

		session = Session.getDefaultInstance(props, this);
	}

	public MailSender withTarget(final String addr) throws AddressException {
		this.targetAddr = InternetAddress.parse(addr);
		return this;
	}

	public MailSender withPostionLink(final String positionLink) {
		this.positionLink = positionLink;
		return this;
	}

	public MailSender withSubject(final String subject) {
		this.subject = subject;
		return this;
	}

	public MailSender withText(final String text) {
		this.text = text;
		return this;
	}

	public MailSender withCurrentDate() {
		this.date = new Date();
		return this;
	}

	public void send() throws MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(FROM_MAIL_ADDR));
		message.setRecipients(Message.RecipientType.TO, targetAddr);
		message.setSubject(subject);
		message.setSentDate(date);
		message.setText(text + " " + positionLink);

		Transport transport = session.getTransport(SMTP);
		transport.connect(SMTP_SERVER, SMTP_PORT, SMTP_USER, SMTP_PASS);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
}
