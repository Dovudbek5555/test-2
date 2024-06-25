package com.example.expess24.service;

import com.example.expess24.entity.Contact;
import com.example.expess24.entity.District;
import com.example.expess24.entity.exception.GenericException;
import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.ContactDto;
import com.example.expess24.repository.ContactRepository;
import com.example.expess24.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final DistrictRepository districtRepository;

    public ApiResponse addContact(ContactDto contactDto) {
        District district = districtRepository.findById(contactDto.getDistrictId())
                .orElseThrow(() -> GenericException.builder().message("District not found").statusCode(404).build());

        Contact contact = Contact.builder()
                .district(district)
                .city(contactDto.getCity())
                .street(contactDto.getStreet())
                .additional(contactDto.getAdditional())
                .build();

        contactRepository.save(contact);
        return new ApiResponse("Contact successfully added", true);
    }

    public ContactDto getOneContact(Integer id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("Contact not found").statusCode(400).build());

        return ContactDto.builder()
                .id(contact.getId())
                .districtId(contact.getDistrict().getId())
                .city(contact.getCity())
                .street(contact.getStreet())
                .additional(contact.getAdditional())
                .build();
    }

    public List<ContactDto> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        List<ContactDto> contactDtos = new ArrayList<>();

        for (Contact contact : contacts) {
            ContactDto contactDto = ContactDto.builder()
                    .id(contact.getId())
                    .districtId(contact.getDistrict().getId())
                    .city(contact.getCity())
                    .street(contact.getStreet())
                    .additional(contact.getAdditional())
                    .build();
            contactDtos.add(contactDto);
        }

        return contactDtos;
    }

    public ApiResponse updateContact(ContactDto contactDto) {
        Contact contact = contactRepository.findById(contactDto.getId())
                .orElseThrow(() -> GenericException.builder().message("Contact not found").statusCode(400).build());

        District district = districtRepository.findById(contactDto.getDistrictId())
                .orElseThrow(() -> GenericException.builder().message("District not found").statusCode(400).build());

        contact.setDistrict(district);
        contact.setCity(contactDto.getCity());
        contact.setStreet(contactDto.getStreet());
        contact.setAdditional(contactDto.getAdditional());

        contactRepository.save(contact);
        return new ApiResponse("Contact successfully updated", true);
    }

    public ApiResponse deleteContact(Integer id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().message("Contact not found").statusCode(400).build());

        contactRepository.delete(contact);
        return new ApiResponse("Contact successfully deleted", true);
    }
}
