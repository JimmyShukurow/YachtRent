package com.example.yachtRent.controller;

import com.example.yachtRent.annotation.ApiPrefixController;
import com.example.yachtRent.model.Yacht;
import com.example.yachtRent.request.YachtRequest;
import com.example.yachtRent.service.YachtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@ApiPrefixController
public class YachtController {
    private final YachtService yachtService;

    public YachtController(YachtService yachtService) {
        this.yachtService = yachtService;
    }

    @GetMapping("yachts/all")
    public ResponseEntity<List<Yacht>> getAllYachts() {

        var yachts =  yachtService.getAllYachts();
        List<Yacht> list = new ArrayList<>();
        yachts.forEach(e->list.add(Yacht.toModel(e)));
        return  ResponseEntity.ok(list);
    }

    @PostMapping("yachts/store")
    public ResponseEntity<Yacht> storeYacht(@RequestBody YachtRequest yachtRequest) {
        var yacht = yachtService.storeYacht(yachtRequest);

        return ResponseEntity.ok(Yacht.toModel(yacht));
    }

    @PutMapping("yachts/{yacht_id}")
    public ResponseEntity<Yacht> updateYacht(@PathVariable("yacht_id") Long Id, @RequestBody YachtRequest yachtRequest) {
        var yacht = yachtService.update(Id, yachtRequest);

        return ResponseEntity.ok(Yacht.toModel(yacht));
    }

    @DeleteMapping("yachts/{yacht_id}")
    public ResponseEntity<String> deleteYacht(@PathVariable("yacht_id") Long Id) {
        yachtService.deleteYacht(Id);

        return ResponseEntity.ok("Yacht by id " + Id + " was deleted");
    }

    @PostMapping("yachts/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        var uploadImage = yachtService.uploadImage(file);
        return ResponseEntity.ok(uploadImage);
    }

}
