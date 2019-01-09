package com.noteapp.noter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component //Marking a class with this annotation makes spring boot app pick it up automatically.
public class DatabaseLoader implements CommandLineRunner{

    @Autowired // Why here again?
    NotesRepository repository;

    @Autowired // W T F is this?
    public DatabaseLoader(NotesRepository repository) {
        this.repository = repository;
    }

    //this method is invoked when the application starts.
    @Override
    public void run(String...strings) throws Exception{
        
    }

}