namespace java com.netty.demo.demo11.thrift

typedef i16 short
typedef i32 int
typedef i64 long
typedef string String
typedef bool boolean

struct Person{
    1: String username;
    2: int age;
    3: boolean married;
}

exception DataException{
    1:String message 2:String callStack
    3:String  date
}

service PersonService{
    Person getPersonByUsername(1: required String username) throws (1:DataException dataException);
    void savePerson(1:required Person person) throws(1: DataException dataException)
}
