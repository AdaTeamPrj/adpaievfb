package com.adateam.adpaievfb.service.impl;

import com.adateam.adpaievfb.domain.Conge;
import com.adateam.adpaievfb.domain.Contrat;
import com.adateam.adpaievfb.domain.Employee;
import com.adateam.adpaievfb.domain.FicheDePaie;
import com.adateam.adpaievfb.domain.TauxDImposition;
import com.adateam.adpaievfb.domain.enumeration.Decision;
import com.adateam.adpaievfb.domain.enumeration.TypeJourTravail;
import com.adateam.adpaievfb.repository.CongeRepository;
import com.adateam.adpaievfb.repository.ContratRepository;
import com.adateam.adpaievfb.repository.FicheDePaieRepository;
import com.adateam.adpaievfb.repository.TauxDImpositionRepository;
import com.adateam.adpaievfb.service.FicheDePaieService;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FicheDePaie}.
 */
@Service
@Transactional
public class FicheDePaieServiceImpl implements FicheDePaieService {

    private static final TypeJourTravail Ouvre = null;

    private static final TypeJourTravail Ouvrable = null;

    private final Logger log = LoggerFactory.getLogger(FicheDePaieServiceImpl.class);

    private final FicheDePaieRepository ficheDePaieRepository;
    private final TauxDImpositionRepository tauxDImpositionRepository;
    private final ContratRepository contratRepository;
    private final CongeRepository congeRepository;

    public FicheDePaieServiceImpl(
        FicheDePaieRepository ficheDePaieRepository,
        TauxDImpositionRepository tauxDImpositionRepository,
        ContratRepository contratRepository,
        CongeRepository congeRepository
    ) {
        this.ficheDePaieRepository = ficheDePaieRepository;
        this.tauxDImpositionRepository = tauxDImpositionRepository;
        this.contratRepository = contratRepository;
        this.congeRepository = congeRepository;
    }

    @Override
    public FicheDePaie save(FicheDePaie ficheDePaie) {
        log.debug("Request to save FicheDePaie : {}", ficheDePaie);
        ficheDePaie.setSalaireNet(calculSalaireNet(ficheDePaie));
        Employee employee = ficheDePaie.getContrat().getEmployee();
        //float salaire_net = getSalaireBase(employee);
        ficheDePaie.setSalaireBrut(getSalaireBase(employee));
        return ficheDePaieRepository.save(ficheDePaie);
    }

    @Override
    public FicheDePaie update(FicheDePaie ficheDePaie) {
        log.debug("Request to update FicheDePaie : {}", ficheDePaie);
        return ficheDePaieRepository.save(ficheDePaie);
    }

