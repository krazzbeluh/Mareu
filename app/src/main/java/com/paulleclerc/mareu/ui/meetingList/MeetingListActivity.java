package com.paulleclerc.mareu.ui.meetingList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.ui.addMeeting.AddMeetingActivity;

public class MeetingListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MeetingListRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        mRecyclerView = findViewById(R.id.meeting_list_view);

        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new MeetingListRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void addMeeting(View view) {
        Intent intent = new Intent(MeetingListActivity.this, AddMeetingActivity.class);
        startActivity(intent);
    }
}
