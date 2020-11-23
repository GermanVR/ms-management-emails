package mx.com.actinver.ms.services.impl;

import java.io.IOException;
import java.util.Base64;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import mx.com.actinver.ms.beans.EmailAttachBean;
import mx.com.actinver.ms.beans.EmailBean;
import mx.com.actinver.ms.exceptions.SendEmailException;
import mx.com.actinver.ms.helper.IEmailHelper;
import mx.com.actinver.ms.services.IEmailService;
import mx.com.actinver.ms.utilities.IValidateFields;

@Service
@Slf4j
public class EmailServiceImpl implements IEmailService {

	private static final String EMAIL_FROM = "luis.german.vazquez.renteria@everis.com";

	private static final String EMAIL_ERROR_SENDING = "Error sending Mail: ";

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean;

	@Value("classpath:/mail-fenix-logo.jpg")
	private Resource resourceFile;

	@Autowired
	private IValidateFields validateFields;

	@Autowired
	private IEmailHelper emailHelper;

	public void sendSimpleMessage(final EmailBean emailBean) {
		validateFields.validateBean(emailBean);
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(EMAIL_FROM);
			message.setTo(emailBean.getTo());
			message.setSubject(emailBean.getSubject());
			message.setText(emailBean.getBody());
			emailSender.send(message);
		} catch (MailException exception) {
			throw new SendEmailException(EMAIL_ERROR_SENDING + exception.getMessage());
		}
	}

	@Override
	public void sendMessageWithAttachment(EmailAttachBean emailAttachBean) {
		validateFields.validateattachBean(emailAttachBean);
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(EMAIL_FROM);
			helper.setTo(emailAttachBean.getTo());
			helper.setSubject(emailAttachBean.getSubject());
			helper.setText(emailAttachBean.getBody());
			generateArrayDS(emailAttachBean, helper);
			emailSender.send(message);
		} catch (MessagingException e) {
			throw new SendEmailException(EMAIL_ERROR_SENDING + e.getMessage());
		}
	}

	private void generateArrayDS(EmailAttachBean emailAttachBean, MimeMessageHelper helper) {

		emailAttachBean.getAttachs().forEach(a -> {
			ByteArrayDataSource dataSource = new ByteArrayDataSource(Base64.getDecoder().decode(a.getAttach()), a.getContentType());
			try {
				helper.addAttachment(a.getContentId(), dataSource);
			} catch (MessagingException e) {
				log.error("Error to attach: {} ", e.getMessage());
			}
		});

	}

	@Override
	public void sendMessageWithTemplate(EmailBean emailBean) {
		validateFields.validateBean(emailBean);

		Template freemarkerTemplate = null;
		String htmlBody = null;
		try {
			freemarkerTemplate = freeMarkerConfigurationFactoryBean.getObject().getTemplate("basic-template.ftl");
			htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, emailHelper.toMap(emailBean));
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(EMAIL_FROM);
			helper.setTo(emailBean.getTo());
			helper.setSubject(emailBean.getSubject());
			helper.setText(htmlBody, true);
			helper.addInline("attachment.png", resourceFile);
			emailSender.send(message);
		} catch (IOException | TemplateException | MessagingException e) {
			throw new SendEmailException(EMAIL_ERROR_SENDING + e.getMessage());
		}
	}
}
