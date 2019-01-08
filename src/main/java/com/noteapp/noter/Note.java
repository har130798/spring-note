package com.noteapp.noter;

import java.sql.Date;
import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data //Lombok annotation for boilerplate code
@Entity //JPA annotation denotes that the whole class is for storage in a relational table.

public class Note {

    // Id denotes primary-key and generated value is telling that it's automatically generated.
    private @Id @GeneratedValue Long noteId; 
    private String title;
    private String body;
    private LocalDate createDate;

    private Note() {}

    public Note (String title, String body) {
        this.body = body;
        this.title = title;
        this.createDate = LocalDate.now();
    }

}

