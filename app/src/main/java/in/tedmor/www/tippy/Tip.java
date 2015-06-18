package in.tedmor.www.tippy;

import java.text.DecimalFormat;

/**
 * Created by Ted on 2015-06-17.
 */
public class Tip {
    static final DecimalFormat CURRENCY = new DecimalFormat("0.00");
    static final DecimalFormat PERCENT = new DecimalFormat("#.##");
    static final int[] RATINGS = {12, 14, 16, 18, 20};
    double cost, percentage, tip, total;
    int rating, people;
    String sign;
    public Tip() {}
    public Tip(double cost, double percentage, int people, String sign) {
        this.cost = cost;
        this.percentage = percentage;
        this.tip = (percentage / 100) * cost;
        this.total = this.tip + this.cost;
        this.people = people;
        this.rating = getRatingFromPercentage(this.percentage);
        this.sign = sign;
    }

    public int getRatingFromPercentage(double percentage) {
        int pers = (int) Math.round((percentage - 10.00)/2.00);
        // pers will be an int with 1, 2, 3, 4, 5, etc. being the ratings.
        if (pers < 1) {
            return 1;
        } else if (pers > 5) {
            return 5;
        }
        return pers;
    }

    public int getPeople() {
        return this.people;
    }

    public String getTipPerPersonToString() {
        return sign + CURRENCY.format(this.tip / people);
    }

    public String getTotalPerPersonToString() {
        return sign + CURRENCY.format((this.total / people));
    }

    public String getTipToString() {
        return sign + CURRENCY.format(this.tip);
    }

    public String getCostToString() {
        return sign + CURRENCY.format(this.cost);
    }

    public double getPercentage() {
        return this.percentage;
    }

    public String getPercentageToString() {

        return PERCENT.format(this.percentage) + "%";
    }
    public double getTotal() {
        return this.total;
    }

    public String getTotalToString() {
        return sign + CURRENCY.format(this.total);
    }

    /*
     * @param rating: An integer from 1 to 5, representing the rating that the user
     * would like to assign to their service.
     */
    public static int getPercentageFromRating(int rating) {
        return RATINGS[rating - 1];
    }

    public int getRating() {
        return this.rating;
    }
}
