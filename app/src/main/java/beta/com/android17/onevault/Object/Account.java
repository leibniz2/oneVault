package beta.com.android17.onevault.Object;

import java.util.ArrayList;

/**
 * Created by Melgo on 11/8/2015.
 */
public class Account {
    private int KEY_ID;
    private String KEY_NAME = "";
    private String KEY_DESCRIPTION = "";
    private String KEY_CURRENCY = "";
    private double KEY_INIT_BALANCE = 0;
    private boolean KEY_HAS_CARD = false;
    private double KEY_LIMIT = 0;
    private boolean IS_ACTIVE = true;
    private ArrayList<Transaction> LIST_INCOME_TRANSACTIONS = new ArrayList<Transaction>();
    private ArrayList<Transaction> LIST_EXPENSE_TRANSACTIONS = new ArrayList<Transaction>();

    public Account(){}



    /**
     * @param KEY_NAME
     * @param KEY_DESCRIPTION
     * @param KEY_CURRENCY
     * @param KEY_INIT_BALANCE
     * @param KEY_HAS_CARD
     * @param KEY_LIMIT
     * @param LIST_INCOME_TRANSACTIONS
     * @param LIST_EXPENSE_TRANSACTIONS
     */

    public Account(String KEY_NAME, String KEY_DESCRIPTION,
                   String KEY_CURRENCY, double KEY_INIT_BALANCE,
                   boolean KEY_HAS_CARD, double KEY_LIMIT,
                   ArrayList<Transaction> LIST_INCOME_TRANSACTIONS,
                   ArrayList<Transaction> LIST_EXPENSE_TRANSACTIONS) {
        this.KEY_NAME = KEY_NAME;
        this.KEY_DESCRIPTION = KEY_DESCRIPTION;
        this.KEY_CURRENCY = KEY_CURRENCY;
        this.KEY_INIT_BALANCE = KEY_INIT_BALANCE;
        this.KEY_HAS_CARD = KEY_HAS_CARD;
        this.KEY_LIMIT = KEY_LIMIT;
        this.LIST_INCOME_TRANSACTIONS = LIST_INCOME_TRANSACTIONS;
        this.LIST_EXPENSE_TRANSACTIONS = LIST_EXPENSE_TRANSACTIONS;
    }

    public int getKEY_ID() {return KEY_ID;}

    public void setKEY_ID(int KEY_ID) {this.KEY_ID = KEY_ID;}

    public String getKEY_NAME() {return KEY_NAME;}

    public void setKEY_NAME(String KEY_NAME) {this.KEY_NAME = KEY_NAME;}

    public String getKEY_DESCRIPTION() {return KEY_DESCRIPTION;}

    public void setKEY_DESCRIPTION(String KEY_DESCRIPTION) {this.KEY_DESCRIPTION = KEY_DESCRIPTION;}

    public String getKEY_CURRENCY() {return KEY_CURRENCY;}

    public void setKEY_CURRENCY(String KEY_CURRENCY) {this.KEY_CURRENCY = KEY_CURRENCY;}

    public double getKEY_INIT_BALANCE() {return KEY_INIT_BALANCE;}

    public void setKEY_INIT_BALANCE(double KEY_INIT_BALANCE) {this.KEY_INIT_BALANCE = KEY_INIT_BALANCE;}

    public boolean isKEY_HAS_CARD() {return KEY_HAS_CARD;}

    public void setKEY_HAS_CARD(boolean KEY_HAS_CARD) {this.KEY_HAS_CARD = KEY_HAS_CARD;}

    public double getKEY_LIMIT() {return KEY_LIMIT;}

    public void setKEY_LIMIT(double KEY_LIMIT) {this.KEY_LIMIT = KEY_LIMIT;}

    public ArrayList<Transaction> getLIST_INCOME_TRANSACTIONS() {return LIST_INCOME_TRANSACTIONS;}

    public void setLIST_INCOME_TRANSACTIONS(ArrayList<Transaction> LIST_INCOME_TRANSACTIONS) {this.LIST_INCOME_TRANSACTIONS = LIST_INCOME_TRANSACTIONS;}

    public ArrayList<Transaction> getLIST_EXPENSE_TRANSACTIONS() {return LIST_EXPENSE_TRANSACTIONS;}

    public void setLIST_EXPENSE_TRANSACTIONS(ArrayList<Transaction> LIST_EXPENSE_TRANSACTIONS) {this.LIST_EXPENSE_TRANSACTIONS = LIST_EXPENSE_TRANSACTIONS;}

    public boolean IS_ACTIVE() {
        return IS_ACTIVE;
    }

    public void setIS_ACTIVE(boolean IS_ACTIVE) {
        this.IS_ACTIVE = IS_ACTIVE;
    }
}
