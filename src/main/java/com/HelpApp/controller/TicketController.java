package com.HelpApp.controller;

import com.HelpApp.entity.dto.StatDto;
import com.HelpApp.entity.Ticket;
import com.HelpApp.entity.dto.StatGroupDto;
import com.HelpApp.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @PutMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestBody Ticket ticket) {
        ticketService.changeStatus(ticket);
        return ResponseEntity.ok("{}");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        ticketService.delete(id);
        return ResponseEntity.ok("{}");
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam int status) {
        return ResponseEntity.ok(ticketService.list(status));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam int status) {
        ArrayList<StatDto> stats = new ArrayList<>();
        HashMap<String, Integer> statMap = new HashMap<>();

        if (status == 1) {
            ticketService.findAll().forEach(e -> {
                statMap.merge(extractStatus(e.getStatus()), 1, Integer::sum);
            });
        } else if (status == 2) {
            ticketService.findAll().forEach(e -> {
                statMap.merge(extractKind(e.getKind()), 1, Integer::sum);
            });
        } else {
            List<StatGroupDto> groupStats = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                List<StatDto> group = new ArrayList<>();
                HashMap<String, Integer> statMap2 = new HashMap<>();
                if (i == 0) {
                    ticketService.findAll().forEach(e -> {
                        if (e.getKind() == 0) {
                            statMap2.merge(extractStatus(e.getStatus()), 1, Integer::sum);
                        }
                    });
                    statMap2.forEach((key, value) -> {
                        group.add(new StatDto(key, value.toString()));
                    });
                    groupStats.add(new StatGroupDto("Адресная", group));
                } else if (i == 1) {
                    ticketService.findAll().forEach(e -> {
                        if (e.getKind() == 1) {
                            statMap2.merge(extractStatus(e.getStatus()), 1, Integer::sum);
                        }
                    });
                    statMap2.forEach((key, value) -> {
                        group.add(new StatDto(key, value.toString()));
                    });
                    groupStats.add(new StatGroupDto("Психологическая", group));
                } else if (i == 2) {
                    ticketService.findAll().forEach(e -> {
                        if (e.getKind() == 2) {
                            statMap2.merge(extractStatus(e.getStatus()), 1, Integer::sum);
                        }
                    });
                    statMap2.forEach((key, value) -> {
                        group.add(new StatDto(key, value.toString()));
                    });
                    groupStats.add(new StatGroupDto("Гуманитарная", group));
                } else {
                    ticketService.findAll().forEach(e -> {
                        if (e.getKind() == 3) {
                            statMap2.merge(extractStatus(e.getStatus()), 1, Integer::sum);
                        }
                    });
                    statMap2.forEach((key, value) -> {
                        group.add(new StatDto(key, value.toString()));
                    });
                    groupStats.add(new StatGroupDto("Иная", group));
                }
            }
            return ResponseEntity.ok().body(groupStats);
        }

        statMap.forEach((key, value) -> {
            stats.add(new StatDto(key, value.toString()));
        });
        return ResponseEntity.ok().body(stats);
    }

    private String extractKind(int id) {
        if (id == 0) {
            return "Адресная помощь";
        } else if (id == 1) {
            return "Психологическая помощь";
        } else if (id == 2) {
            return "Гуманитарная помощь";
        } else {
            return "Иная помощь";
        }
    }

    private String extractStatus(int id) {
        if (id == 0) {
            return "Новая заявка";
        } else if (id == 1) {
            return "Заявка в работе";
        } else if (id == 2) {
            return "Завершенная заявка";
        } else {
            return "Отмененная заявка";
        }
    }

}
