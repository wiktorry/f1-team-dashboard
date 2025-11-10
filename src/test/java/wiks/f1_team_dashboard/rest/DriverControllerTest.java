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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import wiks.f1_team_dashboard.entities.car.Car;
import wiks.f1_team_dashboard.entities.driver.Driver;
import wiks.f1_team_dashboard.entities.driver.DriverAssignCarRequest;
import wiks.f1_team_dashboard.entities.driver.DriverCreateRequest;
import wiks.f1_team_dashboard.entities.driver.DriverStatus;
import wiks.f1_team_dashboard.repositories.CarRepository;
import wiks.f1_team_dashboard.repositories.DriverRepository;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class DriverControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private CarRepository carRepository;
    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    public void tearDown() {
        driverRepository.deleteAll();
    }

    @Test
    void shouldCreateDriver() throws Exception {
        DriverCreateRequest driverCreateRequest =
                new DriverCreateRequest("Lando", "Norris", DriverStatus.ACTIVE);
        mvc.perform(post("/f1/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverCreateRequest)))
                .andExpect(status().isOk());
        Assertions.assertEquals(1, driverRepository.count());
    }

    @Test
    void shouldReturnBadRequestIfTwoActiveDriversAlreadyExist() throws Exception {
        driverRepository.save(new Driver(0, "Oscar", "Piastri", DriverStatus.ACTIVE, null));
        driverRepository.save(new Driver(0, "Robert", "Kubica", DriverStatus.ACTIVE, null));
        DriverCreateRequest driverCreateRequest =
                new DriverCreateRequest("Lando", "Norris", DriverStatus.ACTIVE);
        mvc.perform(post("/f1/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverCreateRequest)))
                .andExpect(status().isBadRequest());
        Assertions.assertEquals(2, driverRepository.count());
    }

    @Test
    void shouldAssignCarToDriver() throws Exception {
        Driver driver = driverRepository.save(new Driver(0, "Oscar",
                "Piastri", DriverStatus.ACTIVE, null));
        Car car = carRepository.save(new Car(0, null, new ArrayList<>()));
        DriverAssignCarRequest request = new DriverAssignCarRequest(car.getId(), driver.getId());
        mvc.perform(post("/f1/drivers/assign-car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        driver = driverRepository.findById(driver.getId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        Assertions.assertEquals(car.getId(), driver.getCar().getId());
    }
}
