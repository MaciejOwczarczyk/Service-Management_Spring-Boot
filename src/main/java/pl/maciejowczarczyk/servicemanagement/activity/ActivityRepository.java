package pl.maciejowczarczyk.servicemanagement.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.user.User;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findAllByServiceTicket(ServiceTicket serviceTicket);
    List<Activity> findAllByServiceTicketAndUser(ServiceTicket serviceTicket, User user);
    Activity findAllById(Long id);
}
