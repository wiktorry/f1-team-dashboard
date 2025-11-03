package wiks.f1_team_dashboard.services.driver;

import wiks.f1_team_dashboard.entities.driver.Driver;
import wiks.f1_team_dashboard.entities.driver.DriverCreateRequest;

public interface DriverService {
    Driver addDriver(DriverCreateRequest driverRequest);
}
