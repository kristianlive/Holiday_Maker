package org.example.repository.addonRepo;

import org.example.entity.Addon;

import java.util.List;

public interface AddonRepository {
        Addon findById(Long id);
        void create(Addon addon);
        void update(Addon addon);
        void remove(Addon addon);
        List<Addon> getAllAddon();
    }

