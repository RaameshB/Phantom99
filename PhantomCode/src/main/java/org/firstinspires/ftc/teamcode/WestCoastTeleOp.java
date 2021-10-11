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
    double tempPower;
    float powerMult = 5;

    boolean isBumpersPressed = false;
    boolean isAPressed = false;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, this);

        waitForStart();

        while(!isStopRequested()) {

            carouselSpinnerPower = gamepad2.left_stick_y;
            intakePower = gamepad2.right_stick_y;

            if(isAPressed && !gamepad1.a){
                isAPressed = false;
            }

            leftDrivePower = gamepad1.left_stick_y;
            rightDrivePower = gamepad1.right_stick_y;

            if(!gamepad1.right_bumper && !gamepad1.left_bumper) {
                isBumpersPressed = false;
            }

            if (gamepad1.right_bumper && !isBumpersPressed && powerMult < 10) {
                powerMult += 1;
                isBumpersPressed = true;
            }

            if (gamepad1.left_bumper && !isBumpersPressed && powerMult > 1) {
                powerMult -= 1;
                isBumpersPressed = true;
            }

            leftDrivePower *= powerMult/10;
            rightDrivePower *= powerMult/10;

            if(gamepad1.a && !isAPressed){
                tempPower = rightDrivePower;
                rightDrivePower = -leftDrivePower;
                leftDrivePower = -tempPower;
                isAPressed = true;
            }

            robot.leftDrive.setPower(leftDrivePower);
            robot.rightDrive.setPower(rightDrivePower);

            telemetry.addData("powerMult: ", powerMult);
            telemetry.update();
            idle();

        }
    }
}