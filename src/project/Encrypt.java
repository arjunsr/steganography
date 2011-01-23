/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package project;



/**
 *
 * @author deepak
 */
class KeyGen
{
    protected long privatekey;
    protected long publickey;
    protected long moduluo;
    protected long totient;
    protected long gcd(long num1,long num2)
    {
	return((num2==0)?num1:gcd(num2,num1%num2));
    }
    protected long nthprime(long n)
    {
	long current=1,i=0;
	while(n>0)
	    {
		while(1 == 1)
		    {
			if(i==current)
			    current=current+2;
			for(i=2;i<current;i++)
			    {
				if((current%i)==0)
				    break;
			    }
			if(i<current)
			    current=current+2;
			else
			    break;
		    }
		n--;
		if(current==1)
		    current=current+2;
	    }
	return current;
    }
protected long gen_publickey(long totient)
    {
	long i;
	for(i=2;i<totient;i++)
	    {
		if(gcd(totient,i)==1)
		    return i;
	    }
	System.out.println("Public Key Generation Failed...");
	return 0;

    }
    protected long gen_privatekey(long totient,long publickey)
    {
	long i=totient+1;
	long num;
	do
	    {
		num=(publickey*i)-1;
		if((num%totient)==0)
		    return i;
		i++;
	    }while(i>totient);
	System.out.println("Private Key Generation Failed...");
	return 0;
    }
    protected long gen_pq(char char_arr[],int length)
    {
	int store;
	long p_gen = 0;
	for(int i=0;i<length;i++)
	    {
		store=(int)char_arr[i];
		if((store>=65)&&(store<=90))
		    store=store-64;
		if((store>=97)&&(store<=122))
		    store=store-70;
		if((store>48)&&(store<=58))
		    store=store+5;
		//System.out.println("Store is : "+store);
		if(store<10)
		    {
			p_gen=p_gen*10;
			p_gen=p_gen+store;
		    }
		else if(store<100)
		    {
			p_gen=p_gen*100;
			p_gen=p_gen+store;
		    }
		else
		    {
			p_gen=p_gen*1000;
			p_gen=p_gen+store;
		    }
		//System.out.println("Pgen is : "+p_gen);
	    }
	return p_gen;
    }

    KeyGen(String password)
    {
	int passlength=password.length();
	char p_array[] = new char[(passlength/2)];
	char q_array[] = new char[(passlength/2)];
	password.getChars(0,passlength/2,p_array,0);
	password.getChars((passlength/2)+1,passlength,q_array,0);
	long p_gen=gen_pq(p_array,passlength/2);
	long q_gen=gen_pq(q_array,passlength/2);
	long p,q;
	p=nthprime(p_gen);
	q=nthprime(q_gen);
	moduluo=p*q;
	totient=(p-1)*(q-1);
	publickey=gen_publickey(totient);
	privatekey=gen_privatekey(totient,publickey);
    }
    protected void printKey()
    {
	System.out.println("Private Key is "+ privatekey);
	System.out.println("Publc Key is "+ publickey);
    }
}

class Encrypt extends KeyGen
{

    public String message;
    public long encrypted[];
    protected char decrypted[];
    protected int length;
    protected long modpow(long base,long exponent,long modulus)
    {
        long result = 1;
        while (exponent > 0)
            {
                if ((exponent & 1) == 1)
                    {
                        // multiply in this bit's contribution while using modulus to keep result small
                        result = ((result* base) % modulus);
                    }
                // move to the next bit of the exponent, square (and mod) the base accordingly
                exponent=exponent>>1;
                base = (base * base) % modulus;
            }

        return result;
    }
    Encrypt(String password,String msg)
    {
	super(password);
	message=msg;
	encrypted=new long[message.length()];
	//decrypted=new char[length()];
    }

    Encrypt(long Encrypted[],int length_inp,String password)
    {
	super(password);
    	length=length_inp;
	encrypted=new long[length];
	decrypted=new char[length];
	for(int i=0;i<length;i++)
	    {
		encrypted[i]=Encrypted[i];
	    }
    }
public void doEncrypt()
    {
	char msg[]=new char[message.length()];
	for(int i=0;i<message.length();i++)
	    {
		msg[i]=message.charAt(i);
		encrypted[i]=modpow(msg[i],publickey,moduluo);
	    }

    }

    public void doDecrypt()
    {
	for(int i=0;i<length;i++)
	    {
		decrypted[i]=(char)modpow(encrypted[i],privatekey,moduluo);
	    }

	message=new String(decrypted);
         //JOptionPane.showMessageDialog(null,message);
    }
    public void printEncrypted()
    {
	for(int i=0;i<message.length();i++)
	    System.out.println("The Encrypted String is:"+ encrypted[i]);
    }
    public void printDecrypted()
    {
	System.out.println("The Decrypted String is:"+ message);
    }

}
/*class test
{
    public static void main(String[] args)
    {
	//Encrypt encrypt_obj;
	Encrypt decrypt_obj;
	long values[] = new long[4];
	values[0] = 27399583;
	values[1] = 9682533;
	values[2] = 40204085;
	values[3] = 36744502;
	decrypt_obj = new Encrypt(values,4,"Pass");
	decrypt_obj.doDecrypt();
	decrypt_obj.printDecrypted();


    }
}
*/
