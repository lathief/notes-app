package com.lathief.NoteKeep.controller;

import com.lathief.NoteKeep.model.entities.note.Label;
import com.lathief.NoteKeep.model.entities.note.Note;
import com.lathief.NoteKeep.service.interfaces.LabelService;
import com.lathief.NoteKeep.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "Label Management", description = "APIs for Managing Label")
@RequestMapping("/v1/label")
public class LabelController {
    @Autowired
    LabelService labelService;
    @Autowired
    Response response;
    @Operation(summary = "Get all labels", tags = {"Label Management"})
    @GetMapping
    public ResponseEntity<Map> getAllLabels(){
        return new ResponseEntity<>(response.success(labelService.getAllLabels()), HttpStatus.OK);
    }
    @Operation(summary = "Get label by id", tags = {"Label Management"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getLabelById(@PathVariable Long id){
        Label label = labelService.getLabelsById(id);
        if (label != null){
            return new ResponseEntity<>(response.success(label), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Label tidak ada", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Create label", tags = {"Label Management"})
    @PostMapping
    public ResponseEntity<?> postLabel(@RequestBody Label label){
        return new ResponseEntity<>(labelService.insertLabel(label), HttpStatus.CREATED);
    }
    @Operation(summary = "Update label by id", tags = {"Label Management"})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLabel(@PathVariable Long id, @RequestBody Label label){
        Map result = labelService.updateLabel(id, label);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Label tidak ada", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Delete label by id", tags = {"Label Management"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLabel(@PathVariable Long id){
        Map result = labelService.deleteLabel(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Label tidak ada", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}
