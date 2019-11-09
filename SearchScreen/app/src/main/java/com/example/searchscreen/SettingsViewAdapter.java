package com.example.searchscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SettingsViewAdapter extends RecyclerView.Adapter<SettingsViewAdapter.ViewHolder> {

    public ArrayList<String> mImageNames = new ArrayList<>();
    public ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public SettingsViewAdapter(ArrayList<String> mImageNames, ArrayList<String> mImages, Context mContext){
        this.mImages = mImages;
        this.mImageNames = mImageNames;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.imageName.setText(mImageNames.get(position));

        holder.settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

/*                if (mImageNames.get(position)=="Define") {
                    Toast.makeText(mContext, "Define", Toast.LENGTH_SHORT).show();
                    MainActivity.test();
                }
                else if (mImageNames.get(position)=="Find") {
                    Toast.makeText(mContext, "Find", Toast.LENGTH_SHORT).show();
                    MainActivity.test();

                }
                else if (mImageNames.get(position)=="Image Search") {
                    Toast.makeText(mContext, "Image Search", Toast.LENGTH_SHORT).show();
                    MainActivity.test();

                }
                else if (mImageNames.get(position)=="Text-to-Speech") {
                    Toast.makeText(mContext, "Text-to-Speech", Toast.LENGTH_SHORT).show();
                    MainActivity.test();

                }
                else if (mImageNames.get(position)=="Translate") {
                    Toast.makeText(mContext, "Translate", Toast.LENGTH_SHORT).show();
                    MainActivity.test();
*/
                }
//                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();
    //        }
        });

    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView imageName;
        RelativeLayout settingsLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.settings_image);
            imageName = itemView.findViewById(R.id.settings_image_name);
            settingsLayout = itemView.findViewById(R.id.settings_layout);
        }
    }
}
