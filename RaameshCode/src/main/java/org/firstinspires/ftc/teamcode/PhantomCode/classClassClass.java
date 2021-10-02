package org.firstinspires.ftc.teamcode.PhantomCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.RobotConfig;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.TelemetryHelper;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.chassisType;

public class classClassClass extends LinearOpMode {

    TelemetryHelper console;
    RobotConfig robot;

    @Override
    public void runOpMode() throws InterruptedException {

        console = new TelemetryHelper(this);
//        console.enableTelemetry();
        robot = new RobotConfig(hardwareMap, this, console, chassisType.MECANUM);
        robot.init();


    }
}
