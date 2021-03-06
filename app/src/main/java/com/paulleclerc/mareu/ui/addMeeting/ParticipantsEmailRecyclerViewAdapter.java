package com.paulleclerc.mareu.ui.addMeeting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paulleclerc.mareu.R;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsEmailRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantsEmailRecyclerViewAdapter.EmailHolder> {
    private final List<String> mEmailList = new ArrayList<>();

    List<String> getEmailList() {
        return mEmailList;
    }

    private final DeleteEmailCallback mCallback;

    ParticipantsEmailRecyclerViewAdapter(DeleteEmailCallback callback) {
        this.mCallback = callback;
    }

    @NonNull
    @Override
    public EmailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_participants_item, parent, false);
        return new ParticipantsEmailRecyclerViewAdapter.EmailHolder(this, inflatedView);
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

    private void deleteEmail(String email) {
        for (int i = 0; i < mEmailList.size(); i++) {
            if (email.equals(mEmailList.get(i))) {
                mEmailList.remove(i);
                mCallback.onDeleteEmail(i);
                break;
            }
        }
    }

    void addEmail(String email) {
        mEmailList.add(email);
        notifyItemInserted(mEmailList.size() - 1);
    }

    static class EmailHolder extends RecyclerView.ViewHolder {

        final ParticipantsEmailRecyclerViewAdapter mAdapter;

        String mEmail;

        final TextView mEmailView;
        final ImageButton mDeleteButton;

        EmailHolder(ParticipantsEmailRecyclerViewAdapter adapter, @NonNull View itemView) {
            super(itemView);
            mAdapter = adapter;
            this.mEmailView = itemView.findViewById(R.id.email_text_view);
            this.mDeleteButton = itemView.findViewById(R.id.delete_email_button);
        }

        void bindEmail(String email) {
            this.mEmail = email;
            mEmailView.setText(email);

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.deleteEmail(mEmail);
                }
            });
        }
    }

    interface DeleteEmailCallback {
        void onDeleteEmail(int position);
    }
}
