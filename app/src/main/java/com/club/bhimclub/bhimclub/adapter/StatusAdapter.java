package com.club.bhimclub.bhimclub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.model.Contact;
import com.club.bhimclub.bhimclub.model.StatusPost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 16/11/17.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<StatusPost> StatusPost;
    private List<StatusPost> StatusPostFiltered;
    private StatusPostAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mPostName, mTitle, mSubject, mTime, mComment, mViews, mLike ;
//        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            mPostName = view.findViewById(R.id.tvPostName);
            mTitle = view.findViewById(R.id.tv_postTitle);
            mTime = view.findViewById(R.id.tvPostTime);
            mSubject = view.findViewById(R.id.tv_postSubject);
            mComment = view.findViewById(R.id.tv_postComment);
            mViews = view.findViewById(R.id.tv_postViews);
            mLike = view.findViewById(R.id.tv_postLike);

//            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(StatusPostFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public StatusAdapter(Context context, List<StatusPost> StatusPost, StatusPostAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.StatusPost = StatusPost;
        this.StatusPostFiltered = StatusPost;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_single, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final StatusPost statuspost = StatusPostFiltered.get(position);
        holder.mPostName.setText(statuspost.getmName() + " Shared a post.");
        holder.mTitle.setText(statuspost.getmTitle());
        holder.mTime.setText(statuspost.getmTime());
        holder.mSubject.setText(statuspost.getmSubject());
//        holder.mViews.setText(statuspost.getmViews());

       /* Glide.with(context)
                .load(contact.getBitmap() != null ? contact.getBitmap(): R.drawable.ic_account_circle_black_24dp)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return StatusPostFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    StatusPostFiltered = StatusPost;
                } else {
                    List<StatusPost> filteredList = new ArrayList<>();
                    for (StatusPost row : StatusPost) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getmTitle().toLowerCase().contains(charString.toLowerCase()) || row.getmSubject().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    StatusPostFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = StatusPostFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                StatusPostFiltered = (ArrayList<StatusPost>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface StatusPostAdapterListener {
        void onContactSelected(StatusPost statuspost);
    }
}
