package in.tedmor.www.tippy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.currencyChange:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTipRating(View view) {
        System.out.println("Tip me");
    }

    public void generateTip(View view) {
        // just the tippy
        NumberFormat currFrmt = NumberFormat.getCurrencyInstance(Locale.getDefault());
        TextView tippyText = (TextView) findViewById(R.id.tippyValue);
        EditText editBill = (EditText) findViewById(R.id.editBill);
        EditText editPercent = (EditText) findViewById(R.id.editPercent);
        Double tipAmt = Double.parseDouble(editBill.getText().toString()) *
                Double.parseDouble(editPercent.getText().toString()) / 100;
        String tip = currFrmt.format(tipAmt);

        tippyText.setText(tip);
    }
}
