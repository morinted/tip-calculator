package in.tedmor.www.tippy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;


public class HomeActivity extends Activity {
    String currencySign;
    int defaultPercentage;

    public void loadSettings() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultSign = getResources().getString(R.string.sign_dollar);
        currencySign = sharedPref.getString(getString(R.string.SIGN_KEY), defaultSign);
        defaultPercentage = sharedPref.getInt(getString(R.string.PERS_KEY), 15);
        TextView currencySymbol = (TextView) findViewById(R.id.textDollarHint);
        currencySymbol.setText(currencySign);
        EditText percentage = (EditText) findViewById(R.id.editPers);
        if (percentage.getText().length() == 0) {
            percentage.setText(Integer.toString(defaultPercentage), TextView.BufferType.EDITABLE);
        }
    }

    public void populateSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.number_of_people, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadSettings();
        populateSpinner();
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

        TextView tippyText = (TextView) findViewById(R.id.tippyValue);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        EditText editCost = (EditText) findViewById(R.id.editCost);
        EditText editPercent = (EditText) findViewById(R.id.editPers);
        Tip tip = new Tip(Double.parseDouble(editCost.getText().toString()),
                Double.parseDouble(editPercent.getText().toString()),
                Integer.parseInt(spinner.getSelectedItem().toString()));

        String tipAmt = currencySign + tip.getTipToString();

        tippyText.setText(tipAmt);
    }
}
