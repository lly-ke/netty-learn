import com.google.protobuf.InvalidProtocolBufferException;
import com.netty.demo.protobuf.DataInfo;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-21 15:44
 **/
public class ProtoBufTest {


    @Test
    public void test1(){
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三")
//                .setAge(19)
//                .setAddress("北京")
                .build();
        byte[] studentByte = student.toByteArray();

        try {

            DataInfo.Student student1 = DataInfo.Student.parseFrom(studentByte);

            System.out.println(student.getName());
            System.out.println(student.getAge());
            System.out.println(student.getAddress());

            System.out.println(student1);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

}
