package com.fourpool.cointoss.android;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.squareup.seismic.ShakeDetector;

import java.util.Random;

public class MainActivity extends SherlockActivity implements ShakeDetector.Listener {
    private final Random mRandom = new Random();
    private TextView mTvInfoText;
    private ImageView mIvCoin;
    private TextView mTvText;
    private Button mBtnFlipCoin;
    private volatile boolean mShakeInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mTvInfoText = (TextView) findViewById(R.id.tv_intro_text);
        mIvCoin = (ImageView) findViewById(R.id.iv_coin);
        mTvText = (TextView) findViewById(R.id.tv_heads_or_tails);
        mBtnFlipCoin = (Button) findViewById(R.id.btn_flip_coin);

        mBtnFlipCoin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCoin();
            }
        });

        // Shake detection.
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        ShakeDetector shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);
    }

    @Override
    public void hearShake() {
        flipCoin();
    }

    private synchronized void flipCoin() {
        if (mShakeInProgress) {
            return;
        }

        // Replace the intro text with the coin.
        mTvInfoText.setVisibility(View.GONE);
        mIvCoin.setVisibility(View.VISIBLE);
        mTvText.setVisibility(View.VISIBLE);

        mShakeInProgress = true;

        // Disable button during coin flip.
        mBtnFlipCoin.setEnabled(false);

        final boolean isHeads = mRandom.nextBoolean();

        ViewPropertyAnimator.animate(mIvCoin).setDuration(500)
                .rotationYBy(1440)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator arg0) {
                        if (isHeads) {
                            mIvCoin.setImageResource(R.drawable.heads);
                        } else {
                            mIvCoin.setImageResource(R.drawable.tails);
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator arg0) {
                        if (isHeads) {
                            mTvText.setText(R.string.heads);
                        } else {
                            mTvText.setText(R.string.tails);
                        }

                        mShakeInProgress = false;

                        // Re-enable the button.
                        mBtnFlipCoin.setEnabled(true);
                    }
                });
    }
}
