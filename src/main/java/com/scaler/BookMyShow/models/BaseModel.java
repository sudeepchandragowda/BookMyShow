package com.scaler.BookMyShow.models;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseModel {
    @Id
    private long id;
    private Date createdAt;
    private Date updatedAt;
}
