package org.firstinspires.ftc.teamcode.util.HelperClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.MecanumLibs.MecanumHelper;

public abstract class PhantomOpMode extends LinearOpMode {

    public LinearOpMode haha = this;
    public TelemetryHelper telemetry = new TelemetryHelper(this);
    public RobotConfig robot;
    public MecanumHelper chassis;
    public Stopwatch stopwatch = new Stopwatch(this);

    public void setChassis(chassisType type){
        robot =  new RobotConfig(hardwareMap, this, telemetry);
        robot.robotType = type;
        if (type == chassisType.MECANUM) {
             chassis = new MecanumHelper(robot, telemetry, this);
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {

    }
}