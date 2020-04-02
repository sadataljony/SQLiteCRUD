package com.sadataljony.app.android.sqlitecrud.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sadataljony.app.android.sqlitecrud.R;
import com.sadataljony.app.android.sqlitecrud.activity.dialog.DialogEditDeleteCloseActivity;
import com.sadataljony.app.android.sqlitecrud.activity.list.ListPersonActivity;
import com.sadataljony.app.android.sqlitecrud.model.Person;

import java.util.ArrayList;

/**
 * Created by Muhammad Sadat Al-Jony on 02-April-2020.
 */

public class AdapterPerson extends RecyclerView.Adapter<AdapterPerson.ViewHolder> implements Filterable {
    private ArrayList<Person> list;
    private ArrayList<Person> listFiltered;
    private Context pContext;
    private AdapterPerson.AdapterListener listener;
    private ListPersonActivity activity;

    public AdapterPerson(ArrayList<Person> data, Context context, AdapterPerson.AdapterListener listener, ListPersonActivity activity) {
        this.pContext = context;
        this.listener = listener;
        this.list = data;
        this.listFiltered = data;
        this.activity = activity;
    }

    // layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_person, viewGroup, false);
        return new ViewHolder(view);
    }

    // set all values in individual row
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.textViewId.setText(String.valueOf(listFiltered.get(i).getId()));
        viewHolder.textViewName.setText(listFiltered.get(i).getName());
        viewHolder.textViewAge.setText(String.valueOf(listFiltered.get(i).getAge()));
        viewHolder.textViewPhone.setText(listFiltered.get(i).getPhone());
        viewHolder.textViewEmail.setText(String.valueOf(listFiltered.get(i).getEmail()));
        viewHolder.textViewAddress.setText(listFiltered.get(i).getAddress());
        viewHolder.textViewSalary.setText(String.valueOf(listFiltered.get(i).getSalary()));

        viewHolder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // set onClickListener in individual person list row
        viewHolder.listRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = viewHolder.getAdapterPosition();
                Intent intent = new Intent(v.getContext(), DialogEditDeleteCloseActivity.class);// go to DialogEditDeleteCloseActivity after click in person list row
                intent.putExtra("id", listFiltered.get(n).getId());// pass id to DialogEditDeleteCloseActivity
                intent.putExtra("name", listFiltered.get(n).getName());// pass name to DialogEditDeleteCloseActivity
                v.getContext().startActivity(intent);
            }
        });
    }

    // initialize all ui components in row layout
    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout listRow;
        private TextView textViewId, textViewName, textViewAge, textViewPhone, textViewEmail, textViewAddress, textViewSalary;

        public ViewHolder(View view) {
            super(view);
            listRow = view.findViewById(R.id.row_person);
            textViewId = view.findViewById(R.id.textViewId);
            textViewName = view.findViewById(R.id.textViewName);
            textViewAge = view.findViewById(R.id.textViewAge);
            textViewPhone = view.findViewById(R.id.textViewPhone);
            textViewEmail = view.findViewById(R.id.textViewEmail);
            textViewAddress = view.findViewById(R.id.textViewAddress);
            textViewSalary = view.findViewById(R.id.textViewSalary);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(listFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (listFiltered == null) ? 0 : listFiltered.size();
    }

    // method for search person name in person list
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFiltered = list;
                } else {
                    ArrayList<Person> filteredList = new ArrayList<>();
                    for (Person row : list) {
                        // name match condition. this might differ depending on your requirement
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    listFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFiltered = (ArrayList<Person>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface AdapterListener {
        void onContactSelected(Person person);
    }

}