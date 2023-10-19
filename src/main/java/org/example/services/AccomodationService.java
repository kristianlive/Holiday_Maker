package org.example.services;

import org.example.entity.Accomodation;
import org.example.repository.accomodationRepo.AccomodationRepository;

import java.util.Optional;

public class AccomodationService {
    private AccomodationRepository accomodationRepository;

    public AccomodationService(AccomodationRepository accomodationRepository) {
        this.accomodationRepository = accomodationRepository;
    }

    public void findById(Long id) {
        System.out.println(accomodationRepository.get(id));
    }

    public void addAccomodation(Accomodation accomodation) {
        accomodationRepository.add(accomodation);
        System.out.println("Accomodation added");
    }

    public void updateAccomodation(Accomodation accomodation) {
        Optional<Accomodation> accomodationOptional = Optional.ofNullable(accomodationRepository.get(accomodation.getId()));
        if (accomodationOptional.isPresent()) {
            accomodationRepository.update(accomodation);
            System.out.println("Accomodation updated");
        } else {
            System.out.println("Accomodation not found");
        }
    }

    public void removeAccomodation(Long id) {
        if (accomodationRepository.get(id) == null) {
            System.out.println("Accomodation not found");
            return;
        }
        Accomodation accomodation = accomodationRepository.get(id);
        accomodationRepository.remove(accomodation);
        System.out.println("Accomodation removed");
    }

    public void getAllAccomodations() {
        var accomodations = accomodationRepository.getAllAccomodations();
        for (var accomodation : accomodations) {
            System.out.println(accomodation);
        }
        System.out.println("All accomodations listed");
    }
}