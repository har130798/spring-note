package com.noteapp.noter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.*;
import java.net.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final NoteResourceAssembler assembler;

    NoteController (NotesRepository repository, NoteResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/note/all")
    Resources<Resource<Note>> all() {
        
        //Get all notes
        Iterable<Note> iterableNote = repository.findAll();
        
        //Convert Iterable to List
        List<Note> listNote = StreamSupport.stream(iterableNote.spliterator(), false)
            .collect(Collectors.toList());
        
        //List to Stream to List
        List<Resource<Note>> notes = listNote.stream().map(assembler::toResource).collect(Collectors.toList());

        //Return Resources
        return new Resources<>(notes, linkTo(methodOn(NoteController.class).all()).withSelfRel());
    }

    @PostMapping("/note")
    ResponseEntity<?> newNote (@RequestBody Note newNote) {
        
        newNote.setCreateDate(LocalDate.now());
        Resource<Note> resource = assembler.toResource(repository.save(newNote));
        
        URI uri;
        try {
            uri = new URI(resource.getId().expand().getHref());
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        return ResponseEntity
                .created(uri)
                .body(resource);
    }

    @GetMapping("/note")
    Resource<Note> getSingleNote(@RequestParam("id") Long id) {
        Note receivedNote = repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        return assembler.toResource(receivedNote);
    }

    @PutMapping("/note")
    ResponseEntity<?> replaceNote(@RequestBody Note newNote, @RequestParam("id") Long id) {
        Note updatedNote = repository.findById(id)
                .map(note -> {
                    note.setTitle(newNote.getTitle());
                    note.setBody(newNote.getBody());
                    return repository.save(note);
                })
                .orElseGet(() -> {
                    newNote.setNoteId(id);
                    return repository.save(newNote);
                });
        
        Resource<Note> resource = assembler.toResource(updatedNote);
            
        URI uri;
        try {
            uri = new URI(resource.getId().expand().getHref());
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity
                .created(uri)
                .body(resource);
    }

    @DeleteMapping("/note")
    ResponseEntity<?> deleteNote(@RequestParam("id") Long id) {
        repository.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}