package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rohan.dragoncell.GameUtils.Material;
import com.rohan.dragoncell.GameUtils.MaterialsList;
import com.rohan.dragoncell.GameUtils.ObtainMethods;

public class MaterialsBook {

    private boolean questionDrawerActive = false;
    private boolean recipeShowing = false;

    private BitmapFont nameDrawer = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont questionDrawer = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont rarityDrawer = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont IDDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    private BitmapFont descriptionDrawer = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont recipeNameDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    private BitmapFont recipeTypeDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    private Sprite forge = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/forge.png")));
    private Sprite pixelTree = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/pixelTree.png")));

    private Sprite next, down, back, next_high, back_high, return_;
    GlyphLayout layout = new GlyphLayout();

    private int IDpage = 1;
    private int recipePage = 1;
    private Material shownMat;
    private MaterialsList materials;

    public MaterialsBook(MaterialsList materials) {
        this.materials = materials;

        next = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/arrow_right.png")));
        next_high = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/arrow_right_highlight.png")));

        back = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/arrow_left.png")));
        back_high = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/arrow_left_highlight.png")));

        down = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/arrow_down.png")));

        return_ = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/back.png")));

        next.setCenter(450, 730);
        next_high.setCenter(450, 730);

        back.setCenter(125, 730);
        back_high.setCenter(125, 730);

        return_.setCenter(125, 555);

        forge.setCenter(337, 650);
        pixelTree.setCenter(290, 650);

        nameDrawer.setColor(Color.GOLDENROD);

        descriptionDrawer.getData().setScale(0.5f);
        descriptionDrawer.setColor(Color.TAN);

        IDDrawer.setColor(Color.BLACK);
        IDDrawer.getData().setScale(0.5f);

        rarityDrawer.getData().setScale(0.5f);

        recipeNameDrawer.getData().setScale(0.5f);
        recipeNameDrawer.setColor(Color.TAN);

        recipeTypeDrawer.getData().setScale(0.5f);
        recipeTypeDrawer.setColor(Color.GOLDENROD);

        questionDrawer.getData().setScale(4f);

        refreshMaterialShown();
    }

    public void render(SpriteBatch batch) {

        if(!recipeShowing) {
            drawMaterialsBook(batch);
        } else {
            return_.draw(batch);
            drawMaterialRecipe(batch);
            if (return_.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                if(Gdx.input.justTouched()) {
                    recipeShowing = false;
                    recipePage = 1;
                }
            }
        }

        if(questionDrawerActive) {
            questionDrawer.draw(batch, "???", 200, 700);
        }
    }

    private void canBeCrafted(SpriteBatch batch, Material tempMaterial) {
        layout.setText(recipeTypeDrawer, "Workbench");
        recipeTypeDrawer.draw(batch, "Workbench", 180 - layout.width / 2, 600);

        Material[] recipe = tempMaterial.recipe.toArray(new Material[0]);
        int spriteX = 270;
        int spriteY = 700;
        for(Material material : recipe) {
            Material tempMaterial2 = new Material(material);
            material.setVariables(tempMaterial2);
            tempMaterial2.setCenter(spriteX, spriteY);
            tempMaterial2.render(batch);
            spriteX += 50;
            if(spriteX > 400) {
                spriteX = 270;
                spriteY -= 50;
            }

            if (tempMaterial2.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                layout.setText(recipeNameDrawer, tempMaterial2.name);
                recipeNameDrawer.draw(batch, tempMaterial2.name, tempMaterial2.getSprite().getX() +
                        (tempMaterial2.getSprite().getWidth() / 2)- layout.width / 2, tempMaterial2.getSprite().getY() - 10);
            }
        }

        if(tempMaterial.isOre) {

        }
    }

    private void canBeSmelted(SpriteBatch batch, Material tempMaterial) {

        forge.draw(batch);

        layout.setText(recipeTypeDrawer, "Forge");
        recipeTypeDrawer.draw(batch, "Forge", forge.getX() + (forge.getWidth() / 2) - layout.width / 2, 625);

        Material temp1 = new Material(tempMaterial);
        Material temp2 = new Material(tempMaterial.smeltInto);

        temp1.setCenter(290, 650);
        temp2.setCenter(390, 650);

        temp1.render(batch);
        temp2.render(batch);

        if (temp1.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            layout.setText(recipeNameDrawer, temp1.name);
            recipeNameDrawer.draw(batch, temp1.name, temp1.getSprite().getX() +
                    (temp1.getSprite().getWidth() / 2)- layout.width / 2, temp1.getSprite().getY() - 10);
        } else if (temp2.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            layout.setText(recipeNameDrawer, temp2.name);
            recipeNameDrawer.draw(batch, temp2.name, temp2.getSprite().getX() +
                    (temp2.getSprite().getWidth() / 2)- layout.width / 2, temp2.getSprite().getY() - 10);
        }
    }

