package com.noteapp.noter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.*;
import java.util.stream.Collector;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
class NoteController {

    @Autowired //What is this doing ?
    private final NotesRepository repository;

    NoteController (NotesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/note/all")
    Resources<Resource<Note>> all() {
        Iterable<Note> iterableNote = repository.findAll();
        List<Note> listNote = StreamSupport.stream(iterableNote.spliterator(), false)
            .collect(Collectors.toList());
        List<Resource<Note>> notes = listNote.stream()
        .map(note -> new Resource<>(note, 
        linkTo(methodOn(NoteController.class).getSingleNote(note.getId())).withSelfRel(), 
        linkTo(methodOn(NoteController.class).all()).withRel("note"))).collect(Collectors.toList());

        return new Resources<>(notes,
            linkTo(methodOn(NoteController.class).all()).withSelfRel());
    }

    @PostMapping("/note")
    Note newNote (@RequestBody Note newNote) {
        newNote.setCreateDate(LocalDate.now());
        return repository.save(newNote);
    }

    @GetMapping("/note")
    Resource<Note> getSingleNote(@RequestParam("id") Long id) {
        Note receivedNote = repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        return new Resource<>(receivedNote,
            linkTo(methodOn(NoteController.class).getSingleNote(id)).withSelfRel(),
            linkTo(methodOn(NoteController.class).all()).withRel("note"));
    }

    @PutMapping("/note")
    Note replaceNote(@RequestBody Note newNote, @RequestParam("id") Long id) {
        return repository.findById(id)
                .map(note -> {
                    note.setTitle(newNote.getTitle());
                    note.setBody(newNote.getBody());
                    return repository.save(note);
                })
                .orElseGet(() -> {
                    newNote.setNoteId(id);
                    return repository.save(newNote);
                });
    }

    @DeleteMapping("/note")
    void deleteNote(@RequestParam("id") Long id) {
        repository.deleteById(id);
    }

}