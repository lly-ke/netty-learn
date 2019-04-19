import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-19 15:37
 **/
@SpringBootApplication(scanBasePackages = "com.netty.demo.other")
public class SpringBootStart {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStart.class);
    }

}
