package com.berkanaslan.kodsozluk.service.entry;

import com.berkanaslan.kodsozluk.model.Entry;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EntryAddedEvent extends ApplicationEvent {
    private final Entry entry;

    public EntryAddedEvent(Object source, final Entry entry) {
        super(source);
        this.entry = entry;
    }
}
