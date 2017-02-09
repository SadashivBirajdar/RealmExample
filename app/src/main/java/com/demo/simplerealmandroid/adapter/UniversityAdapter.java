package com.demo.simplerealmandroid.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.simplerealmandroid.R;
import com.demo.simplerealmandroid.model.University;
import com.demo.simplerealmandroid.utils.Constants;
import com.demo.simplerealmandroid.view.activity.StudentsActivity;

import io.realm.RealmList;

/**
 * Created by emb-sadabir on 7/2/17.
 */
public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder> {

    private RealmList<University> universities;

    public UniversityAdapter(RealmList<University> universities) {
        this.universities = universities;
    }

    @Override
    public UniversityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_university, parent, false);
        return new UniversityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UniversityViewHolder holder, int position) {
        holder.tvUniversityName.setText(universities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }

    public class UniversityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvUniversityName;

        public UniversityViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvUniversityName = (TextView) itemView.findViewById(R.id.tv_name_university);
        }

        @Override
        public void onClick(View v) {
            University university = universities.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), StudentsActivity.class);
            intent.putExtra(Constants.ID, university.getId());
            v.getContext().startActivity(intent);
        }
    }
}
