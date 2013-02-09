package com.fourpool.cointoss.android;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ImageView ivCoin = (ImageView) findViewById(R.id.iv_coin);
		final TextView tvText = (TextView) findViewById(R.id.tv_heads_or_tails);
		final Button btnFlipCoin = (Button) findViewById(R.id.btn_flip_coin);

		final Random random = new Random();

		btnFlipCoin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int i = random.nextInt(2);

				ViewPropertyAnimator.animate(ivCoin).setDuration(500)
						.rotationYBy(1440)
						.setListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator arg0) {
								if (i == 0) {
									tvText.setText(R.string.heads);
								} else {
									tvText.setText(R.string.tails);
								}
							}

							@Override
							public void onAnimationStart(Animator arg0) {
								if (i == 0) {
									ivCoin.setImageResource(R.drawable.heads);
								} else {
									ivCoin.setImageResource(R.drawable.tails);
								}
							}

						});
			}
		});
	}
}