    private void isSeedDrop(SpriteBatch batch, Material tempMaterial) {

        next.setCenter(337, 650);
        next.draw(batch);

        layout.setText(recipeTypeDrawer, "Farming");
        recipeTypeDrawer.draw(batch, "Farming", next.getX() + (next.getWidth() / 2) - layout.width / 2, 625);

        Material temp1 = new Material(tempMaterial);
        Material temp2 = new Material(tempMaterial.seedDrop);

        temp1.setCenter(290, 650);
        temp2.setCenter(390, 650);

        temp1.render(batch);
        temp2.render(batch);

        if (temp1.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            layout.setText(recipeNameDrawer, temp1.name);
            recipeNameDrawer.draw(batch, temp1.name, temp1.getSprite().getX() +
                    (temp1.getSprite().getWidth() / 2)- layout.width / 2, temp1.getSprite().getY() - 10);
        } else if (temp2.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            layout.setText(recipeNameDrawer, temp2.name);
            recipeNameDrawer.draw(batch, temp2.name, temp2.getSprite().getX() +
                    (temp2.getSprite().getWidth() / 2)- layout.width / 2, temp2.getSprite().getY() - 10);
        }
    }

    private void obtainedFromTree(SpriteBatch batch, Material tempMaterial) {
        next.setCenter(337, 650);
        next.draw(batch);

        layout.setText(recipeTypeDrawer, "Tree");
        recipeTypeDrawer.draw(batch, "Tree", next.getX() + (next.getWidth() / 2) - layout.width / 2, 625);

        Material temp2 = new Material(tempMaterial);
        temp2.setCenter(390, 650);
        temp2.render(batch);

        pixelTree.draw(batch);

        if (temp2.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            layout.setText(recipeNameDrawer, temp2.name);
            recipeNameDrawer.draw(batch, temp2.name, temp2.getSprite().getX() +
                    (temp2.getSprite().getWidth() / 2)- layout.width / 2, temp2.getSprite().getY() - 10);
        }
    }

    private void canBeGround(SpriteBatch batch, Material tempMaterial) {
        down.setCenter(337, 650);
        down.draw(batch);

        layout.setText(recipeTypeDrawer, "Presser");
        recipeTypeDrawer.draw(batch, "Presser", down.getX() + 55 + (down.getWidth() / 2) - layout.width / 2, down.getY() + 20);

        Material temp2 = new Material(tempMaterial);
        temp2.setCenter(337, 615);
        temp2.render(batch);

        Material[] recipe = temp2.grinderRecipe.toArray(new Material[0]);
        int spriteX = 315;
        int spriteY = 690;
        for(Material material : recipe) {
            Material tempMaterial2 = new Material(material);
            material.setVariables(tempMaterial2);
            tempMaterial2.setCenter(spriteX, spriteY);
            tempMaterial2.render(batch);
            spriteX += 50;
            if (spriteX > 400) {
                spriteX = 270;
                spriteY -= 50;
            }

            if (tempMaterial2.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                layout.setText(recipeNameDrawer, tempMaterial2.name);
                recipeNameDrawer.draw(batch, tempMaterial2.name, tempMaterial2.getSprite().getX() +
                        (tempMaterial2.getSprite().getWidth() / 2)- layout.width / 2, tempMaterial2.getSprite().getY() + 45);
            }
        }

        if (temp2.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            layout.setText(recipeNameDrawer, temp2.name);
            recipeNameDrawer.draw(batch, temp2.name, temp2.getSprite().getX() +
                    (temp2.getSprite().getWidth() / 2)- layout.width / 2, temp2.getSprite().getY() - 10);
        }
    }

    private void canBeJuiced(SpriteBatch batch, Material tempMaterial) {

        next.setCenter(337, 650);
        next.draw(batch);

        layout.setText(recipeTypeDrawer, "Juicer");
        recipeTypeDrawer.draw(batch, "Juicer", next.getX() + (next.getWidth() / 2) - layout.width / 2, 625);

        Material temp1 = new Material(tempMaterial);
        Material temp2 = new Material(tempMaterial.juicedInto);

        temp1.setCenter(290, 650);
        temp2.setCenter(390, 650);

        temp1.render(batch);
        temp2.render(batch);

        if (temp1.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            layout.setText(recipeNameDrawer, temp1.name);
            recipeNameDrawer.draw(batch, temp1.name, temp1.getSprite().getX() +
                    (temp1.getSprite().getWidth() / 2)- layout.width / 2, temp1.getSprite().getY() - 10);
        } else if (temp2.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            layout.setText(recipeNameDrawer, temp2.name);
            recipeNameDrawer.draw(batch, temp2.name, temp2.getSprite().getX() +
                    (temp2.getSprite().getWidth() / 2)- layout.width / 2, temp2.getSprite().getY() - 10);
        }
    }

