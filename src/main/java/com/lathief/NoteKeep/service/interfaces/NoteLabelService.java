package com.lathief.NoteKeep.service.interfaces;

import com.lathief.NoteKeep.model.entities.note.Label;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NoteLabelService {
    Map addLabel(Long noteid, List<String> labels);
}
