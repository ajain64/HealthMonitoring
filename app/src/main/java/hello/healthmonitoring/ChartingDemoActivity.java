/**
 * Disclaimer: IMPORTANT:  This Nulana software is supplied to you by Nulana
 * LTD ("Nulana") in consideration of your agreement to the following
 * terms, and your use, installation, modification or redistribution of
 * this Nulana software constitutes acceptance of these terms.  If you do
 * not agree with these terms, please do not use, install, modify or
 * redistribute this Nulana software.
 *
 * In consideration of your agreement to abide by the following terms, and
 * subject to these terms, Nulana grants you a personal, non-exclusive
 * license, under Nulana's copyrights in this original Nulana software (the
 * "Nulana Software"), to use, reproduce, modify and redistribute the Nulana
 * Software, with or without modifications, in source and/or binary forms;
 * provided that if you redistribute the Nulana Software in its entirety and
 * without modifications, you must retain this notice and the following
 * text and disclaimers in all such redistributions of the Nulana Software.
 * Except as expressly stated in this notice, no other rights or licenses, 
 * express or implied, are granted by Nulana herein, including but not limited 
 * to any patent rights that may be infringed by your derivative works or by other
 * works in which the Nulana Software may be incorporated.
 *
 * The Nulana Software is provided by Nulana on an "AS IS" basis.  NULANA
 * MAKES NO WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE, REGARDING THE NULANA SOFTWARE OR ITS USE AND
 * OPERATION ALONE OR IN COMBINATION WITH YOUR PRODUCTS.
 *
 * IN NO EVENT SHALL NULANA BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION,
 * MODIFICATION AND/OR DISTRIBUTION OF THE NULANA SOFTWARE, HOWEVER CAUSED
 * AND WHETHER UNDER THEORY OF CONTRACT, TORT (INCLUDING NEGLIGENCE),
 * STRICT LIABILITY OR OTHERWISE, EVEN IF NULANA HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright (C) 2017 Nulana LTD. All Rights Reserved.
 */
 
