package com.example.cinnamontoastcrunch;



import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.LightingColorFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnSeekBarChangeListener
{

	private SeekBar bar;
	private TextView time;
	private Button startButton;
	private boolean isStarted;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bar = (SeekBar)findViewById(R.id.seekBar1);
		bar.setOnSeekBarChangeListener(this);
		time = (TextView)findViewById(R.id.textTime);
		startButton = (Button)findViewById(R.id.start);
		isStarted = false;
		handler = new Handler();
	}

	protected void onStop()
	{
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onProgressChanged(SeekBar seekBar,int progress, boolean fromUser)
	{
		time.setText("Current time is: " + progress);
		if(progress == 0)
		{
			endProgram();
		}
	}
	
	public void onStartTrackingTouch(SeekBar seekbar)
	{
		startButton.setText("");
	}
	
	public void onStopTrackingTouch(SeekBar seekBar)
	{
		seekBar.setSecondaryProgress(seekBar.getProgress());
		startButton.setText("Start");
	}
	
	public void runProgram(View v)
	{
		
		if(!isStarted)
		{
			startButton.setText("Program Started");
			isStarted = true;
			startButton.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
			bar.setEnabled(false);
			handler.postDelayed(runnable, 1000);
		}
		else
		{
			endProgram();
		}
	}
	
	public void endProgram()
	{
		startButton.setText("Program terminated");
		isStarted = false;
		startButton.getBackground().clearColorFilter();
		bar.setEnabled(true);
	}
	private Runnable runnable = new Runnable()
	{
		public void run()
		{
			int num = bar.getProgress();
			if(num > 0 && isStarted)
			{
				bar.setProgress(num-1);
				handler.postDelayed(this, 1000);
			}
		}
	};
}
