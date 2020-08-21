package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class NameTextInput implements Input.TextInputListener {
    private boolean isClicked;
    private boolean isOk;
    private boolean isCancel;
    private Storage storage;
    private int finalScore;

    public NameTextInput(int finalScore)
    {
        super();
        Gdx.input.getTextInput(this, "Do you want to save the score?", "", "What's your name?");
        isClicked = false;
        isOk = false;
        isCancel = false;
        storage = Storage.getInstance();
        this.finalScore = finalScore;
    }

    @Override
    public void input (String text) {
        storage.store(text, finalScore);
        isOk = true;
        isClicked = true;
    }

    @Override
    public void canceled () {
        isCancel = true;
        isClicked = true;
    }

    public boolean isClicked()
    {
        return isClicked;
    }
    public boolean isOk() {return isOk;}
    public boolean isCancel() {return isCancel;}
}
