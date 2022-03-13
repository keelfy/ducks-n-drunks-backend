package org.keelfy.dndlist;

import org.keelfy.dndlist.configuration.properties.CredentialsProperties;
import org.keelfy.dndlist.configuration.properties.MailProperties;
import org.keelfy.dndlist.configuration.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author Egor Kuzmin
 */
@SpringBootApplication
@EnableScheduling
@EnableWebSecurity
@EnableConfigurationProperties({
		CredentialsProperties.class,
		SecurityProperties.class,
		MailProperties.class
})
public class DucksAndDrunksApplication {

	public static void main(String[] args) {
		SpringApplication.run(DucksAndDrunksApplication.class, args);
	}

}
