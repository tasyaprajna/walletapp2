package com.example.walletapps.service.impl;

import com.example.walletapps.io.entity.TransactionsEntity;
import com.example.walletapps.io.entity.WalletsEntity;
import com.example.walletapps.io.irepository.TransactionsRepository;
import com.example.walletapps.io.irepository.WalletsRepository;
import com.example.walletapps.service.iservice.IServiceTransactions;
import com.example.walletapps.shared.dto.TransactionDTO;
import com.example.walletapps.shared.utils.GenerateRandomPublicId;
import com.example.walletapps.ui.model.response.TransactionResponse;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsServiceImpl implements IServiceTransactions {
    private final TransactionsRepository transactionsRepository;
    private final WalletsRepository walletsRepository;
    private final GenerateRandomPublicId generateRandomPublicId;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository, WalletsRepository walletsEntity1, WalletsRepository walletsRepository, GenerateRandomPublicId generateRandomPublicId) {
        this.transactionsRepository = transactionsRepository;


        this.walletsRepository = walletsRepository;
        this.generateRandomPublicId = generateRandomPublicId;
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        ModelMapper mapper = new ModelMapper();

        //wadah untuk mapping dari entity ke dto
        List<TransactionDTO> value = new ArrayList<>();

        List<TransactionsEntity> transactionsEntities = transactionsRepository.findAll();

        for (TransactionsEntity transactionsEntity : transactionsEntities) {
            value.add(mapper.map(transactionsEntity, TransactionDTO.class));
        }
        return value;
    }

    @Override
    public TransactionDTO addNewTransactions(String walletId, TransactionDTO transactionDTO) {
        ModelMapper mapper = new ModelMapper();

        long oldBalance = 0;
        long amountInit = 0;

        WalletsEntity walletsEntity = walletsRepository.findByWalletid(walletId);
        oldBalance = walletsEntity.getBalance();

        TransactionsEntity transactionsEntity = mapper.map(transactionDTO, TransactionsEntity.class);
        amountInit = transactionsEntity.getAmount();

        transactionsEntity.setWalletsEntity(walletsEntity);
        transactionsEntity.setTransactionsId(generateRandomPublicId.generateUserId(30));

        walletsEntity.setBalance(oldBalance - amountInit);

        TransactionsEntity storevalue = transactionsRepository.save(transactionsEntity);

        TransactionDTO value = mapper.map(storevalue, TransactionDTO.class);


        return value;
    }

    @Override
    public List<TransactionDTO> getAllTransactionsByWalletId(String walletId) {
        ModelMapper mapper = new ModelMapper();

        List<TransactionDTO> value = new ArrayList<>();
        WalletsEntity walletsEntity = walletsRepository.findByWalletid(walletId);

        List<TransactionsEntity> transactionsEntities = transactionsRepository.findByWalletsEntity(walletsEntity);

        for (TransactionsEntity transactionsEntity : transactionsEntities) {
            value.add(mapper.map(transactionsEntity, TransactionDTO.class));
        }
        return value;
    }

    @Override
    public TransactionDTO updateTransactions(String walletId, String transactionsId, TransactionDTO transactionDTO) {
        ModelMapper mapper = new ModelMapper();

        long oldBalance = 0;
        long amountInit = 0;
        long amountUpdate = transactionDTO.getAmount();

        WalletsEntity walletsEntity = walletsRepository.findByWalletid(walletId);
        oldBalance = walletsEntity.getBalance();

        TransactionsEntity transactionsEntity = transactionsRepository.findByTransactionsId(transactionsId);
        amountInit = transactionsEntity.getAmount();

        TransactionsEntity entity = mapper.map(transactionDTO, TransactionsEntity.class);
        entity.setWalletsEntity(walletsEntity);
        entity.setId(transactionsEntity.getId());
        entity.setTransactionsId(transactionsEntity.getTransactionsId());

        if(amountInit > amountUpdate){
            walletsEntity.setBalance(oldBalance + (amountInit - amountUpdate));
        }else {
            walletsEntity.setBalance(oldBalance - (amountUpdate - amountInit));
        }

        TransactionsEntity value = transactionsRepository.save(entity);
        return mapper.map(value, TransactionDTO.class);
    }

    @Override
    public TransactionDTO deleteTransactions(String walletId, String transactionsId) {
        WalletsEntity walletsEntity = walletsRepository.findByWalletid(walletId);
        TransactionsEntity transactionsEntity = transactionsRepository.findByTransactionsId(transactionsId);

        transactionsEntity.setWalletsEntity(walletsEntity);
        transactionsEntity.setDeleted(true);

        TransactionsEntity value = transactionsRepository.save(transactionsEntity);

        ModelMapper mapper = new ModelMapper();

        return mapper.map(value, TransactionDTO.class);


    }
}
