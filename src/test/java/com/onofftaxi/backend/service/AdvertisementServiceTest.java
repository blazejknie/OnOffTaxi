package com.onofftaxi.backend.service;

import com.onofftaxi.backend.model.Advertisement;
import com.onofftaxi.backend.repositories.AdvertisementRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdvertisementServiceTest {

    @Mock
    AdvertisementRepository advertisementRepository;

    @Test
    public void shouldGetAd() {
        advertisementRepository.save(new Advertisement());
        Optional<Advertisement> byId = advertisementRepository.findById(1L);
        assertNotNull(byId);
    }
}
