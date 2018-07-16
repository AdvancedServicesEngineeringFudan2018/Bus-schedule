# Bus-schedule platform

## Background:
Nowadays, Most of the buses have connected to the Internet through the sensor, However, the installment of the sensor have to take a lot of time and could cost a lot of money as well. Besides, we can see that different regions have different types of bus scheduling system. The change of the system could be frequent.
Scenario: Bus schedule

## Empathize ：
If you implement the scenario, who would be your customers?
The goal of our customers could be the driver，the bus company in different regions.

## Define What do your users need?
1.	Drivers only need a smartphone which can provide the position message in time.
2.	The Bus company should have a cloud environment in which we can store the data and monitor the change of the platform. 

 
## Scenario: Bus schedule
## Possible solutions:
Micro service could be a good approach to settle the frequent change of the service. Spring cloud provide tools for developers to quickly build platforms using micro service. Docker is a light container which enables true independence between applications and infrastructure We intend to use spring cloud to build the service and docker is used to deploy the project.

## Key involved stakeholders:
Driver，passenger, Bus company, the government , developer, cloud service provider
## Key metrics for evaluating:
1.	The precise of the position message and whether the company could schedule the bus on time, in other words, whether the bus could come as scheduled and change the schedule when accident happen.
2.	The iteration time of the platform and the applicability of the platform in different regions.

## Similar or related scenarios:
Plane schedule platform

## Author
[GuoSiYing](https://github.com/Guosiying)  

[Jack Miao](https://github.com/miaoxu9999) 

[Fan ZheHao](https://github.com/f953983670)
