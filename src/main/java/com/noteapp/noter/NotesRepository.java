package com.noteapp.noter;

import org.springframework.data.repository.CrudRepository;

public interface NotesRepository extends CrudRepository<Note, Long> {

}

/*
What's this for? 
Why should I write this?
Guide says:
The repository extends Spring Data Commons' CrudRepository and plugs in the type of the domain object and its primary key
That is all that is needed! In fact, you don’t even have to annotate this if it’s top-level and visible. If you use your IDE and open up CrudRepository, you’ll find a fist full of pre-built methods already defined.
Also:
You can define your own repository if you wish. Spring Data REST supports that as well.
*/