package org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.UtilityLibs.MecanumLibs.MecanumHelper;

public abstract class PhantomOpMode extends LinearOpMode {

    public TelemetryHelper console;
    public RobotConfig robot;
    public MecanumHelper chassis;
    public Stopwatch stopwatch;

    public void setChassis(chassisType type){
        robot =  new RobotConfig(hardwareMap, this, console, type);
        if (type == chassisType.MECANUM) {
            chassis = new MecanumHelper(robot, console, this);
        }
    }

    public abstract void opModeCode() throws InterruptedException;

    @Override
    public final void runOpMode() throws InterruptedException {
        console = new TelemetryHelper(this);
        stopwatch = new Stopwatch(this);
//        console.enableTelemetry();
        opModeCode();
    }

}