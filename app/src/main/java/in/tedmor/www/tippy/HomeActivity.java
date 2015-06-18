package in.tedmor.www.tippy;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends Activity implements TipCard.OnFragmentInteractionListener {
    String currencySign;
    int defaultPercentage;
    ArrayList<TipCard> tipCards = new ArrayList<TipCard>();

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
        switch (id) {
            case R.id.currencyChange:
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

        TextView tippyText = (TextView) findViewById(R.id.tippyValue);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        EditText editCost = (EditText) findViewById(R.id.editCost);
        EditText editPercent = (EditText) findViewById(R.id.editPers);
        Tip tip = new Tip(Double.parseDouble(editCost.getText().toString()),
                Double.parseDouble(editPercent.getText().toString()),
                Integer.parseInt(spinner.getSelectedItem().toString()),
                this.currencySign);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TipCard tipCard = TipCard.newInstance(tip);
        tipCards.add(tipCard);
        LinearLayout ll = (LinearLayout) findViewById(R.id.tip_list);

        fragmentTransaction.replace(R.id.tip_list, tipCard, "TIP");
        fragmentTransaction.commit();
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
