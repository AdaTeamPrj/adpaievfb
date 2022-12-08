package com.adateam.adpaievfb.service.impl;

import com.adateam.adpaievfb.domain.Conge;
import com.adateam.adpaievfb.domain.Contrat;
import com.adateam.adpaievfb.domain.Cotisation;
import com.adateam.adpaievfb.domain.Employee;
import com.adateam.adpaievfb.domain.FicheDePaie;
import com.adateam.adpaievfb.domain.HeureSup;
import com.adateam.adpaievfb.domain.HeureSup;
import com.adateam.adpaievfb.domain.TauxDImposition;
import com.adateam.adpaievfb.domain.enumeration.Decision;
import com.adateam.adpaievfb.domain.enumeration.TypeConge;
import com.adateam.adpaievfb.domain.enumeration.TypeJourTravail;
import com.adateam.adpaievfb.repository.CongeRepository;
import com.adateam.adpaievfb.repository.ContratRepository;
import com.adateam.adpaievfb.repository.CotisationRepository;
import com.adateam.adpaievfb.repository.FicheDePaieRepository;
import com.adateam.adpaievfb.repository.FicheDePaieRepository;
import com.adateam.adpaievfb.repository.HeureSupRepository;
import com.adateam.adpaievfb.repository.HeureSupRepository;
import com.adateam.adpaievfb.repository.TauxDImpositionRepository;
import com.adateam.adpaievfb.service.FicheDePaieService;
import java.time.LocalDate;
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

    private final Logger log = LoggerFactory.getLogger(FicheDePaieServiceImpl.class);

    private final FicheDePaieRepository ficheDePaieRepository;

    // public FicheDePaieServiceImpl(FicheDePaieRepository ficheDePaieRepository) {
    //    this.ficheDePaieRepository = ficheDePaieRepository;

    private final TauxDImpositionRepository tauxDImpositionRepository;
    private final ContratRepository contratRepository;
    private final CongeRepository congeRepository;
    private final HeureSupRepository heureSupRepository;

    private final CotisationRepository cotisationRepository;

    public FicheDePaieServiceImpl(
        FicheDePaieRepository ficheDePaieRepository,
        TauxDImpositionRepository tauxDImpositionRepository,
        ContratRepository contratRepository,
        CongeRepository congeRepository,
        CotisationRepository cotisationRepository,
        HeureSupRepository heureSupRepository
    ) {
        this.ficheDePaieRepository = ficheDePaieRepository;
        this.tauxDImpositionRepository = tauxDImpositionRepository;
        this.contratRepository = contratRepository;
        this.congeRepository = congeRepository;
        this.cotisationRepository = cotisationRepository;
        this.heureSupRepository = heureSupRepository;
    }

    @Override
    public FicheDePaie save(FicheDePaie ficheDePaie) {
        log.debug("Request to save FicheDePaie : {}", ficheDePaie);

        ficheDePaie.setSalaireBrut(
            getSalaireBase(ficheDePaie.getContrat().getEmployee()) +
            calculConge(ficheDePaie) +
            getHeureSup(ficheDePaie) -
            calculConge(ficheDePaie)
        ); //modifier set to salaire brut quan on aura la fonction
        ficheDePaie.setMontantNetAvantImpots(getMontantNetAvantImpots(ficheDePaie.getSalaireBrut()));
        ficheDePaie.setSalaireNet(calculSalaireNet(ficheDePaie));
        ficheDePaie.setEmployeur(ficheDePaie.getContrat().getEmployeur());
        //float salaire_net = getSalaireBase(employee);
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

    public float getHeureSup(FicheDePaie ficheDePaie) {
        //les HS sont calculées mensuellement
        List<HeureSup> listeHeureSup = heureSupRepository.findAll();
        int i = 0;
        float montantHeureSup = 0f;
        Employee employee = ficheDePaie.getContrat().getEmployee();
        float nbHeure = 0f;
        while (i < listeHeureSup.size()) {
            Boolean testdate =
                (listeHeureSup.get(i).getDate().compareTo(ficheDePaie.getEndDate()) <= 0) &&
                (listeHeureSup.get(i).getDate().compareTo(ficheDePaie.getStartDate()) >= 0);

            if (listeHeureSup.get(i).getEmployee().getId() == employee.getId() && testdate) {
                nbHeure = nbHeure + listeHeureSup.get(i).getNbHeure();
            }
            i++;
        }

        float salaireBase = getSalaireBase(employee);
        if (nbHeure <= 30) {
            montantHeureSup = nbHeure * ((salaireBase / 151.67f) * 1.25f);
            return (montantHeureSup);
        } else {
            montantHeureSup = 30 * ((salaireBase / 151.67f) * 1.25f);
            montantHeureSup += (nbHeure - 30) * ((salaireBase / 151.67f) * 1.50f);
            return (montantHeureSup);
        }
    }

    public float getMontantNetAvantImpots(Float salaireBrut) {
        //chercher les cotisations dans la table cotisations
        List<Cotisation> ListeCotisation = cotisationRepository.findAll();
        int i = 0;
        float salaireNetAvantImpot = salaireBrut;
        while (i < ListeCotisation.size()) {
            salaireNetAvantImpot = salaireNetAvantImpot - ListeCotisation.get(i).getTaux() * salaireBrut / 100;
            i++;
        }
        return salaireNetAvantImpot;
    }

    public float getnbDaysConge(FicheDePaie ficheDePaie, Employee employee, TypeConge typeConge) {
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
                testdate &&
                listeConge.get(i).getTypeConge() == typeConge
            ) {
                LocalDate dateDebut = listeConge.get(i).getHoldateStart();
                LocalDate dateDeFin = ficheDePaie.getEndDate();
                if (listeConge.get(i).getHoldateStart().isBefore(ficheDePaie.getStartDate()));

                {
                    dateDebut = ficheDePaie.getStartDate();
                }
                if (listeConge.get(i).getHoldateEnd().isBefore(ficheDePaie.getEndDate()));

                {
                    dateDeFin = listeConge.get(i).getHoldateEnd();
                }
                //nb_days+=listeConge.get(i).getHoldateEnd(). listeConge.get(i).getHoldateStart();
                nb_days += ChronoUnit.DAYS.between(listeConge.get(i).getHoldateStart(), listeConge.get(i).getHoldateEnd());
            }
            i++;
        }
        return nb_days;
    }

    public float calculConge(FicheDePaie ficheDePaie) {
        Employee employee = ficheDePaie.getContrat().getEmployee();
        float salaireBase = getSalaireBase(employee);
        TypeJourTravail typeJourTravail = ficheDePaie.getContrat().getTypeJourTravail();
        float nb_days = getnbDaysConge(ficheDePaie, employee, TypeConge.CongePaye);
        nb_days += getnbDaysConge(ficheDePaie, employee, TypeConge.RTT);
        ficheDePaie.setProFees(salaireBase);
        float conge_pay = 0f;

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

    public float deductionConge(FicheDePaie ficheDePaie) {
        Employee employee = ficheDePaie.getContrat().getEmployee();
        float salaireBase = getSalaireBase(employee);
        TypeJourTravail typeJourTravail = ficheDePaie.getContrat().getTypeJourTravail();
        float nb_days = getnbDaysConge(ficheDePaie, employee, TypeConge.CongePaye);
        nb_days += getnbDaysConge(ficheDePaie, employee, TypeConge.RTT);
        nb_days += getnbDaysConge(ficheDePaie, employee, TypeConge.CongeSansSolde);
        ficheDePaie.setProFees(salaireBase);
        float conge_deduc = 0f;

        if (typeJourTravail == TypeJourTravail.Ouvre) {
            conge_deduc = (salaireBase * nb_days) / 22;
            return (conge_deduc);
        }

        if (typeJourTravail == TypeJourTravail.Ouvrable) {
            conge_deduc = (salaireBase * nb_days) / 26;
            return (conge_deduc);
        } else {
            return (conge_deduc);
        }
    }

    public float calculSalaireNet(FicheDePaie ficheDePaie) {
        Employee employee = ficheDePaie.getContrat().getEmployee();

        float salaire_net = ficheDePaie.getMontantNetAvantImpots();

        //To do transform Salarie de base en salaire brut, set salaire brut
        //To do calcul cotisation
        //To do seek the correct tauxImmposition
        //salaire_net = getMontantNetAvantImpots(salaire_net);
        if (ficheDePaie.getContrat().getEmployee().getTauxImposition() != null) {
            salaire_net = salaire_net - ficheDePaie.getContrat().getEmployee().getTauxImposition() * salaire_net / 100;
            return (salaire_net);
        }
        TauxDImposition imposition = getTauxImposition(salaire_net);
        //   salaire_net = salaire_net - imposition.calculs_imposition(imposition, salaire_net);
        return (salaire_net);
    }
}
