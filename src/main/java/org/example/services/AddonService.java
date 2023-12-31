package org.example.services;


import org.example.entity.CustomTrip;
import org.example.entity.Addon;
import org.example.repository.addonRepo.AddonRepository;

import java.util.List;
import java.util.Optional;

public class AddonService {
    private final AddonRepository addonRepository;
    private List<CustomTrip> customTrips;

    public AddonService(AddonRepository addonRepository) {
        this.addonRepository = addonRepository;
    }


    public Addon findById(Long id) {
        /*System.out.println(addonRepository.findById(id));*/
        return addonRepository.findById(id);
    }

    public void addAddon(Addon Addon) {
        addonRepository.create(Addon);
        System.out.println("Addon added");
    }

    public void updateAddon(Addon Addon) {
        Optional<Addon> AddonOptional = Optional.ofNullable(addonRepository.findById( Addon.getId()));
        if (AddonOptional.isPresent()) {
            addonRepository.update(Addon);

            System.out.println("Addon updated");
        } else {
            System.out.println("Addon not found");
        }

    }

    public void removeAddon(Long id) {
        if (addonRepository.findById(id) == null) {
            System.out.println("Addon not found");
            return;
        }
        Addon addon = addonRepository.findById(id);
        addonRepository.remove(addon);
        System.out.println("Addon removed");
    }

    public void getAllAddons() {
       var addons = addonRepository.getAllAddon();
        for (Addon addon: addons) {
            System.out.println(addon);
        }

    }

}
