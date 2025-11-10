package wiks.f1_team_dashboard.services.driver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiks.f1_team_dashboard.entities.car.Car;
import wiks.f1_team_dashboard.entities.driver.Driver;
import wiks.f1_team_dashboard.entities.driver.DriverAssignCarRequest;
import wiks.f1_team_dashboard.entities.driver.DriverCreateRequest;
import wiks.f1_team_dashboard.entities.driver.DriverStatus;
import wiks.f1_team_dashboard.exceptions.BadRequestException;
import wiks.f1_team_dashboard.exceptions.NotFoundException;
import wiks.f1_team_dashboard.repositories.CarRepository;
import wiks.f1_team_dashboard.repositories.DriverRepository;


@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    @Override
    public Driver addDriver(DriverCreateRequest driverRequest) {
        int activeDrivers = driverRepository.countByStatus(DriverStatus.ACTIVE);
        if (activeDrivers == 2 && driverRequest.status() == DriverStatus.ACTIVE) {
            throw new BadRequestException("Team already has two active drivers.");
        }
        Driver driver = new Driver(
                0,
                driverRequest.firstName(),
                driverRequest.lastName(),
                driverRequest.status(),
                null
        );
        return driverRepository.save(driver);
    }

    @Override
    public Driver assignCar(DriverAssignCarRequest driverAssignCarRequest) {
        Driver driver = driverRepository.findById(driverAssignCarRequest.driverId())
                .orElseThrow(() -> new NotFoundException("Driver not found"));
        Car car = carRepository.findById(driverAssignCarRequest.carId())
                .orElseThrow(() -> new NotFoundException("Car not found"));
        driver.setCar(car);
        car.setDriver(driver);
        return driverRepository.save(driver);
    }
}
