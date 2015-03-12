package com.nutsdev.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by user on 12.03.15.
 */
public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, boolean isGoingDown) {
    /*    AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5F, 0.8F, 1.0F);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.5F, 0.8F, 1.0F);
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", isGoingDown ? 100 : -100, 0);
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -25, 25, -20, 20, -15, 15, -10, 10, -5, 5, 0);
        animatorSet.playTogether(animatorTranslateY, animatorTranslateX, animatorScaleX, animatorScaleY);
        animatorSet.setInterpolator(new AnticipateInterpolator());
        animatorSet.setDuration(1000);
        animatorSet.start(); */

        YoYo.with(Techniques.RubberBand).duration(1000).playOn(holder.itemView);
    }

    public static void animateToolbar(View containerToolbar) {
        containerToolbar.setRotationX(-90);
        containerToolbar.setAlpha(0.2F);
        containerToolbar.setPivotX(0.0F);
        containerToolbar.setPivotY(0.0F);

        Animator alpha = ObjectAnimator.ofFloat(containerToolbar, "alpha", 0.2F, 0.4F, 0.6F, 0.8F, 1.0F).setDuration(4000);
        Animator rotationX = ObjectAnimator.ofFloat(containerToolbar, "rotationX", -90, 60, -45, 45, -10, 30, 0, 20, 0, 5, 0).setDuration(8000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(alpha, rotationX);
        animatorSet.start();
    }

}
