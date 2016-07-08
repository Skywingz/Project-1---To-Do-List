package immersive.android.assembly.general.ga_todo_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupListActivity extends AppCompatActivity {

    private static final int INTENT_RESULT_CODE = 88;

    private RecyclerView recycler;
    private GroupAdapter adapter;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("To-Do Lists");
        setSupportActionBar(toolbar);

        SingletonManager singletonManager = SingletonManager.getInstance();
        inflater = this.getLayoutInflater();

        recycler = (RecyclerView) findViewById(R.id.groupListRecyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);

        adapter = new GroupAdapter(singletonManager.getGroupObjects());
        adapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final String groupTitle, final int groupID) {
                switch(view.getId()) {
                    case R.id.groupListObjectPlayButton:
                        Intent intent = new Intent(GroupListActivity.this, ItemListActivity.class);
                        intent.putExtra("start_item_list_group_title", groupTitle);
                        intent.putExtra("start_item_list_group_id", groupID);
                        startActivityForResult(intent, INTENT_RESULT_CODE);
                        break;
                    case R.id.groupListObjectTitle:
                        final View dialogView = inflater.inflate(R.layout.popup_modify_group, null);
                        final EditText title = (EditText) dialogView.findViewById(R.id.popupModifyGroupEditText);
                        final Button delete = (Button) dialogView.findViewById(R.id.popupModifyGroupDeleteButton);
                        final TextView cancelButton = (TextView) dialogView.findViewById(R.id.popupModifyGroupCancelButton);
                        final TextView modifyButton = (TextView) dialogView.findViewById(R.id.popupModifyGroupModifyButton);
                        title.setText(groupTitle);

                        AlertDialog.Builder builder = new AlertDialog.Builder(GroupListActivity.this);
                        builder.setView(dialogView);

                        final AlertDialog dialog = builder.create();

                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SingletonManager manager = SingletonManager.getInstance();
                                GroupObject go = manager.getGroupObjectFromList(groupID);
                                int objectPosition = go.getPosition();
                                manager.removeGroupObject(objectPosition, groupID);

                                adapter.notifyItemRemoved(objectPosition);

                                // TODO Update DB

                                dialog.dismiss();
                            }
                        });

                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        modifyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (title.getText().toString().trim().length() == 0) {
                                    title.setError("This field cannot be blank");
                                } else if (!title.getText().toString().trim().equals(groupTitle)) {
                                    SingletonManager manager = SingletonManager.getInstance();

                                    GroupObject go = manager.getGroupObjectFromList(groupID);
                                    manager.modifyGroupObject(go.getPosition(), groupID, title.getText().toString().trim(),
                                            go.getCompletedItems(), go.getTotalItems());

                                    adapter.notifyItemChanged(go.getPosition());

                                    dialog.dismiss();
                                } else {
                                    dialog.dismiss();
                                }

                                // TODO UPdate DB
                            }
                        });

                        dialog.show();
                        break;
                    default: break;
                }


            }
        });

        recycler.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View dialogView = inflater.inflate(R.layout.popup_add_group, null);
                final EditText title = (EditText) dialogView.findViewById(R.id.popupGroupAddEditText);
                final TextView cancelButton = (TextView) dialogView.findViewById(R.id.popupGroupAddCancelButton);
                final TextView addButton = (TextView) dialogView.findViewById(R.id.popupGroupAddAddButton);

                AlertDialog.Builder builder = new AlertDialog.Builder(GroupListActivity.this);
                builder.setView(dialogView);

                final AlertDialog dialog = builder.create();

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (title.getText().toString().trim().length() == 0) {
                            title.setError("This field cannot be blank");
                        } else {
                            SingletonManager singletonManager = SingletonManager.getInstance();
                            int position = singletonManager.getGroupListSize();
                            GroupObject go = new GroupObject(position, singletonManager.getGroupIndex(),
                                    title.getText().toString().trim(), 0, 0);
                            singletonManager.updateGroupIndex();
                            singletonManager.addGroupObject(go);

                            adapter.notifyItemInserted(position);

                            dialog.dismiss();

                            // TODO update DB
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                SingletonManager manager = SingletonManager.getInstance();
                int groupID = data.getIntExtra("intent_group_id", 0);

                ArrayList<ItemObject> items = manager.getItemList(groupID);
                int newTotalItems = items.size();
                int newCompletedItems = 0;
                for (ItemObject item : items) {
                    if (item.isChecked()) {
                        newCompletedItems++;
                    }
                }

                GroupObject go = manager.getGroupObjectFromList(groupID);
                manager.modifyGroupObject(go.getPosition(), groupID, go.getName(), newCompletedItems, newTotalItems);

                adapter.notifyItemChanged(go.getPosition());

                // TODO Update DB

            }
        }
    }

}
