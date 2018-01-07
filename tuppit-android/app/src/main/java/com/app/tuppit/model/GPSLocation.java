package com.app.tuppit.model;

/**
 * Created by David on 1/9/16.
 */
public class GPSLocation { //TODO quizas esta clase no sea necesaria, usar Location?

    private double latitude;
    private double longitude;

    public GPSLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLocation(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GPSLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    /*
        private double[] getGPS() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

            Location l = null;

            for (int i=providers.size()-1; i>=0; i--) {
                l = lm.getLastKnownLocation(providers.get(i));
                if (l != null) break;
            }

            double[] gps = new double[2];
            if (l != null) {
                gps[0] = l.getLatitude();
                gps[1] = l.getLongitude();
            }
            return gps;
        }

    */
}
