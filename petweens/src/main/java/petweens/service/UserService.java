package petweens.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import petweens.mapper.UserMapper;
import petweens.model.User;
import petweens.util.PasswordUtil;

@Service(value = "userService")
public class UserService {
	@Resource(name = "userMapper")
	private UserMapper userMapper;
	
	
	public boolean isUserNameExist(String name){
		User user = userMapper.getUserByName(name);
		if(user!=null){
			return true;
		}
		return false;
	}
	public boolean isUserEmailExist(String email){
		User user = userMapper.getUserByEmail(email);
		if(user!=null){
			return true;
		}
		return false;
	}
	public boolean insertUser(User user) {
		if(!user.resistValidate())return false;
		if(isResistedUser(user))return false;
		String salt = PasswordUtil.createBase64Salt();
		String hash = PasswordUtil.getHash(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(hash);
		userMapper.insertUser(user);
		return true;
	}
	public boolean isResistedUser(User user){
		if(user.loginValidate()){
			User dbUser = userMapper.getUserByEmail(user.getEmail());
			if(dbUser==null)return false;
			String salt = dbUser.getSalt();
			String result = PasswordUtil.getHash(user.getPassword(), salt);
			if(result.equals(dbUser.getPassword())){
				user.setUserid(dbUser.getUserid());
				user.setUsername(dbUser.getUsername());
				return true;
			}
		}
		return false;
	}
	
	
}
