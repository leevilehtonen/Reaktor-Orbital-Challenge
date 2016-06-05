import java.util.ArrayList;

public class Satellite {
	
	public final String name;
	public final double lat;
	public final double lon;
	public final double alt;
	public Vector3 location;
	public ArrayList<Satellite> satellitesToConnect;
	
	public Satellite(String name, double lat, double lon, double alt) {
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		
	}
	public void initializePosition(Vector3 position) {
		this.location = position;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name + " " + this.lat + " " + this.lon + " " + this.alt+"\n";
	}
	public void connectToOtherSatellites(ArrayList<Satellite> mSatellites) {
		satellitesToConnect = new ArrayList<Satellite>();
		for (Satellite satellite : mSatellites) {
			if(Utils.PointLineDistance(location, satellite.location, new Vector3(0, 0, 0))>= Utils.EarthRadius){
				satellitesToConnect.add(satellite);
			}
		}
	}
}

