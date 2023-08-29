import java.util.regex.*; 

public class parseHex
{
    static char[] s;
    public static char[] parseHex (String hexstr) 
    { 
        String p1 = "^[0-9A-Fa-f]+$";
        
        if (hexstr.matches(p1)) {
                s = hexstr.toCharArray();
                return s;
        }
        else{
            throw new NumberFormatException("input is not a hex");
        }
    } 
    public static void convertHexToDec(char[] ch){ 
            String digits = "0123456789ABCDEF";
             //t = t.toUpperCase();  
             int val = 0;  
             for (int i = 0; i < ch.length; i++)  
             {  
                 char c = ch[i];  
                 int d = digits.indexOf(c);  
                 val = 16 * val + d;  
                 
             }  
             System.out.println("Decimal value:" + val); 
    }  
	public static void main(String[] args) {
	    parseHex m = new parseHex();
        char[] t = m.parseHex("A5");
        char[] t1 = m.parseHex("FAA");
        //char[] t2=m.parseHex("T1Q");
        //char[] t3=m.parseHex("ABC");
        //char[] t4=m.parseHex("1QA");
        m.convertHexToDec(t);
        m.convertHexToDec(t1);
       //m.convertHexToDec(t2);
        //m.convertHexToDec(t3);
        //m.convertHexToDec(t4);
	}
}
    
