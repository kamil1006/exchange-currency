package com.example.exchangecurrency.entity;

import lombok.Data;

@Data
public class MyCurrency {
    private String symbol;
    private String name;

    public MyCurrency(String symbol, String country) {
        this.symbol = symbol;
        this.name = country;
    }
}
