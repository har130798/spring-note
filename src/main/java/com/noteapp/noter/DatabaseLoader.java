package com.noteapp.noter;

import org.hibernate.boot.model.relational.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component //Marking a class with this annotation makes spring boot app pick it up automatically.
public class DatabaseLoader implements CommandLineRunner{

    private final NotesRepository repository;

    @Autowired // W T F is this?
    public DatabaseLoader(NotesRepository repository) {
        this.repository = repository;
    }

    //this method is invoked with cmd-line args, loads up data
    @Override
    public void run(String...strings) throws Exception{
        this.repository.save(new Note("strings[0]", "strings[1]"));
    }

}