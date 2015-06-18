package in.tedmor.www.tippy;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class Popover extends DialogFragment implements View.OnClickListener  {

    public static Popover newInstance() {
        Popover popover = new Popover();
        popover.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog);
        return popover;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Remove the default background
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Inflate the new view with margins and background
        View v = inflater.inflate(R.layout.activity_popup, container, false);

        // Set up a click listener to dismiss the popup if they click outside
        // of the background view
        v.findViewById(R.id.popup_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((TextView) v.findViewById(R.id.ratingText)).setText(R.string.tip_rating_3);

        RatingBar rb = (RatingBar) v.findViewById(R.id.ratingBarInput);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                View dialog = (View) ratingBar.getParent();
                TextView ratingText = (TextView) dialog.findViewById(R.id.ratingText);

                int intRating = (int) rating;

                switch (intRating) {
                    case 0:
                        ratingBar.setRating(1.0f); // min rating is 1 star
                        break;
                    case 1:
                        ratingText.setText(R.string.tip_rating_1);
                        break;
                    case 2:
                        ratingText.setText(R.string.tip_rating_2);
                        break;
                    case 3:
                        ratingText.setText(R.string.tip_rating_3);
                        break;
                    case 4:
                        ratingText.setText(R.string.tip_rating_4);
                        break;
                    case 5:
                        ratingText.setText(R.string.tip_rating_5);
                        break;
                }
            }
        });

        // Set listener for the "suggest a tippy! button"
        Button b = (Button) v.findViewById(R.id.suggestTipBtn);
        b.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View view) {
        View dialog = (View) view.getParent();
        float rating = ((RatingBar) dialog.findViewById(R.id.ratingBarInput)).getRating();
        System.out.println("btn got 'tippy' clicked: " + rating);

        HomeActivity activity = (HomeActivity) getActivity();

        activity.generateSuggestedTip(rating);
        dismiss();
    }
}




