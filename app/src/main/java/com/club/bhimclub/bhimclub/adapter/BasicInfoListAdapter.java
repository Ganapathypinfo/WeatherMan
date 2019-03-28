package com.club.bhimclub.bhimclub.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.club.bhimclub.bhimclub.R;
import com.club.bhimclub.bhimclub.model.BasicInfoList;
import com.club.bhimclub.bhimclub.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class BasicInfoListAdapter extends  RecyclerView.Adapter<BasicInfoListAdapter.MyViewHolder>
        implements Filterable {

    private Context context;
//    public OnViewHolderClick<BasicInfoList> listener;
    private List<BasicInfoList.BasicInfo> contactList;
    private List<BasicInfoList.BasicInfo> contactListFiltered;
    private boolean mProfileRequests = false;
    private boolean mConnections = false;
    private BasicInfoListAdapter.BasicInfoListAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone, badge;
        public ImageView thumbnail, ivAccept, ivDeny;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_full_name);
            phone = view.findViewById(R.id.tv_designation);
            thumbnail = view.findViewById(R.id.profile_image);
            ivAccept = view.findViewById(R.id.ivAccept);
            ivDeny = view.findViewById(R.id.ivDeny);
            badge = view.findViewById(R.id.badge_notification);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public BasicInfoListAdapter(Context context, List<BasicInfoList.BasicInfo> contactList, BasicInfoListAdapter.BasicInfoListAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }
    public BasicInfoListAdapter(Context context, boolean mProfileRequests, boolean mConnections, List<BasicInfoList.BasicInfo> contactList, BasicInfoListAdapter.BasicInfoListAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.mConnections = mConnections;
        this.mProfileRequests = mProfileRequests;
    }



    @Override
    public BasicInfoListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_recycler_card_layout, parent, false);

        return new BasicInfoListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BasicInfoListAdapter.MyViewHolder holder, final int position) {
        final BasicInfoList.BasicInfo contact = contactListFiltered.get(position);
        holder.name.setText(contact.getFirstname());
        holder.phone.setText(contact.getDesignation());
        if(mProfileRequests){
            holder.badge.setVisibility(View.GONE);
            holder.ivAccept.setVisibility(View.VISIBLE);
            holder.ivDeny.setVisibility(View.VISIBLE);
        }else if(mConnections){
            holder.badge.setVisibility(View.GONE);
            holder.ivAccept.setVisibility(View.GONE);
            holder.ivDeny.setVisibility(View.VISIBLE);

        }else{
            holder.ivAccept.setVisibility(View.GONE);
            holder.ivDeny.setVisibility(View.GONE);
            if(contact.isMsgRecived()){
                holder.badge.setVisibility(View.VISIBLE);
                holder.badge.setText(String.valueOf(contact.getBadgeCount()));
            }else{
                holder.badge.setVisibility(View.GONE);
                holder.badge.setText("");
            }

        }


        String pictureUri = "http://bhimclub.com/webupload/thumb/1a37d2c9-7d8c-4561-9321-8365ca8f760c_1542137706.jpg";
        Glide.with(context)
//                .load(contact.getProfile_image())
                /*.load(pictureUri)
                .apply(RequestOptions.placeholderOf(R.drawable.contact_icon_profile)

                        .override(100,100))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
                .asBitmap().
                load(pictureUri)
                .apply(new RequestOptions().override(50, 50))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        // resource is your loaded Bitmap
                        holder.thumbnail.setImageBitmap(resource);
                        return true;
                    }
                }).submit();

    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<BasicInfoList.BasicInfo> filteredList = new ArrayList<>();
                    for (BasicInfoList.BasicInfo row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFirstname().toLowerCase().contains(charString.toLowerCase()) || row.getDesignation().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<BasicInfoList.BasicInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface BasicInfoListAdapterListener {
        void onContactSelected(BasicInfoList.BasicInfo contact);
    }


}
