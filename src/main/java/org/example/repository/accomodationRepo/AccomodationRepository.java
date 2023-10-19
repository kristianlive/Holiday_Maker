package org.example.repository.accomodationRepo;

import org.example.entity.Accomodation;

import java.util.List;

public interface AccomodationRepository {


    Accomodation get(Long id);
    void add(Accomodation accomodation);
    void update(Accomodation accomodation);
    void remove(Accomodation accomodation);

    List<Accomodation> getAllAccomodations();
}
