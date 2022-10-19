package com.example.yachtRent.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class YachtRequest {
    private  String name;
    private  Long typeId;
    private  BigDecimal price;
    private  String crew;
    private  Integer sleepingCapacity;
    private  Integer cruiseCapacity;

}
