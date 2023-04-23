package com.lathief.NoteKeep.service;

import com.lathief.NoteKeep.model.entities.UserNote;
import com.lathief.NoteKeep.model.entities.UserNoteKey;
import com.lathief.NoteKeep.model.entities.note.Label;
import com.lathief.NoteKeep.model.entities.note.Note;
import com.lathief.NoteKeep.model.entities.user.User;
import com.lathief.NoteKeep.model.enums.EPermission;
import com.lathief.NoteKeep.repository.UserNoteRepository;
import com.lathief.NoteKeep.repository.note.LabelRepository;
import com.lathief.NoteKeep.repository.note.NoteRepository;
import com.lathief.NoteKeep.service.interfaces.NoteLabelService;
import com.lathief.NoteKeep.service.provider.UserRelatedServiceImpl;
import com.lathief.NoteKeep.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteLabelServiceImpl extends UserRelatedServiceImpl implements NoteLabelService {
    @Autowired
    Response response;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    UserNoteRepository userNoteRepository;
    public Map addLabel(Long noteid, List<String> labels) {
        User owner = getUserByUsername();
        Set<Label> savelabels = new HashSet<>();

        if (noteRepository.existsById(noteid)) {
            Note getNote = noteRepository.findOneById(noteid);
            List<UserNote> userNote = userNoteRepository.findOneNoteById(noteid);
            for (UserNote unote : userNote) {
                if (unote.getNote().getId().equals(noteid)) {
                    if (unote.getPermission().equals(EPermission.OWNER) && unote.getUser().getId().equals(getUserByUsername().getId())){
                        labels.forEach(label -> {
                            if (labelRepository.findOneByName(label) == null) {
                                Label saveLabel = new Label(label);
                                labelRepository.save(saveLabel);
                                savelabels.add(saveLabel);
                            } else {
                                Label getLabel = labelRepository.findOneByName(label);
                                savelabels.add(getLabel);
                            }
                        });
                        getNote.setLabels(savelabels);
                        noteRepository.save(getNote);
                        return response.custom("Note ditambahkan Label", HttpStatus.OK);
                    } else {
                        return response.custom("Unauthorized to set label", HttpStatus.OK);
                    }
                }
            }
        }
        return response.custom("Note Tidak ada", HttpStatus.NOT_FOUND);
    }
}
