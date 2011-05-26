package platzerworld.kegeln.common.map;

import platzerworld.kegeln.R;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class ShowMap extends MapActivity {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.show_map); // bind the layout to the activity

		// create a map view
		RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.mainlayout);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setStreetView(true);
		mapController = mapView.getController();
		mapController.setZoom(16); // Zoon 1 is world view
		int lat = (int) (48.17968 * 1E6); // 48
		int lng = (int) (11.5922 * 1E6); // 11
		GeoPoint point = new GeoPoint(lat, lng);
		mapController.setCenter(point);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, new GeoUpdateHandler());
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public class GeoUpdateHandler implements LocationListener {

		public void onLocationChanged(Location location) {
			int lat = (int) (location.getLatitude() * 1E6);
			int lng = (int) (location.getLongitude() * 1E6);
			GeoPoint point = new GeoPoint(lat, lng);
			mapController.animateTo(point); //	mapController.setCenter(point);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}