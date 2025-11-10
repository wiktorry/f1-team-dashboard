package wiks.f1_team_dashboard.entities.car;

public record CarResponse(int id, int driverId) {

    public CarResponse(Car car) {
        this(car.getId(), car.getDriver() != null ? car.getDriver().getId() : 0);
    }
}
