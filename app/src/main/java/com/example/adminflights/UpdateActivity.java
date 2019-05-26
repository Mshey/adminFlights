package com.example.adminflights;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adminflights.model.Ticket;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SearchActivity";
    private static final String TICKETS = "tickets";
    private String ticketId = "0";
    private String button;

    private EditText where;
    private EditText from;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getIncomingIntent();
        initListeners();

    }

    private void initListeners(){
        findViewById(R.id.selectDate).setOnClickListener(this);
        Button b = findViewById(R.id.update);
        b.setText(button);
        b.setOnClickListener(this);
        where = findViewById(R.id.where);
        from = findViewById(R.id.from);
        date = findViewById(R.id.date);

        if (button.equals("Update")) {
            //current.getFrom()+current.getTo()+current.getDate();
            String[] dateString = ticketId.split("-");
            ticketId = dateString[0];
            from.setText(dateString[1]);
            where.setText(dateString[2]);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(dateString[3]));

            String s = calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
            date.setText(s);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                makeCalendar();
                break;
            case R.id.update:
                update();
                break;
        }
    }

    private void makeCalendar(){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        Log.d(TAG, "calendar things: " + day + " " + month + " " + year);

        DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonth, int dayOfMonth) {
                mmonth+=1;
                String s = myear + "/" + mmonth + "/" + dayOfMonth;
                TextView textView = findViewById(R.id.date);
                textView.setText(s);
            }
        }, day, month, year);

        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    private void update() {

        Log.d(TAG, button + " button pushed");
        String[] dateString = date.getText().toString().split("/");
        Date date1 = new Date(Integer.parseInt(dateString[0]) - 1900, Integer.parseInt(dateString[1]), Integer.parseInt(dateString[2]));

        Ticket ticket = new Ticket(ticketId, from.getText().toString(), where.getText().toString(), date1);

        FirebaseDatabase.getInstance().getReference()
                .child(TICKETS)
                .child(ticketId)
                .setValue(ticket);
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if (getIntent().hasExtra("id")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            ticketId = getIntent().getStringExtra("id");
        }

        if (getIntent().hasExtra("button")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            button = getIntent().getStringExtra("button");
        }
    }
}