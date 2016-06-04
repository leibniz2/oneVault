package beta.com.android17.onevault.Tools;

import java.util.ArrayList;

import beta.com.android17.onevault.Object.AmortizationSched;

/**
 * Created by Melgo on 11/29/2015.
 */
public class Loan_Calculator {

    //INPUT
    private double interestRate = 0;//perYear
    private int loanTermYears = 0;
    private int loanTermMonths = 0;
    private int totalLoanTerm;
    private double extraPaymentperMonth = 0;
    private double loanAmount = 0;

    //OUTPUT
    private double monthlyPayment = 0;
    private double totalPayment = 0;
    private double totalInterest = 0;
    private double monthlyInterest = 0;
    private double annualPayment = 0;
    private double mortgageConstant = 0; //in percent
    private int payOffEarlierBy = 0;//month
    private double interestSaving = 0;
    private ArrayList<AmortizationSched> amortazationSched;
    private AmortizationSched amorSched;

    public Loan_Calculator() {
    }

    public Loan_Calculator(double interestRate, int loanTermYears, int loanTermMonths, double extraPaymentperMonth, double loanAmount) {
        this.interestRate = interestRate;
        this.loanTermYears = loanTermYears;
        this.loanTermMonths = loanTermMonths;
        this.extraPaymentperMonth = extraPaymentperMonth;
        this.loanAmount = loanAmount;
        calculateTotalLoanTerm();
        calculateInterestRate();
        amortazationSched = new ArrayList<>();

    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getExtraPaymentperMonth() {
        return extraPaymentperMonth;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public int getLoanTermYears() {
        return loanTermYears;
    }

    public int getLoanTermMonths() {
        return loanTermMonths;
    }

    public int getTotalLoanTerm() {
        return totalLoanTerm;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public double getMonthlyInterest() {
        return monthlyInterest;
    }

    public double getAnnualPayment() {
        return annualPayment;
    }

    public double getMortgageConstant() {
        return mortgageConstant;
    }

    public int getPayOffEarlierBy() {
        return payOffEarlierBy;
    }

    public double getInterestSaving() {
        return interestSaving;
    }

    public ArrayList<AmortizationSched> getAmortazationSched() {
        return amortazationSched;
    }

    private void calculateTotalLoanTerm() {
        totalLoanTerm = loanTermMonths + (loanTermYears * 12);
    }

    private void calculateInterestRate() {
        interestRate = (interestRate / 100) / 12;
    }

    public void computeOutput() {

        if (interestRate == 0) {
            monthlyPayment = (loanAmount + interestRate) / totalLoanTerm;
        } else {
            monthlyPayment = ((loanAmount * interestRate) * (Math.pow((1 + interestRate), totalLoanTerm)))
                    / ((Math.pow((1 + interestRate), totalLoanTerm)) - 1);
        }
        totalInterest = (monthlyPayment * totalLoanTerm) - loanAmount;
        totalPayment = totalInterest + loanAmount;
        monthlyInterest = totalInterest / totalLoanTerm;
        annualPayment = (totalPayment / totalLoanTerm) * 12;
        mortgageConstant = annualPayment;
        amorSched = new AmortizationSched(this.interestRate, this.loanAmount, monthlyPayment, extraPaymentperMonth);
        amorSched.calculate();

        AmortizationSched tmp2 = new AmortizationSched();
        tmp2.setAmount(amorSched.getAmount());
        tmp2.setCurrentBalance(amorSched.getCurrentBalance());
        tmp2.setExtraPayment(amorSched.getExtraPayment());
        tmp2.setInterestPaid(amorSched.getInterestPaid());
        tmp2.setInterestRate(amorSched.getInterestRate());
        tmp2.setMonthlyPayment(amorSched.getMonthlyPayment());
        tmp2.setPaymentAmount(amorSched.getPaymentAmount());
        tmp2.setPrincipalPaid(amorSched.getPrincipalPaid());
        amortazationSched.add(amorSched);
        int ctr = 1;
        double interestTotal = tmp2.getInterestPaid();
        while (tmp2.getCurrentBalance() > 0) {
            ctr++;

            AmortizationSched tmp = new AmortizationSched();
            tmp.setAmount(tmp2.getAmount());
            tmp.setCurrentBalance(tmp2.getCurrentBalance());
            tmp.setExtraPayment(tmp2.getExtraPayment());
            tmp.setInterestPaid(tmp2.getInterestPaid());
            tmp.setInterestRate(tmp2.getInterestRate());
            tmp.setMonthlyPayment(tmp2.getMonthlyPayment());
            tmp.setPaymentAmount(tmp2.getPaymentAmount());
            tmp.setPrincipalPaid(tmp2.getPrincipalPaid());
            tmp.calculate();
            amortazationSched.add(tmp);
            tmp2.setAmount(tmp.getAmount());
            tmp2.setCurrentBalance(tmp.getCurrentBalance());
            tmp2.setExtraPayment(tmp.getExtraPayment());
            tmp2.setInterestPaid(tmp.getInterestPaid());
            tmp2.setInterestRate(tmp.getInterestRate());
            tmp2.setMonthlyPayment(tmp.getMonthlyPayment());
            tmp2.setPaymentAmount(tmp.getPaymentAmount());
            tmp2.setPrincipalPaid(tmp.getPrincipalPaid());

            interestTotal = interestTotal + tmp2.getInterestPaid();
        }
        payOffEarlierBy = totalLoanTerm - ctr;
        interestSaving = totalInterest - interestTotal;
        if (extraPaymentperMonth != 0) {
            totalInterest = interestTotal;
            totalPayment = totalPayment - interestTotal;
        }
    }
}

