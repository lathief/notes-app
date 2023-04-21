package com.lathief.catatan.service.interfaces;

import com.lathief.catatan.model.entities.note.Label;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NoteLabelService {
    Map addLabel(Long noteid, List<String> labels);
}
