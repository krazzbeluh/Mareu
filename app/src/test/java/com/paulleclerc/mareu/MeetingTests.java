package com.paulleclerc.mareu;

import com.paulleclerc.mareu.model.Meeting;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class MeetingTests {
    private Meeting mMeeting;

    private Date mDate;
    private String mLocation;
    private String mSubject;
    private List<String> mParticipants = new ArrayList<>();
    private int mColor;

    @Before
    public void setUp() {
        mDate = new Date(458864356);
        mLocation = "Salle 4";
        mSubject = "Organization";
        mParticipants.add("LoremIpsum@mareu.fr");
        mParticipants.add("DolorSitAmet@mareu.fr");
        mColor = R.color.MeetingBlue;
        mMeeting = new Meeting(mDate, mLocation, mSubject, mParticipants, mColor);
    }

    @Test
    public void gettersShouldReturnCorrectValues() {
        assertSame(mMeeting.getDate(), mDate);
        assertEquals(mMeeting.getLocation(), mLocation);
        assertEquals(mMeeting.getSubject(), mSubject);

        StringBuilder participants = new StringBuilder();
        for (String participant: mParticipants) participants.append(", ").append(participant);
        assertEquals(participants.toString(), mMeeting.getParticipants());
        assertEquals(mMeeting.getColor(), mColor);
    }

    @Test
    public void getMeetingListShouldReturnEveryMeetings_addMeetingShouldAdd1Meeting() {
        assertEquals(Meeting.getMeetingList().size(), 0);
        Meeting.addMeeting(mMeeting);
        assertEquals(Meeting.getMeetingList().size(), 1);
    }


}
