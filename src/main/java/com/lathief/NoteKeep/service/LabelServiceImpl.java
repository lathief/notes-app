package com.lathief.NoteKeep.service;

import com.lathief.NoteKeep.model.entities.note.Label;
import com.lathief.NoteKeep.repository.note.LabelRepository;
import com.lathief.NoteKeep.service.interfaces.LabelService;
import com.lathief.NoteKeep.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    Response response;
    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    public Label getLabelsById(Long id) {
        if (labelRepository.existsById(id)) {
            return labelRepository.findById(id).get();
        } else {
            return null;
        }
    }

    public Map insertLabel(Label label) {
        labelRepository.save(label);
        return response.custom("Label di save", HttpStatus.CREATED);
    }

    public Map updateLabel(Long id, Label label) {
        if (labelRepository.existsById(id)) {
            labelRepository.updateNote(id, new Date(), label.getName());
            return response.custom("Label di update", HttpStatus.OK);
        } else {
            return null;
        }
    }

    public Map deleteLabel(Long id) {
        if (labelRepository.existsById(id)) {
            labelRepository.deleteById(id);
            return response.custom("label di delete", HttpStatus.OK);
        } else {
            return null;
        }
    }
}
