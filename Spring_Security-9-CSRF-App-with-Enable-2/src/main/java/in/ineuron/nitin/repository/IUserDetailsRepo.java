package in.ineuron.nitin.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import in.ineuron.nitin.model.UserDetails;

public interface IUserDetailsRepo extends CrudRepository<UserDetails, Integer> {
	public Optional<UserDetails> findByUname(String uname);
}
