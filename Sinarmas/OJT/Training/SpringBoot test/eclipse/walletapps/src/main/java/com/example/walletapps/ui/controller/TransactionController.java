package com.example.walletapps.ui.controller;

import com.example.walletapps.io.entity.TransactionsEntity;
import com.example.walletapps.service.iservice.IServiceTransactions;
import com.example.walletapps.shared.dto.TransactionDTO;
import com.example.walletapps.ui.model.request.TransactionRequest;
import com.example.walletapps.ui.model.response.TransactionResponse;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final IServiceTransactions iServiceTransactions;

    public TransactionController(IServiceTransactions iServiceTransactions){
        this.iServiceTransactions = iServiceTransactions;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TransactionResponse> getAllTransactions(){
        ModelMapper mapper = new ModelMapper();
        List<TransactionResponse> value = new ArrayList<>();

        List<TransactionDTO> transactionsDTO = iServiceTransactions.getAllTransactions();

        for (TransactionDTO dto : transactionsDTO){
            value.add(mapper.map(dto, TransactionResponse.class));
        }

        return value;
    }

    @GetMapping(path = "/{walletId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TransactionResponse> getAllTransactionsWalletId(@PathVariable String walletId){
        ModelMapper mapper = new ModelMapper();
        List<TransactionResponse> value = new ArrayList<>();

        List<TransactionDTO> transactionDTOS = iServiceTransactions.getAllTransactionsByWalletId(walletId);

        for(TransactionDTO dto : transactionDTOS){
            value.add(mapper.map(dto, TransactionResponse.class));
        }
        return value;
    }


    @PostMapping(path = "/{walletId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionResponse addNewTransactions(@PathVariable String walletId, @RequestBody TransactionRequest transactionRequest){
        ModelMapper mapper = new ModelMapper();
        TransactionResponse value = new TransactionResponse();
        TransactionDTO transactionDTO = mapper.map(transactionRequest, TransactionDTO.class);

        TransactionDTO storedvalue = iServiceTransactions.addNewTransactions(walletId, transactionDTO);

        value = mapper.map(storedvalue, TransactionResponse.class);

        return value;
    }

    @PutMapping(path = "/{walletId}/{transactionsId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionResponse updateTransactions(@PathVariable String walletId,@PathVariable String transactionsId, @RequestBody TransactionRequest transactionRequest){
        ModelMapper mapper = new ModelMapper();
        TransactionResponse value = new TransactionResponse();

        TransactionDTO transactionDTO = mapper.map(transactionRequest, TransactionDTO.class);
        TransactionDTO updatedData = iServiceTransactions.updateTransactions(walletId, transactionsId, transactionDTO);

        value = mapper.map(updatedData, TransactionResponse.class);

        return value;


    }


    @DeleteMapping(path = "/{walletId}/{transactionsId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionResponse deleteTransactions(@PathVariable String walletId,@PathVariable String transactionsId){
        ModelMapper mapper = new ModelMapper();

        TransactionDTO deletedData = iServiceTransactions.deleteTransactions(walletId, transactionsId);
        return mapper.map(deletedData, TransactionResponse.class);
    }
}
