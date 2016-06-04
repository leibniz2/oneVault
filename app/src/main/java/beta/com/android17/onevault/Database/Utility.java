package beta.com.android17.onevault.Database;

import android.content.Context;

/**
 * Created by Radley Rosal on 10/11/2015.
 */
public class Utility {
    public DatabaseHandler db;
    public Context context;

    public static int ACTIVE_ACCOUNT;

    public Utility(Context context){
        this.context = context;
    }

    public Utility() {
    }

    public double calculateUSDEquivalent(double amount, double exchangeRateFrom){
        return amount/exchangeRateFrom;
    }

    public double covertCurrency(double USDEquivalent, double exchangeRateTo){
        return USDEquivalent*exchangeRateTo;
    }


}
