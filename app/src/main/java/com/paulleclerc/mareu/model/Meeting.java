package com.paulleclerc.mareu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Meeting {
    private static List<Meeting> sMeetingList = new ArrayList<>();

    public static List<Meeting> getMeetingList() {
        return sMeetingList;
    }

    public static void addMeeting(Meeting meeting) {
        sMeetingList.add(meeting);
    }

    public static void removeMeeting(Meeting meeting) {
        sMeetingList.remove(meeting);
    }

    private Date mDate;
    private String mLocation;
    private String mSubject;
    private List<String> mParticipants;
    private int mColor;

    public Date getDate() {
        return mDate;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getSubject() {
        return mSubject;
    }

    public String getParticipants() {
        StringBuilder participants = new StringBuilder();
        for (String participant: mParticipants) {
            participants.append(", ").append(participant);
        }
        return participants.toString();
    }

    public int getColor() {
        return mColor;
    }

    public Meeting(Date date, String location, String subject, List<String> participants, int color) {
        mDate = date;
        mLocation = location;
        mSubject = subject;
        mParticipants = participants;
        mColor = color;
    }
}
