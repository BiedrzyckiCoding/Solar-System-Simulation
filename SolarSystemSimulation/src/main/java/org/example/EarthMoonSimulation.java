package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EarthMoonSimulation {
    //gravitational constant
    static final double G = 6.6743e-11;

    //masses
    static final double M_SUN   = 1.989e30;
    static final double M_EARTH = 5.972e24;
    static final double M_MOON  = 7.347e22;

    //time step, total steps
    static final double DT = 3600; // 1 hour in seconds
    static final int STEPS = 24 * 365; // ~1 year
    static final double POSITION_SCALE = 1.0e9;
    static final double MOON_ORBIT_SCALE = 10.0;

    //Lists (already scaled) for storing Earth & Moon motion
    public List<Double> earthX = new ArrayList<>();
    public List<Double> earthY = new ArrayList<>();
    public List<Double> moonX  = new ArrayList<>();
    public List<Double> moonY  = new ArrayList<>();
    public List<Double> time   = new ArrayList<>();

    //relative moon position
    public List<Double> moonRelX = new ArrayList<>();
    public List<Double> moonRelY = new ArrayList<>();
    public void simulate() {
        //Real-world initial conditions, will later scale this
        //Earth around Sun (~1 AU)
        double xE  = 1.496e11; // meters
        double yE  = 0.0;
        double vxE = 0.0;
        double vyE = 29.78e3;  // ~29.78 km/s

        //Moon around Earth (~384,400 km)
        double xM  = 3.844e8;
        double yM  = 0.0;
        double vxM = 0.0;
        double vyM = 1.022e3;  // ~1.022 km/s

        for (int step = 0; step < STEPS; step++) {
            double t = step * DT;
            time.add(t);

            //Store scaled positions for plotting:
            earthX.add(xE / POSITION_SCALE);
            earthY.add(yE / POSITION_SCALE);

            double absMoonX = xE + (MOON_ORBIT_SCALE * xM);
            double absMoonY = yE + (MOON_ORBIT_SCALE * yM);

            moonX.add(absMoonX / POSITION_SCALE);
            moonY.add(absMoonY / POSITION_SCALE);

            //relative
            moonRelX.add(xM / POSITION_SCALE);
            moonRelY.add(yM / POSITION_SCALE);


            //EARTH around SUN
            //distance from Sun (assume Sun at origin (0,0))
            double rE = Math.sqrt(xE*xE + yE*yE);
            double FE = G * M_SUN * M_EARTH / (rE*rE);
            double aE = FE / M_EARTH;
            double axE = -aE * (xE / rE);
            double ayE = -aE * (yE / rE);

            //update Earth velocity and position
            vxE += axE * DT;
            vyE += ayE * DT;
            xE  += vxE * DT;
            yE  += vyE * DT;

            //moon around Earth
            double rM = Math.sqrt(xM*xM + yM*yM);
            double FM = G * M_EARTH * M_MOON / (rM*rM);
            double aM = FM / M_MOON;
            double axM = -aM * (xM / rM);
            double ayM = -aM * (yM / rM);

            vxM += axM * DT;
            vyM += ayM * DT;
            xM  += vxM * DT;
            yM  += vyM * DT;
        }
    }
    public static void main(String[] args) {
        EarthMoonSimulation sim = new EarthMoonSimulation();
        sim.simulate();

        SwingUtilities.invokeLater(() -> {
            EarthMoonSimulationGui gui = new EarthMoonSimulationGui(
                    sim.earthX,
                    sim.earthY,
                    sim.moonX,
                    sim.moonY,
                    sim.moonRelX,
                    sim.moonRelY,
                    sim.time
            );
            gui.setVisible(true);
        });
    }
}
