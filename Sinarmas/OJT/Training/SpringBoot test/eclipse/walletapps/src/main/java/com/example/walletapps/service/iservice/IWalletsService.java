package com.example.walletapps.service.iservice;

import com.example.walletapps.shared.dto.BalanceDTO;
import com.example.walletapps.shared.dto.WalletsDTO;

import java.util.List;

public interface IWalletsService {
    WalletsDTO addNewWalletData(String userid, WalletsDTO walletsDTO);
    List<WalletsDTO> getListWallet (String userid);
    long getTotalBalance(String userid);
    WalletsDTO updateWalletData(String userid, String walletid, WalletsDTO walletsDTO);
//    BalanceDTO getBalanceWallet (String userid);
}
