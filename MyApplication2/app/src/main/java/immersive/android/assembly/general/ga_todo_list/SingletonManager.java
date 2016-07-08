package immersive.android.assembly.general.ga_todo_list;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Skywingz on 7/7/16.
 */
public final class SingletonManager {

    private static SingletonManager manager = null;
    private static ArrayList<GroupObject> groupObjects;
    private static HashMap<Integer, ArrayList<ItemObject>> itemObjects;
    private static int groupIndex;
    private static int itemIndex;





    private SingletonManager() {
        groupObjects = new ArrayList<>();
        itemObjects = new HashMap<Integer, ArrayList<ItemObject>>();
        groupIndex = 1;
        itemIndex = 1;
    }

    public static SingletonManager getInstance() {
        if (manager == null) {
            manager = new SingletonManager();
        }

        return manager;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        SingletonManager.groupIndex = groupIndex;
    }

    public void updateGroupIndex() {
        groupIndex += 1;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(int itemIndex) {
        SingletonManager.itemIndex = itemIndex;
    }

    public void updateItemIndex() {
        itemIndex += 1;
    }

    public ArrayList<GroupObject> getGroupObjects() {
        return groupObjects;
    }

    public void setGroupObjects(ArrayList<GroupObject> groupObjects) {
        SingletonManager.groupObjects = groupObjects;
    }

    public HashMap<Integer, ArrayList<ItemObject>> getItemObjects() {
        return itemObjects;
    }

    public void setItemObjects(HashMap<Integer, ArrayList<ItemObject>> itemObjects) {
        SingletonManager.itemObjects = itemObjects;
    }

    public GroupObject getGroupObjectFromList(int id) {
        GroupObject go = null;

        for (GroupObject group : groupObjects) {
            if (group.getId() == id) {
                go = group;
                break;
            }
        }

        return go;
    }

    public ArrayList<ItemObject> getItemList(int groupID) {
        return itemObjects.get(groupID);
    }





    public void addGroupObject(GroupObject go) {
        groupObjects.add(go);

        if (!itemObjects.containsKey(go.getId())) {
            itemObjects.put(go.getId(), new ArrayList<ItemObject>());
        }

    }

    public void removeGroupObject(int position, int groupID) {
        groupObjects.remove(position);

        for (int i = 0; i < groupObjects.size(); i++) {
            groupObjects.get(i).setPosition(i);
        }

        if (itemObjects.containsKey(groupID)) {
            itemObjects.remove(groupID);
        }

    }

    public void modifyGroupObject(int position, int id, String title, int completed, int total) {
        groupObjects.get(position).updateGroupObject(position, id, title, completed, total);
    }

    public int getGroupListSize() {
        return groupObjects.size();
    }








    public void addItemObject(ItemObject item) {
        itemObjects.get(item.getGroupID()).add(item);
    }

    public ItemObject getItemObject(int id, int groupID) {
        ItemObject itemObject = null;

        for (ItemObject item : itemObjects.get(groupID)) {
            if (item.getId() == id) {
                itemObject = item;
                break;
            }
        }

        return itemObject;
    }

    public void removeItemObject(int id, int groupID) {
        int index = 0;
        for (ItemObject item : itemObjects.get(groupID)) {
            if (item.getId() == id) {
                index = itemObjects.get(groupID).indexOf(item);
                break;
            }
        }
        itemObjects.get(groupID).remove(index);

        for (int i = 0; i < itemObjects.get(groupID).size(); i++) {
            itemObjects.get(groupID).get(i).setPosition(i);
        }
    }

    public void modifyItemObject(int position, int id, int groupID, String name, String descr, boolean checked) {
        int index = 0;
        for (ItemObject item : itemObjects.get(groupID)) {
            if (item.getId() == id) {
                index = itemObjects.get(groupID).indexOf(item);
                break;
            }
        }

        itemObjects.get(groupID).get(index).updateItemObject(position, id, groupID, name, descr, checked);
    }

    public void updateItemChecked(int id, int groupID, boolean checked) {
        int index = 0;
        ItemObject checkItem = null;
        for (ItemObject item : itemObjects.get(groupID)) {
            if (item.getId() == id) {
                index = itemObjects.get(groupID).indexOf(item);
                checkItem = item;
                break;
            }
        }

        itemObjects.get(groupID).get(index).updateItemObject(checkItem.getPosition(), id, groupID, checkItem.getTitle(), checkItem.getDescription(), checked);
    }

    public int getItemListSize(int groupID) {
        return itemObjects.get(groupID).size();
    }





}
