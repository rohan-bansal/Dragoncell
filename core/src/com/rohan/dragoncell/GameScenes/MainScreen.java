package com.rohan.dragoncell.GameScenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rohan.dragoncell.FileUtils.ModInputProcessor;
import com.rohan.dragoncell.FileUtils.SoundEffects;
import com.rohan.dragoncell.GameUtils.Display.HUD;
import com.rohan.dragoncell.GameUtils.Display.ViewCam;
import com.rohan.dragoncell.GameUtils.Entity.*;
import com.rohan.dragoncell.GameUtils.Material;
import com.rohan.dragoncell.GameUtils.MaterialsList;

public class MainScreen implements Screen {

    private Game game;
    private MaterialsList materials;
    private Player player;
    private SpriteBatch mainBatch = new SpriteBatch();
    public static HUD headsUp;
    private ViewCam camera;
    public static Crafting crafting;
    public static Forge forge;
    public static MaterialsBook materialsBook;
    public static Collection collectionView;
    public static Presser presser;


    private boolean loadData = false;


    public MainScreen(Game game, boolean loadData) {
        this.game = game;
        this.loadData = loadData;

        initPlayer();
        initMaterials();

        ModInputProcessor processor = new ModInputProcessor(player.getInventory());
        Gdx.input.setInputProcessor(processor);
    }

    private void initMaterials() {
        materials = new MaterialsList();
        crafting = new Crafting(player.getInventory(), materials);
        forge = new Forge(player.getInventory(), materials);
        materialsBook = new MaterialsBook(materials);
        collectionView = new Collection(materials, player);
        presser = new Presser(player, materials);

        Gdx.app.log("World", "Materials and Recipes Loaded");

        player.getInventory().addItem(new Material(materials.IRON_ORE), 18);
        player.getInventory().addItem(new Material(materials.HARDENED_STONE), 3);
        player.getInventory().addItem(new Material(materials.BASIC_GEARS), 3);
        player.getInventory().addItem(new Material(materials.SMALL_BLADE), 3);
        player.getInventory().addItem(new Material(materials.CACTUS), 2);
        player.getInventory().addItem(new Material(materials.CACTUS_RESIN), 2);
        player.getInventory().addItem(new Material(materials.COAL), 5);
        player.getInventory().addItem(new Material(materials.STICK));
        player.getInventory().addItem(new Material(materials.WOOD), 6);

    }

    private void initPlayer() {
        player = new Player();
        headsUp = new HUD(player);
        camera = new ViewCam();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(37/255f, 27/255f, 26/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.render();
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
        }

    }



    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
