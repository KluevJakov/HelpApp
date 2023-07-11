package com.HelpApp.service;

import com.HelpApp.entity.Feedback;
import com.HelpApp.entity.Ticket;
import com.HelpApp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> list(int status) {
        return ticketRepository.findAll().stream()
                .filter(e -> e.getStatus() == status)
                .collect(Collectors.toList());
    }
}
