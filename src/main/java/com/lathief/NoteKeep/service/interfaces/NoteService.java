package com.lathief.NoteKeep.service.interfaces;

import com.lathief.NoteKeep.model.entities.note.Note;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NoteService {
    List<Note> getAllNotes();
    Note getNoteById(Long id);
    Map insertNote(Note note);
    Map updateNote(Long id, Note note);
    Map deleteNote(Long id);
}
