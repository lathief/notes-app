package com.lathief.NoteKeep.service;

import com.lathief.NoteKeep.model.entities.UserNote;
import com.lathief.NoteKeep.model.entities.note.Note;
import com.lathief.NoteKeep.model.entities.user.User;
import com.lathief.NoteKeep.model.enums.EPermission;
import com.lathief.NoteKeep.repository.NoteUserRepository;
import com.lathief.NoteKeep.repository.note.LabelRepository;
import com.lathief.NoteKeep.repository.note.NoteRepository;
import com.lathief.NoteKeep.service.interfaces.NoteService;
import com.lathief.NoteKeep.service.provider.UserRelatedServiceImpl;
import com.lathief.NoteKeep.utils.Encryption;
import com.lathief.NoteKeep.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteServiceImpl extends UserRelatedServiceImpl implements NoteService{
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    NoteUserRepository noteUserRepository;
    @Autowired
    Response response;
    @Autowired
    Encryption encryption;
    final String secretKey = "ssshhhhhhhhhhh!!!!";

    public List<Note> getAllNotes() {
        User owner = getUserByUsername();
        List<Note> notes = new ArrayList<>();
        List<UserNote> userNotes = noteUserRepository.findOneUserById(owner.getId());
        for (UserNote unote : userNotes) {
            Note getNote = new Note(unote.getId(), encryption.decrypt(unote.getNote().getTitle(), secretKey), encryption.decrypt(unote.getNote().getContent(), secretKey), unote.getNote().getLabels());
            getNote.setCreatedAt(unote.getNote().getCreatedAt());
            getNote.setUpdatedAt(unote.getNote().getUpdatedAt());
            notes.add(getNote);
        }
        return notes;
    }

    public Note getNoteById(Long id) {
        User owner = getUserByUsername();
        List<Note> notes = new ArrayList<>();
        List<UserNote> userNotes = noteUserRepository.findOneUserById(owner.getId());
        for (UserNote unote : userNotes) {
            if (unote.getNote().getId().equals(id)) {
                Note getNote = new Note(unote.getId(), encryption.decrypt(unote.getNote().getTitle(), secretKey), encryption.decrypt(unote.getNote().getContent(), secretKey), unote.getNote().getLabels());
                getNote.setCreatedAt(unote.getNote().getCreatedAt());
                getNote.setUpdatedAt(unote.getNote().getUpdatedAt());
                return getNote;
            }
        }
        return null;
    }

    public Map insertNote(Note note) {
        Note saveNote = new Note(encryption.encrypt(note.getTitle(), secretKey), encryption.encrypt(note.getContent(), secretKey));
        UserNote userNote = new UserNote(saveNote, getUserByUsername(), EPermission.OWNER);
        noteRepository.save(saveNote);
        noteUserRepository.save(userNote);
        return response.custom("Note di save", HttpStatus.CREATED);
    }

    public Map updateNote(Long id, Note note) {
        User owner = getUserByUsername();
        List<Note> notes = new ArrayList<>();
        List<UserNote> userNotes = noteUserRepository.findOneUserById(owner.getId());
        for (UserNote unote : userNotes) {
            if (unote.getNote().getId().equals(id)) {
                if (unote.getPermission().equals(EPermission.OWNER) || unote.getPermission().equals(EPermission.READ_WRITE)){
                    noteRepository.updateNote(id, encryption.encrypt(note.getTitle(), secretKey), encryption.encrypt(note.getContent(), secretKey), new Date());
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
                List<UserNote> userNotes = noteUserRepository.findOneNoteById(id);
                for (UserNote userNote : userNotes) {
                    if (userNote.getPermission() == EPermission.OWNER && userNote.getUser().getId().equals(owner.getId())) {
                        noteUserRepository.deleteOneNoteById(getNote.getId());
                        noteRepository.deleteOneNoteById(id);
                        return response.custom("Note di delete", HttpStatus.OK);
                    }
                }
            }
        }
        return null;
    }
}
