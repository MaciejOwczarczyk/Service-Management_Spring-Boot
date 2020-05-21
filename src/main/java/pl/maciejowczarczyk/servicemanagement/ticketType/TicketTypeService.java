package pl.maciejowczarczyk.servicemanagement.ticketType;

import java.util.List;

public interface TicketTypeService {

    void saveTicketType(TicketType ticketType);
    void deleteTicketType(TicketType ticketType);
    TicketType findTicketTypeById(Long id);
    List<TicketType> findAllTicketTypes();

}
