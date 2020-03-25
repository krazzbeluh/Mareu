package com.paulleclerc.mareu.ui.meetingList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.model.Meeting;
import com.paulleclerc.mareu.ui.addMeeting.AddMeetingActivity;

public class MeetingListActivity extends AppCompatActivity implements MeetingListRecyclerViewAdapter.MeetingListRVEvents {
    private static final String TAG = MeetingListActivity.class.getSimpleName();

    private MeetingListRecyclerViewAdapter mAdapter;

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
        if (id == R.id.sort_meeting_button) {
            Toast.makeText(MeetingListActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addMeeting(View view) {
        Intent intent = new Intent(MeetingListActivity.this, AddMeetingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyItemInserted(Meeting.getMeetingList().size() - 1);
    }

    @Override
    public void onDeleteMeeting(Meeting meeting) {
        Meeting.removeMeeting(meeting);
        mAdapter.notifyItemRemoved(Meeting.getMeetingList().size() - 1);
    }
}
