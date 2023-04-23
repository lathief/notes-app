package com.lathief.NoteKeep.model.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddLabel {
    private List<String> labels;
}
