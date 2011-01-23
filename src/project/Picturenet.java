
package project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Picturenet {
    private BufferedImage image;                                          // the rasterized image
    public String output_message="";                                            //array holding numeric encrypted message to decrypted
    private int width;
    private int height;
    private int X,Y;

    public Picturenet(String filename)
    {
        try
	    {
		image = ImageIO.read(new File(filename));
		height=image.getHeight();
		width=image.getWidth();
		//		System.out.println(width+" "+height);
	    }
        catch (IOException e)
	    {
		e.printStackTrace();
		throw new RuntimeException("Could not open file: " + filename);
	    }
        if (image == null)
            throw new RuntimeException("Invalid image file: " + filename);
    }

    // return Color of pixel (i, j)
    private Color get(int i, int j)
    {
	//System.out.println(i+" "+j);
	        return new Color(image.getRGB(i,j));
    }

    // change color of pixel (i, j) to c
    private void set(int i, int j, int color)
    {
        image.setRGB(i, j, color);
    }


    // save to given filename - suffix must be png, jpg, or gif
    private void save(String filename)
    {
	save(new File(filename));
    }

    // save to given filename - suffix must be png, jpg, or gif
    private void save(File file)
    {
        String filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        suffix = suffix.toLowerCase();
        if (suffix.equals("jpg") || suffix.equals("png"))
	    {
		try
		    {
			ImageIO.write(image, suffix, file);
		    }
		catch (IOException e)
		    {
			e.printStackTrace();
		    }
	    }

        else
	    {
		System.out.println("Error: filename must end in .jpg or .png");
	    }
    }



                              //picture_name,password,encrypted_message,mode=1 for insert
                              //picture_name,password,encrypted_message,mode=0 for retrival
    public void manage(String picname,String password,String enc_message,int mode)
    {


	String primitive,passwd=password;
	int code[];                                                      //integer array containing (0-9) encrypted message.
	int codesize;                                                    //variable holding the size of the enc_message numeric array.
	int var=0,count=0;
	int pasize=passwd.length();
	int X=11,Y=11;                                      //holds the password size.
	int x=X,y=Y;                                                   // x and y holds the position of pointer during traversal.
	int index=0,mod=mode;                                            //specifies the mode of operation insert or retrive.
	char brk=14;
	int offset=1;
	String pstr="";
	int pdigit[];


	if(mod==1)
	    {
		codesize=enc_message.length();
		code=new int[codesize];
		setcodesize(codesize);                                   //saves the size of the code in picture
		char chararr[]=enc_message.toCharArray();
		for(int k=0;k<codesize;k++)
		    {
			if(chararr[k]==brk)
			    {
				code[k]=brk;
			    }
			else
			    {
				code[k]=chararr[k]-'0';
			    }
		    }
	    }
	else
	    {
		codesize=getcodesize();
		//		System.out.println(codesize);
		code=new int[codesize];
	    }
	//************************************************

	for(int i=0;i<pasize;i++)
	    {
		var=passwd.charAt(i);
		pstr+=String.valueOf(var);
	    }
	int len=pstr.length();
	pdigit=new int[len];
	for(int i=0;i<len;i++)
	    {
		pdigit[i]=pstr.charAt(i)-'0';
		//System.out.println(pdigit[i]);
	    }
	int p=0;

	while(count<codesize)
	    {
		p=p%len;
		var=pdigit[p];
		if(var==0)
		    {
			var=1;
		    }
		//System.out.print(var);
		if((p%2)==0)
		    {
			x+=var;
			if(x>=width)
			    {
				x=X+0;
				y=Y+1;
				Y++;
			    }
			//System.out.print(" x= "+x+"\n");
		    }
		else
		    {
			y+=var;
			if(y>=height)
			    {
				x=X+0;
				y=Y+1;
				Y++;
			    }
			//System.out.print(" y= "+y+"\n");
		    }
		//
		if(mod==1)
		    {

			insert(code[count],index,x,y);
			//System.out.println(index+"  "+x+"  "+y+" code :  "+code[count]);
		    }
		else
		    {

			code[count]=retrieve(index,x,y);
			//			System.out.println(index+"  "+x+"  "+y+" code :  "+code[count]);
		    }
		count++;
		index++;
		p++;
	    }



	//*********************************************************************************************************************************************************

	//traversal through the image starts here.//////////////////////////////////////////////////////////////////////

	/*	while(count<codesize)
	{
		i=i%pasize;
		var=passwd.charAt(i);
		i++;
		offset=var%10;
		var=var/10;
		x+=offset;
		insert(code[count++],x,y)

		index%=3;

		if(mod==1)
		    {
			int data=code[count];
			System.out.println((r++)+"  "+x+"  "+y);
			insert(data,index,x,y);                                      //insert each charactor into the image.

		    }
		else
		    {
			//System.out.println((r++)+"  "+x+"  "+y);

			code[count]=retrieve(index,x,y);                            //retrieve each charactor from the image.
			//System.out.println(code[count]);
		    }
		++count;
		++index;

	}//end of traversal while loop

	*/




	if(mod==1)
	    {
		save("new.png");                                                    //creates an output image containing the message.
	    }
	else
	    {
		for(int k=0;k<codesize;k++)
		    {
			if(code[k]==brk)
			    {
				output_message+=brk;
			    }
			else
			    {
				output_message+=Integer.toString(code[k]);
			    }
		    }
		                                                                     //the retrived message for decrypting is now available in
		                                                                     //variable output_message
	    }
    }

    private void setcodesize(int codesize)
    {
	//Color color=get(10,10);
	//int g=color.getGreen();
	//int r=color.getRed();
	//r<<=16;g<<=8;
	//int result=0;
	//result|=codesize;
	//System.out.println(codesize);
	set(10,10,codesize);

    }

    private int getcodesize()
    {
	Color color=get(10,10);
	int b=color.getBlue();
        int g=color.getGreen();
	int r=color.getRed();
        int result=0;
	r<<=16;
        g<<=8;
	result|=r;
        result|=g;
        result|=b;
	//result=color.getRGB();
	//System.out.println("codesize"+result);
	return result;
    }

    //function to insert the data into image .
    private void insert(int data,int index,int x,int y)
    {

	Color color=get(x,y);
	int b=color.getBlue();
	int g=color.getGreen();
	int r=color.getRed();
	int mask=0;
	mask=~mask;

	switch(index%3)
	    {
	    case 0:
		{
		    mask=mask<<4;
		    b=b&mask;
		    mask=0;
		    mask=data|mask;
		    b=b|mask;
		    mask=0;
		    r<<=16;g<<=8;
		    mask=mask|r;mask=mask|g;mask=mask|b;
		    set(x,y,mask);
		    break;
		}
	    case 1:
		{
		    mask=mask<<4;
		    g=g&mask;
		    mask=0;
		    mask=data|mask;
		    g=g|mask;
		    mask=0;
		    r<<=16;g<<=8;
		    mask=mask|r;mask=mask|g;mask=mask|b;
		    set(x,y,mask);
		    break;
		}
	    case 2:
		{
		    mask=mask<<4;
		    r=r&mask;
		    mask=0;
		    mask=data|mask;
		    r=r|mask;
		    mask=0;
		    r<<=16;g<<=8;
		    mask=mask|r;mask=mask|g;mask=mask|b;
		    set(x,y,mask);
		    break;
		}
	    }
	return;
    }

    //function to retrieve information from image
    //the retrived informationn will be stored in a public variable: message
    private int retrieve(int index,int x,int y)
    {
	//	System.out.println(index+"  "+x+"   "+y);
	Color color=get(x,y);int data=0;int b,g,r;
	int mask=0;
	switch(index%3)
	    {
	    case 0:
		{
		    b=color.getBlue();
		    mask=mask|0x0000000f;
		    b=b & mask;
		    data=b;

		    break;
		}
	    case 1:
		{

		    g=color.getGreen();
		    mask=mask|0x0000000f;
		    g=g & mask;
		    data=g;
		    break;
		}
	    case 2:
		{

		    r=color.getRed();
		    mask=mask|0x0000000f;
		    r=r & mask;
		    data=r;
		    break;
		}
	    }

	return data;
    }
}