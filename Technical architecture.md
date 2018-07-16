![Technical architecture](https://raw.githubusercontent.com/AdvancedServicesEngineeringFudan2018/Bus-schedule/master/Technical%20architecture.png)  

From the picture above，we can see all of the technology and the services we will use in Our platform.  

Firstly, the smart phone installed in the bus will automaticly send the position message at fixed time and we will use a service to collect these message. these message will be sent to the rabbitMq which can serve as message broker.  

Secondly，the service which are responsible for scheduling the bus would get data from rabbitMq. We intend to use spark or other technology to tackle the real-time data collected by service. All the schedule operation will be done in this service.


The other service’s responsibility is as following ：
- Plan management Service：all the plan could be get from this service，by the way，the routine will be gotten from the Baidu Map API
- Driver management Service：The drivers’ driving information ,for instance, the total running time , the accuracy rate per month, could be used to measure the efficiency of a driver and give the reasonable salary to the driver consequently.

All of the service could communicate with each other through the http and will be deployed on docker. The docker will be put on the AWS or Alibaba Cloud. The service will subscribe the data need from the rabbitMq which could save a lot of running cost and time（Reduction the coupling among services）.

Finally，All the service will save the data in MySQL and will get the configuration in github.

	
