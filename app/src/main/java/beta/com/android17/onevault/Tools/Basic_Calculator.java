package beta.com.android17.onevault.Tools;

/**
 * Created by Melgo on 11/21/2015.
 */
public class Basic_Calculator {
    public String str ="";
    public Character op = 'q';
    public double num;
    public double numtemp;

    public String reset() {
        str ="";
        op ='q';
        num = 0;
        numtemp = 0;
        return "";
    }
    public String insert(String j) {

        if(str.contains(".")&&j.equals(".")) {
            //do nothing
        }
        else{
            str = str + j;
            num = Double.valueOf(str).doubleValue();
        }
       return str;


    }
    public void perform() {
        str = "";
        numtemp = num;
    }
    public String calculate() {
        if(op == '+')
            num = numtemp+num;
        else if(op == '-')
            num = numtemp-num;
        else if(op == '/')
            num = numtemp/num;
        else if(op == '*')
            num = numtemp*num;
        else if(op == '%')
            num = numtemp*(num/100);
        str ="";
        op = 'q';
        return ""+num;
    }
}
