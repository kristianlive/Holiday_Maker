package org.example.services;

import org.example.entity.Activity;
import org.example.repository.activRepo.ActivityRepository;
import java.util.List;
import java.util.Optional;

public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public void findById(Long id) {
        System.out.println(activityRepository.get(id));
    }

    public void addActivity(Activity activity) {
        activityRepository.add(activity);
        System.out.println("Activity added");
    }

    public void updateActivity(Activity activity) {
        Optional<Activity> activityOptional = Optional.ofNullable(activityRepository.get(activity.getId()));
        if (activityOptional.isPresent()) {
            activityRepository.update(activity);
            System.out.println("Activity updated");
        } else {
            System.out.println("Activity not found");
        }
    }

    public void removeActivity(Long id) {
        if (activityRepository.get(id) == null) {
            System.out.println("Activity not found");
            return;
        }
        Activity activity = activityRepository.get(id);
        activityRepository.remove(activity);
        System.out.println("Activity removed");
    }

    public void getAllActivities() {
        var activities = activityRepository.getAllActivity();
        for (var activity : activities) {
            System.out.println(activity);
        }
        System.out.println("All activities listed");
    }
}
