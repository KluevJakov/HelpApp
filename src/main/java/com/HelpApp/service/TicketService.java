package com.HelpApp.service;

import com.HelpApp.entity.Feedback;
import com.HelpApp.entity.Ticket;
import com.HelpApp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket create(Ticket ticket) {
        ticket.setCreatedDate(new Date());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> list(int status) {
        return ticketRepository.findAll().stream()
                .filter(e -> e.getStatus() == status)
                .sorted(Comparator.comparing(Ticket::getCreatedDate))
                .collect(Collectors.toList());
    }

    public void changeStatus(Ticket ticket) {
        ticket.setChangedDate(new Date());
        ticketRepository.save(ticket);
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll().stream()
                .collect(Collectors.toList());
    }
}
