package pl.maciejowczarczyk.servicemanagement.ticketStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketStatusServiceImpl implements TicketStatusService {

    private final TicketStatusRepository ticketStatusRepository;

    @Override
    public TicketStatus findTicketStatusByName(String name) {
        return ticketStatusRepository.findAllByName(name);
    }

    @Override
    public TicketStatus findTicketStatusById(Long id) {
        return ticketStatusRepository.findAllById(id);
    }

    @Override
    public List<TicketStatus> findAllTicketStatuses() {
        return ticketStatusRepository.findAll();
    }

    @Override
    public void saveTicketStatus(TicketStatus ticketStatus) {
        ticketStatusRepository.save(ticketStatus);
    }

    @Override
    public void deleteTicketStatus(TicketStatus ticketStatus) {
        ticketStatusRepository.delete(ticketStatus);
    }
}
