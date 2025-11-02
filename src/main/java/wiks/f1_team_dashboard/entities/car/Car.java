package wiks.f1_team_dashboard.entities.car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wiks.f1_team_dashboard.entities.driver.Driver;
import wiks.f1_team_dashboard.entities.tyre.Tyre;

import java.util.List;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @OneToOne(mappedBy = "car")
    private Driver driver;
    @OneToMany(mappedBy = "car")
    private List<Tyre> tyres;
}
