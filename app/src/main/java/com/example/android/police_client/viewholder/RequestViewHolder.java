package com.example.android.police_client.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.police_client.R;
import com.example.android.police_client.modal.request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestViewHolder extends RecyclerView.ViewHolder {
    public TextView description;
    public Button view_req,accept,status;
    public RequestViewHolder(View itemView){
        super(itemView);
        description = itemView.findViewById(R.id.request_description);
        view_req = itemView.findViewById(R.id.view_request);
        accept = itemView.findViewById(R.id.request_accept);
        status = itemView.findViewById(R.id.request_status);
    }
}
