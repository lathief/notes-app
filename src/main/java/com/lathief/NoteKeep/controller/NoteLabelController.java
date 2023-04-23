package com.lathief.NoteKeep. controller;

import com.lathief.NoteKeep.model.payload.request.inputLabel;
import com.lathief.NoteKeep.service.interfaces.NoteLabelService;
import com.lathief.NoteKeep.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Notes Management", description = "APIs for Managing Note")
@RequestMapping("v1/notes")
public class NoteLabelController {
    @Autowired
    Response response;
    @Autowired
    NoteLabelService noteLabelService;
    @Operation(summary = "Add a label to note that the user owns", tags = {"Notes Management"})
    @PostMapping("/{noteid}/addLabel")
    public ResponseEntity<?> addLabel(@PathVariable Long noteid, @RequestBody inputLabel insertlabels) {
        Map result = new HashMap<>();
        result = noteLabelService.addLabel(noteid, insertlabels.getLabels());
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Unauthorized", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Get a label to note that the user owns", tags = {"Notes Management"})
    @GetMapping("/{noteid}/getLabel")
    public ResponseEntity<?> getLabel(@PathVariable Long noteid) {
        Map result = new HashMap<>();
        result = noteLabelService.getLabel(noteid);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Unauthorized", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}
