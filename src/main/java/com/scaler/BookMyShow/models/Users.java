package com.scaler.BookMyShow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Users extends BaseModel{
    private String name;
    private String email;
    private String password;
    @OneToMany
    private List<Ticket> tickets;
}
