package com.rohan.dragoncell.GameUtils.Entity;

public class Player {

    private Inventory inventory;

    public int hearts = 20;
    public int health = 20;
    public int coins = 0;

    public Player() {

        inventory = new Inventory(this);

    }

    public Inventory getInventory() {
        return inventory;
    }


    public void render() {

        inventory.render();
    }
}
