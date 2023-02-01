package com.onofftaxi.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ads")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer", nullable = false)
    private String customer;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "site_url")
    private String siteUrl;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "scope", nullable = false)
    private String scope;
}
