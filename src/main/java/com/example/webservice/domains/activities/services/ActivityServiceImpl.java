package com.example.webservice.domains.activities.services;

import com.example.webservice.commons.PageAttr;
import com.example.webservice.domains.activities.models.entities.Activity;
import com.example.webservice.domains.activities.repositories.ActivityRepository;
import com.example.webservice.domains.users.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepo;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepo) {
        this.activityRepo = activityRepo;
    }

    public Activity save(Activity activity) {
        if (activity.getId() == null) { // new activity (user logged in)
            Activity firstActivity = this.findFirst();
            if (firstActivity != null) {
                Long total = firstActivity.getTotalVisitors();
                activity.setTotalVisitors(++total);
                firstActivity.setTotalVisitors(total);
                this.activityRepo.save(firstActivity);
            }
        }
        return this.activityRepo.save(activity);
    }

    @Override
    public Activity findFirst() {
        return this.activityRepo.findFirstBy();
    }

    @Override
    public Activity findLast(User user) {
        return activityRepo.findFirstByUserOrderByIdDesc(user);
    }

    @Override
    public Page<Activity> findByUser(User user, int page, int size) {
        return this.activityRepo.findByUser(user, PageAttr.getPageRequest(page,size));
    }

    @Override
    public Activity findOne(long id) {
        return this.activityRepo.findById(id).orElse(null);
    }

    @Override
    public List<Activity> findAll() {
        return this.activityRepo.findAll();
    }

    @Override
    public void delete(Long id) {
        this.activityRepo.deleteById(id);
    }

}
