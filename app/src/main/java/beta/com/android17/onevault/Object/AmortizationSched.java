package beta.com.android17.onevault.Object;

/**
 * Created by Melgo on 11/29/2015.
 */
public class AmortizationSched {

    private double paymentAmount = 0;
    private double interestRate = 0;
    private double currentBalance = 0;
    private double interestPaid = 0;
    private double principalPaid = 0;
    private double monthlyPayment = 0;
    private double extraPayment = 0;
    private double amount = 0;

    public AmortizationSched() {
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public double getInterestPaid() {
        return interestPaid;
    }

    public double getPrincipalPaid() {
        return principalPaid;
    }

    public double getAmount() {
        return amount;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public double getExtraPayment() {
        return extraPayment;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public void setInterestPaid(double interestPaid) {
        this.interestPaid = interestPaid;
    }

    public void setPrincipalPaid(double principalPaid) {
        this.principalPaid = principalPaid;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setExtraPayment(double extraPayment) {
        this.extraPayment = extraPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public AmortizationSched(double interestRate, double loanAmount, double monthlyPayment,
                             double extraPayment) {
        this.interestRate = interestRate;
        this.currentBalance = loanAmount;
        this.monthlyPayment = monthlyPayment;
        this.extraPayment = extraPayment;
        this.amount = this.monthlyPayment + this.extraPayment;
    }

    public void calculate() {

        if (currentBalance < amount && currentBalance > 0) {
            principalPaid = currentBalance;
            interestPaid = interestRate * currentBalance;
            currentBalance = currentBalance - principalPaid;
            amount = principalPaid + interestPaid;
        } else{
            interestPaid = interestRate * currentBalance;
            principalPaid = amount - interestPaid;
            currentBalance = currentBalance - principalPaid;

        }
        if (currentBalance <= 0) {
            currentBalance = 0;
        }
        String CB = String.format("%.2f",currentBalance);
        String PP = String.format("%.2f",principalPaid);
        String IP = String.format("%.2f",interestPaid);
        String A = String.format("%.2f",amount);

        amount = Double.parseDouble(A);
        interestPaid = Double.parseDouble(IP);
        principalPaid = Double.parseDouble(PP);
        currentBalance = Double.parseDouble(CB);


    }

}

