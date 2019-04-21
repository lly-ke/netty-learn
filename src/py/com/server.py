from thrift import Thrift

from com.PersonServiceImpl import PersonServiceImpl
from com.netty.demo.demo11.thrift import PersonService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
from thrift.server import TServer

personServiceImpl = PersonServiceImpl()
processor = PersonService.Processor(personServiceImpl)

serverSocket = TSocket.TServerSocket(port=8899)
transportFactory = TTransport.TFramedTransportFactory()
protocolFactory = TCompactProtocol.TCompactProtocolFactory()


server = TServer.TSimpleServer(processor, serverSocket, transportFactory, protocolFactory)
server.serve()