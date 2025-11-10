package wiks.f1_team_dashboard.entities.driver;

public record DriverResponse(int id, String firstName, String lastName,
                             DriverStatus status, int carId) {

    public DriverResponse(Driver driver) {
        this(driver.getId(), driver.getFirstName(), driver.getLastName(),
                driver.getStatus(), driver.getCar() != null ? driver.getCar().getId() : 0);
    }
}