    public void drawMaterialRecipe(SpriteBatch batch) {
        Material tempMaterial = new Material(materials.getMaterialByID(IDpage));
        tempMaterial.getSprite().setScale(2f);
        tempMaterial.setCenter(170, 650);
        tempMaterial.render(batch);

        if(tempMaterial.canBeCrafted) { // item can be crafted by
            if(recipePage == 1) {
                canBeCrafted(batch, tempMaterial);
            } else if(recipePage == 2) {
                canBeSmelted(batch, tempMaterial);
            } else if(recipePage == 3) {
                isSeedDrop(batch, tempMaterial);
            }

            if(tempMaterial.isOre && recipePage == 1) {
                next.setY(600);
                next.draw(batch);
                if(next.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                    if(Gdx.input.justTouched()) {
                        recipePage = 2;
                    }
                }
            } else if(tempMaterial.isSeed && recipePage == 1) {
                next.setY(600);
                next.draw(batch);
                if(next.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                    if(Gdx.input.justTouched()) {
                        recipePage = 3;
                    }
                }
            }

        } else if(tempMaterial.isOre) { // ore can be smelted into
            if(recipePage == 1) {
                canBeSmelted(batch, tempMaterial);
            } else if(recipePage == 2) {
                isSeedDrop(batch, tempMaterial);
            }

            if(tempMaterial.isSeed && recipePage == 1) {
                next.setY(600);
                next.draw(batch);
                if(next.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                    if(Gdx.input.justTouched()) {
                        recipePage += 1;
                    }
                }
            }

        } else if(tempMaterial.isSeed) {  // seed turns into
            isSeedDrop(batch, tempMaterial);

        } else if(tempMaterial.obtainMethod.equals(ObtainMethods.TREE)) { // gotten from tree
            if(recipePage == 1) {
                obtainedFromTree(batch, tempMaterial);
            } else if(recipePage == 2) {
                canBeJuiced(batch, tempMaterial);
            }


            if(tempMaterial.canBeJuiced && recipePage == 1) {
                next.setCenter(450, 600);
                next.draw(batch);
                if(next.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                    if(Gdx.input.justTouched()) {
                        recipePage += 1;
                    }
                }
            }

        } else if(tempMaterial.canBeJuiced) {
            canBeJuiced(batch, tempMaterial);

        } else if(tempMaterial.canBeGround) {
            canBeGround(batch, tempMaterial);
        }
    }

    public void drawMaterialsBook(SpriteBatch batch) {

        if(!questionDrawerActive) {
            shownMat.render(batch);

            layout.setText(nameDrawer, shownMat.name);
            nameDrawer.draw(batch, shownMat.name, (330 - layout.width / 2), 700);

            layout.setText(descriptionDrawer, shownMat.description);
            descriptionDrawer.draw(batch, shownMat.description, (290 - layout.width / 2), 570);

            layout.setText(rarityDrawer, ObtainMethods.rarities.get(shownMat.rarity));
            switch(shownMat.rarity) {
                case 1:
                    rarityDrawer.setColor(Color.LIME); //common
                    break;
                case 2:
                    rarityDrawer.setColor(Color.OLIVE); //uncommon
                    break;
                case 3:
                    rarityDrawer.setColor(Color.BLUE); //rare
                    break;
                case 4:
                    rarityDrawer.setColor(Color.MAROON); //epic
                    break;
                case 5:
                    rarityDrawer.setColor(Color.ORANGE); //legendary
                    break;
                case 6:
                    rarityDrawer.setColor(Color.SCARLET); //mystic
                    break;
            }
            rarityDrawer.draw(batch, ObtainMethods.rarities.get(shownMat.rarity), (330 - layout.width / 2), 650);
        }

        layout.setText(IDDrawer, "ID: " + IDpage);
        IDDrawer.draw(batch, "ID: " + IDpage + "", (170 - layout.width / 2), 610);

        if(IDpage != materials.materialList.size()) {
            next.setCenter(450, 730);
            if (next.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                next_high.draw(batch);
                if(Gdx.input.justTouched()) {
                    IDpage += 1;
                    refreshMaterialShown();
                }
            } else {
                next.draw(batch);
            }
        }
        if(IDpage != 1) {
            if (back.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                back_high.draw(batch);
                if(Gdx.input.justTouched()) {
                    IDpage -= 1;
                    refreshMaterialShown();
                }
            } else {
                back.draw(batch);
            }
        }

        if (shownMat.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            if(shownMat.canBeCrafted || shownMat.isSeed || shownMat.isOre || shownMat.canBeFilled || shownMat.canBeJuiced
                    || shownMat.canBeGround || shownMat.obtainMethod.equals(ObtainMethods.TREE)) {
                if(Gdx.input.justTouched()) {
                    recipeShowing = true;
                }
            }

        }
    }

    private void refreshMaterialShown() {
        for(Material material : materials.materialList) {
            if(material.discovered) {
                if(material.ID == IDpage) {
                    questionDrawerActive = false;
                    shownMat = new Material(material.name, material.description, material.ID, material.rarity);
                    shownMat.getSprite().setScale(2f);
                    shownMat.setCenter(170, 650);
                    shownMat = material.setVariables(shownMat);
                }
            } else {
                if(material.ID == IDpage) {
                    questionDrawerActive = true;
                }
            }

        }
    }
}
