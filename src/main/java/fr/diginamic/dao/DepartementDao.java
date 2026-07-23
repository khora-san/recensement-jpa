package fr.diginamic.dao;

import fr.diginamic.entities.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * Expose les méthodes métier de Departement
 */
public class DepartementDao {

    private final EntityManager em;

    /**
     *
     * @param em l'EntityManager utilisé
     *           pour les opérations de persistance
     */
    public DepartementDao(EntityManager em) {
        this.em = em;
    }

    /**
     * Recherche un département à partir de son code INSEE.
     *
     * @param code le code du département
     * @return le département correspondant, ou null si aucun département ne correspond
     */
    public Departement findByCode(String code) {
        TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d WHERE d.code = :code", Departement.class);
        query.setParameter("code", code);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    /**
     * Sauvegarde dans la base un département
     *
     * @param departement l'objet departement
     */
    public void save(Departement departement) {
        em.persist(departement);
    }
}