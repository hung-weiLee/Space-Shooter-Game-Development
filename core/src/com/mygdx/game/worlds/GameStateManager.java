package com.mygdx.game.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.worlds.States.EndGame;
import com.mygdx.game.worlds.States.Menu;
import com.mygdx.game.worlds.States.StageOneMap;

//manage Menu (begin state), GameMap (play state), EndGame (end state)
public class GameStateManager {
    private static GameStateManager _instance;
    private StageOneMap playState;
    private Menu beginState;
    private EndGame endState;
    private int stateNum; //1 = beginState, 2 = playState, 3 = endState
    private Music music;
    private long playStateEnd;

	private boolean hard;

    protected GameStateManager()
    {
        beginState = new Menu();
        stateNum = 1;
        setMusic("opening.mp3", true, 0.5f);
        music.play();
        playStateEnd = 0;
    }

    private void setMusic(String musicPath, boolean looping, float volume)
    {
        music = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        music.setLooping(looping);
        music.setVolume(volume);
    }

    public static GameStateManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new GameStateManager();
        }
        return _instance;
    }

    public void update()
    {

        switch (stateNum)
        {
            case 1: //the game is at state 1
				hard=beginState.getHard();
                if (beginState.readyToChange()) { // if state 1 is triggered to move to next state
                    playState = new StageOneMap(hard);
                    beginState.dispose();
                    beginState = null;
                    stateNum += 1; //stateNum = 2
                    playStateEnd = 0;
                    changeMusic("playSound.mp3", true, 0.5f);
                }
                break;

            case 2:
                if (playStateIsTriggered() && playStateEnd==0)//just win/lose
                {
                    playStateEnd = TimeUtils.nanoTime();
                }

                if (playStateIsTriggered() && TimeUtils.nanoTime() - playStateEnd > 4000000000L) {
                    endState = new EndGame(playState.getScore());
                    playState.dispose();
                    playState = null;
                    stateNum += 1; //stateNum = 3
                    changeMusic("opening.mp3", true, 0.1f);
                }
                break;

            case 3:
                if (endState.readyToChange()) {
                    beginState = new Menu();
                    endState.dispose();
                    endState = null;
                    stateNum = 1; //loop back to beginning state
                }
                else if (endState.readyToExit())
                {
                    Gdx.app.exit();
                }
                break;
        }
    }

    public void changeMusic(String newMusicPath, boolean newLooping, float newVolume)
    {
        music.stop();
        setMusic(newMusicPath, newLooping, newVolume);
        music.play();
    }


    //the play state is triggered to move to next state
    private boolean playStateIsTriggered()
    {
        return (playState.isLose() ||
                playState.isWin());
    }

    //render a state depending on the state number
    public void render(OrthographicCamera camera, SpriteBatch batch)
    {
        switch (stateNum)
        {
            case 1: //if updated state number is 1, move to state 1: beginningState
                beginState.render(batch);
                break;
            case 2:
                playState.update(Gdx.graphics.getDeltaTime());
                playState.render(camera, batch);
                break;
            case 3:
                endState.update(Gdx.graphics.getDeltaTime());
                endState.render(batch);
                break;
        }
    }
}
