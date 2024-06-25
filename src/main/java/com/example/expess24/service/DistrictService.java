package com.example.expess24.service;

import com.example.expess24.entity.District;
import com.example.expess24.entity.Region;
import com.example.expess24.entity.exception.GenericException;
import com.example.expess24.payload.ApiResponse;
import com.example.expess24.payload.DistrictDto;
import com.example.expess24.repository.DistrictRepository;
import com.example.expess24.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;

    public ApiResponse addDistrict(DistrictDto districtDto){
        boolean b = districtRepository.existsByNameAndRegionId(districtDto.getName(), districtDto.getRegionId());
        if (!b){
            Region region = regionRepository.findById(districtDto.getRegionId()).orElseThrow(() -> GenericException.builder().message("Region not found").statusCode(404).build());
            District district = District.builder()
                    .name(districtDto.getName())
                    .region(region)
                    .build();
            districtRepository.save(district);
            return new ApiResponse("District succesfully added", true);
        }
        return new ApiResponse("This district already exist", false);
    }

    public DistrictDto getOneDistrict(Integer id){
        District district = districtRepository.findById(id).orElseThrow(() -> GenericException.builder().message("District not found").statusCode(400).build());
        DistrictDto districtDto = DistrictDto.builder()
                .id(district.getId())
                .name(district.getName())
                .regionId(district.getRegion().getId())
                .build();
        return districtDto;
    }

    public List<DistrictDto> getAllDistricts(){
        List<District> districts = districtRepository.findAll();
        List<DistrictDto> districtDtos = new ArrayList<>();
        for (District district : districts) {
            DistrictDto districtDto = DistrictDto.builder()
                    .id(district.getId())
                    .name(district.getName())
                    .regionId(district.getRegion().getId())
                    .build();
            districtDtos.add(districtDto);
        }
        return districtDtos;
    }

    public ApiResponse updateDistrict(DistrictDto districtDto){
        District district = districtRepository.findById(districtDto.getId()).orElseThrow(() -> GenericException.builder().message("District not found").statusCode(400).build());
        Region region = regionRepository.findById(districtDto.getRegionId()).orElseThrow(() -> GenericException.builder().message("Region not found").statusCode(400).build());
        boolean b = districtRepository.existsByNameAndRegionId(districtDto.getName(), districtDto.getRegionId());
        if (!b){
            district.setName(district.getName());
            district.setRegion(region);
            districtRepository.save(district);
            return new ApiResponse("District succesfully updated", true);
        }
        return new ApiResponse("This district in this region already exist", false);
    }

    public ApiResponse deleteDistrict(Integer id){
        District district = districtRepository.findById(id).orElseThrow(() -> GenericException.builder().message("District not found").statusCode(400).build());
        districtRepository.delete(district);
        return new ApiResponse("District succesfully deleted", true);
    }

}
