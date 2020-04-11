package com.paulleclerc.mareu.ui.meetingList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.paulleclerc.mareu.model.Meeting;
import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.model.MeetingService;

import java.util.Date;
import java.util.List;

public class MeetingListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingListRecyclerViewAdapter.MeetingHolder> {

    private final MeetingListRVEvents mEventHandler;
    private final MeetingService mMeetingService = new MeetingService();

    private List<Meeting> mMeetingList;

    public interface MeetingListRVEvents {
        void onDeleteMeeting(Meeting meeting);
    }

    void filterBy(Date date) {
        mMeetingList = mMeetingService.getMeetingWithFilter(date);
        this.notifyDataSetChanged();
    }

    void filterBy(String location) {
        mMeetingList = mMeetingService.getMeetingWithFilter(location);
        this.notifyDataSetChanged();
    }

    void noFilter() {
        mMeetingList = mMeetingService.getMeetingList();
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MeetingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_meeting, parent, false);
        return new MeetingHolder(inflatedView, mEventHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingHolder holder, int position) {
        Meeting meeting = mMeetingList.get(position);
        holder.bindMeeting(meeting);
    }

    MeetingListRecyclerViewAdapter(MeetingListRVEvents eventHandler) {
        this.mEventHandler = eventHandler;
        this.mMeetingList = this.mMeetingService.getMeetingList();
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }

    static class MeetingHolder extends RecyclerView.ViewHolder {

        private Meeting mMeeting;
        private final MeetingListRVEvents mEventHandler;

        final ImageView mThumbnailView;
        final TextView mTitleView;
        final TextView mParticipantsView;
        final TextView mLocationView;
        final TextView mDateView;
        final RelativeLayout mRelativeLayout;
        final ImageButton mDeleteButton;

        MeetingHolder(@NonNull View itemView, MeetingListRVEvents eventHandler) {
            super(itemView);
            this.mEventHandler = eventHandler;

            this.mThumbnailView = itemView.findViewById(R.id.meeting_list_thumbnail);
            this.mTitleView = itemView.findViewById(R.id.meeting_list_title);
            this.mLocationView = itemView.findViewById(R.id.meeting_list_location);
            this.mDateView = itemView.findViewById(R.id.meeting_list_date);
            this.mParticipantsView = itemView.findViewById(R.id.meeting_list_subtitle);
            this.mRelativeLayout = itemView.findViewById(R.id.meeting_item_relativeLayout);
            this.mDeleteButton = itemView.findViewById(R.id.meeting_list_delete);
        }

        void bindMeeting(Meeting meeting) {
            this.mMeeting = meeting;
            mTitleView.setText(meeting.getSubject());
            mLocationView.setText(meeting.getLocation());
            mDateView.setText(meeting.getDateFormatted());
            mParticipantsView.setText(meeting.getParticipants());
            mThumbnailView.setColorFilter(ContextCompat.getColor(itemView.getContext(), meeting.getColor()));

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEventHandler.onDeleteMeeting(mMeeting);
                }
            });

            mParticipantsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mParticipantsView.setMaxLines((mParticipantsView.getMaxLines() == 1) ? Integer.MAX_VALUE : 1);
                }
            });
        }
    }
}
