package mx.com.actinver.ms.beans;

import java.io.Serializable;

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
public class EmailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] to;
	private String recipientName;
	private String subject;
	private String body;

}
