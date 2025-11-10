package wiks.f1_team_dashboard.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import wiks.f1_team_dashboard.entities.car.Car;
import wiks.f1_team_dashboard.repositories.CarRepository;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class CarControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CarRepository carRepository;
    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres");

    @AfterEach
    public void tearDown() {
        carRepository.deleteAll();
    }

    @Test
    void shouldCreateCar() throws Exception {
        mvc.perform(post("/f1/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertEquals(1, carRepository.count());
    }

    @Test
    void shouldReturnBadRequestIfTwoCarsAlreadyExist() throws Exception {
        carRepository.save(new Car(0, null, new ArrayList<>()));
        carRepository.save(new Car(0, null, new ArrayList<>()));
        mvc.perform(post("/f1/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
