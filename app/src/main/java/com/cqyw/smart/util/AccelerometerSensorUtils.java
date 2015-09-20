package com.cqyw.smart.util;

import com.iflytek.eduassisttask.util.log.LogUtils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 重力加速度传感器
 * 加速度传感器又叫G-sensor，返回x、y、z三轴的加速度数值。
 * 该数值包含地心引力的影响，单位是m/s^2。
 * 将手机平放在桌面上，x轴默认为0，y轴默认0，z轴默认9.81。
 * 将手机朝下放在桌面上，z轴为-9.81。
 * 将手机向左倾斜，x轴为正值。
 * 将手机向右倾斜，x轴为负值。
 * 将手机向上倾斜，y轴为负值。
 * 将手机向下倾斜，y轴为正值。
 * 本工程主要用于检测平衡的只要 X Y轴倾斜角度在[-1,1]之间即可
 * @author YuHong (hongyu@ifltek.com)
 *
 * 2015年8月26日
 */
public class AccelerometerSensorUtils implements SensorEventListener {

    public static final String TAG = "AccelerometerSensor"; 

    private static AccelerometerSensorUtils mInstance;

//    private Context mContext;

    /**
     * 传感器时间回调
     */
    private SensorEventInterface mListener;
    private SensorManager mSensorManager;
    Sensor mAcclertSensor;

    public static AccelerometerSensorUtils getAccelerometerSensor(Context ctx) {
        if(null == mInstance)
        {
            mInstance = new AccelerometerSensorUtils(ctx);
        }
        return mInstance;
    }
    public AccelerometerSensorUtils(Context _ctx) {
        mSensorManager = (SensorManager)_ctx.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            mAcclertSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    /**
     * 检测是否支持传感器
     * @return
     */
    private boolean checkSupportSensor() {
        return null != mAcclertSensor;
    }
    /**
     * 注册传感器
     */
    public void registerSensor() {
        if(checkSupportSensor())
        {
            mSensorManager.registerListener(this, mAcclertSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    /**
     * 解除注册
     * 
     */
    public void unRegisterSensor() {
        mSensorManager.unregisterListener(this);
    }

    public void setmListener(SensorEventInterface mListener) {
        this.mListener = mListener;
    }
    /**
     * 
     * SensorEvent.values[0]：加速度在X轴的负值
     * SensorEvent.values[1]：加速度在Y轴的负值
     * SensorEvent.values[2]：加速度在Z轴的负值

     * 例如：

     * 当手机Z轴朝上平放在桌面上，并且从左到右推动手机，此时X轴上的加速度是正数。

     * 当手机Z轴朝上静止放在桌面上，此时Z轴的加速度是+9.81m/sec^2。

     * 当手机从空中自由落体，此时加速度是0

     * 当手机向上以Am/sec^2的加速度向空中抛出，此时加速度是A+9.81m/sec^2
     * 
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(mListener == null)
        {
            return;
        }
        if(event.values.length < 3)
        {
            LogUtils.d(TAG, "false");
            mListener.onDataChanged(false); 
        }else
        {
            float x = Math.abs(event.values[0]);
            float y = Math.abs(event.values[1]);
            if(x >= 1 || y >= 1){
                LogUtils.d(TAG, "false");
                mListener.onDataChanged(false);
            }else
            {
                LogUtils.d(TAG, "true");
                mListener.onDataChanged(true);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 感应器事件回调
     * @author YuHong (hongyu@ifltek.com)
     *
     * 2015年8月26日
     */
    public interface SensorEventInterface {
        void onDataChanged(boolean isPortrait);
    }
}
