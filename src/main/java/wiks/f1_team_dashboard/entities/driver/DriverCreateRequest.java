package wiks.f1_team_dashboard.entities.driver;

public record DriverCreateRequest(String firstName, String lastName, DriverStatus status) {
}

