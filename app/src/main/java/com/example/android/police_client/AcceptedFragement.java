package com.example.android.police_client;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.police_client.modal.request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AcceptedFragement extends Fragment {
    private boolean accepted_button_stat = false;
    private View AccFragmentView;
    private RecyclerView myRequestList;
    private DatabaseReference requestRef;
    static  int cnt;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState){
        AccFragmentView = inflater.inflate(R.layout.fragement_accepted_requests,container,false);
        requestRef = FirebaseDatabase.getInstance().getReference().child("Test");
        myRequestList = AccFragmentView.findViewById(R.id.acc_request_list);

        myRequestList.setLayoutManager(new LinearLayoutManager(getContext()));
        return AccFragmentView;
    }
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<request> options =
                new FirebaseRecyclerOptions.Builder<request>()
                        .setQuery(requestRef,request.class)
                        .build();
        FirebaseRecyclerAdapter<request,RequestViewHolder> adapter =
                new FirebaseRecyclerAdapter<request, AcceptedFragement.RequestViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AcceptedFragement.RequestViewHolder holder, int i, @NonNull final request request) {
                        final Button accptref = holder.itemView.findViewById(R.id.acc_points);
                        //final DatabaseReference updateRef = getRef(i).child("status");

                        if (request.isStatus()) {

                        holder.itemView.findViewById(R.id.acc_request_description).setVisibility(View.VISIBLE);
                        holder.itemView.findViewById(R.id.acc_points).setVisibility(View.VISIBLE);
                        holder.itemView.findViewById(R.id.acc_request_status).setVisibility(View.VISIBLE);
                        holder.itemView.findViewById(R.id.acc_view_request).setVisibility(View.VISIBLE);


                        final double Lat = request.getLatitude();
                        final double Long = request.getLongitude();
                        final long contact = request.getContact();
                        final String desc = request.getDesc();
                        final boolean makePublicStatus = request.isMakePublicStatus();
                        final boolean status = request.isStatus();
                        final String type = request.getType();


//                        if(request.isStatus())
//                            accptref.setText("Accepted");
//                        else
//                            accptref.setText("Accept");
                        holder.description.setText(request.getDesc());
                        final Button status_ref = holder.itemView.findViewById(R.id.acc_request_status);
                        if (request.getResolve().equals("y")) {
//                     Toast.makeText(getContext(),""+request.getResolve(),Toast.LENGTH_SHORT).show();
                            status_ref.setText("Resolved");
                            status_ref.setBackgroundColor(Color.parseColor("#87CEEB"));
                            cnt += 100;

                        } else {
//                     Toast.makeText(getContext(), "" + request.getResolve(), Toast.LENGTH_SHORT).show();
                            status_ref.setText("Not Resolved");
                            status_ref.setBackgroundColor(Color.parseColor("#AF002A"));
                        }
                            TextView points = holder.itemView.findViewById(R.id.acc_points);
                            points.setText(""+cnt);
                        holder.itemView.findViewById(R.id.acc_view_request).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(view.getContext(), "view is clicked", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                                intent.putExtra("lat", Lat);
                                intent.putExtra("long", Long);
                                intent.putExtra("desc", desc);
                                intent.putExtra("contact", contact);
                                intent.putExtra("type", type);
                                intent.putExtra("status", status);
                                //request.setStatus(true);
                                view.getContext().startActivity(intent);

                            }
                        });

//                        holder.itemView.findViewById(R.id.request_accept).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                updateRef.setValue(true);
//                                accptref.setText("Accepted");
//                                accepted_button_stat = true;
//
//
//
//                            }
//                        });
//                 final String list_request = getRef(i).getKey();
//                 DatabaseReference getTypeRef = getRef(i).child("801").getRef();
//                 getTypeRef.addValueEventListener(new ValueEventListener() {
//                     @Override
//                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                         if(dataSnapshot.exists())
//                         {
//                             String type = dataSnapshot.child("desc").toString();
//
//                         }
//
//                     }
//
//                     @Override
//                     public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                     }
//                 })
                        //Toast.makeText(getActivity(),"sd"+request.getDesc(),Toast.LENGTH_SHORT).show();

                    }
                } //end if
                    @NonNull
                    @Override
                    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accepted_list,parent,false);
                        RequestViewHolder holder = new RequestViewHolder(view);
                        return holder;
                    }
                };


        myRequestList.setAdapter(adapter);
        adapter.startListening();

    }
    public static class RequestViewHolder extends RecyclerView.ViewHolder{
        TextView description;
        Button accept,view,status;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.acc_request_description);
            accept = itemView.findViewById(R.id.acc_points);
            view = itemView.findViewById(R.id.acc_view_request);
            status = itemView.findViewById(R.id.acc_request_status);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cnt = 0;
    }
}
