package beta.com.android17.onevault.Tools;

/**
 * Created by paks on 12/4/15.
 */
public class Tip_Calculator {
    //Input
    private double bill_Input;
    private double tax_Input;
    private double split_No;
    private double tip_Percent;
    //Output
    private double tip_Amount;
    private double total_Check;
    private double each_Tip_Amount;
    private double each_Pay;

    public void calculateTip(double bill_Input, double tax_Input, double split_No, double tip_Percent){
        double tempAmount;
        this.bill_Input = bill_Input;
        this.tax_Input = tax_Input/100;
        this.split_No = split_No;
        this.tip_Percent = tip_Percent/100;
        tempAmount = bill_Input+(bill_Input*this.tax_Input);
        this.tip_Amount = tempAmount*this.tip_Percent;
        tempAmount = tempAmount+(this.tip_Amount);
        this.total_Check = tempAmount;
        this.each_Tip_Amount = this.tip_Amount / this.split_No;
        this.each_Pay =  this.total_Check / this.split_No;
    }

    public double getBill_Input() {
        return bill_Input;
    }

    public void setBill_Input(double bill_Input) {
        this.bill_Input = bill_Input;
    }

    public double getTax_Input() {
        return tax_Input;
    }

    public void setTax_Input(double tax_Input) {
        this.tax_Input = tax_Input;
    }

    public double getSplit_No() {
        return split_No;
    }

    public void setSplit_No(double split_No) {
        this.split_No = split_No;
    }

    public double getTip_Percent() {
        return tip_Percent;
    }

    public void setTip_Percent(double tip_Percent) {
        this.tip_Percent = tip_Percent;
    }

    public double getTip_Amount() {
        return tip_Amount;
    }

    public void setTip_Amount(double tip_Amount) {
        this.tip_Amount = tip_Amount;
    }

    public double getTotal_Check() {
        return total_Check;
    }

    public void setTotal_Check(double total_Check) {
        this.total_Check = total_Check;
    }

    public double getEach_Tip_Amount() {
        return each_Tip_Amount;
    }

    public void setEach_Tip_Amount(double each_Tip_Amount) {
        this.each_Tip_Amount = each_Tip_Amount;
    }

    public double getEach_Pay() {
        return each_Pay;
    }

    public void setEach_Pay(double each_Pay) {
        this.each_Pay = each_Pay;
    }
}
