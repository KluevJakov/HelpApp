package com.HelpApp.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@EqualsAndHashCode
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /* данные из заявки */
    private String name;
    private String surname;
    private String phone;
    private String text;

    private String comment; //комментарий волонтёра
    private int kind; //вид помощи 0 - адресная, 1 - психологическая, 2 - гуманитарная, 3 - иная
    private int status; //статус заявки 0 - новая, 1 - в работе, 2 - завершенная, 3 -отмененная
    private Date createdDate;

    private Long changedUserId; //пользователь, меняющий карточку
    private Date changedDate; //последнее время изменение карточки

}