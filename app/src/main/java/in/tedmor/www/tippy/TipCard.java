package in.tedmor.www.tippy;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TipCard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TipCard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TipCard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TIP = "tip";

    // TODO: Rename and change types of parameters
    private Tip tip;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TipCard.
     */
    // TODO: Rename and change types and number of parameters
    public static TipCard newInstance(Tip tip) {
        TipCard fragment = new TipCard();
        fragment.setTip(tip);
        return fragment;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }
    public TipCard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_tip_card, container, false);
        setValues(result);
        return result;
    }

    public void setValues(View view) {
        // Set percentage
        TextView pers = (TextView) view.findViewById(R.id.displayPercentage);
        pers.setText(tip.getPercentageToString());
        RatingBar rb = (RatingBar) view.findViewById(R.id.displayStars);
        rb.setRating(tip.getRating());
        TextView cost = (TextView) view.findViewById(R.id.displayCost);
        cost.setText(tip.getCostToString());
        TextView tipDisplay = (TextView) view.findViewById(R.id.displayTip);
        tipDisplay.setText(tip.getTipToString());
        TextView total = (TextView) view.findViewById(R.id.displayTotal);
        total.setText(tip.getTotalToString());

        if (tip.getPeople() == 1) {
            View txtGroup = view.findViewById(R.id.textNumPeople);
            GridLayout me = (GridLayout) txtGroup.getParent();
            me.removeView(txtGroup);
            txtGroup = view.findViewById(R.id.textTipPP);
            me.removeView(txtGroup);
            txtGroup = view.findViewById(R.id.textTotalPP);
            me.removeView(txtGroup);
            txtGroup = view.findViewById(R.id.displayTipPerPerson);
            me.removeView(txtGroup);
            txtGroup = view.findViewById(R.id.displayTotalPerPerson);
            me.removeView(txtGroup);
        } else {
            TextView txtNumPeople = (TextView) view.findViewById(R.id.textNumPeople);
            String fmt = getResources().getString(R.string.tipcardnumpeople);
            String people = String.format(fmt, tip.getPeople());
            txtNumPeople.setText(people);

            TextView tipPerPerson = (TextView) view.findViewById(R.id.displayTipPerPerson);
            tipPerPerson.setText(tip.getTipPerPersonToString());
            TextView ttlPerPerson = (TextView) view.findViewById(R.id.displayTotalPerPerson);
            ttlPerPerson.setText(tip.getTotalPerPersonToString());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
