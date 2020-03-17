package com.paulleclerc.mareu.ui.addMeeting.fragments.subject;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.events.DeleteEmailEvent;
import com.paulleclerc.mareu.ui.meetingList.MeetingListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParticipantsEmailRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantsEmailRecyclerViewAdapter.EmailHolder> {
    private List<String> mEmailList = new ArrayList<>(Arrays.asList("exemple@email.fr", "paulleclerc@vigiclean.com"));

    private final Listener callback;
    public interface Listener {
        void onClickDelete(String email);
    }

    public ParticipantsEmailRecyclerViewAdapter(Listener callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public EmailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_participants_item, parent, false);
        return new ParticipantsEmailRecyclerViewAdapter.EmailHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailHolder holder, int position) {
        String email = mEmailList.get(position);
        holder.bindEmail(email, callback);
    }

    @Override
    public int getItemCount() {
        return mEmailList.size();
    }

    private void deleteEmail(String email) {
        mEmailList.remove(email);
    }

    static class EmailHolder extends RecyclerView.ViewHolder {
        private static final String TAG = EmailHolder.class.getSimpleName();

        String mEmail;

        TextView mEmailView;
        ImageButton mDeleteButton;

        EmailHolder(@NonNull View itemView) {
            super(itemView);
            this.mEmailView = itemView.findViewById(R.id.email_text_view);
            this.mDeleteButton = itemView.findViewById(R.id.delete_email_button);

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: Click");
                }
            });
        }

        void bindEmail(String email, final ParticipantsEmailRecyclerViewAdapter.Listener callback) {
            this.mEmail = email;
            mEmailView.setText(email);

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClickDelete(mEmail);
                }
            });
        }
    }
}
