package com.mygdx.game.worlds.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utilities.PopUp;
import com.mygdx.game.utilities.SettingPopUp;
import com.mygdx.game.utilities.Storage;
import com.mygdx.game.utilities.TextPopUp;

public class Menu {
    private Texture bg;
    private Texture title;
    private Stage stage;
	private boolean hard;
    private Image playButton;
    private Image playHardButton;
    private Image tutorialButton;
    private Image settingButton;
    private Image aboutButton;
    private BitmapFont font;
    private Storage storage;
    private PopUp popUp;
    private boolean readyToChange;

    public Menu()
    {
        readyToChange = false;
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        font.setColor(Color.YELLOW);
        storage = Storage.getInstance();
        popUp = null;
        bg = new Texture("test_back.jpg");
        title = new Texture("title.png");
        stage = new Stage();

		hard=false;

        playButton = new Image(new Texture("playButton.png"));
        playButton.setPosition(stage.getWidth()/2-playButton.getWidth()/2, stage.getHeight()/2-playButton.getHeight()/2);

        playHardButton = new Image(new Texture("playHardButton.png"));
        playHardButton.setPosition(stage.getWidth()/2-playButton.getWidth()/2, stage.getHeight()/2-playButton.getHeight()/2-100);
        
		tutorialButton = new Image(new Texture("tutorialButton.png"));
        tutorialButton.setPosition(stage.getWidth()/2-tutorialButton.getWidth()/2, stage.getHeight()/2-tutorialButton.getHeight()/2-170);
        settingButton = new Image(new Texture("settingButton.png"));
        settingButton.setPosition(stage.getWidth()/2-settingButton.getWidth()/2, stage.getHeight()/2-settingButton.getHeight()/2-70*2-100);
        aboutButton = new Image(new Texture("aboutButton.png"));
        aboutButton.setPosition(stage.getWidth()/2-aboutButton.getWidth()/2, stage.getHeight()/2-aboutButton.getHeight()/2-70*3-100);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                readyToChange = true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                playButton.setPosition(playButton.getX()+3, playButton.getY()+3);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                playButton.setPosition(playButton.getX()-3, playButton.getY()-3);
            }
        });


        playHardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
				hard=true;
                readyToChange = true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                playHardButton.setPosition(playHardButton.getX()+3, playHardButton.getY()+3);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                playHardButton.setPosition(playHardButton.getX()-3, playHardButton.getY()-3);
            }
        });

        settingButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (popUp != null)
                    popUp.close();
                popUp = new SettingPopUp("Setting", 0,0);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                settingButton.setPosition(settingButton.getX()+3, settingButton.getY()+3);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                settingButton.setPosition(settingButton.getX()-3, settingButton.getY()-3);
            }
        });

        aboutButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (popUp != null)
                    popUp.close();
                popUp = new TextPopUp("About", 300,100, "core/assets/about.txt");
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                aboutButton.setPosition(aboutButton.getX()+3, aboutButton.getY()+3);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                aboutButton.setPosition(aboutButton.getX()-3, aboutButton.getY()-3);
            }
        });

        tutorialButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (popUp != null)
                    popUp.close();
                popUp = new TextPopUp("Tutorial", 300,400, "core/assets/tutorial.txt");
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                tutorialButton.setPosition(tutorialButton.getX()+3, tutorialButton.getY()+3);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                tutorialButton.setPosition(tutorialButton.getX()-3, tutorialButton.getY()-3);
            }
        });

        stage.addActor(playButton);
        stage.addActor(tutorialButton);
		stage.addActor(playHardButton);
        stage.addActor(aboutButton);
        stage.addActor(settingButton);
        Gdx.input.setInputProcessor(stage);
    }

	public boolean getHard()
	{
		return hard;
	}

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(bg, 0, 0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        batch.draw(title, 20, MyGdxGame.HEIGHT-220, MyGdxGame.WIDTH - 20, 140);
        font.draw(batch, "Top 3 scores", MyGdxGame.WIDTH-300, MyGdxGame.HEIGHT-230);
        font.draw(batch, storage.getTopEntry(3), MyGdxGame.WIDTH-330, MyGdxGame.HEIGHT-260);
        batch.end();
        stage.act();
        stage.draw();
    }


    public boolean readyToChange()
    {
        boolean temp = readyToChange;
        readyToChange = false;
        return temp;
    }

    public void dispose() {
        bg.dispose();
        title.dispose();
        font.dispose();
        if (popUp != null)
            popUp.dispose();
        stage.dispose();
    }
}
