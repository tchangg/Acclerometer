package com.example.acclerometer;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.app.Activity;
import android.view.Menu;
import android.widget.*;
import android.view.*;

public class MainActivity extends Activity implements SensorEventListener
{
  private boolean initialized;
  private SensorManager sensorManager;
  private Sensor accelerometer;
  private final float NOISE = (float) 2.0;
  double ax, ay, az;
  private boolean recordButtonOn = false;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initialized = false;
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    
    sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  protected void onResume()
  {
    super.onResume();
    sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
  }
  protected void onPause()
  {
    super.onPause();
    sensorManager.unregisterListener(this);
  }
  
  public void onToggleClicked(View view)
  {
    recordButtonOn = ((ToggleButton) view).isChecked();
  }
  
  public void onAccuracyChanged(Sensor sensor, int accuracy)
  {
    
  }
  
  public void onSensorChanged(SensorEvent event)
  {
    TextView xaxis = (TextView)findViewById(R.id.xaxis);
    TextView yaxis = (TextView)findViewById(R.id.yaxis);
    TextView zaxis = (TextView)findViewById(R.id.zaxis);
    ax = event.values[0];
    ay = event.values[1];
    az = event.values[2];
    xaxis.setText("X-Axis: " + Double.toString(ax));
    yaxis.setText("Y-Axis: " + Double.toString(ay));
    zaxis.setText("Z-Axis: " + Double.toString(az));
    if(recordButtonOn) // pass data to database
    {
      AccelDbHelper accelDbHelper = new AccelDbHelper(getBaseContext());
      //Gets data repository in write mode
      SQLiteDatabase db = accelDbHelper.getWritableDatabase();
      
      //Create a new Map of values, where column names are the keys
      ContentValues values = new ContentValues();
      values.put(AccelDbHelper.X_AXIS, ax);
      values.put(AccelDbHelper.Y_AXIS, ay);
      values.put(AccelDbHelper.Z_AXIS, az);
      
      //Insert new row, returning the primary key value of the new row
      long newRowId;
      newRowId = db.insert(AccelDbHelper.ACCEL_TABLE, "NULL", values);
    }
  }

}
