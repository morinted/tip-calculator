package in.tedmor.www.tippy;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ArrayList;

public class HomeActivity extends Activity implements TipCard.OnFragmentInteractionListener {
    String currencySign;
    int defaultPercentage;
    ArrayList<TipCard> tipCards = new ArrayList<TipCard>();

    public void loadSettings() {
        String defaultSign = getResources().getString(R.string.sign_dollar);
        currencySign = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString("SIGN", defaultSign);
        defaultPercentage = Integer.parseInt(PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString("PERS", "15"));
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

    public void openSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                openSettings();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTipRating(View view) {
        Popover.newInstance().show(getFragmentManager(), null);
    }

    public void generateTip(View view) {
        // just the tippy

        // validation part

        EditText editCost = (EditText) findViewById(R.id.editCost);
        EditText editPercent = (EditText) findViewById(R.id.editPers);
        double cost = 0.0;
        boolean returnFlag = false;
        double percentage = 0.0;
        try {
            percentage = Double.parseDouble(editPercent.getText().toString());
            if (percentage <= 0.0) {
                throw new NumberFormatException("Percentage is zero");
            }
            editPercent.setHint(getResources().getString(R.string.percent_placeholder));
            editPercent.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        } catch (Exception e) {
            System.err.println("Invalid percent");
            editPercent.setText(null);
            editPercent.setHintTextColor(getResources().getColor(R.color.pitty_red));
            editPercent.setHint(getResources().getString(R.string.percent_err));
            editPercent.requestFocus();
            returnFlag = true;
        }
        try {
            cost = Double.parseDouble(editCost.getText().toString());
            if (cost <= 0.0) {
                throw new NumberFormatException("Cost is zero");
            }
            editCost.setHint(getResources().getString(R.string.bill_placeholder));
            editCost.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        } catch (Exception e) {
            System.err.println("Invalid cost");
            editCost.setText(null);
            editCost.setHintTextColor(getResources().getColor(R.color.pitty_red));
            editCost.setHint(getResources().getString(R.string.bill_err));
            editCost.requestFocus();
            returnFlag = true;
        }

        if (returnFlag) {
            return;
        }
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(spinner.getWindowToken(), 0);
        Tip tip = new Tip(cost,
                percentage,
                Integer.parseInt(spinner.getSelectedItem().toString()),
                this.currencySign);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TipCard tipCard = TipCard.newInstance(tip);
        tipCards.add(tipCard);
        LinearLayout ll = (LinearLayout) findViewById(R.id.tip_list);
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,
                R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.tip_list, tipCard, "TIP");
        fragmentTransaction.commit();
        findViewById(R.id.gridLayout).requestFocus();
    }


    public void generateSuggestedTip(float rating) {
        System.out.println("from home activity, the rating given is: " + rating);

        EditText percentField = (EditText) findViewById(R.id.editPers);
        Integer percentValue = (int)rating * 2 + 10;
        percentField.setText(percentValue.toString());

        Button tippyButton = (Button) findViewById(R.id.btnGenerate);

        System.out.println("Tippy! is performing a click");
        tippyButton.performClick();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println(uri);
    }

}
