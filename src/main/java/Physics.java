public class Physics {
    static double get_acceleration(double force, double mass){
        return force / mass;
    }

    static double[] get_displacement(double angle, double distance){
        double[] disp = new double[2];

        disp[0] = distance * Math.sin(Math.toRadians(angle))*10;
        disp[1] = distance * Math.cos(Math.toRadians(angle))*10;

        return disp;
    }
}
