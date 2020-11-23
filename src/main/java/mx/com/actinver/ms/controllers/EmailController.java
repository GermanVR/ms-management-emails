package mx.com.actinver.ms.controllers;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.TemplateException;
import mx.com.actinver.ms.beans.EmailAttachBean;
import mx.com.actinver.ms.beans.EmailBean;
import mx.com.actinver.ms.services.IEmailService;
import mx.com.actinver.ms.utilities.IResponseUtil;

@RestController
@RequestMapping("${application.baseUri}")
public class EmailController {

	private static final String SENT = "Listo";

	private static final String SUCCESS_MESSAGE = "Mensaje Enviado Correctamente";

	@Autowired
	public IEmailService emailService;

	@Autowired
	private IResponseUtil responseUtil;

	@PostMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createMail(@RequestBody EmailBean mailObject) {
		emailService.sendSimpleMessage(mailObject);
		return responseUtil.successResponseEntity(SENT, SUCCESS_MESSAGE);
	}

	@PostMapping(value = "/sendHtml", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createHtmlMail(@RequestBody EmailBean mailObject) throws IOException, MessagingException, TemplateException {
		emailService.sendMessageWithTemplate(mailObject);
		return responseUtil.successResponseEntity(SENT, SUCCESS_MESSAGE);
	}

	@PostMapping(value = "/sendAttachment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createMailWithAttachment(@RequestBody EmailAttachBean emailBean) {
		emailService.sendMessageWithAttachment(emailBean);
		return responseUtil.successResponseEntity(SENT, SUCCESS_MESSAGE);
	}
}