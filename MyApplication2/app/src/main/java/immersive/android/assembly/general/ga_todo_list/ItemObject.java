package immersive.android.assembly.general.ga_todo_list;


public class ItemObject {

    private int position;
    private int id;
    private int groupID;
    private String title;
    private String description;
    private boolean isChecked;


    public ItemObject() {}

    public ItemObject(int pos, int num, int group, String name, String des, boolean checked) {
        this.position = pos;
        this.id = num;
        this.groupID = group;
        this.title = name;
        this.description = des;
        this.isChecked = checked;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void updateItemObject(int pos, int num, int group, String name, String des, boolean checked) {
        this.position = pos;
        this.id = num;
        this.groupID = group;
        this.title = name;
        this.description = des;
        this.isChecked = checked;
    }


}
