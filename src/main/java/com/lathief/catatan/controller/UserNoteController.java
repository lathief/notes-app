package com.lathief.catatan.controller;

import com.lathief.catatan.model.entities.note.Note;
import com.lathief.catatan.model.payload.request.ShareNoteRequest;
import com.lathief.catatan.service.interfaces.UserNoteService;
import com.lathief.catatan.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
    public ResponseEntity<?> updateNote(@PathVariable Long noteid, @RequestBody ShareNoteRequest shareIds){
        Map result = new HashMap<>();
        for (Map.Entry<String,String> entry : shareIds.getShareIds().entrySet()) {
            result = userNoteService.shareNote(noteid, entry.getKey(), entry.getValue());
        }

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Unauthorized", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}
