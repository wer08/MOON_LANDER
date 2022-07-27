package com.codegym.games.moonlander;

import com.codegym.engine.cell.*;



public class MoonLanderGame extends Game
{
    public static final int WIDTH =64;
    public static final int HEIGHT =64;

    private boolean isGameStopped;
    private GameObject platform;
    private boolean isUpPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private Rocket rocket;
    private GameObject landscape;
     private boolean isLandscape1Chosen = true;
     private boolean isLandscape2Chosen = false;

    private int score;
    private void chooseLandscape()
    {


    }
    
    
    public void initialize()
    {
        showGrid(false);
        setScreenSize(WIDTH,HEIGHT);
        createGame();
    }
    private void createGame()
    {
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
        isGameStopped = false;

        createGameObjects();
        drawScene();
        setTurnTimer(50);
        score = 1000;
    }
    private void drawScene()
    {
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++) {
                setCellColor(x, y, Color.BLACK);
            }
        }
        landscape.draw(this);

        rocket.draw(this);
    }
    private void createGameObjects()
    {
        //showMessageDialog(Color.ALICEBLUE,"Press Enter for Landscape 1 or press ESC for Landscape 2",Color.BLACK,26);
        rocket = new Rocket(WIDTH/2,0);
        if(isLandscape1Chosen)
        {
            landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);
        }
        if(isLandscape2Chosen)
        {
            landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE2f);
        }
        platform = new GameObject(23, MoonLanderGame.HEIGHT - 1, ShapeMatrix.PLATFORM);

    }

    @Override
    public void onTurn(int step)
    {
        rocket.move(isUpPressed,isLeftPressed,isRightPressed);
        check();
        if(score>0)
        {
            score--;
        }
        setScore(score);
        drawScene();

    }

    @Override
    public void setCellColor(int x, int y, Color color)
    {
        if(x<0||x>=WIDTH||y<0||y>=HEIGHT)
            return;
        super.setCellColor(x, y, color);
    }

    @Override
    public void onKeyPress(Key key)
    {
       if(key == Key.UP)
       {
           isUpPressed=true;
       }
       if(key == Key.LEFT)
       {
           isLeftPressed=true;
           isRightPressed=false;
       }
       if(key == Key.RIGHT)
       {
           isRightPressed=true;
           isLeftPressed=false;
       }
     /*  if(key == Key.SPACE&&isGameStopped)
       {
           createGame();
       }*/
       if(key == Key.ENTER&&isGameStopped)
       {
           isLandscape1Chosen = true;
           isLandscape2Chosen = false;
           createGame();
       }
       if(key == Key.ESCAPE&&isGameStopped)
       {
           isLandscape2Chosen = true;
           isLandscape1Chosen = false;
           createGame();
       }

    }

    @Override
    public void onKeyReleased(Key key)
    {
        if(key == Key.UP)
        {
            isUpPressed=false;
        }
        if(key == Key.LEFT)
        {
            isLeftPressed=false;
        }
        if(key == Key.RIGHT)
        {
            isRightPressed=false;
        }
    }
    private void check()
    {
        if(rocket.isCollision(landscape)&&!(rocket.isCollision(platform)&& rocket.isStopped()))
        {
            gameOver();
        }
        if(rocket.isCollision(platform)&& rocket.isStopped())
        {
            win();
        }
    }
    private void win()
    {
        isGameStopped = true;
        rocket.land();
        showMessageDialog(Color.PINK,"SUCCES \n Press Enter for Landscape 1 \n or press ESC for Landscape 2",Color.BLACK,26);
        stopTurnTimer();
    }
    private void gameOver()

    {
        isGameStopped = true;
        rocket.crash();
        score =0;
        setScore(score);
        showMessageDialog(Color.BLACK,"FAILURE \n Press Enter for Landscape 1 \n or press ESC for Landscape 2",Color.WHITE,26);
        stopTurnTimer();


    }
}