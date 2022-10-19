# Notes

## Account Service
`/create`
- **params:** 
  - currency
  - initial balance (defaults to 0)
- **behaviour:**
  - new account created
  - uses KYC service to validate
  - persists account in account repository
  - returns created account


`/debit`
- **params:**
  - account id
  - amount
- **behaviour:**
  - account debited by amount
  - throws IllegalArgumentException if amount > balance
  - throws IllegalArgumentException if account does not exist

`/credit`
- **params:**
  - account id
  - amount
- **behaviour:**
  - account credited with amount
  - throws IllegalArgumentException if account does not exist

```
/delete
/update
/block
/findById
```

## Transaction Service

`/transfer` 
- **params:** 
  - source account id
  - target account id
  - amount
- **behaviour:**
  - calls account service `/debit` for source account
  - calls account service `/credit` for target account
  - persists transaction in transaction repository
  - Transactional operation - rollback in case of failure
  - returns error if transaction fails
  - returns error if source and target are the same account

```
/cancel
/report
```

```
     _____________
    | Client      |
    |_____________|
           |
        /transfer
           |
     _____________
    | Transaction |
    | Service     |
    |_____________|
        |       |
      /debit  /credit
        |       |
     _____________
    | Account     |         
    | Service     |
    |_____________|
 
```

## System Design Notes

```
customer - name, email, id
registration service ( + KYC)
login service
One to Many Account

account - id, customer id, balance, status
Account creation service
Account management service
Many to One Customer
Many to Many Transaction

transaction - amount, id, source account id, target account id
Transaction service
Many to Many Account
resilience
consistency (transactional operations)
security

DB - JPA + PostgreSQL / H2
Cache


```
