package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rohan.dragoncell.GameScenes.MainScreen;
import com.rohan.dragoncell.GameUtils.Entity.Object.BreakableObject;
import com.rohan.dragoncell.GameUtils.ItemStack;
import com.rohan.dragoncell.GameUtils.Material;
import com.rohan.dragoncell.GameUtils.MaterialsList;
import com.rohan.dragoncell.GameUtils.ObtainMethods;

import java.util.ArrayList;
import java.util.Random;

import static com.rohan.dragoncell.GameUtils.ObtainMethods.areas;

public class Collection {

    private Player player;
    private MaterialsList materials;
    private ArrayList<BreakableObject> trees = new ArrayList<BreakableObject>();
    private ArrayList<BreakableObject> desert = new ArrayList<BreakableObject>();
    private ArrayList<BreakableObject> ores = new ArrayList<BreakableObject>();
    private ArrayList<BreakableObject> treesToRemove = new ArrayList<BreakableObject>();
    private ArrayList<BreakableObject> oresToRemove = new ArrayList<BreakableObject>();
    private ArrayList<BreakableObject> desertToRemove = new ArrayList<BreakableObject>();
    private Random rand = new Random();
    private Sprite axeIcon = new Sprite(new Texture(Gdx.files.internal("Interface/World/Collection/axe.png")));
    private Sprite hammerIcon = new Sprite(new Texture(Gdx.files.internal("Interface/World/Collection/hammer.png")));

    public int areaNumber = 1;
    private int biomeType = 1;
    private int prevBiomeType = 1;
    private Rectangle tempRect;
    private Color tempColor;

    private int spawnOre_ = 0;
    private int spawnCactus_ = 0;
    private int spawnTree_ = 0;


