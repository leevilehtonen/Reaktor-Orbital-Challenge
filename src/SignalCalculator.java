import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SignalCalculator {

	private SatelliteScanner mSatelliteScanner;
	private ArrayList<Satellite> mSatellites;
	private ArrayList<Satellite> mSatellitesToBegin = new ArrayList<Satellite>();
	private ArrayList<Satellite> mSatellitesToEnd = new ArrayList<Satellite>();
	private ArrayList<Satellite> mSatelliteConnection = new ArrayList<Satellite>(); 
	private ArrayList<Satellite> mSatellitesUsed = new ArrayList<Satellite>();
	private ArrayList<String> mPossibleSatelliteConnections = new ArrayList<String>();
 	
	private Vector3 begin;
	private Vector3 end;

	public SignalCalculator() {
	}

	public void start() {
		
		mSatelliteScanner = new SatelliteScanner();
		try {
			mSatelliteScanner.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mSatellites = mSatelliteScanner.getSatellites();
		begin = mSatelliteScanner.getBegin();
		end = mSatelliteScanner.getEnd();
		begin = Utils.LLAtoXYZ(begin);
		end = Utils.LLAtoXYZ(end);

		for (Satellite mSatellite : mSatellites) {
			mSatellite.initializePosition(
					Utils.LLAtoXYZ(new Vector3(mSatellite.lat, mSatellite.lon, mSatellite.alt)));
		}
		for (Satellite mSatellite : mSatellites) {
			mSatellite.connectToOtherSatellites(mSatellites);
		}
		
		mSatellitesToBegin = new ArrayList<Satellite>();
		mSatellitesToEnd = new ArrayList<Satellite>();

		for (Satellite satellite : mSatellites) {
			if (Utils.canConnect(begin, satellite)) {
				mSatellitesToBegin.add(satellite);
			}
		}
		for (Satellite satellite : mSatellites) {
			if (Utils.canConnect(end, satellite)) {
				mSatellitesToEnd.add(satellite);
			}
		}
		calculatePossiblePaths(mSatellitesToBegin.get(0), mSatellitesToEnd.get(0));
		System.out.println(Collections.min(mPossibleSatelliteConnections, new Comparator<String>() {
			
			@Override
			public int compare(String o1, String o2) {
				return o1.length()-o2.length();
			}
		}));

	}

	public void calculatePossiblePaths(Satellite mSatelliteToBegin, Satellite mSatelliteToEnd) {

		mSatelliteConnection.add(mSatelliteToBegin);
		mSatellitesUsed.add(mSatelliteToBegin);

		if (mSatelliteToBegin.equals(mSatelliteToEnd)) {
			String connection = "";
			for (Satellite satellite : mSatelliteConnection) {
				connection = connection + satellite.name + ",";
			}
			connection = connection.substring(0,connection.length()-1);
			mPossibleSatelliteConnections.add(connection);
			
		} else {
			for (Satellite mConnectableSatellite : mSatelliteToBegin.satellitesToConnect) {
				if (!mSatellitesUsed.contains(mConnectableSatellite))
					calculatePossiblePaths(mConnectableSatellite, mSatelliteToEnd);
			}
		}
		mSatelliteConnection.remove(mSatelliteConnection.size()-1);
		mSatellitesUsed.remove(mSatelliteToEnd);
	}

}
