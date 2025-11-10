package wiks.f1_team_dashboard.entities.tyre;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wiks.f1_team_dashboard.entities.car.Car;

import java.time.LocalDateTime;

@Entity
@Table(name = "tyres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tyre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private TyrePosition position;
    @Enumerated(EnumType.STRING)
    @Column(name = "compound")
    private TyreCompound compound;
    @Column(name = "temperature")
    private double temperature;
    @Column(name = "pressure")
    private double pressure;
    @Column(name = "wear")
    private double wear;
    @Column(name = "is_damaged")
    private boolean isDamaged;
    @Column(name = "is_now_used")
    private boolean isNowUsed;
    @Column(name = "round_id")
    private int roundId;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
