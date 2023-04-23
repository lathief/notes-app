package com.lathief.NoteKeep.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface NoteUserService {
    Map shareNote(Long noteid, String email, String permission);

    Map getUserAccess(Long noteid);
    Map editUserAccess(Long noteid, String email, String permission);
}
