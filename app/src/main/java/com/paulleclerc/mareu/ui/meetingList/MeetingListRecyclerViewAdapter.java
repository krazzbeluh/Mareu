package com.paulleclerc.mareu.ui.meetingList;

import android.media.Image;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paulleclerc.mareu.model.Meeting;
import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.model.MeetingService;

import java.util.Date;
import java.util.List;

public class MeetingListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingListRecyclerViewAdapter.MeetingHolder> {
    private static final String TAG = MeetingListRecyclerViewAdapter.class.getSimpleName();

    private MeetingListRVEvents mEventHandler;
    private MeetingService mMeetingService = new MeetingService();

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
        private static final String TAG = MeetingHolder.class.getSimpleName();

        private Meeting mMeeting;
        private MeetingListRVEvents mEventHandler;

        View mThumbnailView;
        TextView mTitleView;
        TextView mSubtitleView;
        RelativeLayout mRelativeLayout;
        ImageButton mDeleteButton;

        MeetingHolder(@NonNull View itemView, MeetingListRVEvents eventHandler) {
            super(itemView);
            this.mEventHandler = eventHandler;

            this.mThumbnailView = itemView.findViewById(R.id.meeting_list_thumbnail);
            this.mTitleView = itemView.findViewById(R.id.meeting_list_title);
            this.mSubtitleView = itemView.findViewById(R.id.meeting_list_subtitle);
            this.mRelativeLayout = itemView.findViewById(R.id.meeting_item_relativeLayout);
            this.mDeleteButton = itemView.findViewById(R.id.meeting_list_delete);
        }

        void bindMeeting(Meeting meeting) {
            this.mMeeting = meeting;
            String title = meeting.getSubject() + " - " + meeting.getLocation() + " - " + meeting.getDateFormatted();
            mTitleView.setText(title);
            mSubtitleView.setText(meeting.getParticipants());
            mThumbnailView.setBackgroundTintList(itemView.getResources().getColorStateList(meeting.getColor()));

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEventHandler.onDeleteMeeting(mMeeting);
                }
            });
        }
    }
}
