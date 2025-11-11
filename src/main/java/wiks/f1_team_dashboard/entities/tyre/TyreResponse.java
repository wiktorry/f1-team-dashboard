package wiks.f1_team_dashboard.entities.tyre;

public record TyreResponse(int id, TyrePosition position, TyreCompound compound) {
    public TyreResponse(Tyre tyre) {
        this(tyre.getId(), tyre.getPosition(), tyre.getCompound());
    }
}
