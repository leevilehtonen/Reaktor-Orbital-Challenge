
public class Utils {
	public static double EarthRadius = 6371;
	
	public static Vector3 LLAtoXYZ (Vector3 original) {
		Vector3 xyz = new Vector3(0, 0, 0);	
		xyz.setX((EarthRadius + original.getZ()) * Math.cos(Math.toRadians(original.getX())) * Math.cos(Math.toRadians(original.getY())));
		xyz.setY((EarthRadius + original.getZ()) * Math.cos(Math.toRadians(original.getX())) * Math.sin(Math.toRadians(original.getY())));
		xyz.setZ((EarthRadius + original.getZ()) * Math.sin(Math.toRadians(original.getX())));
		return xyz;
		
	}
	public static Vector3 Minus(Vector3 a, Vector3 b) {
		return new Vector3(b.getX()-a.getX(), b.getY()-a.getY(), b.getZ()-a.getZ());
	}
	
	public static double PointLineDistance(Vector3 begin, Vector3 end, Vector3 point) {	
		return VectorLength(CrossProduct(Minus(point,begin), Minus(begin, end)))/VectorLength(Minus(begin, end));
		
	}
	public static Vector3 CrossProduct(Vector3 a, Vector3 b) {	
		
		double x,y,z;
		x = a.getY() * b.getZ() - a.getZ()*b.getY();
		y = a.getZ() * b.getX() - a.getX()*b.getZ();
		z = a.getX() * b.getY() - a.getY()*b.getX();
		
		return new Vector3(x, y, z);
	}
	
	public static double VectorLength(Vector3 a) {
		return Math.sqrt(Math.pow(a.getX(), 2)+Math.pow(a.getY(), 2)+Math.pow(a.getZ(), 2));
	}
	
	public static boolean canConnect(Vector3 person, Satellite satellite) {
		
		double distance = Utils.VectorLength(Utils.Minus(person, satellite.location));
		
		if(distance>Math.sqrt(2)*EarthRadius) {
			return false;
		} else if(Math.sqrt(Math.pow(EarthRadius, 2)+ Math.pow(distance, 2))> (EarthRadius+satellite.alt))
			return false; {		
		} 
			return true;
			
	}
	


}
