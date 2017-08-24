package immersive.android.assembly.general.ga_todo_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    private ArrayList<GroupObject> groups;
    private Context context;

    private static OnItemClickListener listener;


    public GroupAdapter(ArrayList<GroupObject> group) {
        this.groups = group;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, String groupTitle, int groupID);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.group_list_object_layout, parent, false);
        GroupViewHolder holder = new GroupViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, final int position) {
        final int completedItems = groups.get(position).getCompletedItems();
        final int totalItems = groups.get(position).getTotalItems();
        final int groupID = groups.get(position).getId();
        final String title = groups.get(position).getName();

        if (totalItems > 0 && completedItems == totalItems) {
            holder.title.setBackgroundResource(R.color.light_green_A700);
        } else {
            holder.title.setBackgroundResource(R.color.brown_200);
        }

        String description = completedItems + " / " + totalItems;
        holder.title.setText(groups.get(position).getName());
        holder.completion.setText(description);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(view, title, groupID);
                }
            }
        };

        holder.playButton.setOnClickListener(clickListener);
        holder.title.setOnClickListener(clickListener);

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
