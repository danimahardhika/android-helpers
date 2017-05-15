package com.danimahardhika.android.helpers.animation;

import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/*
 * Android Helpers
 *
 * Copyright (c) 2017 Dani Mahardhika
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class AnimationHelper {

    public static Animator show(@NonNull View view) {
        return new Animator(view, Type.SHOW);
    }

    public static Animator hide(@NonNull View view) {
        return new Animator(view, Type.HIDE);
    }

    public static Animator fade(@NonNull View view) {
        return new Animator(view, Type.FADE);
    }

    public static class Animator {

        private final View view;
        private final Type type;
        private int duration;
        private TimeInterpolator interpolator;
        private Callback callback;

        private Animator(@NonNull View view, Type type) {
            this.view = view;
            this.type = type;
            this.duration = 200;
            this.interpolator = new DecelerateInterpolator();
        }

        public Animator duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Animator interpolator(@NonNull TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }


        public Animator callback(@NonNull Callback callback) {
            this.callback = callback;
            return this;
        }

        public void start() {
            switch (type) {
                case SHOW:
                    animateShow(this);
                    break;
                case HIDE:
                    animateHide(this);
                    break;
                case FADE:
                    animateFade(this);
                    break;
                default:
                    animateFade(this);
                    break;
            }
        }
    }

    private static void animateShow(final Animator animator) {
        animator.view.animate().cancel();
        animator.view.setScaleX(0f);
        animator.view.setScaleY(0f);
        animator.view.setAlpha(0f);
        animator.view.setVisibility(View.VISIBLE);

        animator.view.animate()
                .setDuration(animator.duration)
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setInterpolator(animator.interpolator)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(android.animation.Animator animation) {
                        super.onAnimationStart(animation);
                        if (animator.callback != null) animator.callback.onAnimationStart();
                    }

                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        super.onAnimationEnd(animation);
                        if (animator.callback != null) animator.callback.onAnimationEnd();;
                    }
                });
    }

    private static void animateHide(final Animator animator) {
        animator.view.animate().cancel();
        animator.view.setScaleX(1f);
        animator.view.setScaleY(1f);
        animator.view.setAlpha(1f);
        animator.view.setVisibility(View.VISIBLE);

        animator.view.animate()
                .setDuration(animator.duration)
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setInterpolator(animator.interpolator)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(android.animation.Animator animation) {
                        super.onAnimationStart(animation);
                        if (animator.callback != null) animator.callback.onAnimationStart();
                    }

                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        super.onAnimationEnd(animation);
                        animator.view.setVisibility(View.GONE);
                        if (animator.callback != null) animator.callback.onAnimationEnd();
                    }
                });
    }

    private static void animateFade(final Animator animator) {
        final float startAlpha = animator.view.getVisibility() == View.VISIBLE ? 1f : 0f;
        final float endAlpha = animator.view.getVisibility() == View.VISIBLE ? 0f : 1f;

        animator.view.animate().cancel();
        animator.view.setAlpha(startAlpha);
        animator.view.setVisibility(View.VISIBLE);

        animator.view.animate()
                .setDuration(animator.duration)
                .alpha(endAlpha)
                .setInterpolator(animator.interpolator)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(android.animation.Animator animation) {
                        super.onAnimationStart(animation);
                        if (animator.callback != null) animator.callback.onAnimationStart();
                    }

                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        super.onAnimationEnd(animation);
                        if (startAlpha == 1f) {
                            animator.view.setVisibility(View.GONE);
                        }

                        if (animator.callback != null) animator.callback.onAnimationEnd();
                    }
                });
    }

    private enum Type {
        SHOW,
        HIDE,
        FADE
    }

    public interface Callback {
        void onAnimationStart();
        void onAnimationEnd();
    }
}
