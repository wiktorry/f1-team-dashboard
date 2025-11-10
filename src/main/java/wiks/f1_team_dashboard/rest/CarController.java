package wiks.f1_team_dashboard.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiks.f1_team_dashboard.entities.car.CarResponse;
import wiks.f1_team_dashboard.services.car.CarService;

@RestController
@RequestMapping("/f1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    public CarResponse createCar() {
        return new CarResponse(carService.createCar());
    }
}
