package me.tallonscze.guishop.data;

public class ItemNavigationData {
    private ItemData backItem;
    public ItemNavigationData(){
        createBackItem();
    }

    private void createBackItem(){
        backItem = new ItemData("diamond_axe", 1, "Back to inventory");
        backItem.setBack(true);
    }

    public ItemData getBackItem(){
        return backItem;
    }
}
