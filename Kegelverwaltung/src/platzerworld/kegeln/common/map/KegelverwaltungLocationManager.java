package platzerworld.kegeln.common.map;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class KegelverwaltungLocationManager{
	private static LocationManager locationManager;
	private MapController mapController;
	private MapView mapView;
	
	static private KegelverwaltungLocationManager _instance;
	
	private static Context ctx;
	
	private KegelverwaltungLocationManager(){
		
	}
	
	static synchronized public KegelverwaltungLocationManager getInstance(Context context) {
		if (_instance == null)
			_instance = new KegelverwaltungLocationManager();
		init(context);
		return _instance;
	}
	
	private static void init(Context aCtx){
		ctx = aCtx;
		locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public void bind(MapView aMapView){
		this.mapView = aMapView;
		mapController = mapView.getController();
		
		setProperties();
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, new GeoUpdateHandler());
	}
	
	private void setProperties(){
		mapView.setBuiltInZoomControls(true);
		mapView.setStreetView(true);
		mapController.setZoom(16); // Zoon 1 is world view
		int lat = (int) (48.17968 * 1E6); // 48
		int lng = (int) (11.5922 * 1E6); // 11
		GeoPoint point = new GeoPoint(lat, lng);
		mapController.setCenter(point);
	}
	
	public boolean isRouteDisplayed(){
		return true;
	}
	
	public class GeoUpdateHandler implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			int lat = (int) (location.getLatitude() * 1E6);
			int lng = (int) (location.getLongitude() * 1E6);
			GeoPoint point = new GeoPoint(lat, lng);
			mapController.animateTo(point); //	mapController.setCenter(point);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
	
}
