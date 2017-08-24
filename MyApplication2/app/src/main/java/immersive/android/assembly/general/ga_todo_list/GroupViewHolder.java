package immersive.android.assembly.general.ga_todo_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class GroupViewHolder extends RecyclerView.ViewHolder {

    public TextView title, completion;
    public ImageView playButton;

    public GroupViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.groupListObjectTitle);
        completion = (TextView) itemView.findViewById(R.id.groupListObjectCompletionNum);
        playButton = (ImageView) itemView.findViewById(R.id.groupListObjectPlayButton);
    }
}
