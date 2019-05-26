package com.example.adminflights;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.adminflights.adapters.TicketAdapter;
import com.example.adminflights.model.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String TICKETS = "tickets";

    private Integer  numberOfTickets = 0;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        findViewById(R.id.add).setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TicketAdapter adapter = new TicketAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(TICKETS);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MyCallback myCallback = new MyCallback() {
                    @Override
                    public void onCallback(Ticket ticket) {
                        adapter.addToTickets(ticket);
                        numberOfTickets++;
                    }
                };
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Ticket ticket = ds.getValue(Ticket.class);
                    Log.d(TAG, "ticket stuff:" + Objects.requireNonNull(ticket).getId() + " " + ticket.getFrom() + ticket.getTo() + ticket.getDate());
                    //addToTheList(ticket);
                    myCallback.onCallback(ticket);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //or at least an alert
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Log.d(TAG, "clicked on add");
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(numberOfTickets + 1));
                intent.putExtra("button", "Add");
                startActivity(intent);
                break;
        }
    }
}
