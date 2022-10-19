package com.example.yachtRent.service;

import com.example.yachtRent.entity.YachtEntity;
import com.example.yachtRent.exception.YachtIsMissingException;
import com.example.yachtRent.repository.YachtRepository;
import com.example.yachtRent.request.YachtRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class YachtService {
    private final YachtRepository yachtRepository;

    public YachtService(YachtRepository yachtRepository) {
        this.yachtRepository = yachtRepository;
    }

    public List<YachtEntity> getAllYachts(){
        var list = yachtRepository.findAll();
        return (List)list;
    }

    public YachtEntity storeYacht(YachtRequest yachtRequest) {
        LocalDateTime date = LocalDateTime.now();
        var yacht = new YachtEntity();
        yacht.setName(yachtRequest.getName());
        yacht.setTypeId(yachtRequest.getTypeId());
        yacht.setPrice(yachtRequest.getPrice());
        yacht.setCrew(yachtRequest.getCrew());
        yacht.setSleepingCapacity(yachtRequest.getSleepingCapacity());
        yacht.setCruiseCapacity(yachtRequest.getCruiseCapacity());
        yacht.setCreatedAt(date);
        yachtRepository.save(yacht);
        return yacht;
    }

    public YachtEntity update(Long id, YachtRequest yachtRequest) {
        var yachtEntity = yachtRepository.findById(id);
        if (yachtEntity.isEmpty()) {
            throw new YachtIsMissingException();
        }
        var yacht = yachtEntity.get();
        yacht.setName(yachtRequest.getName());
        yacht.setTypeId(yachtRequest.getTypeId());
        yacht.setPrice(yachtRequest.getPrice());
        yacht.setCrew(yachtRequest.getCrew());
        yacht.setSleepingCapacity(yachtRequest.getSleepingCapacity());
        yacht.setCruiseCapacity(yachtRequest.getCruiseCapacity());
        yachtRepository.save(yacht);

        return yacht;

    }

    public void deleteYacht(Long id) {
        var yachtEntity = yachtRepository.findById(id);
        if (yachtEntity.isEmpty()) {
            throw new YachtIsMissingException();
        }

        yachtRepository.deleteById(id);
    }
}
