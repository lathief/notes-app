package com.lathief.NoteKeep.controller;

import com.lathief.NoteKeep.model.entities.note.Note;
import com.lathief.NoteKeep.model.payload.response.MessageResponse;
import com.lathief.NoteKeep.service.interfaces.NoteService;
import com.lathief.NoteKeep.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "Notes Management", description = "APIs for Managing Note")
@RequestMapping("/v1/notes")
public class NoteController {
    @Autowired
    NoteService noteService;
    @Autowired
    Response response;
    @GetMapping
    @Operation(summary = "Get all notes owned by user", tags = {"Notes Management"})
    public ResponseEntity<?> getAllNotes(){
        return new ResponseEntity<>(response.success(noteService.getAllNotes()), HttpStatus.OK);
    }
    @Operation(summary = "Get notes based on the id owned by the user", tags = {"Notes Management"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable Long id){
        Note note = noteService.getNoteById(id);
        if (note != null){
            return new ResponseEntity<>(response.success(note), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Note tidak ada", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Create note owned by the user", tags = {"Notes Management"})
    @PostMapping
    public ResponseEntity<?> postNote(@RequestBody Note note){
        return new ResponseEntity<>(noteService.insertNote(note), HttpStatus.CREATED);
    }
    @Operation(summary = "Update note based on the id owned by the user", tags = {"Notes Management"})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody Note note){
        Map result = noteService.updateNote(id, note);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Note tidak ada", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Delete note based on the id owned by the user", tags = {"Notes Management"})
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
