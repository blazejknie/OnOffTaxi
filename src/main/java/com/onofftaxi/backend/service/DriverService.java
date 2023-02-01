package com.onofftaxi.backend.service;

import com.onofftaxi.backend.model.dto.DriverDto;
import com.onofftaxi.backend.repositories.DriverRepository;
import com.onofftaxi.backend.exception.NotFoundException;
import com.onofftaxi.backend.model.Driver;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class DriverService {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public List<DriverDto> getAll() {
        return driverRepository.findAll().stream().map(x-> modelMapper
                .map(x, DriverDto.class)).collect(Collectors.toList());
    }

    public List<DriverDto> getAllSortedByStatus() {
        return driverRepository.findAllByOrderByStatusDesc().stream()
                .map(x-> modelMapper.map(x, DriverDto.class)).collect(Collectors.toList());
    }

    public List<DriverDto> getAllFromDistrict(String district) {
        return driverRepository.findAllByPlace_DistrictOrderByStatusDesc(district).stream()
                .map(x-> modelMapper.map(x, DriverDto.class)).collect(Collectors.toList());
    }

    public Driver add(DriverDto driverDto) {
        Driver driver = modelMapper.map(driverDto, Driver.class);
        driver.setPassword(passwordEncoder.encode(driver.getPassword()));
        return driverRepository.save(driver);
    }

    public DriverDto update(DriverDto driverDto) {
        Driver driver = modelMapper.map(driverDto, Driver.class);
        if (driverRepository.existsById(driverDto.getId())) {
            driverRepository.save(driver);
            return driverDto;
        } else throw new NotFoundException("driver", driver.getId());
    }

    public DriverDto getByEmail(String email) {
        Driver byEmail = driverRepository.findByEmail(email);
        if (byEmail != null) {
            return modelMapper.map(byEmail, DriverDto.class);
        }
        return null;
    }

    public void deleteDriver(DriverDto driverDto) {
        Driver driver = modelMapper.map(driverDto, Driver.class);
        driverRepository.delete(driver);
    }

    public DriverDto getByLogin(String login) {
        Driver byLogin = driverRepository.getByLogin(login);
        if (byLogin!=null){
            return modelMapper.map(byLogin, DriverDto.class);
        }
        return null;
    }

    public DriverDto getByPhoneNumber(String phoneNumber) {
        Driver byPhone = driverRepository.findByPhone(phoneNumber);
        if (byPhone != null) {
            return modelMapper.map(byPhone, DriverDto.class);
        }
        return null;
    }

    public DriverDto getByDisplayedName(String displayedName) {
        Driver byDisplayedName = driverRepository.findByDisplayedName(displayedName);
        if (byDisplayedName != null) {
            return modelMapper.map(byDisplayedName, DriverDto.class);
        }
        return null;
    }

    public void changeUserPassword(DriverDto driver, String password) {
        driver.setPassword(passwordEncoder.encode(password));
        update(driver);
    }

    public Optional<DriverDto> getDriver() {
        return Optional.ofNullable(getByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}
