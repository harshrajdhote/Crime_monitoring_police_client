package com.example.android.police_client;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_list,parent,false);
      return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RequestViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return data.description.length;
    }
    public class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
      private TextView description;
      private Button view_req,accept,status;
      public RequestViewHolder(View itemView){
          super(itemView);
          description = itemView.findViewById(R.id.request_description);
          view_req = itemView.findViewById(R.id.view_request);
          accept = itemView.findViewById(R.id.request_accept);
          status = itemView.findViewById(R.id.request_status);
          itemView.setOnClickListener(this);

      }
      public void bindView(int pos){
          description.setText(data.description[pos]);
          view_req.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Toast.makeText(view.getContext(), "view is clicked", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(view.getContext(), MapsActivity.class);
                  view.getContext().startActivity(intent);

              }
          });
      }
      public void onClick(View view){
          Toast.makeText(view.getContext(), "Item is clicked ", Toast.LENGTH_SHORT).show();
      }
    }
}
