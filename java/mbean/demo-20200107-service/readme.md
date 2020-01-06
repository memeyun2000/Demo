# mbean service 端

> 增加如下代码
```
/**
 * JMX Connector Service
 */
LocateRegistry.createRegistry(8081);
JMXServiceURL url = new JMXServiceURL
("service:jmx:rmi:///jndi/rmi://localhost:8081/jmxrmi");
JMXConnectorServer jcs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
jcs.start();
```
