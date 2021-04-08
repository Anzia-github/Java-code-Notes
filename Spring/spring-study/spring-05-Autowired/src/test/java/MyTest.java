import com.kuang.pojo.People;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test1() {

        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

        People people = context.getBean("people", People.class);
        System.out.println(people.getName());
        people.getDog1().shout();
        people.getCat().shout();
    }
}
