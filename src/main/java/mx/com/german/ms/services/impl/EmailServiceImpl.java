package mx.com.german.ms.services.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import mx.com.german.ms.beans.EmailBean;
import mx.com.german.ms.services.IEmailService;

/**
 * @Autor Luis German Vazquez Renteria
 * @Proyecto: https://github.com/GermanVR/
 * @Correo: german_1241@hotmail.com
 */
@Service
public class EmailServiceImpl implements IEmailService {

	private static final String NOREPLY_ADDRESS = "lvazqure@everis.com";

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean;

	@Value("classpath:/mail-fenix-logo.jpg")
	private Resource resourceFile;

	public void sendSimpleMessage(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(NOREPLY_ADDRESS);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);

			emailSender.send(message);
		} catch (MailException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void sendMessageWithAttachment(EmailBean emailBean, MediaType mediaType) {
		String attach = emailBean.getAttach();
		assertNotNull("Attachment cannot be null", attach);
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(NOREPLY_ADDRESS);
			helper.setTo(emailBean.getTo());
			helper.setSubject(emailBean.getSubject());
			helper.setText(emailBean.getText());
			helper.setReplyTo(NOREPLY_ADDRESS);

			helper.addAttachment("Invoice." + mediaType.getSubtype(), new ByteArrayDataSource(Base64.getDecoder().decode(attach), mediaType.toString()));

			emailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	//	@Override
	//	public void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel) throws MessagingException {
	//
	//		Context thymeleafContext = new Context();
	//		thymeleafContext.setVariables(templateModel);
	//
	//		String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);
	//
	//		sendHtmlMessage(to, subject, htmlBody);
	//	}

	@Override
	public void sendMessageUsingFreemarkerTemplate(String to, String subject, Map<String, Object> templateModel) throws IOException, TemplateException, MessagingException {

		Template freemarkerTemplate = freeMarkerConfigurationFactoryBean.getObject().getTemplate("template-freemarker.ftl");
		String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
		sendHtmlMessage(to, subject, htmlBody);
	}

	private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(NOREPLY_ADDRESS);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);
		helper.addInline("attachment.png", resourceFile);
		emailSender.send(message);
	}

}
