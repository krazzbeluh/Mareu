package com.paulleclerc.mareu.model;

import com.paulleclerc.mareu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MeetingService {
    public static final String DATE_FORMATTER = "EEEE dd MMMM yyyy - HH:mm";

    private static final List<Meeting> sMeetingList = new ArrayList<>(Collections.singletonList(new Meeting(new Date(),
            "Salle 3",
            "Comptabilité",
            new ArrayList<>(Arrays.asList("paul@lamzone.fr", "alexandra@lamzone.fr")),
            R.color.MeetingBlue)));

    public List<Meeting> getMeetingList() {
        return sMeetingList;
    }

    public void addMeeting(Meeting meeting) {
        sMeetingList.add(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        sMeetingList.remove(meeting);
    }

    public List<Meeting> getMeetingWithFilter(Date date) {
        List<Meeting> meetingList = new ArrayList<>();

        Calendar selectedDate = Calendar.getInstance();
        Calendar comparingDate = Calendar.getInstance();
        selectedDate.setTime(date);

        for (Meeting meeting : sMeetingList) {
            comparingDate.setTime(meeting.getDate());
            if (comparingDate.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR)
                    && comparingDate.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH)
                    && comparingDate.get(Calendar.DAY_OF_MONTH) == selectedDate.get(Calendar.DAY_OF_MONTH))
                meetingList.add(meeting);
        }

        return meetingList;
    }

    public List<Meeting> getMeetingWithFilter(String location) {
        List<Meeting> meetingList = new ArrayList<>();

        for (Meeting meeting : sMeetingList) {
            if (meeting.getLocation().equals(location)) meetingList.add(meeting);
        }

        return meetingList;
    }
}
