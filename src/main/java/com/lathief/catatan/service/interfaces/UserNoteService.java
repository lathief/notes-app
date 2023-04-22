package com.lathief.catatan.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserNoteService {
    Map shareNote(Long noteid, String email, String permission);
}
