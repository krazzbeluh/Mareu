package com.paulleclerc.mareu.ui.addMeeting.fragments.subject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.paulleclerc.mareu.Model.Meeting;
import com.paulleclerc.mareu.R;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingSubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingSubjectFragment extends Fragment implements ParticipantsEmailRecyclerViewAdapter.DeleteEmailCallback {
    private static final String TAG = MeetingSubjectFragment.class.getSimpleName();

    private Pattern mEmailPattern = Pattern.compile("^(.+)@(.+)$");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LinearLayoutManager mLinearLayoutManager;
    private ParticipantsEmailRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    private TextView mEmailTextView;
    private ImageButton mAddEmailButton;

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
        mRecyclerViewAdapter = new ParticipantsEmailRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mAddEmailButton = Objects.requireNonNull(getView()).findViewById(R.id.add_email_button);
        mAddEmailButton.setEnabled(false);
        mAddEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mEmailTextView.getText().toString();
                if (mEmailPattern.matcher(mail).matches()) {
                    mRecyclerViewAdapter.addEmail(mail);
                    mEmailTextView.setText("");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Erreur")
                            .setMessage("\"" + mail + "\" n'est pas une adresse valide. Veuillez entrer une adresse mail correcte !")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create()
                            .show();
                }
            }
        });

        mEmailTextView = Objects.requireNonNull(getView()).findViewById(R.id.email_edit_text);
        mEmailTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final boolean hasText = count != 0;
                mAddEmailButton.setEnabled(hasText);
                mAddEmailButton.setBackgroundTintList(hasText ? ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)) : ColorStateList.valueOf(getResources().getColor(R.color.grayOut)));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meeting_subject, container, false);
    }

    @Override
    public void onDeleteEmail(int position) {
        mRecyclerView.removeViewAt(position);
        mRecyclerViewAdapter.notifyItemRemoved(position);
        mRecyclerViewAdapter.notifyItemRangeChanged(position, mRecyclerViewAdapter.getEmailList().size());

        //mRecyclerViewAdapter.notifyDataSetChanged();
    }

    void onAddEmail(String email) {
        mRecyclerViewAdapter.notifyItemInserted(mRecyclerViewAdapter.getItemCount() - 1);
    }
}
