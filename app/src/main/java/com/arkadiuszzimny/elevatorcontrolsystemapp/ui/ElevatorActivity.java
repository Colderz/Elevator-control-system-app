package com.arkadiuszzimny.elevatorcontrolsystemapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.arkadiuszzimny.elevatorcontrolsystemapp.R;
import com.arkadiuszzimny.elevatorcontrolsystemapp.databinding.ActivityElevatorBinding;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments.MainFragment;
import com.arkadiuszzimny.elevatorcontrolsystemapp.ui.fragments.PanelFragment;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ElevatorActivity extends AppCompatActivity {

    ActivityElevatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityElevatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setFragment(new MainFragment());

        FrameLayout button = binding.button;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(v);
            }
        });
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    public void load(View view) {
        animateButtonWidth();

        fadeOutTextAndShowProgressDialog();

        nextAction();
    }

    private void animateButtonWidth() {
        ValueAnimator anim = ValueAnimator.ofInt(binding.button.getMeasuredWidth(), getFabWidth());

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = binding.button.getLayoutParams();
                layoutParams.width = val;
                binding.button.requestLayout();
            }
        });
        anim.setDuration(250);
        anim.start();
    }

    private void fadeOutTextAndShowProgressDialog() {
        binding.text.animate().alpha(0f)
                .setDuration(250)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        showProgressDialog();
                    }
                })
                .start();
    }

    private void showProgressDialog() {
        binding.progressBar.setAlpha(1f);
        binding.progressBar
                .getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        binding.progressBar.setVisibility(VISIBLE);
    }

    private void nextAction() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealButton();

                fadeOutProgressDialog();

                delayedStartNextActivity();
            }
        }, 1700);
    }

    private void revealButton() {
        binding.button.setElevation(0f);

        binding.reveal.setVisibility(VISIBLE);

        int cx = binding.reveal.getWidth();
        int cy = binding.reveal.getHeight();


        int x = (int) (getFabWidth() / 2 + binding.button.getX());
        int y = (int) (getFabWidth() / 2 + binding.button.getY());

        float finalRadius = Math.max(cx, cy) * 1.2f;

        Animator reveal = ViewAnimationUtils
                .createCircularReveal(binding.reveal, x, y, getFabWidth(), finalRadius);

        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reset(animation);
//                finish();
            }

            private void reset(Animator animation) {
                super.onAnimationEnd(animation);
                binding.reveal.setVisibility(INVISIBLE);
                binding.text.setVisibility(VISIBLE);
                binding.text.setAlpha(1f);
                binding.button.setElevation(4f);
                ViewGroup.LayoutParams layoutParams = binding.button.getLayoutParams();
                layoutParams.width = (int) (getResources().getDisplayMetrics().density * 300);
                binding.button.requestLayout();
            }
        });

        reveal.start();
    }

    private void fadeOutProgressDialog() {
        binding.progressBar.animate().alpha(0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        }).start();
    }

    private void delayedStartNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new PanelFragment())
                        .commit();
            }
        }, 200);
        binding.button.setVisibility(INVISIBLE);
    }

    private int getFabWidth() {
        return (int) getResources().getDimension(R.dimen.fab_size);
    }
}