package petweens.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import petweens.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/conf/applicationContext.xml","classpath:petweens/test/testMybatisContext.xml","file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class TestUserMapper {
	@Resource(name = "userMapper")
	UserMapper userDAO;
	
	@Test
	public void insertUser(){
		User user = new User();
		user.setEmail("asdf@naver.com");
		user.setPassword("1234");
		user.setSalt("1541");
		user.setUsername("kim");
		userDAO.insertUser(user);
		User dbUser = userDAO.getUserByEmail(user.getEmail());
		assertEquals(user.getEmail(),dbUser.getEmail());
		userDAO.deleteUserById(dbUser.getUserid());
		assertNull(userDAO.getUserById(dbUser.getUserid()));
	}
	
}
