package com.paulleclerc.mareu.model;

import com.paulleclerc.mareu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MeetingService {
    private static final String TAG = MeetingService.class.getSimpleName();
    public static final String DATE_FORMATTER = "EEEE dd MMMM yyyy - HH:mm";

    private static List<Meeting> sMeetingList = new ArrayList<>(Collections.singletonList(new Meeting(new Date(),
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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.FRANCE);

        for (Meeting meeting: sMeetingList) {
            if (formatter.format(meeting.getDate()).equals(formatter.format(date))) meetingList.add(meeting);
        }

        return meetingList;
    }

    public List<Meeting> getMeetingWithFilter(String location) {
        List<Meeting> meetingList = new ArrayList<>();

        for (Meeting meeting: sMeetingList) {
            if (meeting.getLocation().equals(location)) meetingList.add(meeting);
        }

        return meetingList;
    }
}
