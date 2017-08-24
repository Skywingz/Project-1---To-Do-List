package immersive.android.assembly.general.ga_todo_list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private ArrayList<ItemObject> items;
    private Context context;

    private static OnItemClickListener listener;



    public ItemAdapter(ArrayList<ItemObject> item) {
        this.items = item;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int itemID);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }





    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_object_layout, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final boolean checked = items.get(position).isChecked();
        final int itemID = items.get(position).getId();
        final int groupID = items.get(position).getGroupID();

        if (checked) {
            holder.layout.setBackgroundResource(R.color.light_green_A700);
        } else {
            holder.layout.setBackgroundResource(R.color.transparent);
        }

        holder.title.setText(items.get(position).getTitle());
        holder.description.setText(items.get(position).getDescription());
        holder.box.setChecked(checked);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(view, itemID);
                }
            }
        };

        holder.delete.setOnClickListener(clickListener);
        holder.title.setOnClickListener(clickListener);

        holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SingletonManager manager = SingletonManager.getInstance();
                if (b) {
                    holder.layout.setBackgroundResource(R.color.light_green_A700);
                } else {
                    holder.layout.setBackgroundResource(R.color.transparent);
                }

                manager.updateItemChecked(itemID, groupID, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
