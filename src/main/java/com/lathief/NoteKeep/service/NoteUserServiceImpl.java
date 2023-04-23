package com.lathief.NoteKeep.service;

import com.lathief.NoteKeep.model.entities.UserNote;
import com.lathief.NoteKeep.model.entities.user.User;
import com.lathief.NoteKeep.model.enums.EPermission;
import com.lathief.NoteKeep.model.payload.response.ShareNoteResponse;
import com.lathief.NoteKeep.repository.NoteUserRepository;
import com.lathief.NoteKeep.repository.note.NoteRepository;
import com.lathief.NoteKeep.repository.user.UserRepository;
import com.lathief.NoteKeep.service.interfaces.NoteUserService;
import com.lathief.NoteKeep.service.provider.UserRelatedServiceImpl;
import com.lathief.NoteKeep.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class NoteUserServiceImpl extends UserRelatedServiceImpl implements NoteUserService {
    @Autowired
    Response response;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    NoteUserRepository noteUserRepository;
    public Map shareNote(Long noteid, String email, String permission) {
        User loginUser = getUserByUsername();
        if (Objects.equals(loginUser.getEmail(), email)) {
            return response.custom("Note di share ke akun sendiri", HttpStatus.BAD_REQUEST);
        }
        if (noteRepository.findById(noteid).isEmpty()) {
            return response.custom("Note tidak di temukan", HttpStatus.NOT_FOUND);
        }
        if (!noteUserRepository.findPermission(loginUser.getId(), noteid).equals("OWNER")){
            return response.custom("Unauthorized to share note", HttpStatus.UNAUTHORIZED);
        }

        User getUser = userRepository.findOneByEmail(email);
        if (getUser == null) {
            return response.custom("User tidak di temukan", HttpStatus.NOT_FOUND);
        }

        List<UserNote> userNotes = noteUserRepository.findOneNoteById(noteid);
        for (UserNote unote : userNotes) {
            if (unote.getUser().equals(getUser)) {
                return response.notAccepted("note should be shared");
            }
        }
        UserNote userNote = new UserNote(noteRepository.findOneById(noteid), getUser, EPermission.valueOf(permission));
        noteUserRepository.save(userNote);
        return response.custom("Note di share", HttpStatus.OK);
    }

    public Map getUserAccess(Long noteid) {
        User loginUser = getUserByUsername();
        if (!noteRepository.findById(noteid).isPresent()) {
            return response.custom("Note tidak di temukan", HttpStatus.NOT_FOUND);
        }
        if (!noteUserRepository.findPermission(loginUser.getId(), noteid).equals("OWNER")){
            return response.custom("Unauthorized to get user access", HttpStatus.UNAUTHORIZED);
        }
        List<UserNote> userNotes = noteUserRepository.findOneNoteById(noteid);
        List<ShareNoteResponse> shareNotes = new ArrayList<>();
        for (UserNote unote : userNotes) {
            if (unote.getNote().getId().equals(noteid)) {
                shareNotes.add(new ShareNoteResponse(userRepository.findOneById(unote.getUser().getId()).getEmail(), String.valueOf(unote.getPermission())));
            }
        }
        return response.success(shareNotes);
    }
    public Map editUserAccess(Long noteid, String email, String permission) {
        User loginUser = getUserByUsername();
        if (Objects.equals(loginUser.getEmail(), email)) {
            return response.custom("tidak bisa edit permission diri sendiri", HttpStatus.BAD_REQUEST);
        }
        if (!noteRepository.findById(noteid).isPresent()) {
            return response.custom("Note tidak di temukan", HttpStatus.NOT_FOUND);
        }
        if (!noteUserRepository.findPermission(loginUser.getId(), noteid).equals("OWNER")){
            return response.custom("Unauthorized to edit access note", HttpStatus.UNAUTHORIZED);
        }
        User getUser = userRepository.findOneByEmail(email);
        if (getUser == null) {
            return response.custom("User tidak di temukan", HttpStatus.NOT_FOUND);
        }
        List<UserNote> userNotes = noteUserRepository.findOneNoteById(noteid);
        List<ShareNoteResponse> shareNotes = new ArrayList<>();
        for (UserNote unote : userNotes) {
            if (unote.getUser().equals(getUser)) {
                unote.setPermission(EPermission.valueOf(permission));
                noteUserRepository.save(unote);
            }
        }
        return response.success("edit access successfully");
    }
}
