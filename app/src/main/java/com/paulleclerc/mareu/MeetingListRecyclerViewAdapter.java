package com.paulleclerc.mareu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paulleclerc.mareu.Model.Meeting;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        Meeting meeting = Meeting.sMeetingList.get(position);
        holder.bindMeeting(meeting);
    }

    @Override
    public int getItemCount() {
        return Meeting.sMeetingList.size();
    }

    static class MeetingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = MeetingHolder.class.getSimpleName();

        private Meeting mMeeting;

        @BindView(R.id.meeting_list_thumbnail)
        public View mThumbnailView;
        @BindView(R.id.meeting_list_title)
        public TextView mTitleView;
        @BindView(R.id.meeting_list_subtitle)
        public TextView mSubtitleView;

        MeetingHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
