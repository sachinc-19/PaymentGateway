
# Payment Gateway

## 👉 Project overview

🚀 A backend system for processing international money transfers, handling transaction validations, and seamless integration with country-specific payment service providers (PSPs).

### APIs for the transaction

#### Create a transaction

```bash
POST https://paymentgateway-k53u.onrender.com/api/payout/process
Sample request:
{
    "credential": {
        "username": "superUser",
        "password": "superPass"
    },
    "operation": "PAYOUT",
    "paymentDetails": {
        "paymentId": "2",
        "transferDetails": {
            "sendCountryCode": "IN",
            "sendAmount": {
                "currencyCode": "INR",
                "value": 100.0 
            },
            "receiveCountryCode": "BD",
            "receiveAmount": {
                "currencyCode": "BDT",
                "value": 200.0
            }
        },
        "purpose": "Family Support",
        "pspDetails": {
            "accountNumber": "123456",
            "partnerName": "PSP1",
            "bankName": "BANK OF BANGLADESH",
            "bankCode": "123",
            "branchCode": "BD132949",
            "beneficiaryName": "MOHD. RASHID ALAM"
        },
        "paymentMethod": "ACCT"
    }
}
```

```bash
Sample Response:
{
    "status": "Success",
    "code": 202,
    "message": "Payout request received and is being processed"
}

```

#### Fetch current status of transaction
```bash
GET https://paymentgateway-k53u.onrender.com/api/payout/status?paymentid={paymentId}

```

```bash
Sample Response:
{
    "status": "ACKNOWLEDGED",
    "code": 200,
    "message": "Transaction Acknowledged successfully"
}
{
    "status": "PAID",
    "code": 200,
    "message": "Paid successful"
}

```

#### Backend Structure

🚀 **Controllers :** Receives API requests (e.g., /payout) and forwards them to the Service Layer.

🚀 **Service (Authorization, Authentication) :** Handles authentication, authorization, and initial request validation. Once authenticated, it forwards the request to the PayoutEngine for routing.

🚀 **Engine (Routing and Common Validation) :** 		
○ Routing Rules: Decides which PSP to use based on predefined routing logic or dynamic real-time conditions (e.g., transaction amount, country, speed, fees).
○ Common Field Validation: Validates fields that are common across all international transfers (e.g., account number, name, currency). These checks are independent of the PSP and specific to general money transfer logic.
○ Once the routing decision is made and the common validation passes, the transaction is passed to the Processor Layer.
This layer represents the core business logic for handling transaction processing and intermediate steps before the final payout. It orchestrates the payout process by calling Processor.

🚀 **Processor  (Country and PSP-Specific Validation, External API Calls) :** 
○ PSP-Specific Validation: Performs country-specific and PSP-specific validations (e.g., bank account format, country-specific rules). Each PSP might have different field requirements and validation rules.
○ External API Calls: Makes external API calls to the selected PSP to process the transaction. This is where the transaction is finalized (e.g., account verification, fraud checks, actual transfer).
Handles fallbacks and retry mechanisms in case of PSP failure.This is the final processing layer, which typically interacts with external systems (e.g., banks, third-party payment gateways). It performs the actual transaction, updates statuses, and logs the result.

🚀 **Models :** Data transfer object (DTO) representing the incoming payout request. DTO representing the response after processing the transaction.

🚀 **Utility Services (helpers, retry mechanisms, etc.) :** Utility class for implementing retry mechanisms

🚀 **Repository :** This folder contains the repository interfaces that provide data access functionality, typically extending JpaRepository. Responsible for handling CRUD operations related to User and Transaction entities.

🚀 **Exceptions :** Custom exception class to handle validation errors.

### Schemas:

#### PaymentTransaction
```bash
CREATE TABLE `paymenttransaction` (
  `paymentid` varchar(255) NOT NULL,
  `channel` varchar(255) DEFAULT NULL,
  `createdon` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lastupdated` datetime(6) DEFAULT NULL,
  `modifiedon` datetime(6) DEFAULT NULL,
  `reasoncode` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `substate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`paymentid`)
) 
```

#### PaymentTransactionDetails

```bash
CREATE TABLE `paymenttransactiondetails` (
  `paymentid` varchar(255) NOT NULL,
  `channel` varchar(255) DEFAULT NULL,
  `createdon` datetime(6) DEFAULT NULL,
  `deliveryestimate` datetime(6) DEFAULT NULL,
  `localdeliveryestimate` datetime(6) DEFAULT NULL,
  `modifiedon` datetime(6) DEFAULT NULL,
  `overallfx` int DEFAULT NULL,
  `overallfxmultiplier` int DEFAULT NULL,
  `partnername` varchar(255) DEFAULT NULL,
  `partnerreference` varchar(255) DEFAULT NULL,
  `purpose` varchar(255) DEFAULT NULL,
  `receivecountrycode` varchar(255) DEFAULT NULL,
  `sendcountrycode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`paymentid`)
)
```

#### PspDetails

```bash
CREATE TABLE `pspdetails` (
  `paymentid` varchar(255) NOT NULL,
  `accountnumber` varchar(255) DEFAULT NULL,
  `bankcode` varchar(255) DEFAULT NULL,
  `bankname` varchar(255) DEFAULT NULL,
  `beneficiaryname` varchar(255) DEFAULT NULL,
  `branchcode` varchar(255) DEFAULT NULL,
  `modifiedon` datetime(6) DEFAULT NULL,
  `partnerid` varchar(255) DEFAULT NULL,
  `partnername` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`paymentid`)
) 
```


#### Amount

```bash
CREATE TABLE `amount` (
  `amounttype` varchar(255) NOT NULL,
  `paymentid` varchar(255) NOT NULL,
  `currencycode` varchar(255) DEFAULT NULL,
  `fxrate` int DEFAULT NULL,
  `fxratemultiplier` int DEFAULT NULL,
  `modifiedon` datetime(6) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `valuemultiplier` int DEFAULT NULL,
  PRIMARY KEY (`amounttype`,`paymentid`),
  KEY `FKbqj8bdjdsqnipei4kk6dmg6c3` (`paymentid`),
  CONSTRAINT `FKbqj8bdjdsqnipei4kk6dmg6c3` FOREIGN KEY (`paymentid`) REFERENCES `paymenttransactiondetails` (`paymentid`)
)
```

#### PaymentProcessingScheduled

```bash
CREATE TABLE `paymentprocessingscheduled` (
  `paymentid` varchar(255) NOT NULL,
  `apiname` varchar(255) DEFAULT NULL,
  `creationtime` datetime(6) DEFAULT NULL,
  `partner` varchar(255) DEFAULT NULL,
  `scheduletime` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `timerdata` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`paymentid`)
) 
```

#### User

```bash
CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
PRIMARY KEY (`username`)
) 
```
