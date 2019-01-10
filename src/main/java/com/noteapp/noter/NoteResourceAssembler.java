package com.noteapp.noter;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class NoteResourceAssembler implements ResourceAssembler<Note, Resource<Note>> {
    @Override
    public Resource<Note> toResource(Note note) {
        return new Resource<>(note,
            linkTo(methodOn(NoteController.class).getSingleNote(note.getId())).withSelfRel(),
            linkTo(methodOn(NoteController.class).all()).withRel("note")
        );
    }
}