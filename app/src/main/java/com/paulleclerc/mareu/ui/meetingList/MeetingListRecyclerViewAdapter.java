package com.paulleclerc.mareu.ui.meetingList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paulleclerc.mareu.model.Meeting;
import com.paulleclerc.mareu.R;

public class MeetingListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingListRecyclerViewAdapter.MeetingHolder> {
    private static final String TAG = MeetingListRecyclerViewAdapter.class.getSimpleName();

    @NonNull
    @Override
    public MeetingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_meeting, parent, false);
        return new MeetingHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingHolder holder, int position) {
        Meeting meeting = Meeting.getMeetingList().get(position);
        holder.bindMeeting(meeting);
    }

    @Override
    public int getItemCount() {
        return Meeting.getMeetingList().size();
    }

    static class MeetingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = MeetingHolder.class.getSimpleName();

        private Meeting mMeeting;

        View mThumbnailView;
        TextView mTitleView;
        TextView mSubtitleView;
        RelativeLayout mRelativeLayout;

        MeetingHolder(@NonNull View itemView) {
            super(itemView);
            this.mThumbnailView = itemView.findViewById(R.id.meeting_list_thumbnail);
            this.mTitleView = itemView.findViewById(R.id.meeting_list_title);
            this.mSubtitleView = itemView.findViewById(R.id.meeting_list_subtitle);
            mRelativeLayout = itemView.findViewById(R.id.meeting_item_relativeLayout);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + "CLICK!");
        }

        void bindMeeting(Meeting meeting) {
            this.mMeeting = meeting;
            String title = meeting.getSubject() + " - " + meeting.getLocation();
            mTitleView.setText(title);
            mSubtitleView.setText(meeting.getParticipants());
        }
    }
}
