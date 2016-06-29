package com.example.pc.ponggame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import java.io.File;

import static android.util.FloatMath.sqrt;


public class game extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    float axisX, axisY, axisZ;

    byte[] readWriteBuffer;
    byte [] numReadWritten;
    public FT311SPIMasterInterface spimInterface;
    /*graphical objects*/
    EditText readText;
    EditText writeText;
    EditText deviceAddressText,writeDeviceAddressText;
    EditText addressText,writeAddressText;
    EditText numBytesText;
    EditText statusText,writeStatusText;
    EditText clockFreqText;

    Button readButton, writeButton;
    Button configButton, resetButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        spimInterface = new FT311SPIMasterInterface(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        //Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

             axisX = event.values[0];
             axisY = event.values[1];
             axisZ = event.values[2];


        ImageView up=(ImageView) findViewById(R.id.arr1);
        ImageView ups=(ImageView) findViewById(R.id.arr1s);
        ImageView down=(ImageView) findViewById(R.id.arr2);
        ImageView downs=(ImageView) findViewById(R.id.arr2s);
        TextView data1 = (TextView) findViewById(R.id.textView);
        if(axisZ>6){
            up.setVisibility(View.VISIBLE);
            ups.setVisibility(View.INVISIBLE);
            down.setVisibility(View.INVISIBLE);
            downs.setVisibility(View.VISIBLE);
            data=0xff;
            data1.setText(Integer.toString(data));
            }else if(axisZ>3){
            up.setVisibility(View.INVISIBLE);
            ups.setVisibility(View.VISIBLE);
            down.setVisibility(View.INVISIBLE);
            downs.setVisibility(View.VISIBLE);
            data=0x0A;
            data1.setText(Integer.toString(data));
        }else{
            up.setVisibility(View.INVISIBLE);
            ups.setVisibility(View.VISIBLE);
            down.setVisibility(View.VISIBLE);
            downs.setVisibility(View.INVISIBLE);
            data=0x00;
            data1.setText(Integer.toString(data));


        }

            String srcStr = data1.getText().toString();
            String destStr = "";
            /*switch(inputFormat)
            {
                case FORMAT_HEX:
                {
                    String[] tmpStr = srcStr.split(" ");

                    for(int i = 0; i < tmpStr.length; i++)
                    {
                        if(tmpStr[i].length() == 0)
                        {
                            msgToast("Incorrect input for HEX format."
                                    +"\nThere should be only 1 space between 2 HEX words.",Toast.LENGTH_SHORT);
                            return;
                        }
                        else if(tmpStr[i].length() != 2)
                        {
                            msgToast("Incorrect input for HEX format."
                                    +"\nIt should be 2 bytes for each HEX word.",Toast.LENGTH_SHORT);
                            return;
                        }
                    }

                    try
                    {
                        destStr = hexToAscii(srcStr.replaceAll(" ", ""));
                    }
                    catch(IllegalArgumentException e)
                    {
                        msgToast("Incorrect input for HEX format."
                                +"\nAllowed charater: 0~9, a~f and A~F",Toast.LENGTH_SHORT);
                        return;
                    }
                }
                break;

                case FORMAT_DEC:
                {
                    String[] tmpStr = srcStr.split(" ");

                    for(int i = 0; i < tmpStr.length; i++)
                    {
                        if(tmpStr[i].length() == 0)
                        {
                            msgToast("Incorrect input for DEC format."
                                    +"\nThere should be only 1 space between 2 DEC words.",Toast.LENGTH_SHORT);
                            return;
                        }
                        else if(tmpStr[i].length() != 3)
                        {
                            msgToast("Incorrect input for DEC format."
                                    +"\nIt should be 3 bytes for each DEC word.",Toast.LENGTH_SHORT);
                            return;
                        }
                    }

                    try
                    {
                        destStr = decToAscii(srcStr.replaceAll(" ", ""));
                    }
                    catch(IllegalArgumentException e)
                    {
                        if(e.getMessage().equals("ex_a"))
                        {
                            msgToast("Incorrect input for DEC format."
                                    +"\nAllowed charater: 0~9",Toast.LENGTH_SHORT);
                        }
                        else
                        {
                            msgToast("Incorrect input for DEC format."
                                    +"\nAllowed range: 0~255",Toast.LENGTH_SHORT);
                        }
                        return;
                    }
                }
                break;

                case FORMAT_ASCII:
                default:
                    destStr = srcStr;
                    break;
            }*/

        byte numBytes = (byte) destStr.length();

        for (int i = 0; i < numBytes; i++) {
                readWriteBuffer[i] = (byte)destStr.charAt(i);
            }
            spimInterface.SendData(numBytes, readWriteBuffer, numReadWritten);
        }

        /*String hexToAscii(String s) throws IllegalArgumentException
        {
            int n = s.length();
            StringBuilder sb = new StringBuilder(n / 2);
            for (int i = 0; i < n; i += 2)
            {
                char a = s.charAt(i);
                char b = s.charAt(i + 1);
                sb.append((char) ((hexToInt(a) << 4) | hexToInt(b)));
            }
            return sb.toString();
        }*/

        /*static int hexToInt(char ch)
        {
            if ('a' <= ch && ch <= 'f') { return ch - 'a' + 10; }
            if ('A' <= ch && ch <= 'F') { return ch - 'A' + 10; }
            if ('0' <= ch && ch <= '9') { return ch - '0'; }
            throw new IllegalArgumentException(String.valueOf(ch));
        }*/

        /*String decToAscii(String s) throws IllegalArgumentException
        {

            int n = s.length();
            boolean pause = false;
            StringBuilder sb = new StringBuilder(n / 2);
            for (int i = 0; i < n; i += 3)
            {
                char a = s.charAt(i);
                char b = s.charAt(i + 1);
                char c = s.charAt(i + 2);
                int val = decToInt(a)*100 + decToInt(b)*10 + decToInt(c);
                if(0 <= val && val <= 255)
                {
                    sb.append((char) val);
                }
                else
                {
                    pause = true;
                    break;
                }
            }

            if(false == pause)
                return sb.toString();
            throw new IllegalArgumentException("ex_b");
        }*/
   }





