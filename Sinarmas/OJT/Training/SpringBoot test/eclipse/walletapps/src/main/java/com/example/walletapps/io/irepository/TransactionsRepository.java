package com.example.walletapps.io.irepository;

import com.example.walletapps.io.entity.TransactionsEntity;
import com.example.walletapps.io.entity.WalletsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<TransactionsEntity, Long> {
    List<TransactionsEntity> findByWalletsEntity(WalletsEntity walletsEntity);

    TransactionsEntity findByTransactionsId(String transactionsId);
}
