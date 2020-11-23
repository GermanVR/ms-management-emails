package mx.com.actinver.ms.beans;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailAttachBean extends EmailBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<AttachmentBean> attachs;

}
