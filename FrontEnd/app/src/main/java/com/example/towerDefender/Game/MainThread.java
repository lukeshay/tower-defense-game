package com.example.towerDefender.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.towerDefender.Game.GameView;
import com.example.towerDefender.R;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    /**
     * The {@link Canvas} associated with this {@link Thread}
     */
    public static Canvas canvas;
    public Bitmap background;
    private int targetFPS = 60;
    private long averageFPS;

    /**
     * Constructs a new {@link MainThread}.
     * @param surfaceHolder the {@link SurfaceHolder} for this {@link Thread} to use
     * @param gameView the {@link GameView} associated with this {@link Thread}
     */
    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.background = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.night_background);
    }

    /**
     * Sets the value of the 'running' field to the provided boolean
     * @param isRunning the value to set 'running' to
     */
    public void setRunning(boolean isRunning){
        running = isRunning;
    }


    /**
     * Draws to the canvas when if a certain amount of time has passed between the last update. Prints out FPS at each update
     */
    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / targetFPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {

            } finally {
                if (canvas != null)            {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == targetFPS)        {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                Log.d("THREAD_FPS", "MainThread's averageFPS: " + averageFPS);
            }
        }

    }

}
