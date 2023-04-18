package com.lathief.catatan.service.interfaces;

import com.lathief.catatan.model.entities.note.Label;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface LabelService {
    List<Label> getAllLabels();
    Label getLabelsById(Long id);
    Map insertLabel(Label label);
    Map updateLabel(Long id, Label label);
    Map deleteLabel(Long id);
}