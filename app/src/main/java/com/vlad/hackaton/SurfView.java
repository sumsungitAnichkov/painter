package com.vlad.hackaton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class SurfView extends SurfaceView {

    public static enum DrawingMode {
        Line, Circle, Rect, Spline
    }

    private final static String TAG = "SURFACE";

    Path path;
    Thread drawing;
    SurfaceHolder holder;

    Paint brush;

    ArrayList<Path> objects = new ArrayList<>();
    DrawingMode mode = DrawingMode.Rect;

    public SurfView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SurfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SurfView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public SurfView(Context context) {
        super(context);
        init();
    }

    private void init() {
        holder = getHolder();

        brush = new Paint();
        brush.setStrokeWidth(10);
        brush.setColor(Color.CYAN);
        brush.setStyle(Paint.Style.STROKE);
    }

    Point pressedDot = new Point();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX(), y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressedDot.set((int) x, (int) y);

                Path path = new Path();
                path.moveTo(x, y);

                objects.add(path);

                break;

            case MotionEvent.ACTION_MOVE:

                switch (mode) {
                    case Line:

                        path = new Path();
                        path.moveTo(pressedDot.x, pressedDot.y);
                        objects.set(objects.size() - 1, path);

                        objects.get(objects.size() - 1).lineTo(x, y);
                        break;

                    case Circle:
                        path = new Path();
                        path.moveTo(pressedDot.x, pressedDot.y);
                        objects.set(objects.size() - 1, path);

                        objects.get(objects.size() - 1)
                                .addCircle(pressedDot.x, pressedDot.y,
                                        (float) Math.sqrt((pressedDot.x - x) * (pressedDot.x - x) +
                                                (pressedDot.y - y) * (pressedDot.y - y)),
                                        Path.Direction.CW);
                        break;
                    case Rect:
                        path = new Path();
                        path.moveTo(pressedDot.x, pressedDot.y);
                        objects.set(objects.size() - 1, path);

                        objects.get(objects.size() - 1).addRect(pressedDot.x, pressedDot.y,
                                x, y, Path.Direction.CW);
                        break;

                    case Spline:
                        objects.get(objects.size() - 1).lineTo(x, y);
                        objects.get(objects.size() - 1).moveTo(x, y);
                        break;
                }

                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        update();

        return true;
    }

    public void update() {
        Canvas mat = holder.lockCanvas();
        mat.drawColor(Color.WHITE);
        for (Path obj : objects) {
            mat.drawPath(obj, brush);
        }
        holder.unlockCanvasAndPost(mat);
    }

    public void setMode(DrawingMode mode) {
        this.mode = mode;
    }

    public void redo() {
        if (!objects.isEmpty())
            objects.remove(objects.size() - 1);

        update();
    }

    public void setColor(int r, int g, int b) {
        brush.setColor(Color.rgb(r, g, b));
    }
}
