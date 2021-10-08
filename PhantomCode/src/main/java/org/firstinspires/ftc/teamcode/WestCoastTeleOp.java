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
    float powerMult;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, this);


        robot.leftDrive.setPower(leftDrivePower);
        robot.rightDrive.setPower(rightDrivePower);
    }
}