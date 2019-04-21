from com.netty.demo.demo11.thrift import ttypes


class PersonServiceImpl:

    def getPersonByUsername(self,username):
        print('username:' + username)

        person = ttypes.Person()
        person.username = username
        return person



    def savePerson(self, person):
        print('person:' + person.__str__())