package com.example.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SemiCircleDrawable extends View {

    RectF oval = new RectF();

    private Paint backgroundPaint;
    private Paint progressPaint;
    private Paint progressPointPaint;

    private float top;
    private float bottom;
    private float left ;
    private float right;

    //hoanh
    private float abscissaOfCenterOfCircle;
    //tung do
    private float ordinateOfCenterOfCircle;
    private float radius;
    private float thickness;
    private int colorBackground;
    private int colorProgress;
    private int progress;
    private int max;


    public float getAbscissaOfCenterOfCircle() {
        return abscissaOfCenterOfCircle;
    }

    public void setAbscissaOfCenterOfCircle(float abscissaOfCenterOfCircle) {
        this.abscissaOfCenterOfCircle = abscissaOfCenterOfCircle;
    }

    public float getOrdinateOfCenterOfCircle() {
        return ordinateOfCenterOfCircle;
    }

    public void setOrdinateOfCenterOfCircle(float ordinateOfCenterOfCircle) {
        this.ordinateOfCenterOfCircle = ordinateOfCenterOfCircle;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }

    public int getColorProgress() {
        return colorProgress;
    }

    public void setColorProgress(int colorProgress) {
        this.colorProgress = colorProgress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public SemiCircleDrawable(Context context) {
        super(context);
        calculate();
    }

    public SemiCircleDrawable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SemiCircleDrawable,
                0, 0);
        calculate();
        try{
            abscissaOfCenterOfCircle = attributes.getFloat(R.styleable.SemiCircleDrawable_abscissa_of_center_of_circle,0);
            ordinateOfCenterOfCircle = attributes.getFloat(R.styleable.SemiCircleDrawable_abscissa_of_center_of_circle,0);
            radius = attributes.getFloat(R.styleable.SemiCircleDrawable_radius,0);
            thickness = attributes.getFloat(R.styleable.SemiCircleDrawable_thickness_of_progress,0);
            colorBackground = attributes.getColor(R.styleable.SemiCircleDrawable_background_color,Color.WHITE);
            colorProgress = attributes.getColor(R.styleable.SemiCircleDrawable_progress_color,Color.RED);
            progress = (int) attributes.getFloat(R.styleable.SemiCircleDrawable_progress,0);
            max = (int) attributes.getFloat(R.styleable.SemiCircleDrawable_max,0);
        }finally {
            attributes.recycle();
        }
        calculate();
        //todo on 18/8
    }

    public SemiCircleDrawable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SemiCircleDrawable(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        double degree = convertPercentToDegree(progress);

        initBackground(canvas);
        initProgress(degree,canvas);
        initProgressPoint(degree,canvas);
    }

    private void initProgress(double degree, Canvas canvas) {
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(colorProgress);
        progressPaint.setStrokeWidth(thickness);

        oval.set(left,top,right,bottom);
        canvas.drawArc(oval,180, (float) degree,false,progressPaint);
    }

    private void initProgressPoint(double degree, Canvas canvas){
        double[][] afterConvert =  convertDegreeToPosition(degree,(bottom-top)/2);

        progressPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPointPaint.setStyle(Paint.Style.FILL);
        progressPointPaint.setColor(colorProgress);

        canvas.drawCircle((float)afterConvert[0][0],(float) afterConvert[0][1],thickness/2,progressPointPaint);
    }

    private void initBackground(Canvas canvas) {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setColor(colorBackground);
        backgroundPaint.setStrokeWidth(thickness);

        canvas.drawCircle(abscissaOfCenterOfCircle,ordinateOfCenterOfCircle,radius, backgroundPaint);
    }

    double convertPercentToDegree(int percent){
        return percent*3.6;
    }

    private double [][] convertDegreeToPosition(double degree, double radius){
        double convertDegree = convertToPI(degree);
        double x = 0;
        double y = 0;
        if (convertDegree<=Math.PI/2){
            x = radius - Math.cos(convertDegree)*radius + left;
            y = radius - Math.sin(convertDegree)*radius + top;
        }else if(convertDegree>Math.PI/2&&convertDegree<=Math.PI){
            convertDegree = Math.PI- convertDegree ;
            x = Math.cos(convertDegree)*radius + left + radius;
            y = radius - Math.sin(convertDegree)*radius + top;
        }else if(convertDegree>=Math.PI&&convertDegree<=3/4*Math.PI){
            convertDegree = convertDegree - Math.PI ;
            x = Math.cos(convertDegree)*radius + left + radius;
            y = Math.sin(convertDegree)*radius + top + radius;
        }else if(convertDegree>3/4*Math.PI&&convertDegree<=2*Math.PI){
            convertDegree = 2*Math.PI - convertDegree ;
            x = radius - Math.cos(convertDegree)*radius + left;
            y = Math.sin(convertDegree)*radius + top + radius;
        }
        double[][] result = new double[1][2];
        result[0][0]=x;
        result[0][1]=y;
        return result;
    }

    private double convertToPI(double degree){
        return degree*Math.PI/180;
    }

    public void initProgress(int progress){
        this.progress=progress;
        invalidate();
    }

    private void calculate(){
        top = ordinateOfCenterOfCircle - radius;
        bottom = ordinateOfCenterOfCircle + radius;
        left = abscissaOfCenterOfCircle - radius;
        right = abscissaOfCenterOfCircle + radius;
    }

}
