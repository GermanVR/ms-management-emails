package mx.com.actinver.ms.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
public class FreemarkerConfig {

	@Autowired
	private Environment env;

	@Bean
	public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath(env.getProperty("server.email.templates.path"));
		return bean;
	}

	@Bean
	public FreeMarkerViewResolver freemarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setCache(true);
		resolver.setPrefix(env.getProperty("server.email.templates.prefix"));
		resolver.setSuffix(env.getProperty("server.email.templates.suffix"));
		return resolver;
	}
}
