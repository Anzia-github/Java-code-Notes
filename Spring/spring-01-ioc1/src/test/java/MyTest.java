import com.kaung.dao.UserDaoImpl;
import com.kaung.dao.UserDaoMysqlImpl;
import com.kaung.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        //用户实际调用的是业务层，dao层不需要实际接触！
        // UserServiceImpl userService = new UserServiceImpl();
        //
        // //利用接口去获取，就不用在业务层疯狂改代码了
        // userService.setUserDao(new UserDaoMysqlImpl());
        // userService.getUser();

        // 获取ApplicationContext，拿到Spring的容器
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // 容器在，需要什么，就直接get什么
        UserServiceImpl userServiceImpl = (UserServiceImpl) context.getBean("UserServiceImpl");

        userServiceImpl.getUser();
    }
}
