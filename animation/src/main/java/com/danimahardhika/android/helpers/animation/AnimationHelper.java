package com.danimahardhika.android.helpers.animation;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.danimahardhika.android.helpers.core.ContextHelper;
import com.danimahardhika.android.helpers.core.WindowHelper;

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

    public static Animator fade(@NonNull View view) {
        return new Animator(view, Type.FADE);
    }

    public static Animator slideDownIn(@NonNull View view) {
        return new Animator(view, Type.SLIDE_DOWN_IN);
    }

    public static Animator slideUpIn(@NonNull View view) {
        return new Animator(view, Type.SLIDE_UP_IN);
    }

    public static Animator slideDownOut(@NonNull View view) {
        return new Animator(view, Type.SLIDE_DOWN_OUT);
    }

    public static Animator slideUpOut(@NonNull View view) {
        return new Animator(view, Type.SLIDE_UP_OUT);
    }

    public static class Animator {

        private final View view;
        private final Type type;

        private int duration;
        private TimeInterpolator interpolator;
        private android.animation.Animator.AnimatorListener listener;

        private Animator(@NonNull View view, Type type) {
            this.view = view;
            this.type = type;
            this.duration = 200;
            this.interpolator = new DecelerateInterpolator();
            this.listener = null;
        }

        public Animator duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Animator interpolator(@NonNull TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public Animator listener(@NonNull android.animation.Animator.AnimatorListener listener) {
            this.listener = listener;
            return this;
        }

        public void start() {
            switch (type) {
                case SHOW:
                    animateShow(this);
                    break;
                case FADE:
                    animateFade(this);
                    break;
                case SLIDE_DOWN_IN:
                case SLIDE_DOWN_OUT:
                    animateSlideDown(this);
                    break;
                case SLIDE_UP_IN:
                case SLIDE_UP_OUT:
                    animateSlideUp(this);
                    break;
                default:
                    animateFade(this);
                    break;
            }
        }
    }

    private static void animateShow(Animator animator) {
        animator.view.animate().cancel();
        animator.view.setScaleX(0f);
        animator.view.setScaleY(0f);
        animator.view.setAlpha(0f);
        animator.view.setVisibility(View.VISIBLE);

        animator.view.animate()
                .setDuration(animator.duration)
                .scaleX(1)
                .scaleY(1)
                .alpha(1)
                .setInterpolator(animator.interpolator)
                .setListener(animator.listener);
    }

    private static void animateFade(Animator animator) {
        float startAlpha = animator.view.getVisibility() == View.VISIBLE ? 1f : 0f;
        float endAlpha = animator.view.getVisibility() == View.VISIBLE ? 0f : 1f;

        animator.view.animate().cancel();
        animator.view.setAlpha(startAlpha);
        animator.view.setVisibility(View.VISIBLE);

        animator.view.animate()
                .setDuration(animator.duration)
                .alpha(endAlpha)
                .setInterpolator(animator.interpolator)
                .setListener(animator.listener);
    }

    private static void animateSlideDown(Animator animator) {
        float originalPosition = animator.view.getY();
        Context context = ContextHelper.getBaseContext(animator.view);
        Point point = WindowHelper.getScreenSize(context);

        animator.view.animate().cancel();
        if (animator.type == Type.SLIDE_DOWN_IN) {
            animator.view.setY(-point.y);
            animator.view.setVisibility(View.VISIBLE);

            animator.view.animate()
                    .translationY(originalPosition)
                    .setInterpolator(animator.interpolator)
                    .setListener(animator.listener);
        } else if (animator.type == Type.SLIDE_DOWN_OUT) {
            animator.view.setVisibility(View.VISIBLE);

            animator.view.animate()
                    .translationY(point.y)
                    .setInterpolator(animator.interpolator)
                    .setListener(animator.listener);
        }
    }

    private static void animateSlideUp(Animator animator) {
        float originalPosition = animator.view.getY();
        Context context = ContextHelper.getBaseContext(animator.view);
        Point point = WindowHelper.getScreenSize(context);

        animator.view.animate().cancel();
        if (animator.type == Type.SLIDE_UP_IN) {
            animator.view.setY(point.y);
            animator.view.setVisibility(View.VISIBLE);

            animator.view.animate()
                    .translationY(originalPosition)
                    .setInterpolator(animator.interpolator)
                    .setListener(animator.listener);
        } else if (animator.type == Type.SLIDE_UP_OUT) {
            animator.view.setVisibility(View.VISIBLE);

            animator.view.animate()
                    .translationY(-point.y)
                    .setInterpolator(animator.interpolator)
                    .setListener(animator.listener);
        }
    }

    private enum Type {
        SHOW,
        FADE,
        SLIDE_DOWN_IN,
        SLIDE_UP_IN,
        SLIDE_DOWN_OUT,
        SLIDE_UP_OUT
    }
}
