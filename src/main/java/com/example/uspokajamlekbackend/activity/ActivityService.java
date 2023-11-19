package com.example.uspokajamlekbackend.activity;

import com.example.uspokajamlekbackend.user.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private PatientService patientService;

    public List<ActivityResponse> getUserActivities(Long userId){
        return activityRepository.getActivitiesByPatientActivityId(userId).stream().map(
                activity -> new ActivityResponse(
                        activity.getId(),
                        activity.getName(),
                        activity.getDate(),
                        activity.getMood(),
                        activity.getPatientActivity().getId()
                        )
        ).toList();
    }

    public void addActivity(ActivityRequest activityRequest){
        activityRepository.save(
                Activity.builder()
                        .name(activityRequest.getName())
                        .date(activityRequest.getDate())
                        .mood(activityRequest.getMood())
                        .patientActivity(patientService.getById(activityRequest.getUserId()))
                        .build()
        );
    }
}
