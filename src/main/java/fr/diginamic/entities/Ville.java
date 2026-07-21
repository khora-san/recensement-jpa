package fr.diginamic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Représente une Ville française.
 * Rattaché à un Département (relation ManyToOne).
 * Unicité garantie sur le couple d'attributs {"departement_id", "code"})
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"departement_id", "code"}))
public class Ville {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer code;
    private String nom;
    private int population;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

}
