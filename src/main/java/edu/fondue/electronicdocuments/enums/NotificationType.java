package edu.fondue.electronicdocuments.enums;

import lombok.Getter;

@Getter
public enum NotificationType {
    ADD_ROLE("Go to your organization profile."),
    DELETE_ROLE("Go to your organization profile.");

    private String linkText;

    NotificationType(final String linkText) {
        this.linkText = linkText;
    }
}
