package com.lathief.catatan.service;

import com.lathief.catatan.model.entities.UserNote;
import com.lathief.catatan.model.entities.UserNoteKey;
import com.lathief.catatan.model.entities.note.Note;
import com.lathief.catatan.model.entities.user.User;
import com.lathief.catatan.model.enums.EPermission;
import com.lathief.catatan.repository.UserNoteRepository;
import com.lathief.catatan.repository.note.NoteRepository;
import com.lathief.catatan.repository.user.UserRepository;
import com.lathief.catatan.service.interfaces.UserNoteService;
import com.lathief.catatan.service.provider.UserRelatedServiceImpl;
import com.lathief.catatan.utils.Response;
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
    public Map shareNote(Long noteid, Long shareuserid, String permission) {
        System.out.println("share note" + noteid + " " + shareuserid + " permission:" + permission);
        User owner = getUserByUsername();
        if (!noteRepository.findById(noteid).isPresent()) {
            return response.custom("Note tidak di temukan", HttpStatus.NOT_FOUND);
        }
        if (!userRepository.findById(shareuserid).isPresent()) {
            return response.custom("User tidak di temukan", HttpStatus.NOT_FOUND);
        }
        List<UserNote> userNotes = userNoteRepository.findOneUserById(owner.getId());
        for (UserNote unote : userNotes) {
            if (unote.getNote().getId().equals(noteid)) {
                System.out.println(unote.getNote().getId() + " " + noteid);
                if (unote.getPermission().equals(EPermission.OWNER) && unote.getUser().getId().equals(getUserByUsername().getId())){
                    UserNoteKey key = new UserNoteKey();
                    key.setNoteId(noteid);
                    key.setUserId(shareuserid);
                    UserNote userNote = new UserNote(noteRepository.findOneById(noteid), userRepository.findOneById(shareuserid), EPermission.valueOf(permission));
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
