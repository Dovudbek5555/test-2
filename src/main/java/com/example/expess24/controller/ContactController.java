package com.example.expess24.controller;

import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.ContactDto;
import com.example.expess24.service.ContactService;
import com.example.expess24.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public HttpEntity<?> addContact(@RequestBody ContactDto contactDto){
        ApiResponse apiResponse = contactService.addContact(contactDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneDistrict(@PathVariable Integer id){
        ContactDto oneDistrict = contactService.getOneContact(id);
        return ResponseEntity.ok(oneDistrict);
    }

    @GetMapping("/list")
    public HttpEntity<?> getAllDistricts(){
        List<ContactDto> allDistricts = contactService.getAllContacts();
        return ResponseEntity.ok(allDistricts);
    }

    @PutMapping
    public HttpEntity<?> updateDistrict(@RequestBody ContactDto contactDto){
        ApiResponse apiResponse = contactService.updateContact(contactDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteDistrict(@PathVariable Integer id){
        ApiResponse apiResponse = contactService.deleteContact(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
