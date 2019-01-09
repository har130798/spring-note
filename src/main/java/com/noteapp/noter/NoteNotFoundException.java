package com.noteapp.noter;

class NoteNotFoundException extends RuntimeException {

    NoteNotFoundException (Long id) {
        super("Could not find note " + id);
    }
}

