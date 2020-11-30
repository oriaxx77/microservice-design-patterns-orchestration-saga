# Saga orchestration in kotlin with spring boot
* https://microservices.io/patterns/data/saga.html

# TODO 
- Use REST.
- Eliminate server-commons. Use an order-microservice-client.
- Eliminate all the todos in the code.
- Write system an unit tests.

# Webshop use case
An e-commerce application with 3 microservices:
* order-microservice
* payment-microservice
* shipping-microservice

Order sequence, the saga:
1. **order-microservice**: creates the order. It is the saga orchestrator.
2. **payment-microservice**: deals with the payment with 50% success.
3. **sipping-microservice**: ships the order with 50% success.


# Running the example

## Docker
### Kill everything
* `docker stop $(docker ps -a -q)`
* `docker rm $(docker ps -a -q)`
### Start compose
* Navigate to *./infrastructure/kafka-only-docker-compose* directory
* `docker-compose up`
## Start the microservices
* This are 3 spring boot microservices written in kafka
* Start `com.oriaxx77.micoserviceplay.order.OrderMicroservice`
* Start `com.oriaxx77.micoserviceplay.payment.PaymentMicroservice`
* Start `com.oriaxx77.micoserviceplay.shipping.ShippingMicroservice`

# Test the app with browser
* Create orders: `http://127.0.0.1:9095/order`
* List order sagas: `http://127.0.0.1:9095/ordersagas`