package hello.healthmonitoring;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.nulana.NChart.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ChartingDemoActivity extends Activity implements NChartSeriesDataSource, NChartValueAxisDataSource {
    NChartView mNChartView;
    private String _trainingDB;
    Random random = new Random();
    int nextLine = 0;
    int[] nums = {1, 2, 3, 4, 5};
    ArrayList<float[]> lines = new ArrayList<float[]>();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        mNChartView = (NChartView) findViewById(R.id.surface);
        _trainingDB = getApplicationContext().getFilesDir().getPath()+"/ActivityDB";
        System.out.println(_trainingDB);
        loadView();
    }

    private void loadView() {
        // Paste your license key here.
        mNChartView.getChart().setLicenseKey("cHRclQeIbtI7dSVZV0Ys1noFd1m3zmKrCwX+pK7KE9aFutmUDFIaDOpPkv5h5LccALWR5+d6KeLdAXb2S4fE2Ns/3wH7ZinQEsfkzOY5LxpjcriGKn406zeKp8COcY9LDcxUMAvKRN0MYtIrJ8HsR9a6PrDrYNen/p+7PsJv8FRgOy6+qjJrgjX0m28jJzLbtP8feK9X9encmrils9woRP0Pi19M8JFZ6i3eaIRN9FO6qI/505V7pQEweOCP8bUb1+MoUiL24HYF9HXdYCe46g110p7EkYT0/hXqba3ZKNFkWWVp1p7SzL7aRycylTwsqnMn+y6l8Al7kkTzN1ClMBugxMFsjn9t50qjC+p51rDYwKH0431NbrLPz2Or/595CSiz/1UraXlFPmRGE7pn5qY1SeHF7JwRfqnZMYC/rm8bA6zsVJiQThdlKbL8di+7AA1TaCvzcobzlpBQDytICts5TgBYm1vL72h8CHw87eo5N2xMIPE8vE1tq8WiNVqovW8IDlJ+vihR7m6zk/9YZJUBXTr2wI7YfJwA8wt0Rflb2sVxlH0Vh45E5FfrHw0a8NoB59yHP6YVMIi+H5qUXVcNyrrp0c8IgTpHTeqp2wNpOgiG25tpkRPqpUvOsCtD2v7MjgkvvIDLCpsaFwfbySjZYhyPkKXgdAKnhd811Ko=");

        float[][] colors = {
                { 0.38f, 0.8f, 0.91f },
                { 0.2f, 0.86f, 0.22f },
                { 0.9f, 0.29f, 0.51f }
        };


        // Switch on antialiasing.
        mNChartView.getChart().setShouldAntialias(true);

        mNChartView.getChart().getCartesianSystem().setMargin(new NChartMargin(10.0f, 10.0f, 10.0f, 20.0f));

        // Switch 3D on. We will have a kind of fake 3D, because the Z-axis will have no values. Just the columns will be volumetric.
        mNChartView.getChart().setDrawIn3D(true);

        // Set the data source for the X- and Z-Axis, because we want custom values on them.
        //mNChartView.getChart().getCartesianSystem().getXAxis().setDataSource(this);
        //mNChartView.getChart().getCartesianSystem().getZAxis().setDataSource(this);


        // Create series that will be displayed on the chart.
        //NChartColumnSeries series = new NChartColumnSeries();

        // Set brush that will fill that series with color.
        //series.setBrush(new NChartSolidColorBrush(Color.argb(255, 97, 205, 232)));

        // Set data source for the series.
        //series.setDataSource(this);

        // Add series to the chart.
        //mNChartView.getChart().addSeries(series);
        try {
            File trainingFile = File.createTempFile("training_set", null, this.getCacheDir());
            PrintWriter writer = new PrintWriter(trainingFile);

            Log.d("ok1", "print");

        SQLiteDatabase database = SQLiteDatabase.openDatabase(_trainingDB, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.query(
                "group3activitydb",     // The table name
                new String[] {"timeStamp, xValue, yValue", "zValue", "label"},
                null,//"label = 0",            // The WHERE clause
                null,
                null,
                null,
                "label, timestamp"//, yValue, xValue, zValue"        // Can put sort order here, e.g. "timeStamp DESC"
        );

        int item = 0;

        while (cursor.moveToNext())
        {
            if (item == 300){}
                //break;

            float[] coordinates = new float[3];
            coordinates[0] = cursor.getFloat(cursor.getColumnIndex("xValue"));
            coordinates[1] = cursor.getFloat(cursor.getColumnIndex("yValue"));
            coordinates[2] = cursor.getFloat(cursor.getColumnIndex("zValue"));

            lines.add(coordinates);


            item++;

        }

            Log.d("ok",Integer.toString(item));





        cursor.close();
        }
        catch (IOException ex) {

        }

        float[] c1 = {-1, 5, 6};
        float[] c2 = {5, 4, -2};
        float[] c3 = {6, 7, -3};

        float[] c4 = {3,4,5};
        float[] c5 = {2,1,-1};
        float[] c6 = {4,3,8};

        /*lines.add(c1);
        lines.add(c2);
        lines.add(c3);

        lines.add(c4);
        lines.add(c5);
        lines.add(c6);*/

        createSeries();
        mNChartView.getChart().updateData();

/*
        NChartLineSeries series3 = new NChartLineSeries();
        //series3.setBrush(new NChartSolidColorBrush(Color.argb(255, (int)(255 * colors[2][0]),(int)(255 * colors[2][1]),(int)(255 * colors[2][2]))));
        //series3.setLineThickness(3.0f);
        series3.setDataSource(this);
        //series3.tag = 2;
        mNChartView.getChart().addSeries(series3);
        mNChartView.getChart().updateData();

        NChartLineSeries series4 = new NChartLineSeries();
        series4.setBrush(new NChartSolidColorBrush(Color.argb(255, (int)(255 * colors[2][0]),(int)(255 * colors[2][1]),(int)(255 * colors[2][2]))));
        series4.setLineThickness(3.0f);
        series4.setDataSource(this);
        series4.tag = 2;
        mNChartView.getChart().addSeries(series4);

        // Update data in the chart
        //
        // .
        mNChartView.getChart().updateData();*/
    }

    protected void onResume() {
        super.onResume();
        mNChartView.onResume();
    }

    protected void onPause() {
        super.onPause();
        mNChartView.onPause();
    }

    void createSeries() {
        for (int i = 0; i < 60; ++i) {
            NChartLineSeries series = new NChartLineSeries();
            series.setDataSource(this);
            //series.tag = i;
            //series.setBrush(brushes[i]);
            //series.setLineThickness(3.0f);
            if (i < 60)
                series.setBrush(new NChartSolidColorBrush(Color.argb(255, 97, 205, 232)));
            if (i < 40)
                series.setBrush(new NChartSolidColorBrush(Color.argb(0, 97, 205, 232)));
            if (i < 20)
                series.setBrush(new NChartSolidColorBrush(Color.argb(255, 0, 205, 232)));
            mNChartView.getChart().addSeries(series);

        }
    }

    public NChartPoint[] points(NChartSeries series) {
        // Create points with some data for the series.
        ArrayList<NChartPoint> result = new ArrayList<NChartPoint>();


        for (int i = nextLine; i < nextLine+100; ++i)
        //for (int i = 0; i < 60; i++)
            result.add(new NChartPoint(NChartPointState.PointStateWithXYZ(lines.get(i)[0],lines.get(i)[1],lines.get(i)[2]), series));
        //result.add(new NChartPoint());

        nextLine += 100;

        return result.toArray(new NChartPoint[result.size()]);
    }

    public String name(NChartSeries series) {
        return "";//"My series in 3D";
    }

    public Bitmap image(NChartSeries series) {
        return null;
    }

    public String name(NChartValueAxis nChartValueAxis) {
        return null;
    }
    
    public NChartPoint[] extraPoints(NChartSeries series) {
        return null;
    }

    public Number min(NChartValueAxis nChartValueAxis) {
        return null;
    }

    public Number max(NChartValueAxis nChartValueAxis) {
        return null;
    }

    public Number step(NChartValueAxis nChartValueAxis) {
        return null;
    }

    public String[] ticks(NChartValueAxis nChartValueAxis) {
        // Choose ticks by the kind of axis.
        switch (nChartValueAxis.getKind())
        {
            case X:
                // Return five tick names, because we have five points in the series.
                //return new String[] {"Alpha", "Beta", "Gamma", "Delta", "Epsilon"};

            case Z:
                // Return an array with an empty string to draw noting on the single tick of Z-Axis.
                return new String[] {""};

            default:
                // We do not have other axes.
                return null;
        }
    }

    public String[] extraTicks(NChartValueAxis axis) {
        return null;
    }

    public Number length(NChartValueAxis nChartValueAxis) {
        // Choose length by the kind of axis.
        switch (nChartValueAxis.getKind())
        {
            case Z:
                // We want Z-Axis to be shorter than the others because it has no values. The default length of axes is 1.0.
                // So let Z-Axis be 0.3 for example.
                //return 0.3;

            default:
                // All other axes should have the default length.
                return null;
        }
    }

    public String doubleToString(double v, NChartValueAxis nChartValueAxis) {
        return null;
    }

    public Date minDate(NChartValueAxis nChartValueAxis) {
        return null;
    }

    public Date maxDate(NChartValueAxis nChartValueAxis) {
        return null;
    }

    public Number dateStep(NChartValueAxis nChartValueAxis) {
        return null;
    }

    public String dateToString(Date date, double v, NChartValueAxis nChartValueAxis) {
        return null;
    }
}
