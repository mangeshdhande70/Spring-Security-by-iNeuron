package in.ineuron.nitin.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import in.ineuron.nitin.model.UserDetails;

public interface IUserService extends UserDetailsService{
	public String regsiter(UserDetails details);
}
