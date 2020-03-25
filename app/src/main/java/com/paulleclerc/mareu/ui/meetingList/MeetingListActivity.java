package com.paulleclerc.mareu.ui.meetingList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.model.Meeting;
import com.paulleclerc.mareu.model.MeetingService;
import com.paulleclerc.mareu.ui.addMeeting.AddMeetingActivity;

import java.util.Date;

public class MeetingListActivity extends AppCompatActivity implements MeetingListRecyclerViewAdapter.MeetingListRVEvents {
    private static final String TAG = MeetingListActivity.class.getSimpleName();

    private MeetingListRecyclerViewAdapter mAdapter;

    private MeetingService mMeetingService = new MeetingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        RecyclerView recyclerView = findViewById(R.id.meeting_list_view);

        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MeetingListRecyclerViewAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_filter_date:
                Date date = new Date();
                new SingleDateAndTimePickerDialog.Builder(this)
                        .title(getResources().getString(R.string.filter_by_date))
                        .displayMinutes(false)
                        .mainColor(getResources().getColor(R.color.colorPrimary))
                        .displayHours(false)
                        .displayDays(false)
                        .displayMonth(true)
                        .displayYears(true)
                        .displayDaysOfMonth(true)
                        .displayAmPm(false)
                        .mustBeOnFuture()
                        .minutesStep(15)
                        .defaultDate(date)
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                mAdapter.filterBy(date);
                            }
                        })
                        .display();
                return true;
            case R.id.action_filter_room:
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.filter_by_room))
                        .setItems(R.array.meetingLocations, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAdapter.filterBy(getResources().getStringArray(R.array.meetingLocations)[which]);
                            }
                        })
                        .show();
                return true;
            case R.id.action_no_filter:
                mAdapter.noFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addMeeting(View view) {
        Intent intent = new Intent(MeetingListActivity.this, AddMeetingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.noFilter();
    }

    @Override
    public void onDeleteMeeting(Meeting meeting) {
        mMeetingService.removeMeeting(meeting);
        mAdapter.notifyDataSetChanged();
    }
}
