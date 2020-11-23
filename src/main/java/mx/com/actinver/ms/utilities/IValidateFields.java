package mx.com.actinver.ms.utilities;

import mx.com.actinver.ms.beans.EmailAttachBean;
import mx.com.actinver.ms.beans.EmailBean;

public interface IValidateFields {

	public void validateBean(EmailBean emailBean);

	public void validateattachBean(EmailAttachBean emailAttachBean);

}