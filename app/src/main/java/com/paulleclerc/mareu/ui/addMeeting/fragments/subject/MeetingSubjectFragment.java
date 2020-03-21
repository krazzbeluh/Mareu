package com.paulleclerc.mareu.ui.addMeeting.fragments.subject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.model.Meeting;
import com.paulleclerc.mareu.model.MeetingBuilder;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingSubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingSubjectFragment extends Fragment implements ParticipantsEmailRecyclerViewAdapter.DeleteEmailCallback, View.OnClickListener {
    private static final String TAG = MeetingSubjectFragment.class.getSimpleName();

    private Pattern mEmailPattern = Pattern.compile("^(.+)@(.+)$");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LinearLayoutManager mLinearLayoutManager;
    private ParticipantsEmailRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    private TextView mSubjectTextView;
    private TextView mLocationTextView;
    private TextView mEmailTextView;
    private ImageButton mAddEmailButton;
    private Button mNextButton;

    private Button mRedButton;
    private Button mOrangeButton;
    private Button mYellowButton;
    private Button mGreenButton;
    private Button mBlueButton;
    private Button mPurpleButton;

    private Button mSelectedButton;

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

        mSubjectTextView = Objects.requireNonNull(getView()).findViewById(R.id.subject_edit_text);
        mLocationTextView = Objects.requireNonNull(getView()).findViewById(R.id.location_edit_text);
        mAddEmailButton = Objects.requireNonNull(getView()).findViewById(R.id.add_email_button);
        mEmailTextView = Objects.requireNonNull(getView()).findViewById(R.id.email_edit_text);
        mNextButton = Objects.requireNonNull(getView()).findViewById(R.id.next_to_date_button);
        mRedButton = Objects.requireNonNull(getView()).findViewById(R.id.red_button);
        mOrangeButton = Objects.requireNonNull(getView()).findViewById(R.id.orange_button);
        mYellowButton = Objects.requireNonNull(getView()).findViewById(R.id.yellow_button);
        mGreenButton = Objects.requireNonNull(getView()).findViewById(R.id.green_button);
        mBlueButton = Objects.requireNonNull(getView()).findViewById(R.id.blue_button);
        mPurpleButton = Objects.requireNonNull(getView()).findViewById(R.id.purple_button);

        configureAddEmailButton();
        configureEmailTextView();
        configureColorButtons();
        configureNextButton();
    }

    private void configureAddEmailButton() {
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
                                public void onClick(DialogInterface dialog, int which) {}
                            })
                            .create()
                            .show();
                }
            }
        });
    }

    private void configureEmailTextView() {
        mEmailTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final boolean hasText = count != 0;
                mAddEmailButton.setEnabled(hasText);
                mAddEmailButton.setBackgroundTintList(hasText ? ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)) : ColorStateList.valueOf(getResources().getColor(R.color.grayOut)));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void configureColorButtons() {
        mRedButton.setOnClickListener(this);
        mOrangeButton.setOnClickListener(this);
        mYellowButton.setOnClickListener(this);
        mGreenButton.setOnClickListener(this);
        mBlueButton.setOnClickListener(this);
        mPurpleButton.setOnClickListener(this);
    }

    private void setSelectedButton(Button button) {
        int buttonId = button.getId();
        mSelectedButton = button;
        mRedButton.setBackground(Objects.requireNonNull(getContext()).getResources().getDrawable((mRedButton.getId() == buttonId) ? R.drawable.ic_check_box_white_24dp : R.drawable.ic_box_white_24dp));
        mOrangeButton.setBackground(Objects.requireNonNull(getContext()).getResources().getDrawable((mOrangeButton.getId() == buttonId) ? R.drawable.ic_check_box_white_24dp : R.drawable.ic_box_white_24dp));
        mYellowButton.setBackground(Objects.requireNonNull(getContext()).getResources().getDrawable((mYellowButton.getId() == buttonId) ? R.drawable.ic_check_box_white_24dp : R.drawable.ic_box_white_24dp));
        mGreenButton.setBackground(Objects.requireNonNull(getContext()).getResources().getDrawable((mGreenButton.getId() == buttonId) ? R.drawable.ic_check_box_white_24dp : R.drawable.ic_box_white_24dp));
        mBlueButton.setBackground(Objects.requireNonNull(getContext()).getResources().getDrawable((mBlueButton.getId() == buttonId) ? R.drawable.ic_check_box_white_24dp : R.drawable.ic_box_white_24dp));
        mPurpleButton.setBackground(Objects.requireNonNull(getContext()).getResources().getDrawable((mPurpleButton.getId() == buttonId) ? R.drawable.ic_check_box_white_24dp : R.drawable.ic_box_white_24dp));
    }

    private int getSelectedColor() {
        if (mRedButton.equals(mSelectedButton)) {
            return R.color.MeetingRed;
        } else if (mOrangeButton.equals(mSelectedButton)) {
            return R.color.MeetingOrange;
        } else if (mYellowButton.equals(mSelectedButton)) {
            return R.color.MeetingYellow;
        } else if (mGreenButton.equals(mSelectedButton)) {
            return R.color.MeetingGreen;
        } else if (mBlueButton.equals(mSelectedButton)) {
            return R.color.MeetingBlue;
        } else {
            return R.color.MeetingPurple;
        }
    }

    private void configureNextButton() {
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeetingBuilder.builder.setSubject(mSubjectTextView.getText().toString());
                MeetingBuilder.builder.setLocation(mLocationTextView.getText().toString());
                MeetingBuilder.builder.setParticipants(mRecyclerViewAdapter.getEmailList());
                MeetingBuilder.builder.setColor(getSelectedColor());
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
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        if (isColorButton(buttonId)) {
            setSelectedButton((Button) v);
        }
    }

    private boolean isColorButton(int id) {
        return  (id == R.id.red_button
                || id == R.id.orange_button
                || id == R.id.yellow_button
                || id == R.id.green_button
                || id == R.id.blue_button
                || id == R.id.purple_button);
    }
}
