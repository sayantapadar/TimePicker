package apps.sayan.timepicker;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SAYAN on 27-05-2016.
 */
public class TimeHelper {

    static final int START=100;
    static final int END=200;
    private static final String URL = "https://api.myjson.com/";

    static int startHour=0;
    static int startMin=0;
    static int endHour=0;
    static int endMin=0;

    static Context context;

    static void updateTemporaryTime(int whatIsClicked, int hour, int min){
        switch(whatIsClicked){
            case START:
                startHour=hour;
                startMin=min;
                break;
            case END:
                endHour=hour;
                endMin=min;
                break;
        }
    }
    static String[] getTime(int whatIsClicked){
        String time[]=new String [2];
        switch (whatIsClicked) {
            case START:
                if (startHour > 12) {
                    time[0] = (startHour - 12) + ":" + (startMin<10?"0"+startMin:startMin);
                    time[1] = "PM";
                }
                else if(startHour==0){
                    time[0] = "12" + ":" + (startMin<10?"0"+startMin:startMin);
                    time[1] = "AM";
                }
                else if(startHour==12){
                    time[0] = "12" + ":" + (startMin<10?"0"+startMin:startMin);
                    time[1] = "PM";
                }
                else {
                    time[0] = startHour + ":" + (startMin<10?"0"+startMin:startMin);
                    time[1] = "AM";
                }
                break;
            case END:
                if (endHour > 12) {
                    time[0] = (endHour - 12) + ":" + (endMin<10?"0"+endMin:endMin);
                    time[1] = "PM";
                }
                else if(endHour==0){
                    time[0] = "12" + ":" + (endMin<10?"0"+endMin:endMin);
                    time[1] = "AM";
                }
                else if(endHour==12){
                    time[0] = "12" + ":" + (endMin<10?"0"+endMin:endMin);
                    time[1] = "PM";
                }
                else {
                    time[0] = endHour + ":" + (endMin<10?"0"+endMin:endMin);
                    time[1] = "AM";
                }
        }
        return time;
    }
    static void setPreviousData(final TextView tvStart, final TextView tvStartAMPM, final TextView tvEnd, final TextView tvEndAMPM, final ProgressDialog bar){
        RestAdapter adapter=new RestAdapter.Builder()
                .setEndpoint(URL)
                .build();

        TimeAPI api=adapter.create(TimeAPI.class);
        api.getTime(new Callback<POJO>() {
            @Override
            public void success(POJO pojo, Response response) {
                startHour=pojo.getStartHour();
                startMin=pojo.getStartMinute();
                endMin=pojo.getEndMinute();
                endHour=pojo.getEndHour();

                String time[]= TimeHelper.getTime(TimeHelper.START);
                tvStart.setText(time[0]);
                tvStartAMPM.setText(time[1]);
                time= TimeHelper.getTime(TimeHelper.END);
                tvEnd.setText(time[0]);
                tvEndAMPM.setText(time[1]);

                bar.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                AlertDialog alert=new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Failed to retrieve")
                        .create();
                alert.show();
                error.printStackTrace();
            }
        });
    }
    static void updateChanges(final TextView tvStart, final TextView tvStartAMPM, final TextView tvEnd, final TextView tvEndAMPM, final ProgressDialog bar){
        POJO pojo= new POJO();
        pojo.setStartHour(startHour);
        pojo.setEndMinute(endMin);
        pojo.setEndHour(endHour);
        pojo.setStartMinute(startMin);

        RestAdapter adapter=new RestAdapter.Builder()
                .setEndpoint(URL)
                .build();
        TimeAPI api=adapter.create(TimeAPI.class);
        api.postTime(pojo, new Callback<POJO>() {
            @Override
            public void success(POJO pojo, Response response) {
                setPreviousData(tvStart, tvStartAMPM, tvEnd, tvEndAMPM, bar);
            }

            @Override
            public void failure(RetrofitError error) {
                bar.dismiss();
                AlertDialog alert=new AlertDialog.Builder(context)
                        .setTitle("Error")
                        .setMessage("Failed to save changes")
                        .create();
                alert.show();
                setPreviousData(tvStart, tvStartAMPM, tvEnd, tvEndAMPM, bar);
            }
        });
    }
}
