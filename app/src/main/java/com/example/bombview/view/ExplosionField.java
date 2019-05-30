package com.example.bombview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.bombview.factory.ParticleFactory;
import com.example.bombview.utils.BitmapUtils;
import com.example.bombview.utils.ExplosionAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ExplosionField extends View {

    private OnClickListener onClickListener;
    private Random mRandom = new Random();//随机数生成
    private List<ExplosionAnimator> explosionAnimators;
    private HashMap<View, ExplosionAnimator> explosionAnimatorHashMap;
    private ParticleFactory particleFactory;

    public ExplosionField(Context context, ParticleFactory factory) {
        super(context);
        attch2Activity((Activity) context);
        explosionAnimators = new ArrayList<>();
        explosionAnimatorHashMap = new HashMap<>();
        particleFactory = factory;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(ExplosionAnimator animator : explosionAnimators){
            animator.draw(canvas);
        }
    }

    /**
     * 添加全屏动画的场地
     * @param activity
     */
    public void attch2Activity(Activity activity){
        ViewGroup root = activity.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        root.addView(this, layoutParams);
    }

    /**
     * 为View添加爆炸效果的监听器
     */
    public void addListener(View view){
        if(view instanceof ViewGroup){
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for(int i = 0; i < count; i++){
                addListener(group.getChildAt(i));
            }
        }else{
            view.setOnClickListener(getOnClickListener());
        }
    }

    private OnClickListener getOnClickListener() {
        if(onClickListener == null){
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加动画效果
                    ExplosionField.this.exploded(v);
                }
            };
        }
        return onClickListener;
    }

    /**
     * 敌军到达战场，颤抖吧，爆炸吧
     */
    private void exploded(final View view){
        if(explosionAnimatorHashMap.get(view) != null && explosionAnimatorHashMap.get(view).isStarted()){
            return;
        }
        if(view.getVisibility() != View.VISIBLE || view.getAlpha() == 0){
            return;
        }
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);//view在屏幕中的坐标
        int actionBarHeight = ((ViewGroup)view.getParent()).getTop();//ActionBar高度
        int statusBarHeigh = ((Activity)getContext()).getWindow().getDecorView().getTop();//状态栏高度

        rect.offset(0, -actionBarHeight-statusBarHeigh);//得到view在content中相对的高度

        if(rect.height() == 0 || rect.width() == 0){//view的宽度或者高度为0，无法展示爆炸效果
            return;
        }

        //震动效果，通过属性动画生成递增值
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((mRandom.nextFloat() - 0.5f)*(view.getWidth()*0.05f));
                view.setTranslationY((mRandom.nextFloat()-0.5f)*(view.getHeight()*0.05f));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setTranslationX(0f);
                view.setTranslationY(0f);
                view.setClickable(true);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                view.setClickable(false);
                exploded(view, rect);

            }
        });
        animator.start();
    }

    private void exploded(final View view, Rect rect) {
        final ExplosionAnimator explosionAnimator = new ExplosionAnimator(particleFactory, this, BitmapUtils.createBitmapFromView(view), rect);
        explosionAnimators.add(explosionAnimator);
        explosionAnimatorHashMap.put(view, explosionAnimator);
        explosionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setClickable(true);
                view.animate().setDuration(150).scaleX(1f).scaleY(1f).alpha(1f).start();
                explosionAnimators.remove(animation);
                explosionAnimatorHashMap.remove(view);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                view.setClickable(false);
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f).start();
            }
        });
        explosionAnimator.start();
    }

}
