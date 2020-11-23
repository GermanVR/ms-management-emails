package mx.com.actinver.ms.services;

import java.io.IOException;

import javax.mail.MessagingException;

import freemarker.template.TemplateException;
import mx.com.actinver.ms.beans.EmailAttachBean;
import mx.com.actinver.ms.beans.EmailBean;

public interface IEmailService {

	void sendSimpleMessage(final EmailBean emailBean);

	void sendMessageWithTemplate(final EmailBean emailBean) throws IOException, TemplateException, MessagingException;

	void sendMessageWithAttachment(final EmailAttachBean emailAttachBean);
}
