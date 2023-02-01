package com.onofftaxi.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drivers")
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class Driver extends User {

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false)
    @Email
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "company", nullable = false)
    private String company;
    @Column(name = "nip", nullable = false)
    private String nip;
    @Column(name = "regon", nullable = false)
    private String regon;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name = "postcode")
    private String postcode;
    @Column(name = "license")
    private String license;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "displayed_name")
    private String displayedName;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "card_payment")
    private Boolean cardPayment;
    @Column(name = "status", columnDefinition = "tinyint")
    @Enumerated
    private Status status;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "driver")
    private Set<ServiceBundle> services;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "driver")
    private Place place;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "driver")
    private Geolocation geolocation;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Driver driver = (Driver) o;
        return firstName.equals(driver.firstName) &&
                lastName.equals(driver.lastName) &&
                email.equals(driver.email) &&
                phone.equals(driver.phone) &&
                nip.equals(driver.nip) &&
                regon.equals(driver.regon) &&
                street.equals(driver.street) &&
                city.equals(driver.city) &&
                postcode.equals(driver.postcode) &&
                license.equals(driver.license);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, email, phone, nip, regon, street, city, postcode, license);
    }


}
