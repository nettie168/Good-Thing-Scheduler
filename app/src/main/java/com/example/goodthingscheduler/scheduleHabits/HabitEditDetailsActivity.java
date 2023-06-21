package com.example.goodthingscheduler.scheduleHabits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goodthingscheduler.SchedulerActivity;
import com.example.goodthingscheduler.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class HabitEditDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_edit_details);

        ImageView habitImg = findViewById(R.id.habitTitleImg);
        TextView habitTitle = findViewById(R.id.habitTitleView);

        habitImg.setImageResource(RoutineUtils.habitImgId);
        habitTitle.setText(RoutineUtils.habitName);
        
        //setMPGraph();
        //setLechoGraph();

    }
    
 /*   public void setGraph(){
        // on below line we are initializing our graph view.
        GraphView graphView = findViewById(R.id.idGraphView);

        // on below line we are adding data to our graph view.
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                // on below line we are adding 
                // each point on our x and y axis.
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });

        // after adding data to our line graph series.
        // on below line we are setting 
        // title for our graph view.
        graphView.setTitle("My Graph View");

        // on below line we are setting 
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(18);

        // on below line we are adding 
        // data series to our graph view.
        graphView.addSeries(series);
    }*/

/*    private void setMPGraph(){
        LineChart habitLineChartView = findViewById(R.id.habitLineGraph);

        String[] xAxisData = {"Mon", "Tue", "Wed", "thur", "fri", "sat", "sun"};
        int[] yAxisData = {2, 4, 0, 0, 1, 8, 4};

        ArrayList<String> xAxisValues = new ArrayList<>();
        ArrayList<Entry> yAxisValues = new ArrayList<>();

        for(int i = 0; i < xAxisData.length; i++){
            xAxisValues.add(i, xAxisData[i]);
        }

        for(int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new Entry(yAxisData[i], i));
        }

        LineDataSet set1 = new LineDataSet(yAxisValues, "");
        LineData data = new LineData(xAxisValues, set1);

        habitLineChartView.setData(data);
    }*/

    private void setLechoGraph(){
        LineChartView habitLineChartView = findViewById(R.id.habitLineGraph);

        String[] xAxisData = {"Mon", "Tue", "Wed", "thur", "fri", "sat", "sun"};
        int[] yAxisData = {2, 4, 0, 0, 1, 8, 4};

        List xAxisValues = new ArrayList<>();
        List yAxisValues = new ArrayList<>();

        Line line = new Line(yAxisValues);

        for(int i = 0; i < xAxisData.length; i++){
            xAxisValues.add(i, new AxisValue(i).setLabel(xAxisData[i]));
        }

        for(int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }


        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);
        habitLineChartView.setLineChartData(data);

    }

    public void deleteHabit(View view){
        HabitDBHandler habitDBHandler = new HabitDBHandler(this);
      //  habitDBHandler.deleteHabit(new HabitModel(RoutineUtils.habitSelId));
        finish();
        startActivity(new Intent(getApplicationContext(), SchedulerActivity.class));
        Toast.makeText(this, "deleting "+ RoutineUtils.habitName, Toast.LENGTH_SHORT).show();
    }
}