package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "tel1", group = "group")
public class TeleOp1 extends LinearOpMode {

    RobotConfig robot = new RobotConfig();

    double rightDrivePower;
    double leftDrivePower;

    double powerMultiplier = 0.7;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();

        while (!isStopRequested()) {
            rightDrivePower = gamepad1.right_stick_y;
            leftDrivePower = gamepad1.left_stick_y;

            rightDrivePower *= powerMultiplier;
            leftDrivePower *= powerMultiplier;

            robot.leftDrive.setPower(leftDrivePower);
            robot.rightDrive.setPower(rightDrivePower);

            idle();
        }
    }
}
