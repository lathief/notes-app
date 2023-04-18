package com.lathief.catatan.controller;

import com.lathief.catatan.model.entities.note.Note;
import com.lathief.catatan.model.payload.response.MessageResponse;
import com.lathief.catatan.service.interfaces.NoteService;
import com.lathief.catatan.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/notes")
public class NoteController {
    @Autowired
    NoteService noteService;
    @Autowired
    Response response;
    @GetMapping
    public ResponseEntity<?> getAllNotes(){
        return new ResponseEntity<>(response.success(noteService.getAllNotes()), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable Long id){
        Note note = noteService.getNoteById(id);
        if (note != null){
            return new ResponseEntity<>(response.success(note), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Note tidak ada", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<?> postNote(@RequestBody Note note){
        return new ResponseEntity<>(noteService.insertNote(note), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody Note note){
        Map result = noteService.updateNote(id, note);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Note tidak ada", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id){
        Map result = noteService.deleteNote(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Note tidak ada", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

}
