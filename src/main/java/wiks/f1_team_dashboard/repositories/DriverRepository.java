package wiks.f1_team_dashboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wiks.f1_team_dashboard.entities.driver.Driver;
import wiks.f1_team_dashboard.entities.driver.DriverStatus;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    int countByStatus(DriverStatus status);
}
