import com.kuang.pojo.Student;
import com.kuang.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Student student = (Student) context.getBean("student");
        System.out.println(student.toString());
    }

    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("userbeans.xml");
        //利用了反射
        User user = context.getBean("user2", User.class);
        System.out.println(user.toString());
    }
}
