package com.paulleclerc.mareu.ui.addMeeting.fragments.subject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paulleclerc.mareu.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingSubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingSubjectFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LinearLayoutManager mLinearLayoutManager;
    private ParticipantsEmailRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeetingSubjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeetingSubjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    private static MeetingSubjectFragment newInstance(String param1, String param2) {
        MeetingSubjectFragment fragment = new MeetingSubjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = Objects.requireNonNull(getView()).findViewById(R.id.participants_rv);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewAdapter = new ParticipantsEmailRecyclerViewAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meeting_subject, container, false);
    }
}
