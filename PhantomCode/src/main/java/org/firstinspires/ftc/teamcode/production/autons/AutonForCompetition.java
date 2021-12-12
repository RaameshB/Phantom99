package org.firstinspires.ftc.teamcode.production.autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.RobotConfig;
import org.firstinspires.ftc.teamcode.util.initArgs;
import org.firstinspires.ftc.teamcode.util.ChassisController;

@Autonomous
public class AutonForCompetition extends LinearOpMode {

    RobotConfig robot = new RobotConfig();




    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, this, initArgs.CALIBRATE_IMU);
        waitForStart();

        //setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }
}
