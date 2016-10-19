package obor.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfig {
	@Bean
	public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
		return new MyCustomizer();
	}

	private static class MyCustomizer implements EmbeddedServletContainerCustomizer {
		@Override
		public void customize(ConfigurableEmbeddedServletContainer container) {
			container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
			container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
			container.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/405"));
			container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
		
		}

	}
}
