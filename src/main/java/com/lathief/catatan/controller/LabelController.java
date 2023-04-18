package com.lathief.catatan.controller;

import com.lathief.catatan.model.entities.note.Label;
import com.lathief.catatan.model.entities.note.Note;
import com.lathief.catatan.service.interfaces.LabelService;
import com.lathief.catatan.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/label")
public class LabelController {
    @Autowired
    LabelService labelService;
    @Autowired
    Response response;
    @GetMapping
    public ResponseEntity<Map> getAllLabels(){
        return new ResponseEntity<>(response.success(labelService.getAllLabels()), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getLabelById(@PathVariable Long id){
        Label label = labelService.getLabelsById(id);
        if (label != null){
            return new ResponseEntity<>(response.success(label), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Label tidak ada", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<?> postLabel(@RequestBody Label label){
        return new ResponseEntity<>(labelService.insertLabel(label), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLabel(@PathVariable Long id, @RequestBody Label label){
        Map result = labelService.updateLabel(id, label);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.custom("Label tidak ada", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
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
