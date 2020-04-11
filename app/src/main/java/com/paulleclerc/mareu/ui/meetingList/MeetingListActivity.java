package com.paulleclerc.mareu.ui.meetingList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.model.Meeting;
import com.paulleclerc.mareu.model.MeetingService;
import com.paulleclerc.mareu.ui.addMeeting.AddMeetingActivity;

import java.util.Calendar;

public class MeetingListActivity extends AppCompatActivity implements MeetingListRecyclerViewAdapter.MeetingListRVEvents {
    public static final String DATE_FORMATTER = "dd/MM/yy HH:mm:ss";

    private MeetingListRecyclerViewAdapter mAdapter;

    private final MeetingService mMeetingService = new MeetingService();

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
                final Context context = this;
                new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                                final int month = monthOfYear + 1;
                                Calendar cal = Calendar.getInstance();
                                cal.set(year, monthOfYear, dayOfMonth);
                                mAdapter.filterBy(cal.getTime());
                            }
                        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                        .show();
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
