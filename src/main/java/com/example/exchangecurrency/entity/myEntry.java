package com.example.exchangecurrency.entity;

import lombok.Data;

@Data
public class myEntry {
    String description;
    String value;

    public myEntry(String description, String value) {
        this.description = description;
        this.value = value;
    }
}
