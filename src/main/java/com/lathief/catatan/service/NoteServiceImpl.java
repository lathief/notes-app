package com.lathief.catatan.service;

import com.lathief.catatan.model.entities.UserNote;
import com.lathief.catatan.model.entities.UserNoteKey;
import com.lathief.catatan.model.entities.note.Label;
import com.lathief.catatan.model.entities.note.Note;
import com.lathief.catatan.model.entities.user.User;
import com.lathief.catatan.model.enums.EPermission;
import com.lathief.catatan.model.enums.ERole;
import com.lathief.catatan.repository.UserNoteRepository;
import com.lathief.catatan.repository.note.LabelRepository;
import com.lathief.catatan.repository.note.NoteRepository;
import com.lathief.catatan.service.interfaces.NoteService;
import com.lathief.catatan.service.provider.UserRelatedServiceImpl;
import com.lathief.catatan.utils.Response;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteServiceImpl extends UserRelatedServiceImpl implements NoteService{
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    UserNoteRepository userNoteRepository;
    @Autowired
    Response response;
    public List<Note> getAllNotes() {
        User owner = getUserByUsername();
        List<Note> notes = new ArrayList<>();
        List<UserNote> userNotes = userNoteRepository.findOneUserById(owner.getId());
        for (UserNote unote : userNotes) {
            notes.add(unote.getNote());
        }
        return notes;
    }

    public Note getNoteById(Long id) {
        User owner = getUserByUsername();
        List<Note> notes = new ArrayList<>();
        List<UserNote> userNotes = userNoteRepository.findOneUserById(owner.getId());
        for (UserNote unote : userNotes) {
            if (unote.getNote().getId().equals(id)) {
                return unote.getNote();
            }
        }
        return null;
    }

    public Map insertNote(Note note) {
        Label label = labelRepository.findOneByName("unlabeled");
        Set<Label> labels = new HashSet<>();
        labels.add(label);
        note.setLabels(labels);
        UserNoteKey key = new UserNoteKey();
        key.setNoteId(note.getId());
        key.setUserId(getUserByUsername().getId());
        UserNote userNote = new UserNote(note, getUserByUsername(), EPermission.OWNER);
        noteRepository.save(note);
        userNoteRepository.save(userNote);
        return response.custom("Note di save", HttpStatus.CREATED);
    }

    public Map updateNote(Long id, Note note) {
        User owner = getUserByUsername();
        List<Note> notes = new ArrayList<>();
        List<UserNote> userNotes = userNoteRepository.findOneUserById(owner.getId());
        for (UserNote unote : userNotes) {
            if (unote.getNote().getId().equals(id)) {
                if (unote.getPermission().equals(EPermission.OWNER) || unote.getPermission().equals(EPermission.READ_WRITE)){
                    noteRepository.updateNote(id, note.getTitle(), note.getContent(), new Date());
                    return response.custom("Note di update", HttpStatus.OK);
                } else {
                    return response.custom("Unauthorized to update note", HttpStatus.OK);
                }
            }
        }
        return response.custom("Note tidak di temukan", HttpStatus.NOT_FOUND);
    }

    public Map deleteNote(Long id) {
        if (noteRepository.existsById(id)) {
            User owner = getUserByUsername();
            Note getNote = noteRepository.findOneById(id);
            if (getNote != null) {
                List<UserNote> userNotes = userNoteRepository.findOneNoteById(id);
                for (UserNote userNote : userNotes) {
                    if (userNote.getPermission() == EPermission.OWNER && userNote.getUser().getId().equals(owner.getId())) {
                        userNoteRepository.deleteOneNoteById(getNote.getId());
                        noteRepository.deleteOneNoteById(id);
                        return response.custom("Note di delete", HttpStatus.OK);
                    }
                }
            }
        }
        return null;
    }
}
