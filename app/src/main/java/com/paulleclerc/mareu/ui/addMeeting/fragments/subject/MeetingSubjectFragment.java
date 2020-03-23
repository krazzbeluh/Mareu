package com.paulleclerc.mareu.ui.addMeeting.fragments.subject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.model.MeetingBuilder;
import com.paulleclerc.mareu.ui.addMeeting.ParticipantsEmailRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingSubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/*public class MeetingSubjectFragment extends Fragment implements ParticipantsEmailRecyclerViewAdapter.DeleteEmailCallback, View.OnClickListener {
    public interface OnNavigateClickListener { void onNavButtonClicked(boolean isRight); }
    private OnNavigateClickListener mOnNavigateClickListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try { mOnNavigateClickListener = (OnNavigateClickListener) context; } catch (ClassCastException e) { throw new ClassCastException(((Activity) context).getLocalClassName() + " must implement OnButtonClickListener");}
    }













    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meeting_subject, container, false);
    }






}
*/