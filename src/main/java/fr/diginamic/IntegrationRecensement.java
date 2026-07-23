package fr.diginamic;

import fr.diginamic.dao.DepartementDao;
import fr.diginamic.dao.RegionDao;
import fr.diginamic.dao.VilleDao;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Region;
import fr.diginamic.entities.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Classe exécutable permettant d'intégrer les données du fichier recensement.csv en base de données.
 * Le programme est rejouable : il vérifie l'existence de chaque donnée avant de l'intégrer.
 */
public class IntegrationRecensement {

    public static void main(String[] args) throws IOException, URISyntaxException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("maConfig1");
        EntityManager em = emf.createEntityManager();

        RegionDao regionDao = new RegionDao(em);
        DepartementDao departementDao = new DepartementDao(em);
        VilleDao villeDao = new VilleDao(em);

        Path path = Paths.get(IntegrationRecensement.class.getClassLoader().getResource("recensement.csv").toURI());
        List<String> lignes = Files.readAllLines(path);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        for (int i = 1; i < lignes.size(); i++) {
            String ligne = lignes.get(i);
            String[] tokens = ligne.split(";");
            int codeRegion = Integer.parseInt(tokens[0]);
            String nomRegion = tokens[1];
            String codeDepartement = tokens[2];
            int codeCommune = Integer.parseInt(tokens[5]);
            String nomCommune = tokens[6];
            int population = Integer.parseInt(tokens[7].replace(" ", ""));

            // 1. On cherche si cette région existe déjà en base
            Region region = regionDao.findByCode(codeRegion);

            // 2. Si elle n'existe pas (null), on la crée et on la sauvegarde
            if (region == null) {
                region = new Region();
                region.setCode(codeRegion);
                region.setNom(nomRegion);
                regionDao.save(region);
            }
            // À ce stade, "region" contient TOUJOURS un objet Departement valide :
            // - soit celle qu'on vient de créer
            // - soit celle qui existait déjà (récupérée à l'étape 1)

            Departement departement = departementDao.findByCode(codeDepartement);
            if (departement == null) {
                departement = new Departement();
                departement.setCode(codeDepartement);
                departement.setRegion(region);
                departementDao.save(departement);
            }

            Ville ville = villeDao.findByDepartementAndCode(departement, codeCommune);
            if (ville == null) {
                ville = new Ville();
                ville.setCode(codeCommune);
                ville.setNom(nomCommune);
                ville.setDepartement(departement);
                ville.setPopulation(population);
                villeDao.save(ville);
            }
        }

        tx.commit();
        em.close();
        emf.close();
    }
}