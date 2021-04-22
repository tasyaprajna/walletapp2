package com.example.walletapps.service.impl;

import com.example.walletapps.io.entity.UserEntity;
import com.example.walletapps.io.entity.WalletsEntity;
import com.example.walletapps.io.irepository.UserRepository;
import com.example.walletapps.io.irepository.WalletsRepository;
import com.example.walletapps.service.iservice.IWalletsService;
import com.example.walletapps.shared.dto.BalanceDTO;
import com.example.walletapps.shared.dto.UserDTO;
import com.example.walletapps.shared.dto.WalletsDTO;
import com.example.walletapps.shared.utils.GenerateRandomPublicId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletsServiceImpl implements IWalletsService {
    @Autowired
    WalletsRepository walletsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GenerateRandomPublicId generateRandomPublicId;

    @Override
    public long getTotalBalance(String userid) {
        List<WalletsEntity> walletsData = walletsRepository.findAllByUser(userRepository.findByUserid(userid));
        if (walletsData == null){
            return 0;
        }
        long totalBalance = 0;

        for (WalletsEntity walletsEntity : walletsData){
            totalBalance = totalBalance + walletsEntity.getBalance();
        }
        return totalBalance;
    }

    @Override
    public WalletsDTO updateWalletData(String userid, String walletid, WalletsDTO walletsDTO) {
        WalletsEntity walletsData = walletsRepository.findByWalletid(walletid);
        if (walletsData == null || ! userid.equals(walletsData.getUser().getUserid())){
            return null;
        }

        //update nohp or balance
        walletsData.setNoHP(walletsDTO.getNoHP());
        walletsData.setBalance(walletsDTO.getBalance());

        WalletsEntity updateData = walletsRepository.save(walletsData);

        return new ModelMapper().map(updateData, WalletsDTO.class);
    }

//    @Override
//    public BalanceDTO getBalanceWallet(String userId) {
//        ModelMapper mapper = new ModelMapper();
//        UserEntity userEntity = userRepository.findByUserid(userId);
//        List<WalletsEntity> wallets = walletsRepository.findAllByUser(userEntity);
//        long total = 0;
//        for(WalletsEntity walletsEntity : wallets){
//            total = total + walletsEntity.getBalance();
//        }
//        BalanceDTO value = new BalanceDTO();
//        value.setBalance(total);
//        value.setUserName(userEntity.getUsername());
//
//
//
//    return value;
//    }

    @Override
    public List<WalletsDTO> getListWallet(String userId) {
        ModelMapper mapper = new ModelMapper();

        List<WalletsDTO> value = new ArrayList<>();

        //Get User
        UserEntity userEntity = userRepository.findByUserid(userId);

        List<WalletsEntity> wallets = walletsRepository.findAllByUser(userEntity);

        for (WalletsEntity walletsEntity : wallets){
            value.add(mapper.map(walletsEntity, WalletsDTO.class));
        }
        return value;
    }


    @Override
    public WalletsDTO addNewWalletData(String userid, WalletsDTO walletsDTO) {
        ModelMapper mapper = new ModelMapper();

        //Generate Wallets Id
        walletsDTO.setWalletId(generateRandomPublicId.generateUserId(30));
        //Get User
        UserEntity userData = userRepository.findByUserid(userid);
        //Set User
        walletsDTO.setUser(mapper.map(userData, UserDTO.class));

        WalletsEntity entity = mapper.map(walletsDTO, WalletsEntity.class);

        //Save data ke Database (table: walletstbl)
        WalletsEntity storedData = walletsRepository.save(entity);

        return mapper.map(storedData, WalletsDTO.class);
    }
}
