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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemListActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ItemAdapter adapter;
    private LayoutInflater inflater;
    private int sentGroupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        sentGroupID = intent.getIntExtra("start_item_list_group_id", 0);

        setContentView(R.layout.activity_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("start_item_list_group_title"));
        toolbar.setTitleTextColor(0xFFFFFF00);
        setSupportActionBar(toolbar);


        final SingletonManager singletonManager = SingletonManager.getInstance();
        inflater = this.getLayoutInflater();

        recycler = (RecyclerView) findViewById(R.id.itemListRecyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);

        adapter = new ItemAdapter(singletonManager.getItemList(sentGroupID));
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemID) {
                final ItemObject item = singletonManager.getItemObject(itemID, sentGroupID);
                final String itemTitle = item.getTitle();
                final String itemDescription = item.getDescription();

                switch(view.getId()) {
                    case R.id.itemListObjectDeleteButton:
                        singletonManager.removeItemObject(itemID, sentGroupID);
                        adapter.notifyItemRemoved(item.getPosition());

                        updateGroupScreen();

                        // TODO update DB

                        break;
                    case R.id.itemListObjectTitle:
                        final View dialogView = inflater.inflate(R.layout.popup_modify_item, null);
                        final EditText title = (EditText) dialogView.findViewById(R.id.popupModifyItemEditTextTitle);
                        final EditText description = (EditText) dialogView.findViewById(R.id.popupModifyItemEditTextDescription);
                        final TextView cancelButton = (TextView) dialogView.findViewById(R.id.popupModifyItemCancelButton);
                        final TextView modifyButton = (TextView) dialogView.findViewById(R.id.popupModifyItemModifyButton);
                        title.setText(itemTitle);
                        description.setText(itemDescription);

                        AlertDialog.Builder builder = new AlertDialog.Builder(ItemListActivity.this);
                        builder.setView(dialogView);

                        final AlertDialog dialog = builder.create();

                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        modifyButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (title.getText().toString().trim().length() == 0 || description.getText().toString().trim().length() == 0) {
                                    if (title.getText().toString().trim().length() == 0) {title.setError("This field cannot be blank");}
                                    if (description.getText().toString().trim().length() == 0) {description.setError("This field cannot be blank");}
                                } else if (!title.getText().toString().trim().equals(itemTitle)
                                        || !description.getText().toString().trim().equals(itemDescription)) {

                                    SingletonManager manager = SingletonManager.getInstance();
                                    manager.modifyItemObject(item.getPosition(), item.getId(), item.getGroupID(), title.getText().toString().trim(),
                                            description.getText().toString().trim(), item.isChecked());

                                    adapter.notifyItemChanged(item.getPosition());

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
                final View dialogView = inflater.inflate(R.layout.popup_add_item, null);
                final EditText title = (EditText) dialogView.findViewById(R.id.popupItemAddEditTextTitle);
                final EditText description = (EditText) dialogView.findViewById(R.id.popupItemAddEditTextDescription);
                final TextView cancelButton = (TextView) dialogView.findViewById(R.id.popupItemAddCancelButton);
                final TextView addButton = (TextView) dialogView.findViewById(R.id.popupItemAddAddButton);

                AlertDialog.Builder builder = new AlertDialog.Builder(ItemListActivity.this);
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
                        if (title.getText().toString().trim().length() == 0 || description.getText().toString().trim().length() == 0) {
                            if (title.getText().toString().trim().length() == 0) {title.setError("This field cannot be blank");}
                            if (description.getText().toString().trim().length() == 0) {description.setError("This field cannot be blank");}
                        } else {
                            SingletonManager singletonManager = SingletonManager.getInstance();

                            int position = singletonManager.getItemListSize(sentGroupID);
                            int newID = (singletonManager.getItemIndex() * 10000) + sentGroupID;
                            ItemObject io = new ItemObject(position, newID, sentGroupID, title.getText().toString().trim(),
                                    description.getText().toString().trim(), false);
                            singletonManager.updateItemIndex();
                            singletonManager.addItemObject(io);

                            adapter.notifyItemInserted(position);

                            updateGroupScreen();

                            dialog.dismiss();
                        }

                        // TODO UPdate DB
                    }
                });

                dialog.show();
            }
        });

    }

    public void onCheckboxClicked(View view) {
        updateGroupScreen();
    }

    private void updateGroupScreen() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("intent_group_id", sentGroupID);
        setResult(RESULT_OK, resultIntent);
    }




    @Override
    protected void onPause() {
        super.onPause();

        updateGroupScreen();
    }
}
