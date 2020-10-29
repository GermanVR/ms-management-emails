package mx.com.german.ms.configs;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @Autor Luis German Vazquez Renteria
 * @Proyecto: https://github.com/GermanVR/
 * @Correo: german_1241@hotmail.com
 */
@Configuration
public class EmailConfig {

	@Bean
	public JavaMailSender getJavaMailSender(Environment env) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(env.getProperty("server.email.host"));
		mailSender.setPort(Integer.parseInt(env.getProperty("server.email.port")));

		mailSender.setUsername(env.getProperty("server.email.username"));
		mailSender.setPassword(env.getProperty("server.email.password"));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.smtp.auth", env.getProperty("server.email.smtp.auth"));
		props.put("mail.debug", "true");
		props.put("mail.smtp.starttls.enable", env.getProperty("server.email.starttls.enable"));
		props.put("mail.transport.protocol", env.getProperty("server.email.transport.protocol"));
		mailSender.setJavaMailProperties(props);
		return mailSender;
	}

	@Bean
	public SimpleMailMessage templateSimpleMessage() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText("This is the test email template for your email:\n%s\n");
		return message;
	}

}
