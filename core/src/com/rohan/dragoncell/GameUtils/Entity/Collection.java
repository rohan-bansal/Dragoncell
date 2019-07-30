package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.rohan.dragoncell.GameScenes.MainScreen;
import com.rohan.dragoncell.GameUtils.Entity.Object.BreakableObject;
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
    private ArrayList<BreakableObject> ores = new ArrayList<BreakableObject>();
    private ArrayList<BreakableObject> treesToRemove = new ArrayList<BreakableObject>();
    private ArrayList<BreakableObject> oresToRemove = new ArrayList<BreakableObject>();
    private Random rand = new Random();
    private Sprite axeIcon = new Sprite(new Texture(Gdx.files.internal("Interface/World/Collection/axe.png")));
    private Sprite hammerIcon = new Sprite(new Texture(Gdx.files.internal("Interface/World/Collection/hammer.png")));

    private int areaNumber = 1;
    private int biomeType = 1;

    private BitmapFont nameDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);

    public Collection(MaterialsList materials, Player player) {
        this.player = player;
        this.materials = materials;

        nameDrawer.getData().setScale(0.8f);
        hammerIcon.setScale(1.5f);

        refreshView();
    }

    public void render(SpriteBatch batch) {

        detectSceneChange();


        for(BreakableObject tree : trees) {
            if(biomeType == 1 || biomeType == 2) {
                tree.render(batch);
            }
        }

        for(BreakableObject ore : ores) {
            if(biomeType == 5) {
                ore.render(batch);
            }
        }

        player.renderPlayer(batch);

        Rectangle tempRect = new Rectangle(player.position.x, player.position.y, player.currentFrame.getRegionWidth(), player.currentFrame.getRegionHeight());
        if(biomeType == 1 || biomeType == 2) {
            treeBiome(batch, tempRect);
        } else if(biomeType == 5) {
            oreBiome(batch, tempRect);

        }

        for(BreakableObject t : treesToRemove) {
            trees.remove(t);
        }

        for(BreakableObject t : oresToRemove) {
            ores.remove(t);
        }

        treesToRemove.clear();
        oresToRemove.clear();

    }

    private void treeBiome(SpriteBatch batch, Rectangle tempRect) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            for (BreakableObject tree_ : trees) {
                if (tempRect.overlaps(tree_.sprite.getBoundingRectangle())) {
                    if (tree_.hits == 3) {
                        treesToRemove.add(tree_);
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

    private void oreBiome(SpriteBatch batch, Rectangle tempRect) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            for (BreakableObject ore_ : ores) {
                if (tempRect.overlaps(ore_.sprite.getBoundingRectangle())) {
                    if (ore_.hits == 15) {
                        oresToRemove.add(ore_);
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
                        }
                    } else {
                        ore_.hits += 1;
                    }
                }
            }
        }
        for(BreakableObject object_ : ores) {
            if(biomeType == 5) {
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

    //biomeType = rand.nextInt((6 - 1) + 1) + 1;

    private void detectSceneChange() {

        if(areas.get(areaNumber) != null) {
            if(player.position.x > 490) { // right

                biomeType = Integer.parseInt(areas.get(areaNumber)[1].split(" ")[0]);
                areaNumber = Integer.parseInt(areas.get(areaNumber)[1].split(" ")[1]);

                MainScreen.headsUp.changeCollectionScene(biomeType);
                player.position.x = 50;
            } else if(player.position.x < 40) { // left

                biomeType = Integer.parseInt(areas.get(areaNumber)[3].split(" ")[0]);
                areaNumber = Integer.parseInt(areas.get(areaNumber)[3].split(" ")[1]);

                MainScreen.headsUp.changeCollectionScene(biomeType);
                player.position.x = 480;
            } else if(player.position.y < 40) { // down

                biomeType = Integer.parseInt(areas.get(areaNumber)[2].split(" ")[0]);
                areaNumber = Integer.parseInt(areas.get(areaNumber)[2].split(" ")[1]);

                MainScreen.headsUp.changeCollectionScene(biomeType);
                player.position.y = 430;
            } else if(player.position.y > 439) { // up

                biomeType = Integer.parseInt(areas.get(areaNumber)[0].split(" ")[0]);
                areaNumber = Integer.parseInt(areas.get(areaNumber)[0].split(" ")[1]);

                MainScreen.headsUp.changeCollectionScene(biomeType);
                player.position.y = 50;
            }
        } else {
            Gdx.app.log("Collection", "Scene Loading Error");
        }


    }

    private void discoveredItem(Material material) {
        if(!material.discovered) {
            material.discovered = true;
            MainScreen.headsUp.alert.put("alert_text", "New Material Chain");
            MainScreen.headsUp.alert.put("alert_description", material.name + " : ID #" + material.ID);
            unlockItems(material);
            /*for(Material material_ : materials.materialList) {
                if(material_.recipe.contains(material) || material.smeltInto == material_ || material.seedDrop == material_ || material.juicedInto == material_
                    || material_.grinderRecipe.contains(material)) {
                    material_.discovered = true;
                }
            }*/
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

        for(int x = 0; x < 5; x++) {
            BreakableObject tempTree = null;
            int spawnPercentage = rand.nextInt(100);
            if(spawnPercentage <= 60) {
                tempTree = new BreakableObject("Interface/World/Collection/tree1.png");
            } else if(spawnPercentage <= 80) {
                tempTree = new BreakableObject("Interface/World/Collection/tree2.png");
            } else if(spawnPercentage <= 100) {
                tempTree = new BreakableObject("Interface/World/Collection/tree4.png");
            }
            tempTree.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((400 - 30) + 1) + 30);
            for(BreakableObject tree : trees) {
                if(tree.sprite.getBoundingRectangle().overlaps(tempTree.sprite.getBoundingRectangle())) {
                    tempTree.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((400 - 30) + 1) + 30);
                }
            }
            trees.add(tempTree);
        }
        for(int x = 0; x < 15; x++) {
            BreakableObject tempOre = null;
            int spawnPercentage = rand.nextInt(100);
            if(spawnPercentage <= 30) {
                tempOre = new BreakableObject("Materials/stone.png");
                tempOre.sprite.setSize(32, 32);
            } else if(spawnPercentage <= 55) {
                tempOre = new BreakableObject("Materials/coal.png");
                tempOre.sprite.setSize(32, 32);
            } else if(spawnPercentage <= 70) {
                tempOre = new BreakableObject("Materials/iron_ore.png");
                tempOre.sprite.setSize(32, 32);
            } else if(spawnPercentage <= 85) {
                tempOre = new BreakableObject("Materials/copper_ore.png");
                tempOre.sprite.setSize(32, 32);
            } else if(spawnPercentage <= 95) {
                tempOre = new BreakableObject("Materials/sasmite_ore.png");
                tempOre.sprite.setSize(32, 32);
            } else if(spawnPercentage <= 100) {
                tempOre = new BreakableObject("Materials/crimstone_ore.png");
                tempOre.sprite.setSize(32, 32);
            }
            tempOre.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((400 - 30) + 1) + 30);
            for(BreakableObject ore : ores) {
                if(ore.sprite.getBoundingRectangle().overlaps(tempOre.sprite.getBoundingRectangle())) {
                    tempOre.sprite.setPosition(rand.nextInt((460 - 40) + 1) + 40, rand.nextInt((400 - 30) + 1) + 30);
                }
            }
            ores.add(tempOre);
        }
    }
}
