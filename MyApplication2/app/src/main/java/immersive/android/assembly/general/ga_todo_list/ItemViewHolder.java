package immersive.android.assembly.general.ga_todo_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Skywingz on 7/7/16.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout layout;
    public ImageView delete;
    public TextView title, description;
    public CheckBox box;


    public ItemViewHolder(View itemView) {
        super(itemView);

        layout = (RelativeLayout) itemView.findViewById(R.id.itemListObjectRelativeLayout);
        delete = (ImageView) itemView.findViewById(R.id.itemListObjectDeleteButton);
        title = (TextView) itemView.findViewById(R.id.itemListObjectTitle);
        description = (TextView) itemView.findViewById(R.id.itemListObjectDescription);
        box = (CheckBox) itemView.findViewById(R.id.itemListObjectCheckBox);
    }
}
