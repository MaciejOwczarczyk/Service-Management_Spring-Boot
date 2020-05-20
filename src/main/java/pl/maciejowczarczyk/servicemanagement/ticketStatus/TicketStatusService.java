package pl.maciejowczarczyk.servicemanagement.ticketStatus;

import java.util.List;

public interface TicketStatusService {

    TicketStatus findTicketStatusByName(String name);
    TicketStatus findTicketStatusById(Long id);
    List<TicketStatus> findAllTicketStatuses();
    void saveTicketStatus(TicketStatus ticketStatus);
    void deleteTicketStatus(TicketStatus ticketStatus);
}
