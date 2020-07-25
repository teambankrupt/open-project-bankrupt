package com.example.coreweb.domains.activities.services;

import com.example.coreweb.utils.PageAttr;
import com.example.coreweb.domains.activities.models.entities.Activity;
import com.example.coreweb.domains.activities.repositories.ActivityRepository;
import com.example.auth.entities.User;
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
