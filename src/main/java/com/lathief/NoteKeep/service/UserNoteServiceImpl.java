package com.lathief.NoteKeep.service;

import com.lathief.NoteKeep.model.entities.UserNote;
import com.lathief.NoteKeep.model.entities.UserNoteKey;
import com.lathief.NoteKeep.model.entities.note.Note;
import com.lathief.NoteKeep.model.entities.user.User;
import com.lathief.NoteKeep.model.enums.EPermission;
import com.lathief.NoteKeep.repository.UserNoteRepository;
import com.lathief.NoteKeep.repository.note.NoteRepository;
import com.lathief.NoteKeep.repository.user.UserRepository;
import com.lathief.NoteKeep.service.interfaces.UserNoteService;
import com.lathief.NoteKeep.service.provider.UserRelatedServiceImpl;
import com.lathief.NoteKeep.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserNoteServiceImpl extends UserRelatedServiceImpl implements UserNoteService {
    @Autowired
    Response response;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    UserNoteRepository userNoteRepository;
    public Map shareNote(Long noteid, String email, String permission) {
        User owner = getUserByUsername();
        if (!noteRepository.findById(noteid).isPresent()) {
            return response.custom("Note tidak di temukan", HttpStatus.NOT_FOUND);
        }
        User getUser = userRepository.findOneByEmail(email);
        if (getUser == null) {
            return response.custom("User tidak di temukan", HttpStatus.NOT_FOUND);
        }
        List<UserNote> userNotes = userNoteRepository.findOneUserById(owner.getId());
        for (UserNote unote : userNotes) {
            if (unote.getNote().getId().equals(noteid)) {
                System.out.println(unote.getNote().getId() + " " + noteid);
                if (unote.getPermission().equals(EPermission.OWNER) && unote.getUser().getId().equals(getUserByUsername().getId())){
                    UserNoteKey key = new UserNoteKey();
                    key.setNoteId(noteid);
                    key.setUserId(getUser.getId());
                    UserNote userNote = new UserNote(noteRepository.findOneById(noteid), getUser, EPermission.valueOf(permission));
                    userNoteRepository.save(userNote);
                    return response.custom("Note di share", HttpStatus.OK);
                } else {
                    return response.custom("Unauthorized to share note", HttpStatus.OK);
                }
            }
        }
        return response.custom("Note tidak di temukan", HttpStatus.NOT_FOUND);
    }
}
