package com.noteapp.noter;

import java.time.LocalDate;
import java.lang.Iterable;

import org.springframework.beans.factory.annotation.Autowired;
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
    Iterable<Note> all() {
        return repository.findAll();
    }

    @PostMapping("/note")
    Note newNote (@RequestBody Note newNote) {
        newNote.setCreateDate(LocalDate.now());
        return repository.save(newNote);
    }

    @GetMapping("/note")
    Note getSingleNote(@RequestParam("id") Long id) {
        return repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
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