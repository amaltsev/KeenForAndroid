package org.yegie.keenforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

public class KeenActivity extends Activity {
    private static final String TAG = "KeenActivity";

    private int size = 3;
    private int diff = 1;
    private int multOnly = 0;
    private long seed = 10101;


    public Bundle getGameData()
    {
        Bundle data = new Bundle();
        data.putInt("size",size);
        data.putInt("diff",diff);
        data.putInt("mult",multOnly);
        data.putLong("seed",seed);
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keen);
    //    Toolbar Toolbar = (Toolbar) findViewById(R.id.toolbar);
    //    setSupportActionBar(Toolbar);

        size=getIntent().getExtras().getInt(MenuActivity.GAME_SIZE,0);
        diff=getIntent().getExtras().getInt(MenuActivity.GAME_DIFF,0);
        multOnly=getIntent().getExtras().getInt(MenuActivity.GAME_MULT,0);
        seed=getIntent().getExtras().getLong(MenuActivity.GAME_SEED,0);

        if(size<3 || size>9) {
            Log.e("KEEN","Got invalid game size, quitting...");
            setResult(Activity.RESULT_CANCELED);
            finish();
        }

        runGame();
    }

    @Override
    public void onBackPressed() {
        /// super.onBackPressed();
        Log.d(TAG,"TODO: Implement undo/redo here");
    }

    private KeenModel gameModel;

    public KeenModel getGameModel()
    {
        return gameModel;
    }

    public void setGameModel(KeenModel a)
    {
        gameModel = a;
    }

    public void runGame()
    {
        //KeenModelBuilder builder=new KeenModelBuilder();

        //KeenModel gameModel = builder.build(size,diff,multOnly,seed);

        Thread gameGenThread = new Thread(new LevelGenMultiThread(this));
        gameGenThread.start();

        while(gameModel == null){
            //put some sort of load screen here
        }

        KeenView gameView = new KeenView(this,gameModel);
        KeenController gameController = new KeenController(gameModel,gameView);

        ViewGroup container = (ViewGroup) findViewById(R.id.keen_container);
        container.addView(gameView);
    }


}
