/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package project;

import javax.swing.JOptionPane;

/**
 *
 * @author 
 */

public class Convert
{
char breek;
public String str="";
public long arr[];
public int len;
    public Convert (long A[],int length)
    {
	int i=0;
	//long  A[]=new long[10];
	//long B[]=new long[10];


	breek=14;
	//Scanner input=new Scanner(System.in);
	//for(i=0;i<10;i++)
	  //  {
		//System.out.printf("Enter number:");
	//	A[i]=350+i;
	  //  }
	for(i=0;i<length;i++)
	    {
		str=str+Long.toString(A[i]);
		str=str+breek;
	    }


    }
    public Convert(String mess)
    {
        int start=0;
        int i;
        int end;
        breek=14;
	String substr="";
	int j=0;
	len=0;

        for(i=0;i<mess.length();i++)
        {
            if(mess.charAt(i)==breek)
            {
                len++;

            }
        }


                    


        arr = new long[len];

      


	for(i=0;i<mess.length();i++)
	    {
           

		if(mess.charAt(i)==breek)
		    {


			end=i;
			substr=mess.substring(start,end);
			//System.out.println(p);

			arr[j]=Long.parseLong(substr);
			//System.out.println(B[j]);
			  j++;

			start=i+1;
		    }
              
	    }

	/*for(i=0;i<j;i++)
	    {
		System.out.println(arr[i]);
	    }

*/

    }

}