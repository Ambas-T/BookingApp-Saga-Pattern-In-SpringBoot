# BookingApp-Saga-Patern-In-SpringBoot
This repository discusses the Saga Pattern using a booking application and is built using the Spring Boot microservices architecture.

**Definition of The Saga Pattern **

The Saga Pattern breaks a long transaction into smaller ones. Each transaction is independent and updates the system. A transaction publishes  an event or message to trigger the next transaction. In case of failure, it will compensate transactions.

In this repository, you will the below design implemented.

 ![Untitled Diagram drawio(1)](https://github.com/Ambas-T/BookingApp-Saga-Patern-In-SpringBoot/assets/148710180/4a5a93b8-b1d0-4de2-96a8-07dd42c7ee9c)

I choose to discuss an orchestration-type Saga Pattern. To build a distributed orchestration transaction first we should have a Saga method that can handle and check if all important local transactions are successful. In our scenario, within the BookingService, there is a method designed to check whether the transactions in FlightService, HotelService, and CarService have been successfully executed. If one of the systems fails, it will trigger the compensating method.
