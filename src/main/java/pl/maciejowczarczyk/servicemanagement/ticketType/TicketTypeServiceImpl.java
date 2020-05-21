package pl.maciejowczarczyk.servicemanagement.ticketType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;

    @Override
    public void saveTicketType(TicketType ticketType) {
        ticketTypeRepository.save(ticketType);
    }

    @Override
    public void deleteTicketType(TicketType ticketType) {
        ticketTypeRepository.delete(ticketType);
    }

    @Override
    public TicketType findTicketTypeById(Long id) {
        return ticketTypeRepository.findAllById(id);
    }

    @Override
    public List<TicketType> findAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }
}
