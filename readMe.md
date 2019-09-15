This project is to create/cancel/view Silver Bars Orders 

This is a springBoot Application

To run the application 

1) CD into the root director of the project and run Maven command 'mvn clean install'
2) Open terminal CD into the target folder and run command java -jar <JAR_FILE_NAME>.jar

To test the application

Launch post man and hit HTTP requests as required (POST/DELETE/GET)

1) To create an order

http://localhost:8080/order (POST)

Ex Payload: 

{
	"userId":1,
	"quantity":1.5,
	"pricePerKg":310,
	"type":"SELL"
}

2) To Cancel an Order

http://localhost:8080/order/1 (DELETE)

3) For Order Summary Board

http://localhost:8080/order/ordersSummary

Ex Response:

{
    "orders": [
        "SELL 5.5 Kg For 306.0",
        "SELL 3.5 Kg For 310.0",
        "SELL 3.0 Kg For 320.0",
        "BUY 1.5 Kg For 320.0",
        "BUY 1.5 Kg For 315.0",
        "BUY 3.0 Kg For 310.0"
    ]
}