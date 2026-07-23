package fr.diginamic.dao;

import fr.diginamic.entities.Region;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * Expose les méthodes métier de Region
 */
public class RegionDao {

    private final EntityManager em;

    /**
     *
     * @param em l'EntityManager utilisé
     *           pour les opérations de persistance
     */
    public RegionDao(EntityManager em) {
        this.em = em;
    }

    /**
     * Recherche une région à partir de son code INSEE.
     *
     * @param code le code de la région
     * @return la région correspondante, ou null si aucune région ne correspond
     */
    public Region findByCode(Integer code) {
        TypedQuery<Region> query = em.createQuery("SELECT r FROM Region r WHERE r.code = :code", Region.class);
        query.setParameter("code", code);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    /**
     * Recherche une région à partir de son nom.
     *
     * @param nom le nom de la région
     * @return la région correspondante, ou null si aucune région ne correspond
     */
    public Region findByNom(String nom) {
        TypedQuery<Region> query = em.createQuery("SELECT r FROM Region r WHERE r.nom = :nom", Region.class);
        query.setParameter("nom", nom);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    /**
     * Sauvegarde dans la base une région
     *
     * @param region l'objet region
     */
    public void save(Region region) {
        em.persist(region);
    }
}