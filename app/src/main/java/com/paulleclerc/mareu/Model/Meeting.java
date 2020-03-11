package com.paulleclerc.mareu.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Meeting {
    public static List<Meeting> sMeetingList = new ArrayList<>();

    private Date mDate;
    private String mLocation;
    private String mSubject;
    private List<String> mParticipants;

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getParticipants() {
        StringBuilder participants = new StringBuilder();
        for (String participant: mParticipants) {
            participants.append(", ").append(participant);
        }
        return participants.toString();
    }

    public void setParticipants(List<String> participants) {
        mParticipants = participants;
    }

    public Meeting(Date date, String location, String subject, List<String> participants) {
        mDate = date;
        mLocation = location;
        mSubject = subject;
        mParticipants = participants;
    }
}
