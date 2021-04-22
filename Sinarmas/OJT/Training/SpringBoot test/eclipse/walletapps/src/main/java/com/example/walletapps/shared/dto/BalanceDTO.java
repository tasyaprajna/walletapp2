package com.example.walletapps.shared.dto;

public class BalanceDTO {
    private static final long serialVersionUID = -8737558474432365610L;
    private String userName;
    private long balance;
    private UserDTO user;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
