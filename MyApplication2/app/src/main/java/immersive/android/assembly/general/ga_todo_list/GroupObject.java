package immersive.android.assembly.general.ga_todo_list;

/**
 * Created by Skywingz on 7/7/16.
 */
public class GroupObject {

    private int position;
    private int id;
    private String name;
    private int completedItems;
    private int totalItems;


    public GroupObject() {}

    public GroupObject(int pos, int num, String title, int completed, int total) {
        this.position = pos;
        this.id = num;
        this.name = title;
        this.completedItems = completed;
        this.totalItems = total;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompletedItems() {
        return completedItems;
    }

    public void setCompletedItems(int completedItems) {
        this.completedItems = completedItems;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void updateGroupObject(int pos, int num, String title, int completed, int total) {
        this.position = pos;
        this.id = num;
        this.name = title;
        this.completedItems = completed;
        this.totalItems = total;
    }



}
