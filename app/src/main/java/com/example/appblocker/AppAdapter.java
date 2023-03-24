package com.example.appblocker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.adapter_design_backend> {

    List<AppModel> appModels = new ArrayList<>();
    Context con;
    List<String> lockedApps = new ArrayList<>();

    public AppAdapter(List<AppModel> appModels, Context con) {
        this.appModels = appModels;
        this.con = con;
    }

    @NonNull
    @Override
    public adapter_design_backend onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  // This will show what will show on screen

        // with this line of code, we import the front-end design to back-end and it is saved in the view
        View view = LayoutInflater.from(con).inflate(R.layout.adapter_design, parent, false);
        adapter_design_backend design = new adapter_design_backend(  view );

        return design;   // if we write "return view" it will give error so we create an object and store value there and then return it.
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_design_backend holder, int position) {
        // earlier in our design there is green square and text-box, in place of green box and text-box we need appicon and appname. The code is written here

        AppModel app = appModels.get(position);

        holder.appname.setText(app.getAppname());
        holder.appicon.setImageDrawable(app.getAppicon());

        if(app.getStatus() == 0){
            holder.appstatus.setImageResource(R.drawable.unlock);
        }
        else{
            holder.appstatus.setImageResource(R.drawable.lock);

            lockedApps.add(app.getPackagename());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (app.getStatus()==0){     // if == 0 means it is unlocked
                    app.setStatus(1);
                    holder.appstatus.setImageResource(R.drawable.lock);
                    Toast.makeText(con, app.getAppname()+" is locked", Toast.LENGTH_LONG).show();
                    lockedApps.add(app.getPackagename());
                    SharedPrefUtil.getInstance(con).putListString(lockedApps);
                }
                else {
                    app.setStatus(0);
                    holder.appstatus.setImageResource(R.drawable.unlock);
                    Toast.makeText(con, app.getAppname()+" is unlocked", Toast.LENGTH_LONG).show();
                    lockedApps.remove(app.getPackagename());
                    SharedPrefUtil.getInstance(con).putListString(lockedApps);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return appModels.size();
    }

    public class adapter_design_backend extends RecyclerView.ViewHolder{

        TextView appname;
        ImageView appicon, appstatus;

        public adapter_design_backend(@NonNull View itemView) {
            super(itemView);
            appname = itemView.findViewById(R.id.appname);
            appicon = itemView.findViewById(R.id.appicon);
            appstatus = itemView.findViewById(R.id.appstatus);
        }
    }

}
