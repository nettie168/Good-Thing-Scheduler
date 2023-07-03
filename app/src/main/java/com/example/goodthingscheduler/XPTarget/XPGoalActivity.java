package com.example.goodthingscheduler.XPTarget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.XPCount.XPCountModel;
import com.example.goodthingscheduler.XPCount.XPDayDBHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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

    private XPDayDBHandler xpDayDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpgoal);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_chevron_left_24);
        getSupportActionBar().setTitle("XP Goal");
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd")));
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        //getSupportActionBar().setBackgroundDrawable(null);
        //getSupportActionBar().setElevation(0);

        xpDayDBHandler = new XPDayDBHandler(this);

        setLineChart();
    }

    private void setLineChart(){
        //setXPArray
        //produces list of XP for this month
        ArrayList<XPCountModel> XPThisMonth = xpDayDBHandler.XPInMonthArray();

        //orders the list of xp by date
        Comparator<XPCountModel> comparator = new CalendarUtils.XPDateComparator();
        XPThisMonth.sort(comparator);
        for(int i = 0; i < XPThisMonth.size(); i++){
            Log.i("LineChart XPThisMonth",XPThisMonth.get(i).getDate()+" XP: "+XPThisMonth.get(i).getXp());
        }

        //list of dates in this month
        ArrayList<LocalDate> daysInXPMonthArray = CalendarUtils.daysInXPMonthArray(LocalDate.now());

        ArrayList<XPCountModel> xpDataArrayList = new ArrayList<>();
        ArrayList<Integer> xpData2 = new ArrayList<>();

        //loop over days in month until today
      /*  for (int i = 0; i < LocalDate.now().getDayOfMonth(); i++){
            LocalDate date_i = daysInXPMonthArray.get(i);
            Log.i("XPGoal Act start","i is "+i+" date_i is: "+date_i);

            for(int j = 0; j < XPThisMonth.size(); j ++){
                //if XPThisMonth contains date and is the first one
            //adds the xpCount
            if(i == 0) {
                if(XPThisMonth.get(j).getDate().equals(date_i)){
                    xpDataArrayList.add(XPThisMonth.get(i));
                    Log.i("XPGoal Act i==0 and datesMatch", "i is " + i + " j is " + j + " date_i is: " + date_i + " XP date is " + XPThisMonth.get(i).getDate());
                }else{
                    xpDataArrayList.add(new XPCountModel(i,date_i.toString(),0));
                    Log.i("XPGoal Act i==0 datesNotMatch", "i is " + i + " j is " + j + " date_i is: " + date_i+" XP date is " + XPThisMonth.get(i).getDate());
                }
            }else if (XPThisMonth.get(j).equals(date_i)){
                int lastTotalXP = XPThisMonth.get(i-1).getXp();
                int newTotalXP = lastTotalXP + XPThisMonth.get(i).getXp();
                //adds the date, and adds the xp to total
                xpDataArrayList.add(new XPCountModel(i,XPThisMonth.get(i).getDate(),newTotalXP));
                Log.i("XPGoal Act dateMatch","i is "+i+" j is "+j+" date_i is: "+date_i+" XP is "+newTotalXP);
            }else{
                int lastTotalXP = XPThisMonth.get(i-1).getXp();
                //if XPThisMonth doesn't contain the date
                //add date & xp = 0
                xpDataArrayList.add(new XPCountModel(i,date_i.toString(),lastTotalXP));
                Log.i("XPGoal Act else","i is "+i+" j is "+j+" date_i is: "+date_i+" XP is "+lastTotalXP);
            }
            }
        }*/

        int j = 0;
        for (int i = 0; i < LocalDate.now().getDayOfMonth()+1; i++){
            //while(j < XPThisMonth.size()){
                Log.i("XP top", "j = "+j +" i is "+i);
                int xpDate;
                if(!XPThisMonth.isEmpty()){ //is this needed? (also check if i==0 or i-1 is needed)
                    xpDate = CalendarUtils.toLocalDate(XPThisMonth.get(j).getDate()).getDayOfMonth();
                }else{
                    xpDate = -10;
                }
                Log.i("XP top, xp is","date: "+XPThisMonth.get(j).getDate()+" XP: "+XPThisMonth.get(j).getXp());
                if(i-1==xpDate-1){
                    if(i==0){
                        xpDataArrayList.add(XPThisMonth.get(j));
                        xpData2.add(XPThisMonth.get(j).getXp());
                        j = j+1;
                        Log.i("XP i==xpDate, i==0", "j = "+j +" i is"+i);
                        Log.i("XP i==xpDate, i==0","date: "+XPThisMonth.get(j).getDate()+" XP: "+XPThisMonth.get(j).getXp());
                    }else{
                        int lastTotalXP = xpDataArrayList.get(i-1).getXp();
                        int newTotalXP = lastTotalXP + XPThisMonth.get(j).getXp();
                        //adds the date, and adds the xp to total
                        xpDataArrayList.add(new XPCountModel(i,XPThisMonth.get(j).getDate(),newTotalXP));
                        xpData2.add(newTotalXP);
                        j = j+1;
                        Log.i("XP i==xpDate", "j = "+j +" i is "+i);
                      //  Log.i("XP i==XpDate","date: "+XPThisMonth.get(j).getDate()+" XP: "+XPThisMonth.get(j).getXp());
                        // Log.i("XP i==0", "j= "+j +" i is"+i);
                       // Log.i("XP i==0","date: "+XPThisMonth.get(j).getDate()+" XP: "+XPThisMonth.get(j).getXp());
                    }
                }else{
                    if(i==0){
                        xpDataArrayList.add(new XPCountModel(i,XPThisMonth.get(j).getDate(),0));
                        xpData2.add(0);
                        Log.i("XP i=/xpDate, i==0", "j = "+j +" i is "+i);
                        Log.i("XP i=/XpDate, i==0","date: "+xpDataArrayList.get(j).getDate()+" XP: "+xpDataArrayList.get(j).getXp());
                    }else{
                        int lastTotalXP = xpDataArrayList.get(i-1).getXp();
                        xpDataArrayList.add(new XPCountModel(i,XPThisMonth.get(j).getDate(),lastTotalXP));
                        xpData2.add(lastTotalXP);
                        Log.i("XP i=/xpDate", "j = "+j +" i is "+i);
                        Log.i("XP i=/XpDate","date: "+xpDataArrayList.get(j).getDate()+" XP: "+xpDataArrayList.get(j).getXp());
                    }
              }
          //  }
        }

        for(int i = 0; i < xpData2.size(); i++){
            Log.i("LineChart XPData2", i+": "+ xpData2.get(i));
        }


        //find Line Chart View
        LineChartView lineChartView = findViewById(R.id.chart);

        String[] axisData = new String[daysInXPMonthArray.size()];

        // ArrayList to Array Conversion
        for (int i = 0; i < daysInXPMonthArray.size(); i++)
            axisData[i] = String.valueOf(daysInXPMonthArray.get(i).getDayOfMonth());

        Integer[] yAxisData2 = new Integer[xpData2.size()];

        // ArrayList to Array Conversion
        for (int i = 0; i < xpData2.size(); i++)
            yAxisData2[i] = xpData2.get(i);

        //Default Code
        //String[] axisData = {"1", "2", "3", "4", "5", "6", "7", "8", "9","10", "11", "12", "13", "14"};

        int[] yAxisData = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400,
        1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800, 2900, 3000};
        //int[] yAxisData2 = {50, 100, 200, 250, 400, 400, 400, 700, 850, 900, 900, 1100, 1200, 1400};
      //  int[] yAxisData2 = {50, 100, 200, 250, 400};
       // Integer[] yAxisData2 = xpDayDBHandler.TotalXPInMonth(daysInXPMonthArray);
       // Integer[] yAxisData2 =
        //ArrayList yAxisData2 = xpDayDBHandler.XPMonth();


        List<PointValue> yAxisValues = new ArrayList<>();
        List<PointValue> yAxisValues2 = new ArrayList<>();

        List<AxisValue> axisValues = new ArrayList<>();

      //  Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0")); //purple
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#808080")).setHasPoints(false); //grey
        Line line2 = new Line(yAxisValues2).setColor(Color.parseColor("#9C27B0")); //grey


        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
            //yAxisValues2.add(new PointValue(i, yAxisData2[i]));
        }

        for (int i = 0; i < yAxisData2.length; i++){
            yAxisValues2.add(new PointValue(i, yAxisData2[i]));
        }

        List<Line> lines = new ArrayList<>();
        lines.add(line);

        //List<Line> lines = new ArrayList<>();
        lines.add(line2);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        lineChartView.setLineChartData(data);

        //Set X Axis Labels
        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#6352a9")); // 03A9F4
        axis.setName("days");

        //Set Y Axis Labels
        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        yAxis.setTextColor(Color.parseColor("#6352a9")); // 03A9F4
        yAxis.setTextSize(16);
        yAxis.setName("Total XP");

        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        //viewport.top = 100;
        viewport.bottom = 0;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);


    }
}