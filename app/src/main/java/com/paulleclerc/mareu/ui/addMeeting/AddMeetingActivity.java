package com.paulleclerc.mareu.ui.addMeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.model.MeetingBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener, ParticipantsEmailRecyclerViewAdapter.DeleteEmailCallback {

    private static final Pattern mEmailPattern = Pattern.compile("^(.+)@(.+)$");
    private static final String TAG = AddMeetingActivity.class.getSimpleName();

    private final Context mContext = this;

    private LinearLayoutManager mLinearLayoutManager;
    private ParticipantsEmailRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    private TextView mSubjectTextView;
    private TextView mLocationTextView;
    private TextView mEmailTextView;
    private ImageButton mAddEmailButton;
    private Button mDateTimePickerButton;
    //private Button mNextButton;

    private Button mRedButton;
    private Button mOrangeButton;
    private Button mYellowButton;
    private Button mGreenButton;
    private Button mBlueButton;
    private Button mPurpleButton;

    private Button mSelectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = findViewById(R.id.participants_rv);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewAdapter = new ParticipantsEmailRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mSubjectTextView = findViewById(R.id.subject_edit_text);
        mLocationTextView = findViewById(R.id.location_edit_text);
        mAddEmailButton = findViewById(R.id.add_email_button);
        mEmailTextView = findViewById(R.id.email_edit_text);
        mDateTimePickerButton  = findViewById(R.id.date_time_picker_button);
        //mNextButton = findViewById(R.id.next_to_date_button);
        mRedButton = findViewById(R.id.red_button);
        mOrangeButton = findViewById(R.id.orange_button);
        mYellowButton = findViewById(R.id.yellow_button);
        mGreenButton = findViewById(R.id.green_button);
        mBlueButton = findViewById(R.id.blue_button);
        mPurpleButton = findViewById(R.id.purple_button);

        configureAddEmailButton();
        configureEmailTextView();
        configureColorButtons();
        configureDateTimePickerButton();
        //configureNextButton();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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

    private void configureDateTimePickerButton() {
        mDateTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SingleDateAndTimePickerDialog.Builder(mContext)
                        .bottomSheet()
                        .curved()
                        .displayMinutes(true)
                        .displayHours(true)
                        .displayDays(false)
                        .displayMonth(true)
                        .displayYears(true)
                        .displayDaysOfMonth(true)
                        .displayAmPm(false)
                        .mustBeOnFuture()
                        .minutesStep(15)
                        .display();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        if (isColorButton(buttonId)) setSelectedButton((Button) v);
    }

    private boolean isColorButton(int id) {
        return  (id == R.id.red_button || id == R.id.orange_button || id == R.id.yellow_button || id == R.id.green_button || id == R.id.blue_button || id == R.id.purple_button);
    }

    private void setSelectedButton(Button button) {
        int buttonId = button.getId();
        mSelectedButton = button;
        mRedButton.setBackground(getResources().getDrawable((mRedButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
        mOrangeButton.setBackground(getResources().getDrawable((mOrangeButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
        mYellowButton.setBackground(getResources().getDrawable((mYellowButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
        mGreenButton.setBackground(getResources().getDrawable((mGreenButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
        mBlueButton.setBackground(getResources().getDrawable((mBlueButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
        mPurpleButton.setBackground(getResources().getDrawable((mPurpleButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
    }

    /*private void configureNextButton() {
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> missingParams = new ArrayList<>();

                String subject = mSubjectTextView.getText().toString();
                if (!subject.equals("")) MeetingBuilder.builder.setSubject(subject);
                else missingParams.add("le sujet");

                String location = mLocationTextView.getText().toString();
                if (!location.equals("")) MeetingBuilder.builder.setLocation(location);
                else missingParams.add("le lieu");

                List<String> participants = mRecyclerViewAdapter.getEmailList();
                if (participants.size() > 0) MeetingBuilder.builder.setParticipants(participants);
                else missingParams.add("les participants");

                MeetingBuilder.builder.setColor(getSelectedColor());

                if (missingParams.size() > 0) {
                    StringBuilder message = new StringBuilder("Veuillez indiquer ");
                    for (int i = 0; i < missingParams.size(); i++) {
                        message.append(missingParams.get(i));
                        if (i == missingParams.size() - 2) message.append(" et ");
                        else if (i < missingParams.size() - 2) message.append(", ");
                    }
                    message.append(".");

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Erreur")
                            .setMessage(message)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            })
                            .create()
                            .show();
                } else {
                    Log.d(TAG, "onClick: meeting ok");
                    // TODO: create meeting and add it to list
                }
            }
        });
    }*/

    private int getSelectedColor() {
        if (mRedButton.equals(mSelectedButton)) return R.color.MeetingRed;
        else if (mOrangeButton.equals(mSelectedButton)) return R.color.MeetingOrange;
        else if (mYellowButton.equals(mSelectedButton)) return R.color.MeetingYellow;
        else if (mGreenButton.equals(mSelectedButton)) return R.color.MeetingGreen;
        else if (mBlueButton.equals(mSelectedButton)) return R.color.MeetingBlue;
        else return R.color.MeetingPurple;
    }

    @Override
    public void onDeleteEmail(int position) {
        mRecyclerView.removeViewAt(position);
        mRecyclerViewAdapter.notifyItemRemoved(position);
        mRecyclerViewAdapter.notifyItemRangeChanged(position, mRecyclerViewAdapter.getEmailList().size());
    }
}
