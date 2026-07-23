package fr.diginamic.dao;

import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Expose les méthodes métier de Ville
 */
public class VilleDao {
    private final EntityManager em;

    /**
     *
     * @param em l'EntityManager utilisé
     *           pour les opérations de persistance
     */
    public VilleDao(EntityManager em) {
        this.em = em;
    }

    /**
     * Recherche une ville par son département et son code ville
     *
     * @param departement l'objet departement
     * @param code        le code de la ville (unique au sein de chaque dpt)
     * @return la ville correspondante, ou null si aucune ville ne correspond
     */
    public Ville findByDepartementAndCode(Departement departement, Integer code) {
        TypedQuery<Ville> query = em.createQuery(
                "SELECT v FROM Ville v WHERE v.departement = :departement AND v.code = :code", Ville.class);
        query.setParameter("departement", departement);
        query.setParameter("code", code);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    /**
     * Sauvegarde dans la base une ville
     *
     * @param ville l'objet ville
     */
    public void save(Ville ville) {
        em.persist(ville);
    }

    /**
     * Affiche les villes par ordre décroissant de leur population
     *
     * @return
     */
    public List<Ville> sortByPopulationDesc() {
        TypedQuery<Ville> query = em.createQuery(
                "SELECT v FROM Ville v ORDER BY v.population DESC", Ville.class);
        return query.getResultList();
    }

}
