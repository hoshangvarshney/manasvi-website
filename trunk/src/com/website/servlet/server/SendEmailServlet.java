package com.website.servlet.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendEmailServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String senderName = req.getParameter("sndName");
		String senderEmail = req.getParameter("sndEmail");
		String message = req.getParameter("msg");
		boolean flag = false;
		if(senderEmail != null && senderName != null && message != null)
		{
			flag = true;
		}
		else
		{
			flag = false;
		}
		message="Sender Name: "+ senderName+"\nSender Email: "+senderEmail+ "\n\n"+message;

		String response = "ERR|Invalid Parameters";

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try
		{
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("hoshang.varshney@gmail.com", "Manasvi Website"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("arnavi.varshney@gmail.com", "Arnavi Varshney"));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress("hoshang.varshney@gmail.com", "Hoshang Varshney"));
			msg.setSubject("Message from " + senderName + " on "+(new Date(System.currentTimeMillis()).toString()));
			msg.setText(message);
			if(flag)
			{
				Transport.send(msg);
				response = "OK|Success";
			}
		}
		catch (AddressException e)
		{
			response = "ERR|ADR001";
		}
		catch (MessagingException e)
		{
			response = "ERR|MSG001";
			e.printStackTrace();
		}
		finally
		{
			PrintWriter out = resp.getWriter();
			out.print(response);
			out.flush();
		}
	}
}
