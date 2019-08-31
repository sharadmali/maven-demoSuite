package libraryFramework;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendReportInEmail {
	
	/*----------------------------------------------------------------------------
    Function Name    	: sendGmailReport
    Description     	: Send email using Gmail    
    ----------------------------------------------------------------------------*/ 
	public static void sendGmailReport() throws IOException {
		final String username = "*********@gmail.com";			// Sender's email ID needs to be mentioned
		final String password = "*********";				// Sender's password needs to be mentioned
		final String usernameTo = "*********@gmail.com";		// Recipient's email ID needs to be mentioned.
		// setting gmail smtp properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		// check the authentication
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {			
			String newline =System.getProperty("line.separator"); 
			String strMessage = "Hello Team,"+newline+newline+"Test Automation execution report has been attached here. Please find the same."+newline+newline+"Thank You."; 		
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			// recipients email address
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usernameTo));
			// add the Subject of email
			message.setSubject("Test Automation : Execution Report");
			Multipart multipart = new MimeMultipart();
			// add the body message
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(strMessage);			
			multipart.addBodyPart(bodyPart);
			// attach the file
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.attachFile(new File(Global.filePath));
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Email Sent Successfully");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/*----------------------------------------------------------------------------
    Function Name    	: sendOutlookReport
    Description     	: Send email using Outlook    
    ----------------------------------------------------------------------------*/ 
	public static void sendOutlookReport() throws IOException {
		final String username = "*********";		// Sender's email ID needs to be mentioned
		final String password = "*********";		// Sender's password needs to be mentioned
		final String usernameTo = "*********";	// Recipient's email ID needs to be mentioned.
		// setting gmail smtp properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "587");
		// check the authentication
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {			
			String newline =System.getProperty("line.separator"); 
			String strMessage = "Hello Team,"+newline+newline+"Test Automation execution report has been attached here. Please find the same."+newline+newline+"Thank You."; 		
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			// recipients email address
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usernameTo));
			// add the Subject of email
			message.setSubject("Test Automation : Execution Report");
			Multipart multipart = new MimeMultipart();
			// add the body message
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(strMessage);			
			multipart.addBodyPart(bodyPart);
			// attach the file
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.attachFile(new File(Global.filePath));
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Email Sent Successfully");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
