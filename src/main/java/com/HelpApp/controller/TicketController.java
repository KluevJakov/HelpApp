package com.HelpApp.controller;

import com.HelpApp.entity.Ticket;
import com.HelpApp.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.create(ticket));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam int status) {
        return ResponseEntity.ok(ticketService.list(status));
    }
}
