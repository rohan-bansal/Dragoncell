package com.rohan.dragoncell.GameScenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.rohan.dragoncell.FileUtils.DataManager;
import com.rohan.dragoncell.FileUtils.ModInputProcessor;
import com.rohan.dragoncell.GameUtils.Display.HUD;
import com.rohan.dragoncell.GameUtils.Display.Rumble;
import com.rohan.dragoncell.GameUtils.Display.ViewCam;
import com.rohan.dragoncell.GameUtils.Entity.*;
import com.rohan.dragoncell.GameUtils.ItemStack;
import com.rohan.dragoncell.GameUtils.Material;
import com.rohan.dragoncell.GameUtils.MaterialsList;

import java.util.ArrayList;

public class MainScreen implements Screen {

    private Game game;
    private MaterialsList materials;
    private Player player;
    private SpriteBatch mainBatch = new SpriteBatch();
    public static HUD headsUp;
    public static Crafting crafting;
    public static Forge forge;
    public static MaterialsBook materialsBook;
    public static Collection collectionView;
    public static Presser presser;
    public static DataManager manager;
    public static Shop shop;

    private boolean rumbleRefresh = false;


    //private boolean loadData = false;
    public static boolean encryptSaveFiles = false;


    public MainScreen(Game game, boolean loadData) {
        this.game = game;
        //this.loadData = loadData;

        initPlayer();
        initMaterials();

        ModInputProcessor processor = new ModInputProcessor(player.getInventory());
        Gdx.input.setInputProcessor(processor);

        if(loadData) {
            manager.loadData();
            loadNewData();
        } else {
            manager.createNewSave();
            applyNewData();
        }
    }

    private void initMaterials() {
        materials = new MaterialsList();
        crafting = new Crafting(player.getInventory(), materials);
        forge = new Forge(player.getInventory(), materials);
        materialsBook = new MaterialsBook(materials);
        collectionView = new Collection(materials, player);
        presser = new Presser(player, materials);
        shop = new Shop(materials, player);

        manager = new DataManager(player);

        for(Material material : materials.materialList) {
            if(material.discovered) {
                collectionView.unlockItems(material);
            }
        }

        Gdx.app.log("World", "Materials and Recipes Loaded");

        player.getInventory().addItem(new Material(materials.IRON_ORE), 20);
        player.getInventory().addItem(new Material(materials.HARDENED_STONE), 3);
        player.getInventory().addItem(new Material(materials.BASIC_GEARS), 3);
        player.getInventory().addItem(new Material(materials.SMALL_BLADE), 3);
        player.getInventory().addItem(new Material(materials.CACTUS), 2);
        player.getInventory().addItem(new Material(materials.CACTUS_RESIN), 2);
        player.getInventory().addItem(new Material(materials.COAL), 5);
        player.getInventory().addItem(new Material(materials.HARDENED_WOOD), 2);
        player.getInventory().addItem(new Material(materials.WOOD), 6);
        player.getInventory().addItem(new Material(materials.SPADE));
        player.getInventory().addItem(new Material(materials.NAILS), 10);

    }

    private void initPlayer() {
        player = new Player();
        headsUp = new HUD(player);
    }

    private void applyNewData() {
        manager.gameData.setInventory(player.getInventory().getInventory());
        //manager.gameData.setAreaNumber(collectionView.areaNumber);
        manager.gameData.setBeachUnlocked(player.beachUnlocked);
        manager.gameData.setDesertUnlocked(player.desertUnlocked);
        manager.gameData.setOreFieldUnlocked(player.oreFieldUnlocked);
        manager.gameData.setIDpage(materialsBook.IDpage);
        manager.gameData.setMaterialsActive(headsUp.matBookActive);
        manager.gameData.setPresserUnlocked(headsUp.presserUnlocked);
        manager.gameData.setLevel(player.getLeveling().getLevel());
        manager.gameData.setSubLevelGoal(player.getLeveling().getSubLevelGoal());
        manager.gameData.setSubLevels(player.getLeveling().getSubLevelPoints());


        for(Material m : materials.materialList) {
            if(m.discovered) {
                manager.gameData.getUnlocked().put(m.name, true);
            }
        }

        manager.saveData();
    }

    private void loadNewData() {
        player.beachUnlocked = manager.gameData.isBeachUnlocked();
        player.oreFieldUnlocked = manager.gameData.isOreFieldUnlocked();
        player.desertUnlocked = manager.gameData.isDesertUnlocked();
        headsUp.presserUnlocked = manager.gameData.isPresserUnlocked();
        headsUp.matBookActive = manager.gameData.isMaterialsActive();

        player.getLeveling().setLevel(manager.gameData.getLevel());
        player.getLeveling().setSubLevelPoints(manager.gameData.getSubLevels());
        player.getLeveling().setSubLevelGoal(manager.gameData.getSubLevelGoal());


        //collectionView.areaNumber = manager.gameData.getAreaNumber();
        materialsBook.IDpage = manager.gameData.getIDpage();

        ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
        for(Material m : materials.materialList) {
            for(ItemStack invItem : manager.gameData.getInventory()) {
                if(invItem.stackedItem.name.equals(m.name)) {
                    temp.add(new ItemStack(new Material(m), invItem.count));
                }
            }
            if(manager.gameData.getUnlocked().keySet().contains(m.name)) {
                m.discovered = manager.gameData.getUnlocked().get(m.name);
            }

        }


        manager.gameData.setInventory(temp);

        player.getInventory().setInventory(manager.gameData.getInventory());

        player.getInventory().refreshInventory();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(37/255f, 27/255f, 26/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        if (Rumble.getRumbleTimeLeft() > 0) {
            rumbleRefresh = true;
            Rumble.tick(Gdx.graphics.getDeltaTime());
            for(Sprite sprite : crafting.craftingSlots) {
                sprite.setPosition(sprite.getX() + Rumble.getPos().x, sprite.getY() + Rumble.getPos().y);
            }
        } else {
            if(rumbleRefresh) {
                crafting.refreshSlots();
                rumbleRefresh = false;
            }
        }

        headsUp.render(delta);

        player.renderInventory();

        if(forge.smeltingActive && !headsUp.forgeActive) {
            forge.threadTime();
        }

        if(!headsUp.presserActive && presser.pressingActive) {
            presser.threadTime();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            player.getInventory().refreshInventory();
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            Gdx.app.log("Main", Gdx.input.getX() + " | " + (800 - Gdx.input.getY()));
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            collectionView.refreshView();
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            player.getLeveling().setSubLevelPoints(player.getLeveling().getSubLevelPoints() + 1);
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            applyNewData();
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            Gdx.app.log("Camera", "Rumble Triggered");
            Rumble.rumble(5, 2f);
        }

    }



    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
