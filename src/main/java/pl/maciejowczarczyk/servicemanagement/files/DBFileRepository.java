package pl.maciejowczarczyk.servicemanagement.files;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maciejowczarczyk.servicemanagement.planner.Planner;
import pl.maciejowczarczyk.servicemanagement.user.User;
import serviceTicket.ServiceTicket;

import java.util.List;

public interface DBFileRepository extends JpaRepository<DBFile, String> {

    List<DBFile> findAllByServiceTicket(ServiceTicket serviceTicket);

    List<DBFile> findAllByServiceTicket_Planners(Planner planner);

    List<DBFile> findAllByUser(User user);

}
