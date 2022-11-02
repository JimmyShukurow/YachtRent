package com.example.yachtRent.service;

import com.example.yachtRent.entity.YachtEntity;
import com.example.yachtRent.exception.YachtIsMissingException;
import com.example.yachtRent.repository.YachtRepository;
import com.example.yachtRent.request.YachtRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class YachtService {
    private final YachtRepository yachtRepository;
    private final String FOLDER_PATH = "/home/jemsit/Desktop/Java/SpringBoot/YachtRentingWebApp/backend/src/main/resources/files/temp/";

    public YachtService(YachtRepository yachtRepository) {
        this.yachtRepository = yachtRepository;
    }

    public List<YachtEntity> getAllYachts(){
        var list = yachtRepository.findAll();
        return (List)list;
    }

    public YachtEntity storeYacht(YachtRequest yachtRequest) {
        LocalDateTime date = LocalDateTime.now();
        var yacht = YachtEntity.builder()
                .name(yachtRequest.getName())
                .typeId(yachtRequest.getTypeId())
                .price(yachtRequest.getPrice())
                .crew(yachtRequest.getCrew())
                .sleepingCapacity(yachtRequest.getSleepingCapacity())
                .cruiseCapacity(yachtRequest.getCruiseCapacity())
                .createdAt(date)
                .build();
        yachtRepository.save(yacht);
        return yacht;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        log.info(file.getOriginalFilename());
        var entity = yachtRepository.findById(5L).get();
        var filePath = FOLDER_PATH + file.getOriginalFilename();
        entity.setImagePath(filePath);
        yachtRepository.save(entity);
        file.transferTo(new File(filePath));

        return "Image is uploaded";
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
