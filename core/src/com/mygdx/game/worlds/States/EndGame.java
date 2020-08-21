package com.mygdx.game.worlds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utilities.NameTextInput;
import com.mygdx.game.utilities.Storage;


public class EndGame {
    private Texture bg;
    private NameTextInput getName;
    private Stage stage;
    private Image menuButton;
    private Image exitButton;
    private boolean unfreezeBg;
    private int finalScore;
    private BitmapFont font;
    private Storage storage;
    private boolean readyToChange;
    private boolean readyToExit;

    public EndGame(int finalScore)
    {
        readyToChange = false;
        readyToExit = false;
        this.finalScore = finalScore;
        bg = new Texture("test_back.jpg");
        getName = new NameTextInput(finalScore);
        unfreezeBg = false; //at first, does not let change to main Menu (until enter name)
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        storage = Storage.getInstance();

        stage = new Stage();
        menuButton = new Image(new Texture("menuButton.png"));
        menuButton.setPosition(stage.getWidth()/2-menuButton.getWidth()/2, stage.getHeight()/2-menuButton.getHeight()/2);
        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                readyToChange = true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                menuButton.setPosition(menuButton.getX()+3, menuButton.getY()+3);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                menuButton.setPosition(menuButton.getX()-3, menuButton.getY()-3);
            }
        });

        exitButton = new Image(new Texture("exitButton.png"));
        exitButton.setPosition(stage.getWidth()/2-exitButton.getWidth()/2, stage.getHeight()/2-exitButton.getHeight()/2-100);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                readyToExit = true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                exitButton.setPosition(exitButton.getX()+3, exitButton.getY()+3);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                exitButton.setPosition(exitButton.getX()-3, exitButton.getY()-3);
            }
        });

        stage.addActor(menuButton);
        stage.addActor(exitButton);
        Gdx.input.setInputProcessor(stage);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(bg, 0, 0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        font.draw(batch, "Your score: " + finalScore, 160, MyGdxGame.HEIGHT-150);
        if (getName.isOk())
            font.draw(batch, "Your ranking:  " + storage.getRanking(finalScore), 160, MyGdxGame.HEIGHT-200);
        batch.end();

        stage.act();
        stage.draw();
    }

    public void update(float delta) {
        if (getName.isClicked()) {
            unfreezeBg = true;
        }
    }

    public boolean readyToChange()
    {
        boolean temp = readyToChange;
        readyToChange = false;
        return  temp;
    }

    public boolean readyToExit()
    {
        boolean temp = readyToExit;
        readyToExit = false;
        return temp;
    }

    public void dispose() {
        bg.dispose();
        font.dispose();
        stage.dispose();
    }
}