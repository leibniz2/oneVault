package beta.com.android17.onevault.Object;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by radleyrosal on 10/22/2015.
 */
public class Transaction {

    private Calendar c = Calendar.getInstance();

    private int KEY_ID;
    private int FKEY_ID;
    private double KEY_AMOUNT = 0;
    private String KEY_PAYMETHOD = "";
    private Date KEY_DATE = new Date(c.getTime().getTime());
    private int KEY_REF_CHECK = 0;
    private String KEY_DESCRIPTION = "";
    private double KEY_TAX = 0;
    private int KEY_QUANTITY = 0;
    private String KEY_PAYER = "";
    private String KEY_TAGS = "";
    private String KEY_STATUS = "";
    private String KEY_CATEGORY = "";
    private String KEY_FREQUENCY = "";
    private int KEY_ISINCOME = 0;


    //private String KEY_UNIT = "";


    public Transaction(){}

    /**
     * @param KEY_AMOUNT
     * @param KEY_PAYMETHOD
     * @param KEY_DATE
     * @param KEY_REF_CHECK
     * @param KEY_DESCRIPTION
     * @param KEY_TAX
     * @param KEY_QUANTITY
     * @param KEY_PAYER
     * @param KEY_TAGS
     * @param KEY_CATEGORY
     * @param KEY_STATUS
     //* @param KEY_UNIT
     */
    public Transaction(double KEY_AMOUNT, String KEY_PAYMETHOD,
                       Date KEY_DATE, int KEY_REF_CHECK,
                       String KEY_DESCRIPTION, double KEY_TAX,
                       int KEY_QUANTITY, String KEY_PAYER,
                       String KEY_TAGS , String KEY_CATEGORY , String KEY_STATUS,
                       String KEY_FREQUENCY,int FKEY_ID
                        ) {
        this.KEY_AMOUNT = KEY_AMOUNT;
        this.KEY_PAYMETHOD = KEY_PAYMETHOD;
        this.KEY_DATE = KEY_DATE;
        this.KEY_REF_CHECK = KEY_REF_CHECK;
        this.KEY_DESCRIPTION = KEY_DESCRIPTION;
        this.KEY_TAX = KEY_TAX;
        this.KEY_QUANTITY = KEY_QUANTITY;
        this.KEY_PAYER = KEY_PAYER;
        this.KEY_TAGS = KEY_TAGS;
        this.KEY_CATEGORY = KEY_CATEGORY;
        this.KEY_STATUS = KEY_STATUS;
        this.KEY_FREQUENCY = KEY_FREQUENCY;
        this.FKEY_ID = FKEY_ID;

    }

    public String getKEY_PAYMETHOD() {
        return KEY_PAYMETHOD;
    }

    public void setKEY_PAYMETHOD(String KEY_PAYMETHOD) {
        this.KEY_PAYMETHOD = KEY_PAYMETHOD;
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public double getKEY_AMOUNT() {return KEY_AMOUNT;}

    public void setKEY_AMOUNT(double KEY_AMOUNT) {this.KEY_AMOUNT = KEY_AMOUNT;}

    public int getKEY_REF_CHECK() {return KEY_REF_CHECK;}

    public void setKEY_REF_CHECK(int KEY_REF_CHECK) {this.KEY_REF_CHECK = KEY_REF_CHECK;}

    public String getKEY_DESCRIPTION() {return KEY_DESCRIPTION;}

    public void setKEY_DESCRIPTION(String KEY_DESCRIPTION) {this.KEY_DESCRIPTION = KEY_DESCRIPTION;}

    public double getKEY_TAX() {return KEY_TAX;}

    public void setKEY_TAX(double KEY_TAX) {this.KEY_TAX = KEY_TAX;}

    public int getKEY_QUANTITY() {return KEY_QUANTITY;}

    public void setKEY_QUANTITY(int KEY_QUANTITY) {this.KEY_QUANTITY = KEY_QUANTITY;}

    public String getKEY_PAYER() {return KEY_PAYER;}

    public void setKEY_PAYER(String KEY_PAYER) {this.KEY_PAYER = KEY_PAYER;}

    public String getKEY_TAGS() {return KEY_TAGS;}

    public void setKEY_TAGS(String KEY_TAGS) {this.KEY_TAGS = KEY_TAGS;}


    public Date getKEY_DATE() {
        return KEY_DATE;
    }

    public void setKEY_DATE(Date KEY_DATE) {
        this.KEY_DATE = KEY_DATE;
    }

    public String getKEY_STATUS() {
        return KEY_STATUS;
    }

    public void setKEY_STATUS(String KEY_STATUS) {
        this.KEY_STATUS = KEY_STATUS;
    }

    public String getKEY_CATEGORY() {
        return KEY_CATEGORY;
    }

    public void setKEY_CATEGORY(String KEY_CATEGORY) {
        this.KEY_CATEGORY = KEY_CATEGORY;
    }

    public String getKEY_FREQUENCY() {
        return KEY_FREQUENCY;
    }

    public void setKEY_FREQUENCY(String KEY_FREQUENCY) {
        this.KEY_FREQUENCY = KEY_FREQUENCY;
    }


    public int getFKEY_ID() {return FKEY_ID;}

    public void setFKEY_ID(int FKEY_ID) {this.FKEY_ID = FKEY_ID;}

    public int getKEY_ISINCOME() {
        return KEY_ISINCOME;
    }

    public void setKEY_ISINCOME(int KEY_ISINCOME) {
        this.KEY_ISINCOME = KEY_ISINCOME;
    }

    //public String getKEY_UNIT() {return KEY_UNIT;}

    //public void setKEY_UNIT(String KEY_UNIT) {this.KEY_UNIT = KEY_UNIT;}
}
