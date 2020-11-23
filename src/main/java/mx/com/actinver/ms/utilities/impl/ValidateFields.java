package mx.com.actinver.ms.utilities.impl;

import org.springframework.stereotype.Component;

import mx.com.actinver.ms.beans.AttachmentBean;
import mx.com.actinver.ms.beans.EmailAttachBean;
import mx.com.actinver.ms.beans.EmailBean;
import mx.com.actinver.ms.exceptions.AttachException;
import mx.com.actinver.ms.exceptions.ValidatorException;
import mx.com.actinver.ms.utilities.IValidateFields;

@Component
public class ValidateFields implements IValidateFields {

	private void validateToAddress(String[] to) {
		if (to == null || to.length < 1) {
			throw new ValidatorException("Addresses cannot be less than 1");
		}
	}

	private void validateTextBody(String text) {
		if (text == null || text.isEmpty()) {
			throw new ValidatorException("The body of the email cannot be empty text");
		}
	}

	private void validateAttach(AttachmentBean attachmentBean) {
		if (attachmentBean.getAttach().isEmpty()) {
			throw new AttachException("Attachment cannot be Empty");
		} else if (attachmentBean.getContentId().isEmpty()) {
			throw new AttachException("Attachment Name cannot be Empty");
		} else if (attachmentBean.getContentType().isEmpty()) {
			throw new AttachException("Attachment Content Type cannot be Empty");
		}
	}

	private void validateSubject(String text) {
		if (text.isEmpty()) {
			throw new ValidatorException("Subject cannot be Empty");
		}
	}

	@Override
	public void validateBean(EmailBean emailBean) {
		validateToAddress(emailBean.getTo());
		validateTextBody(emailBean.getBody());
		validateSubject(emailBean.getSubject());
	}

	@Override
	public void validateattachBean(EmailAttachBean emailAttachBean) {
		validateBean(emailAttachBean);
		if (emailAttachBean.getAttachs().isEmpty())
			throw new AttachException("Attachs cannot be Empty");

		emailAttachBean.getAttachs().forEach(this::validateAttach);
	}

}
