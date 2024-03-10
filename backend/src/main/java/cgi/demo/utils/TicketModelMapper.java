package cgi.demo.utils;

import cgi.demo.DTO.SeatsProjectionIMPL;
import cgi.demo.entities.Ticket;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TicketModelMapper {

    private final ModelMapper mapper;

    public TicketModelMapper() {
        this.mapper = new ModelMapper();
        this.mapper.createTypeMap(Ticket.class, SeatsProjectionIMPL.class)
                .addMapping(Ticket::getRow, SeatsProjectionIMPL::setRow)
                .addMapping(Ticket::getSeat, SeatsProjectionIMPL::setSeat);
    }

    public SeatsProjectionIMPL mapToSeats(Ticket ticket){
        return this.mapper.map(ticket, SeatsProjectionIMPL.class);
    }
}
