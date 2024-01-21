package in.ineuron.nitin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication (exclude = SecurityAutoConfiguration.class )
@SpringBootApplication
public class SpringSecurityJdbcAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJdbcAuthenticationApplication.class, args);
	}

}