    private BitmapFont nameDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    private BitmapFont noPickUpDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);

    public Collection(MaterialsList materials, Player player) {
        this.player = player;
        this.materials = materials;

        nameDrawer.getData().setScale(0.8f);

        noPickUpDrawer.setColor(Color.SCARLET);
        noPickUpDrawer.getData().setScale(0.5f);
        hammerIcon.setScale(1.5f);

        refreshView();
    }

    public void render(SpriteBatch batch) {

        prevBiomeType = biomeType;
        detectSceneChange();


        for(BreakableObject tree : trees) {
            if(biomeType == 1 || biomeType == 2) {
                tree.render(batch);
            }
        }

        for(BreakableObject ore : ores) {
            if(biomeType == 5) {
                ore.render(batch);
                ore.unobtainable = false;
            }
        }

        for(BreakableObject cactus : desert) {
            if(biomeType == 3) {
                cactus.render(batch);
            }
        }

        for(int x = 0; x < spawnOre_; x++) {
            spawnOre();
            spawnOre_ -= 1;
        }
        for(int x = 0; x < spawnCactus_; x++) {
            spawnCactus();
            spawnCactus_ -= 1;
        }
        for(int x = 0; x < spawnTree_; x++) {
            spawnTree();
            spawnTree_ -= 1;
        }

        player.renderPlayer(batch);
        tempRect = new Rectangle(player.position.x, player.position.y, player.currentFrame.getRegionWidth(), player.currentFrame.getRegionHeight());

        for(BreakableObject ore2 : ores) {
            if(biomeType == 5) {
                if(ore2.sprite.getBoundingRectangle().overlaps(tempRect)) {
                    if(materials.getMaterialByID(ore2.ID).levelNeeded > player.getLeveling().getLevel()) {
                        ore2.unobtainable = true;
                        noPickUpDrawer.draw(batch, "Level " + materials.getMaterialByID(ore2.ID).levelNeeded, ore2.sprite.getX() - 10, ore2.sprite.getY() + 45);
                    }
                }
            }
        }

        if(biomeType == 2) {
            int pixel = ScreenUtils.getFrameBufferPixmap(0,0, 1000, 800).getPixel(Math.round(player.position.x), Math.round(player.position.y));
            tempColor = new Color(pixel);
            if(tempColor.r * 255 == 0.0 && tempColor.g * 255 == 74.0 && tempColor.b * 255 == 127.0) {
                player.speed = 0.4f;
                player.position.x += 0.3f;
            } else {
                player.speed = 1.5f;
            }
        }

        if(biomeType == 1 || biomeType == 2) {
            treeBiome(batch, tempRect);
        } else if(biomeType == 5) {
            oreBiome(batch, tempRect);
        } else if(biomeType == 3) {
            desertBiome(batch, tempRect);
        }

        for(BreakableObject t : treesToRemove) {
            trees.remove(t);
        }

        for(BreakableObject t : oresToRemove) {
            ores.remove(t);
        }

        for(BreakableObject t : desertToRemove) {
            desert.remove(t);
        }


        treesToRemove.clear();
        oresToRemove.clear();
        desertToRemove.clear();

    }

    private void treeBiome(SpriteBatch batch, Rectangle tempRect) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            for (BreakableObject tree_ : trees) {
                if (tempRect.overlaps(tree_.sprite.getBoundingRectangle())) {
                    if (tree_.hits == 3) {
                        treesToRemove.add(tree_);
                        player.getLeveling().setSubLevelPoints(player.getLeveling().getSubLevelPoints() + 1);
                        String path = ((FileTextureData) tree_.sprite.getTexture().getTextureData()).getFileHandle().path();
                        if (path.contains("tree2")) {
                            player.getInventory().addItem(new Material(materials.CHERRY));
                            discoveredItem(materials.CHERRY);
                        } else if (path.contains("tree4")) {
                            player.getInventory().addItem(new Material(materials.GREEN_APPLE));
                            discoveredItem(materials.GREEN_APPLE);
                        } else if (path.contains("tree1")) {
                            player.getInventory().addItem(new Material(materials.STICK));
                        }
                        spawnTree_ += 1;
                    } else {
                        tree_.hits += 1;
                    }
                }
            }
        }
        for(BreakableObject object_ : trees) {
            if(biomeType == 1 || biomeType == 2) {
                Rectangle tempRect_ = new Rectangle(player.position.x, player.position.y, player.currentFrame.getRegionWidth(), player.currentFrame.getRegionHeight());
                if (tempRect_.overlaps(object_.sprite.getBoundingRectangle())) {
                    axeIcon.setCenter(object_.sprite.getX() + (object_.sprite.getWidth() / 2), object_.sprite.getY() + 110);
                    axeIcon.draw(batch);
                    if(object_.hits != 0) {
                        nameDrawer.draw(batch, object_.hits + "", object_.sprite.getX() + 90, object_.sprite.getY() + 50);
                    }
                }
            }
        }
    }

    private void desertBiome(SpriteBatch batch, Rectangle tempRect) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            for (BreakableObject cact : desert) {
                if(!cact.unobtainable) {
                    if (tempRect.overlaps(cact.sprite.getBoundingRectangle())) {
                        if (cact.hits == 7) {
                            desertToRemove.add(cact);
                            player.getLeveling().setSubLevelPoints(player.getLeveling().getSubLevelPoints() + 1);
                            String path = ((FileTextureData) cact.sprite.getTexture().getTextureData()).getFileHandle().path();
                            if (path.contains("cactus")) {
                                player.getInventory().addItem(new Material(materials.CACTUS));
                                discoveredItem(materials.CACTUS);
                            }
                            spawnCactus_ += 1;
                        } else {
                            cact.hits += 1;
                        }
                    }
                }

            }
        }
        for(BreakableObject object_ : desert) {
            if(biomeType == 3) {
                if(!object_.unobtainable) {
                    Rectangle tempRect_ = new Rectangle(player.position.x, player.position.y, player.currentFrame.getRegionWidth(), player.currentFrame.getRegionHeight());
                    if (tempRect_.overlaps(object_.sprite.getBoundingRectangle())) {
                        axeIcon.setCenter(object_.sprite.getX() + (object_.sprite.getWidth() / 2), object_.sprite.getY() + 75);
                        axeIcon.draw(batch);
                        if(object_.hits != 0) {
                            nameDrawer.draw(batch, object_.hits + "", object_.sprite.getX() + 35, object_.sprite.getY() + 45);
                        }
                    }
                }

            }
        }
    }
    private void oreBiome(SpriteBatch batch, Rectangle tempRect) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            for (BreakableObject ore_ : ores) {
                if (tempRect.overlaps(ore_.sprite.getBoundingRectangle())) {
                    if(!ore_.unobtainable) {
                        if (ore_.hits == 15) {
                            oresToRemove.add(ore_);
                            player.getLeveling().setSubLevelPoints(player.getLeveling().getSubLevelPoints() + 1);
                            String path = ((FileTextureData) ore_.sprite.getTexture().getTextureData()).getFileHandle().path();
                            if (path.contains("coal")) {
                                player.getInventory().addItem(new Material(materials.COAL));
                                discoveredItem(materials.COAL);
                            } else if (path.contains("iron")) {
                                player.getInventory().addItem(new Material(materials.IRON_ORE));
                                discoveredItem(materials.IRON_ORE);
                            } else if (path.contains("crimstone")) {
                                player.getInventory().addItem(new Material(materials.CRIMSTONE_ORE));
                                discoveredItem(materials.CRIMSTONE_ORE);
                            } else if (path.contains("stone")) {
                                player.getInventory().addItem(new Material(materials.STONE));
                                discoveredItem(materials.STONE);
                            } else if (path.contains("sasmite")) {
                                player.getInventory().addItem(new Material(materials.SASMITE_ORE));
                                discoveredItem(materials.SASMITE_ORE);
                            } else if (path.contains("copper")) {
                                player.getInventory().addItem(new Material(materials.COPPER_ORE));
                                discoveredItem(materials.COPPER_ORE);
                            } else if (path.contains("amber")) {
                                player.getInventory().addItem(new Material(materials.AMBER));
                                discoveredItem(materials.AMBER);
                            }
                            spawnOre_ += 1;
                        } else {
                            ore_.hits += 1;
                        }
                    }
                }
            }
        }
        for(BreakableObject object_ : ores) {
            if(biomeType == 5) {
                if(!object_.unobtainable) {
                    Rectangle tempRect_ = new Rectangle(player.position.x, player.position.y, player.currentFrame.getRegionWidth(), player.currentFrame.getRegionHeight());
                    if (tempRect_.overlaps(object_.sprite.getBoundingRectangle())) {
                        hammerIcon.setCenter(object_.sprite.getX() + (object_.sprite.getWidth() / 2), object_.sprite.getY() + 40);
                        hammerIcon.draw(batch);
                        if(object_.hits != 0) {
                            nameDrawer.draw(batch, object_.hits + "", object_.sprite.getX() + 40, object_.sprite.getY() + 16);
                        }
                    }
                }

            }
        }
    }

    //biomeType = rand.nextInt((6 - 1) + 1) + 1;

    private void detectSceneChange() {

        if(areas.get(areaNumber) != null) {
            if(player.position.x > 490) { // right

                if(checkPossible(1)) {
                    biomeType = Integer.parseInt(areas.get(areaNumber)[1].split(" ")[0]);
                    areaNumber = Integer.parseInt(areas.get(areaNumber)[1].split(" ")[1]);

                    MainScreen.headsUp.changeCollectionScene(biomeType);
                    player.position.x = 50;
                }

            } else if(player.position.x < 40) { // left

                if(checkPossible(3)) {
                    biomeType = Integer.parseInt(areas.get(areaNumber)[3].split(" ")[0]);
                    areaNumber = Integer.parseInt(areas.get(areaNumber)[3].split(" ")[1]);

                    MainScreen.headsUp.changeCollectionScene(biomeType);
                    player.position.x = 480;
                }

            } else if(player.position.y < 40) { // down

                if(checkPossible(2)) {
                    biomeType = Integer.parseInt(areas.get(areaNumber)[2].split(" ")[0]);
                    areaNumber = Integer.parseInt(areas.get(areaNumber)[2].split(" ")[1]);

                    MainScreen.headsUp.changeCollectionScene(biomeType);
                    player.position.y = 430;
                }

            } else if(player.position.y > 439) { // up

                if(checkPossible(0)) {
                    biomeType = Integer.parseInt(areas.get(areaNumber)[0].split(" ")[0]);
                    areaNumber = Integer.parseInt(areas.get(areaNumber)[0].split(" ")[1]);

                    MainScreen.headsUp.changeCollectionScene(biomeType);
                    player.position.y = 50;
                }

            }

            if(prevBiomeType != biomeType) {
                if(biomeType == 2) {
                    for(BreakableObject tree : trees) {
                        recursivePosChange(tree, 1);
                    }
                }
            }

        } else {
            Gdx.app.log("Collection", "Scene Loading Error");
        }


    }

    private boolean checkPossible(int biome) {
        Gdx.app.log("Collection", "Checking Possible Biome");

        int tempBiomeType = Integer.parseInt(areas.get(areaNumber)[biome].split(" ")[0]);
        if(ObtainMethods.getBiomeByInt.get(tempBiomeType).equals("Desert")) {
            if (!player.desertUnlocked) {
                for (ItemStack item : player.getInventory().getInventory()) {
                    if (item.stackedItem.name.equals(materials.getMaterialByID(Integer.parseInt(player.desertRequirement[0])).name)) {
                        if (item.count >= Integer.parseInt(player.desertRequirement[1])) {
                            player.desertUnlocked = true;
                            if(item.count > Integer.parseInt(player.desertRequirement[1])) {
                                item.count -= Integer.parseInt(player.desertRequirement[1]);
                            } else {
                                player.getInventory().materialsToRemove.add(item);
                            }
                            Gdx.app.log("Collection", "Unlocked Desert Area");
                            discoveredItem(materials.WATER, "Desert");
                            return true;
                        }
                    }
                }
                discoveredItem(materials.WATER, "Area Locked", materials.getMaterialByID(Integer.parseInt(player.desertRequirement[0])).name + ": " +
                        Integer.parseInt(player.desertRequirement[1]));
            } else {
                return true;
            }
        } else if(ObtainMethods.getBiomeByInt.get(tempBiomeType).equals("Beach")) {
            if (!player.beachUnlocked) {
                for (ItemStack item : player.getInventory().getInventory()) {
                    if (item.stackedItem.name.equals(materials.getMaterialByID(Integer.parseInt(player.beachRequirement[0])).name)) {
                        if (item.count >= Integer.parseInt(player.beachRequirement[1])) {
                            player.beachUnlocked = true;
                            if(item.count > Integer.parseInt(player.beachRequirement[1])) {
                                item.count -= Integer.parseInt(player.beachRequirement[1]);
                            } else {
                                player.getInventory().materialsToRemove.add(item);
                            }
                            Gdx.app.log("Collection", "Unlocked Beach Area");
                            discoveredItem(materials.WATER, "Beach");
                            return true;
                        }
                    }
                }
                discoveredItem(materials.WATER, "Area Locked", materials.getMaterialByID(Integer.parseInt(player.beachRequirement[0])).name + ": " +
                        Integer.parseInt(player.beachRequirement[1]));
            } else {
                return true;
            }
        } else if(ObtainMethods.getBiomeByInt.get(tempBiomeType).equals("Ore Field")) {

            if(!player.oreFieldUnlocked) {
                player.oreFieldUnlocked = true;
                discoveredItem(materials.WATER, "Ore Field");
                return true;
            } else {
                return true;
            }
        } else {
            Gdx.app.log("Collection", ObtainMethods.getBiomeByInt.get(tempBiomeType));
            return true;
        }
        return false;
    }

    private void recursivePosChange(BreakableObject object, int scenario) {

        if(scenario == 1) {
            if(object.sprite.getY() < 325 && object.sprite.getY() > 175) {
                object.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((400 - 30) + 1) + 30);
                recursivePosChange(object, 1);
            }
        } else if(scenario == 2) {
            for(BreakableObject tree : trees) {
                if(tree.sprite.getBoundingRectangle().overlaps(object.sprite.getBoundingRectangle())) {
                    object.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((400 - 30) + 1) + 30);
                    recursivePosChange(object, 2);
                }
            }
        } else if(scenario == 3) {
            for(BreakableObject ore : ores) {
                if(ore.sprite.getBoundingRectangle().overlaps(object.sprite.getBoundingRectangle())) {
                    object.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((400 - 30) + 1) + 30);
                    recursivePosChange(object, 3);
                }
            }
        } else if(scenario == 4) {
            for(BreakableObject cact : desert) {
                if(cact.sprite.getBoundingRectangle().overlaps(object.sprite.getBoundingRectangle())) {
                    object.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((400 - 30) + 1) + 30);
                    recursivePosChange(object, 3);
                }
            }
        }
    }

    private void discoveredItem(Material material, String... discoveredBiome) {
        if(discoveredBiome.length == 1) {
            MainScreen.headsUp.alert.put("alert_text", "New Area Unlocked");
            MainScreen.headsUp.alert.put("alert_description", discoveredBiome[0]);
        } else if(discoveredBiome.length == 2) {
            MainScreen.headsUp.alert.put("alert_text", discoveredBiome[0]);
            MainScreen.headsUp.alert.put("alert_description", discoveredBiome[1]);
        } else {
            if(!material.discovered) {
                material.discovered = true;
                MainScreen.headsUp.alert.put("alert_text", "New Material Chain");
                MainScreen.headsUp.alert.put("alert_description", material.name + " : ID #" + material.ID);
                unlockItems(material);
            }
        }

    }

    private void unlockItems(Material material) {
        for(Material material_ : materials.materialList) {
            if(material_.recipe.contains(material) || material.smeltInto == material_ || material.seedDrop == material_ || material.juicedInto == material_
                    || material_.grinderRecipe.contains(material)) {
                material_.discovered = true;
                unlockItems(material_);
            }
        }
    }

    public void refreshView() {
        trees.clear();
        ores.clear();
        desert.clear();

        for(int x = 0; x < 5; x++) {
            spawnTree();
        }

        for(int x = 0; x < 10; x++) {
            spawnCactus();
        }

        for(int x = 0; x < 15; x++) {
            spawnOre();
        }
    }

    private void spawnTree() {
        BreakableObject tempTree = null;
        int spawnPercentage = rand.nextInt(100);
        if(spawnPercentage <= 60) {
            tempTree = new BreakableObject("Interface/World/Collection/tree1.png", 0);
        } else if(spawnPercentage <= 80) {
            tempTree = new BreakableObject("Interface/World/Collection/tree2.png", 0);
        } else if(spawnPercentage <= 100) {
            tempTree = new BreakableObject("Interface/World/Collection/tree4.png", 0);
        }
        tempTree.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((400 - 30) + 1) + 30);
        recursivePosChange(tempTree, 2);

        trees.add(tempTree);
    }

    private void spawnCactus() {
        BreakableObject tempCact = new BreakableObject("Interface/World/Collection/cactus.png", 46);
        tempCact.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((400 - 30) + 1) + 30);
        recursivePosChange(tempCact, 4);

        desert.add(tempCact);
    }

    private void spawnOre() {
        BreakableObject tempOre = null;
        int spawnPercentage = rand.nextInt(100);
        if(spawnPercentage <= 40) {
            tempOre = new BreakableObject("Materials/stone.png", 2);
            tempOre.sprite.setSize(32, 32);
        } else if(spawnPercentage <= 60) {
            tempOre = new BreakableObject("Materials/coal.png", 8);
            tempOre.sprite.setSize(32, 32);
        } else if(spawnPercentage <= 75) {
            tempOre = new BreakableObject("Materials/iron_ore.png", 11);
            tempOre.sprite.setSize(32, 32);
        } else if(spawnPercentage <= 77) {
            tempOre = new BreakableObject("Materials/amber.png", 3);
            tempOre.sprite.setSize(32, 32);
        } else if(spawnPercentage <= 90) {
            tempOre = new BreakableObject("Materials/copper_ore.png", 24);
            tempOre.sprite.setSize(32, 32);
        } else if(spawnPercentage <= 97) {
            tempOre = new BreakableObject("Materials/sasmite_ore.png", 6);
            tempOre.sprite.setSize(32, 32);
        } else if(spawnPercentage <= 100) {
            tempOre = new BreakableObject("Materials/crimstone_ore.png", 9);
            tempOre.sprite.setSize(32, 32);
        }
        tempOre.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((440 - 30) + 1) + 30);
        recursivePosChange(tempOre, 3);
        ores.add(tempOre);
    }
}
