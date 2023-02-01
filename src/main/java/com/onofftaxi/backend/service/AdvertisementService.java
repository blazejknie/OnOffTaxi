package com.onofftaxi.backend.service;

import com.onofftaxi.backend.model.Advertisement;
//import com.onofftaxi.backend.repositories.AdvertisementRepository;
import com.onofftaxi.backend.repositories.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AdvertisementService {

    @Autowired
    AdvertisementRepository adveretisementRepository;

    public Optional<Advertisement> getRandomAd() {
        Random r = new Random();
        List<Advertisement> all = adveretisementRepository.findAll();
        long l = r.nextInt(all.size());
        return adveretisementRepository.findById(l+1);
    }
}
