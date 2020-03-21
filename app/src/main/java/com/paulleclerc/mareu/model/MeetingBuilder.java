package com.paulleclerc.mareu.model;

import java.util.List;

public class MeetingBuilder {
    public static final MeetingBuilder builder = new MeetingBuilder();

    private String mSubject;
    private String mLocation;
    private List<String> mParticipants;
    private int mColor;

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public void setParticipants(List<String> participants) {
        mParticipants = participants;
    }

    public void setColor(int color) {
        mColor = color;
    }

    private MeetingBuilder() {}
}
