from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol

from com.netty.demo.demo11.thrift import PersonService, ttypes

tSocket =TSocket.TSocket('localhost', 8899);
# tSocket.setTimeout(600);
transport = TTransport.TFramedTransport(tSocket)
protocol = TCompactProtocol.TCompactProtocol(transport)

client = PersonService.Client(protocol)
transport.open()


person = client.getPersonByUsername("砸我打的")
print(person)
newPerson = ttypes.Person();
newPerson.age= 123
client.savePerson(newPerson)

transport.close()
