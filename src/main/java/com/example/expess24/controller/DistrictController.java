package com.example.expess24.controller;

import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.DistrictDto;
import com.example.expess24.repository.DistrictRepository;
import com.example.expess24.service.DistrictService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/district")
public class DistrictController {

    private final DistrictService districtService;

    @PostMapping
    public HttpEntity<?> addDistrict(@RequestBody DistrictDto districtDto){
        ApiResponse apiResponse = districtService.addDistrict(districtDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneDistrict(@PathVariable Integer id){
        DistrictDto oneDistrict = districtService.getOneDistrict(id);
        return ResponseEntity.ok(oneDistrict);
    }

    @GetMapping("/list")
    public HttpEntity<?> getAllDistricts(){
        List<DistrictDto> allDistricts = districtService.getAllDistricts();
        return ResponseEntity.ok(allDistricts);
    }

    @PutMapping
    public HttpEntity<?> updateDistrict(@RequestBody DistrictDto districtDto){
        ApiResponse apiResponse = districtService.updateDistrict(districtDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteDistrict(@PathVariable Integer id){
        ApiResponse apiResponse = districtService.deleteDistrict(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
