package com.rohan.dragoncell.GameUtils;

public class ItemStack {

    public int count;
    public Material stackedItem;

    public ItemStack(Material stackedItem, int count) {
        this.stackedItem = stackedItem;
        this.count = count;
    }

    public void addItem() {
        count += 1;
    }
}
