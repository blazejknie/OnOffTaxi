package com.onofftaxi.backend.model.dto;

import com.onofftaxi.backend.model.ServiceBundle;
import com.onofftaxi.backend.model.Status;
import com.vividsolutions.jts.geom.Point;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Set;

@Getter
@Setter
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class DriverDto extends UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private String nip;
    private String regon;
    private String street;
    private String city;
    private String postcode;
    private String license;
    private String displayedName;
    private Boolean cardPayment;
    private String description;
    private String imageUrl;
    private Status status;
    private Set<ServiceBundle> services;
    @Id
    private Long geolocationId;
    private Point geolocationPosition;
    private DriverDto geolocationDriver = this;
    @Id
    private Long placeId;
    private String placeName;
    private String placeDistrict;
    private Point placeLocation;
    private DriverDto placeDriver = this;
}
