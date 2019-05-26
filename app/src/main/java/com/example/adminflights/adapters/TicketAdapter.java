package com.example.adminflights.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adminflights.R;
import com.example.adminflights.UpdateActivity;
import com.example.adminflights.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private static final String TAG = "TicketAdapter";

    class TicketViewHolder extends RecyclerView.ViewHolder {
        private final TextView ticketItemView;
        private final LinearLayout layout;

        private TicketViewHolder(View itemView) {
            super(itemView);
            ticketItemView = itemView.findViewById(R.id.textView);
            layout = itemView.findViewById(R.id.recycler_view_item);
        }
    }

    private final LayoutInflater mInflater;
    private List<Ticket> tickets;
    private Context context;

    public TicketAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        tickets = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TicketViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        if (tickets != null) {
            final Ticket current = tickets.get(position);
            final String s = current.getId() + "-" + current.getFrom() + "-" +current.getTo() + "-" + current.getDate().getTime();
            holder.ticketItemView.setText(s);

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"onClick: clicked on:" + s);

                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("id", s);
                    intent.putExtra("button", "Update");
                    context.startActivity(intent);
                }
            });

        } else {
            // Covers the case of data not being ready yet.
            holder.ticketItemView.setText("No tickets");
        }
    }

    public void addToTickets(Ticket ticket) {
        tickets.add(ticket);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (tickets != null)
            return tickets.size();
        else return 0;
    }
}
