package wiks.f1_team_dashboard.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiks.f1_team_dashboard.entities.driver.DriverAssignCarRequest;
import wiks.f1_team_dashboard.entities.driver.DriverCreateRequest;
import wiks.f1_team_dashboard.entities.driver.DriverResponse;
import wiks.f1_team_dashboard.services.driver.DriverService;

@RestController
@RequestMapping("/f1/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    public DriverResponse createDriver(@RequestBody DriverCreateRequest request) {
        return new DriverResponse(driverService.addDriver(request));
    }

    @PostMapping("/assign-car")
    public DriverResponse assignCarToDriver(@RequestBody DriverAssignCarRequest request) {
        return new DriverResponse(driverService.assignCar(request));
    }
}
