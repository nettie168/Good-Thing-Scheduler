package com.example.goodthingscheduler.XPTarget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.R;

import java.lang.reflect.Array;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class XPGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpgoal);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_chevron_left_24);
        getSupportActionBar().setTitle("XP Goal");
        //getSupportActionBar().setBackgroundDrawable(null);
        //getSupportActionBar().setElevation(0);

        setLineChart();
    }

    private void setLineChart(){
        //find Line Chart View
        LineChartView lineChartView = findViewById(R.id.chart);

        YearMonth yearMonth = YearMonth.from(CalendarUtils.selectedDate);

        int daysInMonth = yearMonth.lengthOfMonth();
        //String[] axisData;
        //Array<String> axisData = new java.sql.Array();

        //List<PointValue> yAxisValues = new ArrayList<>();
        //List<AxisValue> xAxisValues = new ArrayList<>();

        //for(int i = 0; i < daysInMonth; i++){
          //  axisData.
       // }

        //Default Code
        String[] axisData = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14"};

        int[] yAxisData = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400};

        List<PointValue> yAxisValues = new ArrayList<>();
        List<AxisValue> axisValues = new ArrayList<>();

      //  Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0")); //purple
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#808080")); //grey



        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List<Line> lines = new ArrayList<>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        lineChartView.setLineChartData(data);

        //Set X Axis Labels
        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#6352a9")); // 03A9F4
        axis.setName("days of this Month");

        //Set Y Axis Labels
        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        yAxis.setTextColor(Color.parseColor("#6352a9")); // 03A9F4
        yAxis.setTextSize(16);
        yAxis.setName("Total XP this Month");

        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        //viewport.top = 100;
        viewport.bottom = 0;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);


    }
}