package wiks.f1_team_dashboard.exceptions;

public record ExceptionResponse(String message, int statusCode, long timeStamp) {
}