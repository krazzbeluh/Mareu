package com.paulleclerc.mareu.ui.addMeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.model.Meeting;
import com.paulleclerc.mareu.model.MeetingService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.paulleclerc.mareu.model.MeetingService.DATE_FORMATTER;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener, ParticipantsEmailRecyclerViewAdapter.DeleteEmailCallback {

    private static final Pattern mEmailPattern = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
    private static final String TAG = AddMeetingActivity.class.getSimpleName();

    private final Context mContext = this;

    private final MeetingService mMeetingService = new MeetingService();
    private final SimpleDateFormat mDateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRANCE);

    private LinearLayoutManager mLinearLayoutManager;
    private ParticipantsEmailRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    private TextView mSubjectTextView;
    private Spinner mLocationSpinner;
    private TextView mEmailTextView;
    private ImageButton mAddEmailButton;
    private TextView mDateTimePickerView;
    private Button mDoneButton;

    private Button mRedButton;
    private Button mOrangeButton;
    private Button mYellowButton;
    private Button mGreenButton;
    private Button mBlueButton;
    private Button mPurpleButton;

    private Button mSelectedButton;
    private Date mSelectedDate;

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
        mLocationSpinner = findViewById(R.id.location_spinner);
        mAddEmailButton = findViewById(R.id.add_email_button);
        mEmailTextView = findViewById(R.id.email_edit_text);
        mDateTimePickerView = findViewById(R.id.meetingDate_editText);
        mDoneButton = findViewById(R.id.done_button);
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
        configureDoneButton();
    }

    private void configureAddEmailButton() {
        mAddEmailButton.setEnabled(false);
        mAddEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mEmailTextView.getText().toString().toLowerCase();
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
                String email = s.toString().toLowerCase();
                boolean isEmail = mEmailPattern.matcher(email).matches();
                mAddEmailButton.setEnabled(isEmail);
                mAddEmailButton.setBackgroundTintList(isEmail ? ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)) : ColorStateList.valueOf(getResources().getColor(R.color.grayOut)));
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
        mDateTimePickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                                //*************Call Time Picker Here ********************
                                new TimePickerDialog(mContext,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                String dateStr = dayOfMonth + "-" + monthOfYear + "-" + year + " " + hourOfDay + ":" + minute + ":00";

                                                try {
                                                    Date date = mDateFormatter.parse(dateStr);
                                                    setDate(date);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), true)
                                        .show();
                            }
                        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
    }

    private void setDate(Date date) {
        mSelectedDate = date;
        String dateTime = mDateFormatter.format(date);
        mDateTimePickerView.setText(dateTime);
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
        mRedButton.setContentDescription((mRedButton.getId() == buttonId) ? "Bouton rouge sélectionné" : "Bouton rouge désélectionné");
        mOrangeButton.setBackground(getResources().getDrawable((mOrangeButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
        mOrangeButton.setContentDescription((mOrangeButton.getId() == buttonId) ? "Bouton orange sélectionné" : "Bouton orange désélectionné");
        mYellowButton.setBackground(getResources().getDrawable((mYellowButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
        mYellowButton.setContentDescription((mYellowButton.getId() == buttonId) ? "Bouton jaune sélectionné" : "Bouton jaune désélectionné");
        mGreenButton.setBackground(getResources().getDrawable((mGreenButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
        mGreenButton.setContentDescription((mGreenButton.getId() == buttonId) ? "Bouton vert sélectionné" : "Bouton vert désélectionné");
        mBlueButton.setBackground(getResources().getDrawable((mBlueButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
        mBlueButton.setContentDescription((mBlueButton.getId() == buttonId) ? "Bouton bleu sélectionné" : "Bouton bleu désélectionné");
        mPurpleButton.setBackground(getResources().getDrawable((mPurpleButton.getId() == buttonId) ? R.drawable.ic_check_box_white : R.drawable.ic_box_white));
        mPurpleButton.setContentDescription((mPurpleButton.getId() == buttonId) ? "Bouton violet sélectionné" : "Bouton violet désélectionné");
    }

    private void configureDoneButton() {
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> missingParams = new ArrayList<>();

                String subject = mSubjectTextView.getText().toString();
                if (subject.equals("")) missingParams.add("le sujet");

                List<String> participants = mRecyclerViewAdapter.getEmailList();
                if (!(participants.size() > 0)) missingParams.add("les participants");

                if (mSelectedDate == null) missingParams.add("la date");

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


                    String location = String.valueOf(mLocationSpinner.getSelectedItem());
                    Meeting newMeeting = new Meeting(mSelectedDate, location, subject, participants, getSelectedColor());
                    mMeetingService.addMeeting(newMeeting);
                    finish();
                }
            }
        });
    }

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