    @Override
    public Optional<FicheDePaie> partialUpdate(FicheDePaie ficheDePaie) {
        log.debug("Request to partially update FicheDePaie : {}", ficheDePaie);

        return ficheDePaieRepository
            .findById(ficheDePaie.getId())
            .map(existingFicheDePaie -> {
                if (ficheDePaie.getSalaireBrut() != null) {
                    existingFicheDePaie.setSalaireBrut(ficheDePaie.getSalaireBrut());
                }
                if (ficheDePaie.getStartDate() != null) {
                    existingFicheDePaie.setStartDate(ficheDePaie.getStartDate());
                }
                if (ficheDePaie.getEndDate() != null) {
                    existingFicheDePaie.setEndDate(ficheDePaie.getEndDate());
                }
                if (ficheDePaie.getDatePaiement() != null) {
                    existingFicheDePaie.setDatePaiement(ficheDePaie.getDatePaiement());
                }
                if (ficheDePaie.getSalaireNet() != null) {
                    existingFicheDePaie.setSalaireNet(ficheDePaie.getSalaireNet());
                }
                if (ficheDePaie.getMontantNetAvantImpots() != null) {
                    existingFicheDePaie.setMontantNetAvantImpots(ficheDePaie.getMontantNetAvantImpots());
                }
                if (ficheDePaie.getProFees() != null) {
                    existingFicheDePaie.setProFees(ficheDePaie.getProFees());
                }
                if (ficheDePaie.getDeductions() != null) {
                    existingFicheDePaie.setDeductions(ficheDePaie.getDeductions());
                }

                return existingFicheDePaie;
            })
            .map(ficheDePaieRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FicheDePaie> findAll() {
        log.debug("Request to get all FicheDePaies");
        return ficheDePaieRepository.findAll();
    }

    public Page<FicheDePaie> findAllWithEagerRelationships(Pageable pageable) {
        return ficheDePaieRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FicheDePaie> findOne(Long id) {
        log.debug("Request to get FicheDePaie : {}", id);
        return ficheDePaieRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FicheDePaie : {}", id);
        ficheDePaieRepository.deleteById(id);
    }

    public TauxDImposition getTauxImposition(Float salaireNet) {
        List<TauxDImposition> listeTaux = tauxDImpositionRepository.findAll();
        int i = 0;
        //to do check date
        //plante si le min salary sont null
        while (i < listeTaux.size()) {
            if (
                listeTaux.get(i).getMinSalary() <= salaireNet &&
                (listeTaux.get(i).getMaxSalary() == null || listeTaux.get(i).getMaxSalary() > salaireNet)
            ) {
                return (listeTaux.get(i));
            }
            i++;
        }
        return null;
    }

    public float getSalaireBase(Employee employee) {
        Float res = -1f;
        //chercher le salaire de base dans la table contrat
        List<Contrat> ListeContrat = contratRepository.findAll();
        int i = 0;
        while (i < ListeContrat.size()) {
            if (ListeContrat.get(i).getEmployee().getId() == employee.getId()) {
                return (ListeContrat.get(i).getSalaireBase());
            }
            i++;
        }
        return res;
    }

    public float getnbDaysConge(FicheDePaie ficheDePaie, Employee employee) {
        //chercher le salaire de base dans la table contrat
        List<Conge> listeConge = congeRepository.findAll();
        int i = 0;
        float nb_days = 0f;
        while (i < listeConge.size()) {
            Boolean testdate =
                listeConge.get(i).getHoldateStart().isBefore(ficheDePaie.getEndDate()) &&
                listeConge.get(i).getHoldateEnd().isAfter(ficheDePaie.getStartDate());
            if (
                listeConge.get(i).getContrat().getEmployee().getId() == employee.getId() &&
                listeConge.get(i).getDecision() == Decision.Accepte &&
                testdate
            ) {
                //nb_days+=listeConge.get(i).getHoldateEnd(). listeConge.get(i).getHoldateStart();
                nb_days += ChronoUnit.DAYS.between(listeConge.get(i).getHoldateEnd(), listeConge.get(i).getHoldateStart());
            }
            i++;
        }
        return nb_days;
    }

    public float calculConge(FicheDePaie ficheDePaie) {
        Employee employee = ficheDePaie.getContrat().getEmployee();
        float salaireBase = getSalaireBase(employee);
        TypeJourTravail typeJourTravail = ficheDePaie.getContrat().getTypeJourTravail();
        float nb_days = getnbDaysConge(ficheDePaie, employee);
        float conge_pay = -1f;

        if (typeJourTravail == TypeJourTravail.Ouvre) {
            conge_pay = (salaireBase * nb_days) / 22;
            return (conge_pay);
        }

        if (typeJourTravail == TypeJourTravail.Ouvrable) {
            conge_pay = (salaireBase * nb_days) / 26;
            return (conge_pay);
        } else {
            return (conge_pay);
        }
    }

    public float calculSalaireNet(FicheDePaie ficheDePaie) {
        Employee employee = ficheDePaie.getContrat().getEmployee();
        float salaire_net = getSalaireBase(employee);
        float conge_pay = calculConge(ficheDePaie);
        ficheDePaie.setMontantNetAvantImpots(conge_pay);
        //To do transform Salarie de base en salaire brut, set salaire brut
        //To do calcul cotisation
        //To do seek the correct tauxImmposition
        TauxDImposition imposition = getTauxImposition(salaire_net);
        salaire_net = salaire_net - imposition.calculs_imposition(imposition, salaire_net);

        System.out.println("salaire_net=" + salaire_net);
        return (salaire_net);
    }
}
