package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;
    private final String toCc;

    public void mailBuilder(final String mailTo, final String subject, final String message, final String toCc) {
        Mail.builder()
                .mailTo(mailTo)
                .subject(subject)
                .message(message)
                .toCc(toCc)
                .build();
    }
}
