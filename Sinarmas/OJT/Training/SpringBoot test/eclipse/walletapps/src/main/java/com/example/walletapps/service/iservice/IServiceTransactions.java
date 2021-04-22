package com.example.walletapps.service.iservice;

import com.example.walletapps.shared.dto.TransactionDTO;

import java.util.List;

public interface IServiceTransactions {
    List<TransactionDTO> getAllTransactions();

    TransactionDTO addNewTransactions(String walletId, TransactionDTO transactionDTO);


    List<TransactionDTO> getAllTransactionsByWalletId(String walletId);

    TransactionDTO updateTransactions(String walletId, String transactionsId, TransactionDTO transactionDTO);

    TransactionDTO deleteTransactions(String walletId, String transactionsId);
}
