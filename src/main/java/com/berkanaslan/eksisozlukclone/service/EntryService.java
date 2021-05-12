package com.berkanaslan.eksisozlukclone.service;

import com.berkanaslan.eksisozlukclone.model.Entry;
import com.berkanaslan.eksisozlukclone.model.dto.EntryDTO;
import com.berkanaslan.eksisozlukclone.model.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class EntryService {

    public EntryDTO convertToDTO(Entry entry) {
        EntryDTO entryDTO = new EntryDTO();
        UserDTO userDTO = new UserDTO();

        userDTO.setId(entry.getUser().getId());
        userDTO.setUsername(entry.getUser().getUsername());

        entryDTO.setId(entry.getId());
        entryDTO.setTitleId(entry.getTitle().getId());
        entryDTO.setUserDTO(userDTO);
        entryDTO.setComment(entry.getComment());
        entryDTO.setCreatedAt(entry.getCreatedAt());
        entryDTO.setUpdatedAt(entry.getUpdatedAt());

        return entryDTO;
    }

}
