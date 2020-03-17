package com.paulleclerc.mareu.events;

public class DeleteEmailEvent {
    public final String mEmail;

    public DeleteEmailEvent(String email) {
        mEmail = email;
    }
}
