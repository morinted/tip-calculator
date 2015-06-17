package in.tedmor.www.tippy;

import java.text.DecimalFormat;

/**
 * Created by Ted on 2015-06-17.
 */
public class Tip {
    static final DecimalFormat CURRENCY = new DecimalFormat("0.00##");
    static final int[] RATINGS = {12, 14, 16, 18, 20};
    double cost, percentage, tip, total;
    int rating, people;
    public Tip() {}
    public Tip(double cost, double percentage, int people) {
        this.cost = cost;
        this.percentage = percentage;
        this.tip = (percentage / 100) * cost;
        this.total = this.tip + this.cost;
        this.people = people;
        this.rating = getRatingFromPercentage(this.percentage);
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
        return CURRENCY.format(this.tip / (double) people);
    }

    public String getTotalPerPersonToString() {
        return CURRENCY.format(this.total / (double) people);
    }

    public String getTipToString() {
        return CURRENCY.format(this.tip);
    }

    public String getCostToString() {
        return CURRENCY.format(this.cost);
    }

    public double getPercentage() {
        return this.percentage;
    }

    public String getPercentageToString() {
        return "%" + this.percentage;
    }

    public String getTotal() {
        return CURRENCY.format(this.total);
    }

    /*
     * @param rating: An integer from 1 to 5, representing the rating that the user
     * would like to assign to their service.
     */
    public static int getPercentageFromRating(int rating) {
        return RATINGS[rating - 1];
    }

    public int getPercentageFromRating() {
        return getPercentageFromRating(this.rating);
    }
}
