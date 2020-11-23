package mx.com.actinver.ms.helper;

import java.util.Map;

import mx.com.actinver.ms.beans.EmailBean;

public interface IEmailHelper {

	public Map<String, Object> toMap(EmailBean emailBean);

}
