package com.netty.demo.demo11;

import com.netty.demo.demo11.thrift.DataException;
import com.netty.demo.demo11.thrift.Person;
import com.netty.demo.demo11.thrift.PersonService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-21 22:42
 **/
public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("PersonServiceImpl.getPersonByUsername.username:" + username);
        Person person = new Person();
        person.setUsername(username);
        if (true) {
            throw new DataException("服务器颠了", ".....", "2018-1-1");
        }
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("PersonServiceImpl.savePerson.person = " + person);
    }
}

