# tronTriggerSmartContract
There are two main function from this application:

#### 1. Trigger a smart contract with the rest API:

Send x TRX coins from the main address to the trigger address and triggers a given smart contract function.
   
1. Sends x (amount) TRX from mainAddress to triggerAddress
2. Wait n seconds
3. Trigger a smart contract (contractAddress) on the triggerAddress
4. Get new balance on triggerAddress and sends whole amount back to mainAddress 
   
Example Post Request
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

#### 2. Trigger smart contract on multiply wallets given from a .csv file if balance on smart contract is available: 

There is a scheduler running every 30s which checks the balance on the smart contract address.

If the balance is > 1000 TRX then:
1. Sends x (amount) TRX from mainAddress to triggerAddress
2. Wait n seconds
3. Trigger a smart contract (contractAddress) on the triggerAddress
4. Get new balance on triggerAddress and sends whole amount back to mainAddress 
   
This steps will start asynchronous on all addresses given in the .csv file.

