package com.paulleclerc.mareu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Meeting {
    public static final String DATE_FORMATTER = "EEEE dd MMMM yyyy - HH:mm";

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
        for (int i = 0; i < mParticipants.size(); i++) {
            if (i != 0) participants.append(", ");
            participants.append(mParticipants.get(i));
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
