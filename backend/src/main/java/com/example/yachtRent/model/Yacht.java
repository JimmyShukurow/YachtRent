package com.example.yachtRent.model;

import com.example.yachtRent.entity.YachtEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Yacht {
    private String name;
    private  Long typeId;
    private BigDecimal price;
    private  String crew;
    private  Integer sleepingCapacity;
    private  Integer cruiseCapacity;

    public static Yacht toModel(YachtEntity yachtEntity) {
        var yacht = new Yacht();
        yacht.setName(yachtEntity.getName());
        yacht.setTypeId(yachtEntity.getTypeId());
        yacht.setPrice(yachtEntity.getPrice());
        yacht.setCrew(yachtEntity.getCrew());
        yacht.setSleepingCapacity(yachtEntity.getSleepingCapacity());
        yacht.setCruiseCapacity(yachtEntity.getCruiseCapacity());

        return yacht;
    }

}
