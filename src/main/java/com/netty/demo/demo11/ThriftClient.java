package com.netty.demo.demo11;

import com.netty.demo.demo11.thrift.Person;
import com.netty.demo.demo11.thrift.PersonService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-21 22:55
 **/
public class ThriftClient {

    public static void main(String[] args) throws TTransportException {
        TTransport transport = new TFramedTransport(new TSocket("127.0.0.1", 8899));
        TProtocol protocol = new TCompactProtocol(transport);

        PersonService.Client client = new PersonService.Client(protocol);


        transport.open();

        try {
            Person person = client.getPersonByUsername("张萨达无多");
            System.out.println(person);
            client.savePerson(new Person("张三", 1, false));
        } catch (TException e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
    }

}
