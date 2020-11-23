package mx.com.actinver.ms.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AttachmentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attach;
	private String contentType;
	private String contentId;

}
