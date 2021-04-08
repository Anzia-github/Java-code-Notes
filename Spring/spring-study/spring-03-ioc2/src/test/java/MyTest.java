import com.kuang.pojo.User;
import com.kuang.pojo.UserT;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {

        // User user = new User();
        // user.setName("hello");
        // user.show();

        //Spirng容器，就类似于婚介网站！
        //bean在一注册之后，就被Spring实例化了，要用的话直接get
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserT user = (UserT) context.getBean("u2");
        user.show();
    }
}
