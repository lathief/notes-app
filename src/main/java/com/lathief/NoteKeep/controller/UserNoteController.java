package com.lathief.NoteKeep.controller;

import com.lathief.NoteKeep.model.entities.note.Note;
import com.lathief.NoteKeep.model.payload.request.ShareNoteRequest;
import com.lathief.NoteKeep.service.interfaces.UserNoteService;
import com.lathief.NoteKeep.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Notes Management", description = "APIs for Managing Note")
@RequestMapping("/v1/notes")
public class UserNoteController {
    @Autowired
    UserNoteService userNoteService;
    @Autowired
    Response response;
    @Operation(summary = "Share note to another user with different permission", tags = {"Notes Management"})
    @PostMapping("/{noteid}/share")
    public ResponseEntity<?> updateNote(@PathVariable Long noteid, @Valid @RequestBody List<ShareNoteRequest> shareNoteRequests){
        Map result = new HashMap<>();
        for (ShareNoteRequest shareNoteRequest : shareNoteRequests) {
            result = userNoteService.shareNote(noteid, shareNoteRequest.getUseremail(), shareNoteRequest.getUserrole());
        }

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Unauthorized", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}
