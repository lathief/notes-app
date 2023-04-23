package com.lathief.NoteKeep. controller;

import com.lathief.NoteKeep.model.payload.request.AddLabel;
import com.lathief.NoteKeep.service.interfaces.LabelService;
import com.lathief.NoteKeep.service.interfaces.NoteLabelService;
import com.lathief.NoteKeep.service.interfaces.NoteService;
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
    NoteService noteService;
    @Autowired
    LabelService labelService;

    @Autowired
    NoteLabelService noteLabelService;
    @Operation(summary = "Add a label to note that the user owns", tags = {"Notes Management"})
    @PostMapping("/{noteid}/addLabel")
    public ResponseEntity<?> updateNote(@PathVariable Long noteid, @RequestBody AddLabel labels) {
        Map result = new HashMap<>();
        result = noteLabelService.addLabel(noteid, labels.getLabels());
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Unauthorized", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}
