package com.paulleclerc.mareu.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.paulleclerc.mareu.model.MeetingService.DATE_FORMATTER;

public class Meeting {

    private Date mDate;
    private String mLocation;
    private String mSubject;
    private List<String> mParticipants;
    private int mColor;

    public Date getDate() {
        return mDate;
    }

    public String getDateFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATTER, Locale.FRANCE);
        return formatter.format(mDate);
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
