# In Progress

TODO: 
- Use REST.
- Eliminate server-commons. Use an order-microservice-client.

# Sage pattern
* https://microservices.io/patterns/data/saga.html

# Saga with Orchestration

An e-commerce application that uses this approach would create an order using an orchestration-based saga that consists of the following steps:

The Order Service receives the POST /orders request and creates the Create Order saga orchestrator
The saga orchestrator creates an Order in the PENDING state
It then sends a Reserve Credit command to the Customer Service
The Customer Service attempts to reserve credit
It then sends back a reply message indicating the outcome
The saga orchestrator either approves or rejects the Order



