package com.example.springboot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;


@Entity // This tells Hibernate to make a table out of this class
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String message;

    private Timestamp subsribeAt;

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public Timestamp getCreatedAt() {

        return subsribeAt;
    }

    public void setCreatedAt(Timestamp timestamp) {

        this.subsribeAt = timestamp;
    }

    public String toString() {

        return this.message + "  " + this.subsribeAt.toString();
    }
}

