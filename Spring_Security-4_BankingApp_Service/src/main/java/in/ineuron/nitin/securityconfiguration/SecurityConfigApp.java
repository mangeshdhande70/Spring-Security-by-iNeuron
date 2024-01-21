package in.ineuron.nitin.securityconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigApp {

	@Autowired
	public void configureAuthorities(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("SecurityConfigApp.configureAuthorities()");
		// {noop} -> No Encoded Password indication
		auth.inMemoryAuthentication().withUser("sachin").password("{noop}tendulkar").roles("CUSTOMER");
		auth.inMemoryAuthentication().withUser("ramesh").password("{noop}mumbai").roles("MANAGER");
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		System.out.println("SecurityConfigApp.configure()");
		// authorize requests(HTTP)
		http.authorizeHttpRequests().antMatchers("/").permitAll().antMatchers("/offers").authenticated()
				.antMatchers("/balance").hasAnyRole("CUSTOMER", "MANAGER").antMatchers("/loanApprove")
				.hasRole("MANAGER").anyRequest().authenticated().and().formLogin().and().rememberMe().and().logout()
				.and().exceptionHandling().accessDeniedPage("/denied").and().sessionManagement().maximumSessions(2)
				.maxSessionsPreventsLogin(true);

		return http.build();
	}
}
