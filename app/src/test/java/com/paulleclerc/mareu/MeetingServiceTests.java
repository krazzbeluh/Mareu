package com.paulleclerc.mareu;

import com.paulleclerc.mareu.model.Meeting;
import com.paulleclerc.mareu.model.MeetingService;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static junit.framework.TestCase.*;

public class MeetingServiceTests {
    private MeetingService mMeetingService;

    @Before
    public void setUp() {
        mMeetingService = new MeetingService();
    }

    @Test
    public void getMeetingList_onAddMeeting_shouldcontain1MoreEntryEqualsToAddedOne() {
        int meetingListSize = mMeetingService.getMeetingList().size();

        Meeting meeting = new Meeting(new Date(), "Salle3", "Comptabilité", new ArrayList<>(Arrays.asList("paul@lamzone.com", "sophie@lamzone.com")), 0);

        mMeetingService.addMeeting(meeting);
        assertEquals(meetingListSize + 1, mMeetingService.getMeetingList().size());
        assertEquals(meeting, mMeetingService.getMeetingList().get(mMeetingService.getMeetingList().size() - 1));
    }

    @Test
    public void getMeetingList_onRemoveMeeting_shouldContainOneLessEntry() {
        int meetingListSize = mMeetingService.getMeetingList().size();
        mMeetingService.removeMeeting(mMeetingService.getMeetingList().get(0));
        assertEquals(meetingListSize - 1, mMeetingService.getMeetingList().size());
    }

    @Test
    public void getMeetingWithFilterDate_withMatchingDate_shouldReturnAtLeastOneMeeting() {
        assertTrue(mMeetingService.getMeetingWithFilter(new Date()).size() > 0);
    }

    @Test
    public void getMeetingWithFilterDate_withNonMatchingDate_shouldReturnVoidList() {
        assertEquals(0, mMeetingService.getMeetingWithFilter(new Date(0)).size());
    }

    @Test
    public void getMeetingWithFilterLocation_withMatchingLocation_shouldReturnAtLeastOneMeeting() {
        assertTrue(mMeetingService.getMeetingWithFilter("Salle 3").size() > 0);
    }

    @Test
    public void getMeetingWithFilterLocation_withNonMatchingLocation_shouldReturnVoidList() {
        assertEquals(0, mMeetingService.getMeetingWithFilter("Lorem Ipsum").size());
    }
}
