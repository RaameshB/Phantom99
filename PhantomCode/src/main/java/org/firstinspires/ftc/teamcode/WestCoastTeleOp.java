package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.UtilityLibs.MotorCluster;
import org.firstinspires.ftc.teamcode.UtilityLibs.RobotConfig;


@TeleOp
public class WestCoastTeleOp extends LinearOpMode {

    RobotConfig robot = new RobotConfig();
    double leftDrivePower;
    double rightDrivePower;
    float powerMult = 5;
    boolean isBumperPressed = false;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, this);

        while(!isStopRequested()){

            leftDrivePower = gamepad1.left_stick_y;
            rightDrivePower = gamepad1.right_stick_y;

            if(!gamepad1.left_bumper && !isBumperPressed && powerMult > 1) {
                powerMult -= 1;
                isBumperPressed = true;

            }


            if(!gamepad1.right_bumper && !isBumperPressed && powerMult < 10){
                powerMult += 1;
                isBumperPressed = true;
            }


            robot.leftDrive.setPower(leftDrivePower);
            robot.rightDrive.setPower(rightDrivePower);

            idle();
        }


    }
}