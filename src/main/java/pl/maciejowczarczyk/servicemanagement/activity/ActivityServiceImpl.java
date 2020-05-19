package pl.maciejowczarczyk.servicemanagement.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.user.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Override
    public List<Activity> findAllByServiceTicket(ServiceTicket serviceTicket) {
        List<Activity> activities = activityRepository.findAllByServiceTicket(serviceTicket);
        if (activities.size() == 0) {
            return null;
        } else {
            return activities;
        }
    }

    @Override
    public List<Activity> findAllByServiceTicketAndUser(ServiceTicket serviceTicket, User user) {
        return activityRepository.findAllByServiceTicketAndUser(serviceTicket, user);
    }

    @Override
    public Activity findById(Long id) {
        return activityRepository.findAllById(id);
    }

    @Override
    public void saveActivity(Activity activity) {
        if (activity != null) {
            activityRepository.save(activity);
        }
    }

    @Override
    public void deleteActivity(Activity activity) {
        activityRepository.delete(activity);
    }
}
