package com.paulleclerc.mareu.ui.addMeeting.fragments.subject;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.ui.meetingList.MeetingListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsEmailRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantsEmailRecyclerViewAdapter.EmailHolder> {
    private List<String> mEmailList = new ArrayList<>();

    @NonNull
    @Override
    public EmailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_participants_item, parent, false);
        return new ParticipantsEmailRecyclerViewAdapter.EmailHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailHolder holder, int position) {
        String email = mEmailList.get(position);
        holder.bindEmail(email);
    }

    @Override
    public int getItemCount() {
        return mEmailList.size();
    }

    static class EmailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = EmailHolder.class.getSimpleName();

        String mEmail;

        TextView mEmailView;

        EmailHolder(@NonNull View itemView) {
            super(itemView);
            this.mEmailView = itemView.findViewById(R.id.email_text_view);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: Click");
        }

        void bindEmail(String email) {
            this.mEmail = email;
            mEmailView.setText(email);
        }
    }
}
