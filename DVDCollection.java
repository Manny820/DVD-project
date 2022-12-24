import java.io.*;

import defaultPackage.DVD;

public class DVDCollection {

	// Data fields
	
	/** The current number of DVDs in the array */
	private int numdvds;
	
	
	/** The array to contain the DVDs */
	private DVD[] dvdArray;
	
	
	/** The name of the data file that contains dvd data */
	private String sourceName;
	
	
	/** Boolean flag to indicate whether the DVD collection was
	    modified since it was last saved. */
	private boolean modified;
	
	
	/**
	 *  Constructs an empty directory as an array
	 *  with an initial capacity of 7. When we try to
	 *  insert into a full array, we will double the size of
	 *  the array first.
	 */
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[7];
	}
	
	public DVDCollection(int num, DVD[] test) 
	{
		numdvds = 0;
		dvdArray = new DVD[7];
	}
	
	
	public String toString() {
		// Return a string containing all the DVDs in the
		// order they are stored in the array along with
		// the values for numdvds and the length of the array.
		// See homework instructions for proper format.
		
		int len = dvdArray.length;
		String s = "No. of DVDs: " +numdvds+ "\nLength of dvdArray: " +len+ 
				   "\n";
		for(int i = 0; i < numdvds; i++)
			s = s+ " dvdArray["+i+"] = " +this.dvdArray[i].toString();

		return s;	// STUB: Remove this line.
	}

	public void addOrModifyDVD(String title, String rating, String runningTime) {
		// NOTE: Be careful. Running time is a string here
		// since the user might enter non-digits when prompted.
		// If the array is full and a new DVD needs to be added,
		// double the size of the array first.
		
		if(rating.equals("G") || rating.equals("PG") || rating.equals("PG-13") || rating.equals("R"))
		{
			int runningTime2 = Integer.parseInt(runningTime);
			if(runningTime2 >= 0)
			{
				if(numdvds == dvdArray.length)
				{
					DVD[] newDvdArray = new DVD[dvdArray.length*2];
					System.arraycopy(dvdArray, 0, newDvdArray, 0, numdvds);
					dvdArray = newDvdArray;
				}
				
				modified = true;
				
				if(numdvds > 0) 
				{
					for(int i = 0; i < numdvds; i++)
					{
						if(dvdArray[i].getTitle().equals(title))
						{
							dvdArray[i].setTitle(title);
							dvdArray[i].setRating(rating);
							dvdArray[i].setRunningTime(runningTime2);
							System.out.println("["+numdvds+"]" + dvdArray[numdvds].toString());
						}
						else 
						{
							if(numdvds < dvdArray.length)
							{
								dvdArray[numdvds] = new DVD(title, rating, runningTime2);
								System.out.println("["+numdvds+"]" + dvdArray[numdvds].toString());
								numdvds++;
							}
						}						
					}
				}
			}
			return;
		}
		return;
	}
	
	
	public void removeDVD(String title) 
	{
		int removeDvd = 0;
		if(numdvds > 0) 
		{
			for(int i = 0; i < numdvds; i++)
			{
				dvdArray[i].getTitle().equals(title);
				removeDvd = i;
			}
		}
		if(removeDvd >= 0)
		{
			modified = true;
			DVD[] newArray = new DVD[dvdArray.length - 1];
			System.arraycopy(dvdArray, 0, newArray, 0, removeDvd);
			System.arraycopy(dvdArray, removeDvd+1, newArray, removeDvd, dvdArray.length - removeDvd -1);
			dvdArray = newArray;
			numdvds--;
		}
		else 
			System.out.println("DVD NOT FOUND");
	}
	
	
	public String getDVDsByRating(String rating) 
	{
		String str = "";
		
		for(int i = 0; i < numdvds; i++)
		{
			if(dvdArray[i].getRating().equals(rating))
				str += (dvdArray[i].toString() + "\n");
		}
		return str;	// STUB: Remove this line.
	}

	
	public int getTotalRunningTime() 
	{
		if (numdvds > 0)
		{
			int total = 0;
			for(int i = 0; i < numdvds; i++)
				total += dvdArray[i].getRunningTime();
	
			return total;	
		}
		else
			return 0;
	}

	
	public void loadData(String filename) 
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			
			while((line = br.readLine()) != null)
			{
				String[] values = line.split(",");
				addOrModifyDVD(values[0],values[1],values[2]);
			}
			modified = false;
			sourceName = filename;
		}
		catch (Exception e)
		{
			System.out.println("\nError: " +e);
		}
	}
	
	
	public void save() 
	{
		try 
		{
			if(!modified)
			{
				System.out.println("Saved. File unchanged.");
				return;
			}
			FileWriter fw = new FileWriter(sourceName);
			if(numdvds > 0)
			{
				for(int i = 0; i < numdvds; i++)
					fw.write(dvdArray[i].getTitle() +"/"+ dvdArray[i].getRating() +"/"+ dvdArray[i].getRunningTime()+"\n");
			}
			fw.close();
			modified = false;
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	// Additional private helper methods go here:



	
}