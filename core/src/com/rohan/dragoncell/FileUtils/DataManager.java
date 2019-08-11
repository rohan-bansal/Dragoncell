package com.rohan.dragoncell.FileUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.rohan.dragoncell.GameScenes.MainScreen;
import com.rohan.dragoncell.GameUtils.Entity.Player;

public class DataManager {

    private FileHandle data;
    public GameData gameData;
    private Json json;
    private Player player;

    public DataManager(Player player) {
        this.player = player;
        this.json = new Json();
    }

    public boolean createNewSave() {
        this.gameData = new GameData();

        boolean exists = Gdx.files.local("save.dc").exists();


        this.gameData.setHealth(10);
        this.gameData.setHearts(10);

        this.data = Gdx.files.local("save.dc");
        saveData();

        Gdx.app.log("Manager", "New Save Created");
        return true;
    }

    public void saveData() {
        if (data != null) {
            if(MainScreen.encryptSaveFiles) {
                data.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)), false);
            } else {
                data.writeString(json.prettyPrint(gameData), false);
            }
            Gdx.app.log("Manager", "Data Saved");
        } else {
            Gdx.app.log("Manager", "Saving Error");
        }
    }

    public boolean loadData() {
        this.data = Gdx.files.local("save.dc");
        try {
            if(MainScreen.encryptSaveFiles) {
                gameData = json.fromJson(GameData.class, Base64Coder.decodeString(data.readString()));
            } else {
                gameData = json.fromJson(GameData.class, data.readString());
            }
            Gdx.app.log("Manager", "Save Loaded");
            return true;
        } catch(Exception e) {
            Gdx.app.log("Manager", "Load Data Failed");
            e.printStackTrace();
            return false;
        }
    }

}
