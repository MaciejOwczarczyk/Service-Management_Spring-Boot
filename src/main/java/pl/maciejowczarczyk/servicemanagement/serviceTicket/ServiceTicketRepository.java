package pl.maciejowczarczyk.servicemanagement.serviceTicket;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.maciejowczarczyk.servicemanagement.files.DBFile;
import pl.maciejowczarczyk.servicemanagement.planner.Planner;
import pl.maciejowczarczyk.servicemanagement.user.User;

import java.util.List;
import java.util.Set;

public interface ServiceTicketRepository extends JpaRepository<ServiceTicket, Long> {

    List<ServiceTicket> findAllByTicketStatusNameAndUsers(String status, User user);

    Set<ServiceTicket> findAllByTicketStatusName(String name);

    List<ServiceTicket> findAllByTicketTypeName(String name);

    List<ServiceTicket> findAllByTicketStatus(TicketStatus ticketStatus);

    List<ServiceTicket> findAllByTicketType(TicketType ticketType);

    ServiceTicket findAllByPlanners(Planner planner);

    ServiceTicket findAllById(Long id);

    ServiceTicket findAllByDbFileList(List<DBFile> dbFiles);
}
