package pl.maciejowczarczyk.servicemanagement.activity;

import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.user.User;

import java.util.List;

public interface ActivityService {

    List<Activity> findAllByServiceTicket(ServiceTicket serviceTicket);
    List<Activity> findAllByServiceTicketAndUser(ServiceTicket serviceTicket, User user);
    Activity findById(Long id);
    void saveActivity(Activity activity);
    void deleteActivity(Activity activity);

}
