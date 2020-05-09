package rna.FinalProjectAliceandRoma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> topics;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    RecyclerViewAdapter(List<String> topics, Context context) {
        this.topics = topics;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String topic = topics.get(position);
        holder.title.setText(topic);

    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);

        }
            @Override
            public void onClick(View view){

                if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());

            }
        }

        String getItem(int id) { return topics.get(id); }

        void setClickListener(ItemClickListener itemClickListener) { this.clickListener = itemClickListener; }


        public interface ItemClickListener {

            void onItemClick(View view, int position);

        }
    }
