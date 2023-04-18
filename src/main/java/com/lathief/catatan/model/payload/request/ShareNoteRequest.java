package com.lathief.catatan.model.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ShareNoteRequest {
    private Map<Long, String> shareIds;
}
