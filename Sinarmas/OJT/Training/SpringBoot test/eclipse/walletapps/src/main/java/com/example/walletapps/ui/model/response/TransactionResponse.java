package com.example.walletapps.ui.model.response;

import com.example.walletapps.shared.dto.WalletsDTO;

import java.time.LocalDateTime;

public class TransactionResponse {
    private String name;
    private long amount;
    private LocalDateTime date;
    private String note;
    private boolean isDeleted;
    private WalletsDTO walletsDTO;
    private String transactionsId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public WalletsDTO getWalletsDTO() {
        return walletsDTO;
    }

    public void setWalletsDTO(WalletsDTO walletsDTO) {
        this.walletsDTO = walletsDTO;
    }

    public String getTransactionsId() {
        return transactionsId;
    }

    public void setTransactionsId(String transactionsId) {
        this.transactionsId = transactionsId;
    }
}
