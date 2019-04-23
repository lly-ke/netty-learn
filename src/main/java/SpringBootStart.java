import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
