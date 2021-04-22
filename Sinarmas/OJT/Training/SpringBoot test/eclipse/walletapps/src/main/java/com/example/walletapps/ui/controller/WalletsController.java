package com.example.walletapps.ui.controller;

import com.example.walletapps.service.iservice.IWalletsService;
import com.example.walletapps.shared.dto.BalanceDTO;
import com.example.walletapps.shared.dto.WalletsDTO;
import com.example.walletapps.ui.model.request.WalletRequest;
import com.example.walletapps.ui.model.response.BalanceResponse;
import com.example.walletapps.ui.model.response.WalletResponse;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/wallets")
public class WalletsController {
    @Autowired
    IWalletsService walletsService;

    @PutMapping(path = "/{userId}/{walletId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public WalletResponse updateWalletAmount(@PathVariable String userId,@PathVariable String walletId, @RequestBody WalletRequest walletRequest){

        ModelMapper mapper = new ModelMapper();

        //WalletRequest --> walletDTO
        WalletsDTO walletsDTO = mapper.map(walletRequest, WalletsDTO.class);
        WalletsDTO updateWallet = walletsService.updateWalletData(userId, walletId, walletsDTO);

        return mapper.map(updateWallet, WalletResponse.class);
    }

    @GetMapping(path = "/{userId}/balance",
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public long getTotalBalance(@PathVariable String userId){
        long totalBalance = walletsService.getTotalBalance(userId);

        return totalBalance;
    }

//    public BalanceResponse getBalanceWallet(@PathVariable String userId){
//        ModelMapper mapper = new ModelMapper();
//        BalanceDTO wallets_balance = walletsService.getBalanceWallet(userId);
//
//        return mapper.map(wallets_balance, BalanceResponse.class);
//    }



    @GetMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public List<WalletResponse> getAllWallet(@PathVariable String userId){
        List<WalletResponse> value = new ArrayList<>();

        ModelMapper mapper = new ModelMapper();
        List<WalletsDTO> wallets = walletsService.getListWallet(userId);

        for (WalletsDTO walletsDTO : wallets){
            value.add(mapper.map(walletsDTO, WalletResponse.class));
        }
        return value;
    }


    @PostMapping(path = "/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public WalletResponse addNewWallet(@PathVariable String userId,@RequestBody WalletRequest walletRequest){
        ModelMapper mapper = new ModelMapper();

        WalletsDTO walletsDTO = mapper.map(walletRequest, WalletsDTO.class);
        WalletsDTO createdWallet = walletsService.addNewWalletData(userId, walletsDTO);

        return mapper.map(createdWallet, WalletResponse.class);
    }
}
