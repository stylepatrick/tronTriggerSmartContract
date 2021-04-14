# tronTriggerSmartContract
Send x TRX coins and triggers a smart contract with Spring Boot 2.
1. Sends x (amount) TRX from mainAddress to triggerAddress
2. Wait n seconds
3. Trigger a smart contract (contractAddress) on the triggerAddress
4. Get new balance on triggerAddress and sends whole amount back to mainAddress

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
    "amount": exchangeAmount,
    "callValue": callValue,
    "feeLimit": feeLimit,
    "function": "function()",
    "parameter": ""
}
```