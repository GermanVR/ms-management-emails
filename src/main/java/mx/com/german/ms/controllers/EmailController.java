package mx.com.german.ms.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.TemplateException;
import mx.com.german.ms.beans.EmailBean;
import mx.com.german.ms.services.IEmailService;

/**
 * @Autor Luis German Vazquez Renteria
 * @Proyecto: https://github.com/GermanVR/
 * @Correo: german_1241@hotmail.com
 */
@RestController
@RequestMapping("/mail")
public class EmailController {

	@Autowired
	public IEmailService emailService;

	@PostMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createMail(@RequestBody @Valid EmailBean mailObject) {
		emailService.sendSimpleMessage(mailObject.getTo(), mailObject.getSubject(), mailObject.getText());
		return ResponseEntity.ok("Todo Bien");
	}

	@PostMapping(value = "/sendHtml", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createHtmlMail(@RequestBody @Valid EmailBean mailObject) throws IOException, MessagingException, TemplateException {

		Map<String, Object> templateModel = new HashMap<>();

		templateModel.put("recipientName", mailObject.getRecipientName());
		templateModel.put("text", mailObject.getText());
		templateModel.put("senderName", mailObject.getSenderName());

		emailService.sendMessageUsingFreemarkerTemplate(mailObject.getTo(), mailObject.getSubject(), templateModel);

		return ResponseEntity.ok("Todo Bien");
	}

	@PostMapping(value = "/sendAttachment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createMailWithAttachment(@RequestBody @Valid EmailBean emailBean, @RequestHeader(name = "content-attach") MediaType mediaType) {
		emailService.sendMessageWithAttachment(emailBean, mediaType);
		return ResponseEntity.ok("Todo Bien");
	}
}
