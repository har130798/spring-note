package com.noteapp.noter;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data //Lombok annotation for boilerplate code
@Entity //JPA annotation denotes that the whole class is for storage in a relational table.

public class Note implements Serializable{

    // Id denotes primary-key and generated value is telling that it's automatically generated.
    private @Id @GeneratedValue Long noteId; 

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "createdate")
    private LocalDate createDate;

    private Note() {} //is this necessary?

    public Note (String title, String body) {
        this.body = body;
        this.title = title;
        this.createDate = LocalDate.now();
    }

    public Long getId() {
        return this.noteId;
    }

}

