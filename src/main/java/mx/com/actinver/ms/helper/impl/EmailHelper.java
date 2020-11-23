package mx.com.actinver.ms.helper.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import mx.com.actinver.ms.beans.EmailBean;
import mx.com.actinver.ms.helper.IEmailHelper;

@Component
public class EmailHelper implements IEmailHelper {

	@Override
	public Map<String, Object> toMap(EmailBean emailBean) {
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("recipientName", emailBean.getRecipientName());
		templateModel.put("body", emailBean.getBody());
		return templateModel;
	}

}
