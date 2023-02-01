package com.onofftaxi.backend.model;

import com.onofftaxi.backend.model.dto.DriverDto;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.testng.Assert.assertEquals;

public class ExamUT {
    ModelMapper mapper = new ModelMapper();

    @Test
    public void checkExamMapping() {
        Place place = new Place();
        place.setName("Kartuzy");
        place.setDistrict("kartuski");
        Driver driver = new Driver("Adam", "Test", "test@wp.pl", "123123123", "ljkljkj", "123123123", "123123123", "Cicha 1", "Kartuzy", "12-123",
                "jkljlk", null, "jljlj", "lkjkljkl", true, Status.OFF, null, place, null);

        DriverDto exam = mapper.map(driver,DriverDto.class);
        assertEquals(driver.getCity(), exam.getCity());
        assertEquals(driver.getCompany(), exam.getCompany());
        assertEquals(driver.getLogin(), exam.getLogin());
        assertEquals(driver.getPlace().getName(), exam.getPlaceName());


        DriverDto updateDto = mapper.map(driver, DriverDto.class);
        assertEquals(driver.getCity(), updateDto.getCity());
        assertEquals(driver.getCompany(), updateDto.getCompany());

    }
}
