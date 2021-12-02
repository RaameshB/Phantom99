package org.firstinspires.ftc.teamcode.util.HelperClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Stopwatch {
    LinearOpMode ln;
    public Stopwatch(LinearOpMode linearOpMode) {
        ln = linearOpMode;
    }
    double time;
    double resetPoint;
    public void resetAndRestart () {
        resetPoint = ln.getRuntime();
    }
    public double getTime () {
        return ln.getRuntime() - resetPoint;
    }
}
