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
    private BasicInfoListAdapter.BasicInfoListAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_full_name);
            phone = view.findViewById(R.id.tv_designation);
            thumbnail = view.findViewById(R.id.profile_image);

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


        Glide.with(context)
//                .load(contact.getProfile_image())
                .load("http://bhimclub.com/webupload/thumb/1a37d2c9-7d8c-4561-9321-8365ca8f760c_1542137706.jpg")
                .apply(RequestOptions.placeholderOf(R.drawable.contact_icon_profile)
                        .error(R.drawable.ic_blocking)
                        .override(500,500))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);

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
