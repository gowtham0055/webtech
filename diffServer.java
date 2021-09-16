import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;
class diffieHelman{
BigInteger ModExponentiation(BigInteger m, BigInteger k, BigInteger n){
 BigInteger mul = new BigInteger("1");
 String s = k.toString(2);
 StringBuilder reverses = new StringBuilder();
 reverses.append(s);
 String reversedBin = reverses.reverse().toString();
 BigInteger prevMod = m.mod(n);
 if (reversedBin.charAt(0) == '1')
 mul = prevMod;
 for(int i = 1; i < reversedBin.length();i++){
 prevMod = (prevMod.pow(2)).mod(n);
 if(reversedBin.charAt(i) == '1'){
 mul = mul.multiply(prevMod);} 
}
 mul = mul.mod(n);
 return mul;
 }
}
public class diffServer {
public static void main(String[] args){
try{
diffieHelman df = new diffieHelman();
Scanner sc = new Scanner(System.in);
ServerSocket ss=new ServerSocket(6666);
Socket s=ss.accept();//establishes connection
DataInputStream dis=new DataInputStream(s.getInputStream());
String str=(String)dis.readUTF();
if(str.equals("1") == true){
String aval = (String)dis.readUTF();
BigInteger a = new BigInteger(aval);
System.out.println("For Bob the a-value is : "+a);
String pval = (String)dis.readUTF();
BigInteger p = new BigInteger(pval);
System.out.println("For Bob the value of p is: "+p);
System.out.println("Enter your confidential parameter-Xa : ");
BigInteger Xa = sc.nextBigInteger();
BigInteger Ya;
Ya = df.ModExponentiation(a, Xa, p);
String Ybval = (String)dis.readUTF();
BigInteger Yb = new BigInteger(Ybval);
System.out.println("For Bob the value of Yb is : "+ Ybval);
System.out.println("Bob : Enter your Ya parameter : ");
System.out.println(Ya);
DataOutputStream dout=new DataOutputStream(s.getOutputStream());
String yastr = Ya.toString();
dout.writeUTF(yastr);
dout.flush();
BigInteger K;
K = df.ModExponentiation(Yb, Xa, p);
System.out.println("The value of Key is : "+K);
}
if(str.equals("2") == true){
System.out.println("Discrete Logarithm Function");
}
if(str.equals("3") == true){
System.out.println("Man In The Middle Attack");
String aval = (String)dis.readUTF();
BigInteger a = new BigInteger(aval);
System.out.println("Bob : a-value is : "+a);
String pval = (String)dis.readUTF();
BigInteger p = new BigInteger(pval);
System.out.println("Bob : p-value is : "+p);
System.out.println("Enter your confidential parameter Xa : ");
BigInteger Xa = sc.nextBigInteger();
BigInteger Ya;
Ya = df.ModExponentiation(a, Xa, p);
String Yc_bval = (String)dis.readUTF();
BigInteger Yc_b = new BigInteger(Yc_bval);
System.out.println("Bob : The value of Yb is : "+ Yc_b);
System.out.println("Bob : Enter the Ya parameter : ");
System.out.println(Ya);
System.out.println("Eve enter your confidential Parameters Xc : ");
BigInteger Xc = sc.nextBigInteger();
BigInteger Yc_a = df.ModExponentiation(a, Xc, p);
System.out.println("The value of Ya is "+ Ya +" but Eve changes it to : "+Yc_a);
DataOutputStream dout=new DataOutputStream(s.getOutputStream());
String yc_astr = Yc_a.toString();
dout.writeUTF(yc_astr);
dout.flush();
BigInteger K;
K = df.ModExponentiation(Yc_b, Xa, p);
System.out.println("The value of Key is : "+K);
}
}catch(Exception e){System.out.println(e);}
}
}
