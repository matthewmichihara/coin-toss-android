package com.fourpool.cointoss.android;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.squareup.seismic.ShakeDetector;

public class MainActivity extends Activity implements ShakeDetector.Listener {
    private final Random mRandom = new Random();
    private ImageView mIvCoin;
    private TextView mTvText;
    private Button mBtnFlipCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvCoin = (ImageView) findViewById(R.id.iv_coin);
        mTvText = (TextView) findViewById(R.id.tv_heads_or_tails);
        mBtnFlipCoin = (Button) findViewById(R.id.btn_flip_coin);

        // Shake detection.
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        ShakeDetector shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);

        mBtnFlipCoin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCoin();
            }
        });
    }

    @Override
    public void hearShake() {
        flipCoin();
    }

    private void flipCoin() {
        // Prevent multiple button clicks during a coin flip.
        mBtnFlipCoin.setEnabled(false);

        final int i = mRandom.nextInt(2);

        ViewPropertyAnimator.animate(mIvCoin).setDuration(500)
                .rotationYBy(1440)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator arg0) {
                        if (i == 0) {
                            mTvText.setText(R.string.heads);
                        } else {
                            mTvText.setText(R.string.tails);
                        }

                        // Re-enable the button.
                        mBtnFlipCoin.setEnabled(true);
                    }

                    @Override
                    public void onAnimationStart(Animator arg0) {
                        if (i == 0) {
                            mIvCoin.setImageResource(R.drawable.heads);
                        } else {
                            mIvCoin.setImageResource(R.drawable.tails);
                        }
                    }

                });
    }
}
