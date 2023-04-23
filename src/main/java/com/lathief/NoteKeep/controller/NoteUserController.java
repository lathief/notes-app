package com.lathief.NoteKeep.controller;

import com.lathief.NoteKeep.model.payload.request.ShareNoteRequest;
import com.lathief.NoteKeep.service.interfaces.NoteUserService;
import com.lathief.NoteKeep.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Notes Management", description = "APIs for Managing Note")
@RequestMapping("/v1/notes")
public class NoteUserController {
    @Autowired
    NoteUserService noteUserService;
    @Autowired
    Response response;
    @Operation(summary = "Share note to another user with different permission", tags = {"Notes Management"})
    @PostMapping("/{noteid}/share")
    public ResponseEntity<?> shareNote(@PathVariable Long noteid, @Valid @RequestBody List<ShareNoteRequest> shareNoteRequests){
        Map result = new HashMap<>();
        for (ShareNoteRequest shareNoteRequest : shareNoteRequests) {
            result = noteUserService.shareNote(noteid, shareNoteRequest.getEmail(), shareNoteRequest.getPermission());
        }

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Unauthorized", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Get all users who can access this note", tags = {"Notes Management"})
    @GetMapping("/{noteid}/access")
    public ResponseEntity<?> getUserAccess(@PathVariable Long noteid){
        Map result = noteUserService.getUserAccess(noteid);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Unauthorized", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Edit users who can access this note", tags = {"Notes Management"})
    @PutMapping("/{noteid}/editaccess")
    public ResponseEntity<?> editAccess(@PathVariable Long noteid, @Valid @RequestBody List<ShareNoteRequest> shareNoteRequests){
        Map result = new HashMap<>();
        for (ShareNoteRequest shareNoteRequest : shareNoteRequests) {
            result = noteUserService.editUserAccess(noteid, shareNoteRequest.getEmail(), shareNoteRequest.getPermission());
        }
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Unauthorized", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}
