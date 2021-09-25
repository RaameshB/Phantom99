package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Auto1", group = "group")
public class Auton extends LinearOpMode {

    RobotConfig robot = new RobotConfig();

    @Override
    public void runOpMode() throws InterruptedException {

            robot.init(hardwareMap);

            waitForStart();

            robot.leftDrive.setPower(0.75);
            robot.rightDrive.setPower(0.75);

            sleep(2000);

//            robot.servo1.setPosition();
    }

}
