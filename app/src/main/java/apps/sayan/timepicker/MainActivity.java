package apps.sayan.timepicker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvStart; //Text View for the start time
    TextView tvStartAMPM; //To show start time is AM or PM
    TextView tvEnd; //Text View for the end time
    TextView tvEndAMPM; //To show end time is AM or PM

    TextView setHoursText;  //The text that says "Set my working hours"
    Button confirm;
    Button cancel;

    ProgressDialog bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimeHelper.context = this;

        tvStart = (TextView) findViewById(R.id.timeStart);
        tvStartAMPM = (TextView) findViewById(R.id.timeStartAMPM);
        tvEnd = (TextView) findViewById(R.id.timeEnd);
        tvEndAMPM = (TextView) findViewById(R.id.timeEndAMPM);

        bar = new ProgressDialog(this);
        bar.setMessage("Getting data");
        bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        bar.setIndeterminate(true);
        bar.setProgress(0);
        bar.show();
        TimeHelper.setPreviousData(tvStart, tvStartAMPM, tvEnd, tvEndAMPM, bar);

        tvStart.setTextColor(getResources().getColor(android.R.color.white));
        tvEnd.setTextColor(getResources().getColor(android.R.color.white));

        setHoursText = (TextView) findViewById(R.id.set_hours);
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        confirm.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);

        setHoursText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TimeFragment timeFragment;
        Bundle bundle;
        switch (v.getId()) {
            case R.id.set_hours:
                confirm.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                confirm.setOnClickListener(this);
                cancel.setOnClickListener(this);
                tvStart.setTextColor(getResources().getColor(android.R.color.darker_gray));
                tvEnd.setTextColor(getResources().getColor(android.R.color.darker_gray));

                tvStart.setEnabled(true);
                tvEnd.setEnabled(true);
                tvStart.setOnClickListener(this);
                tvEnd.setOnClickListener(this);
                break;
            case R.id.timeStart:
                timeFragment = new TimeFragment();
                bundle = new Bundle();
                bundle.putInt("what_is_clicked", TimeHelper.START);
                timeFragment.setArguments(bundle);
                timeFragment.show(getSupportFragmentManager(), "time_picker");
                break;
            case R.id.timeEnd:
                timeFragment = new TimeFragment();
                bundle = new Bundle();
                bundle.putInt("what_is_clicked", TimeHelper.END);
                timeFragment.setArguments(bundle);
                timeFragment.show(getSupportFragmentManager(), "time_picker");
                break;
            case R.id.confirm:
                tvStart.setEnabled(false);
                tvEnd.setEnabled(false);
                tvStart.setTextColor(getResources().getColor(android.R.color.white));
                tvEnd.setTextColor(getResources().getColor(android.R.color.white));
                confirm.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);

                bar.setMessage("Updating changes");
                bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                bar.setIndeterminate(true);
                bar.setProgress(0);
                bar.show();
                TimeHelper.updateChanges(tvStart, tvStartAMPM, tvEnd, tvEndAMPM, bar);
                break;
            case R.id.cancel:
                tvStart.setEnabled(false);
                tvEnd.setEnabled(false);
                tvStart.setTextColor(getResources().getColor(android.R.color.white));
                tvEnd.setTextColor(getResources().getColor(android.R.color.white));
                confirm.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);

                bar.setMessage("Cancelling changes");
                bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                bar.setIndeterminate(true);
                bar.setProgress(0);
                bar.show();
                TimeHelper.setPreviousData(tvStart, tvStartAMPM, tvEnd, tvEndAMPM, bar);
                break;
        }
    }
}
