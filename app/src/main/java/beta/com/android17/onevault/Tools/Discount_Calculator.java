package beta.com.android17.onevault.Tools;

/**
 * Created by paks on 11/25/15.
 */
public class Discount_Calculator {
    private double price;
    private double tax_input;
    private double tax_output;
    private double discount;
    private double savings;
    private double totalPayment;


    public void calculate(double price, double discount, double tax_input){
        discount /= 100;
        tax_input /= 100;
        this.setPrice(price);
        this.setPrice(this.getPrice() - price*discount);
        System.out.println(this.getPrice());
        setSavings((price+ getTax_output())*discount);
        setTax_output(this.getPrice() * tax_input);
        setSavings((price * discount) + (price * discount) * tax_input);
        setTotalPayment(this.getPrice() + getTax_output());

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTax_input() {
        return tax_input;
    }

    public void setTax_input(double tax_input) {
        this.tax_input = tax_input;
    }

    public double getTax_output() {
        return tax_output;
    }

    public void setTax_output(double tax_output) {
        this.tax_output = tax_output;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }
}
