package beta.com.android17.onevault.Utility;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;

import beta.com.android17.onevault.R;

/**
 * Created by RRosall on 12/12/2015.
 */
public class RunTime {

    public static Handler handler;

    public static int ACTIVE_ACCOUNT;
    public static int CURRENT_THEME = 0;
    public static int NEXT_THEME = 0;
    public static int THEME_COLOR;
    public static int DRAWER_LAST_IDENTIFIER = 9;
    public static int DRAWER_IDENTIFIERS[] = new int[30];

    public static int[] colors = {
            R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5
                };

    RunTime(){}

    public static void ThemeChooser() {
        NEXT_THEME = Randomize(4, 0);

        while(NEXT_THEME==CURRENT_THEME){
            NEXT_THEME = Randomize(4,0);
        }

        CURRENT_THEME = NEXT_THEME;
        System.out.println("index theeme: " +CURRENT_THEME);
        THEME_COLOR = colors[CURRENT_THEME]; // get the color id

    }

    public static void Themer(final Toolbar toolbar){
////        random to 0-n , 1-n (random.nextInt(max-min)+1) + min
//        NEXT_THEME = Randomize(4,0);
//
//        while(NEXT_THEME==CURRENT_THEME){
//            NEXT_THEME = Randomize(4,0);
//        }
//
//        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), CURRENT_THEME, NEXT_THEME);
//
//        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                toolbar.setBackgroundColor((Integer) animation.getAnimatedValue());
//            }
//        });
//
//        colorAnimation.setStartDelay(2000);
//        colorAnimation.setDuration(2000);
//        colorAnimation.start();
//
//        CURRENT_THEME = NEXT_THEME;
    }


    public static void Themer(Toolbar toolbar, Toolbar toolbar1){
////        random to 0-n , 1-n (random.nextInt(max-min)+1) + min
//        int x = Randomize(2,1);
////        TODO: CHANGE THEMESSS
//
//        while(CURRENT_THEME==x){
//            x = Randomize(2,0);
//        }
//
//        CURRENT_THEME = x;
//
//        toolbar.setBackgroundColor(colors[x]);
//        if(toolbar1!=null)
//            toolbar1.setBackgroundColor(colors[x]);
    }

    public static int Randomize(int max, int min){
        return (new Random().nextInt( (max-min) + 1)) + min;
    }

    public static class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    public static void delayThemer(final Toolbar toolbar, final Toolbar toolbar1){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(toolbar1==null){
                    Themer(toolbar, toolbar); //TODO: CHANGE THIS
                }
                else{
                    Themer(toolbar, toolbar1);
                }
            }
        } , 1500);
    }


    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

}
