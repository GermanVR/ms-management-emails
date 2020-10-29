package mx.com.german.ms.services;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.http.MediaType;

import freemarker.template.TemplateException;
import mx.com.german.ms.beans.EmailBean;

/**
 * @Autor Luis German Vazquez Renteria
 * @Proyecto: https://github.com/GermanVR/
 * @Correo: german_1241@hotmail.com
 */
public interface IEmailService {

	void sendSimpleMessage(String to, String subject, String text);

	void sendMessageUsingFreemarkerTemplate(String to, String subject, Map<String, Object> templateModel) throws IOException, TemplateException, MessagingException;

	void sendMessageWithAttachment(EmailBean emailBean, MediaType mediaType);
}
