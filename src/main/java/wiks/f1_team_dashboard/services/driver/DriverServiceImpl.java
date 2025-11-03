package wiks.f1_team_dashboard.services.driver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiks.f1_team_dashboard.entities.driver.Driver;
import wiks.f1_team_dashboard.entities.driver.DriverCreateRequest;
import wiks.f1_team_dashboard.entities.driver.DriverStatus;
import wiks.f1_team_dashboard.exceptions.BadRequestException;
import wiks.f1_team_dashboard.repositories.DriverRepository;


@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;

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
}
