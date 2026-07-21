package fr.diginamic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Représente un Département français.
 * Rattaché à une Region (relation ManyToOne).
 * Chaque Ville référence son Departement (navigation possible via Ville.departement,
 * pas de collection inverse déclarée ici).
 * Unicité garantie sur l'attribut code.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private Integer code;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

}
