package petweens.mapper;

import org.springframework.stereotype.Repository;

import petweens.model.User;

@Repository(value = "userMapper")
public interface UserMapper {
	public void insertUser(User user);
	public User getUserById(int id);
	public User getUserByEmail(String email);
	public User getUserByName(String username);
	public void deleteUserById(int id);
}
