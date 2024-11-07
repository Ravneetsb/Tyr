package org.rsb.tyr.services;

import org.rsb.tyr.processors.SecurityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final SecurityProcessor processor;

    @Autowired
    public SecurityService(SecurityProcessor processor) {
        this.processor = processor;
    }

    public String check(String name) {
        return processor.check(name);
    }

    public void allowEntry(String personName, String userName) {
        processor.allowEntry(personName, userName);
    }

    public Boolean isEntryAllowed(String name) {
        return processor.isEntryAllowed(name);
    }

    public void denyEntry(String personName, String userName) {
        processor.denyEntry(personName, userName);
    }
}
