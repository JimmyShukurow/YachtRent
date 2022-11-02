package com.example.yachtRent.entity;

// 1) yacht name
// 2) yacht type
// 3) Price
// 4) Crew
// 5) Sleepiing Capacity
// 6) cruise capacity

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "yachts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YachtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String name;
    private  Long typeId;
    private  Long creatorId;
    private  BigDecimal price;
    private  String crew;
    private  String description;
    private  Integer sleepingCapacity;
    private  Integer cruiseCapacity;
    private  LocalDateTime createdAt;
    private  LocalDateTime deletedAt;
    private  String imagePath;

}
