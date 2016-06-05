import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SatelliteScanner {
	
	private ArrayList<Satellite> mSatellites;
	private BufferedReader mReader;
	private Vector3 begin;
	private Vector3 end;
	
	public SatelliteScanner() {
		mSatellites = new ArrayList<Satellite>();
		try {
			mReader = new BufferedReader(new FileReader("generate.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void read() throws IOException {
		
		String line = "";
		
		  while((line = mReader.readLine()) != null) {
              if(line.contains("SAT")) {
            	  String[] parts = line.split(",");
            	  mSatellites.add(new Satellite(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3])));
              } else if (line.contains("ROUTE")) {
            	  String[] parts = line.split(",");
            	  begin = new Vector3(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), 0);
            	  end = new Vector3(Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), 0); 
              }
          }   
          mReader.close();    
	}

	public ArrayList<Satellite> getSatellites() {
		return mSatellites;
	}
	
	public Vector3 getBegin() {
		return begin;
	}
	
	public Vector3 getEnd() {
		return end;
	}

}
