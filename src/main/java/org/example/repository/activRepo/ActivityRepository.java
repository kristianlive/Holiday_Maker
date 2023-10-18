package org.example.repository.activRepo;

import org.example.entity.Activity;

import java.util.List;

public interface ActivityRepository {
    Activity get(Long id);
    void add(Activity activity);
    void update(Activity activity);
    void remove(Activity activity);
    List<Activity> getAllActivity();
}