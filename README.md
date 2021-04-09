# tronTriggerSmartContract
 Send x TRX coins and triggers a smart contract with Spring Boot 2.

## Example Post Request
```
{
    "mainAddress": {
        "address": "address",
        "privateKey": "privateKey",
        "permissionId": permissionId
    },
    "triggerAddress": {
        "address": "address",
        "privateKey": "privateKey",
        "permissionId": permissionId
    },
    "contractAddress": "smartContractAddress",
    "exchangeAmount": "Amount you like to send to the trigger address"
}
```