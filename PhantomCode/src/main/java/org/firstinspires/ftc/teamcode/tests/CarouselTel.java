package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.util.RobotConfig;

@Disabled
@TeleOp
public class CarouselTel extends LinearOpMode {

    RobotConfig robot = new RobotConfig();
    double carouselSpinnerPower;
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor caer;
    DcMotor intake;
    double intakePower;
    double leftDrivePower;
    double rightDrivePower;
    double tempPower;
    float powerMult = 5;
    boolean isDpadDownPressed = false;
    boolean isDpadUpPressed = false;
    boolean isBumpersPressed = false;
    boolean isAPressed = false;

    @Override
    public void runOpMode() throws InterruptedException {
//        robot.init(hardwareMap, this);
        leftDrive = hardwareMap.dcMotor.get("leftdrive");
        rightDrive = hardwareMap.dcMotor.get("rightdrive");
        caer = hardwareMap.dcMotor.get("caer");

        waitForStart();

        while (!isStopRequested()) {

            carouselSpinnerPower = gamepad2.left_stick_y;
            intakePower = gamepad2.right_stick_y;



            if (isAPressed && !gamepad1.a) {
                isAPressed = false;
            }


            if (gamepad1.a && !isAPressed) {
                tempPower = rightDrivePower;
                rightDrivePower = -leftDrivePower;
                leftDrivePower = -tempPower;
                isAPressed = true;
            }

            leftDrivePower = gamepad1.left_stick_y;
            rightDrivePower = gamepad1.right_stick_y;

            if (!gamepad1.right_bumper && !gamepad1.left_bumper) {
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


            leftDrivePower *= powerMult / 10;
            rightDrivePower *= powerMult / 10;
            leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);


            leftDrive.setPower(leftDrivePower);
            rightDrive.setPower(rightDrivePower);
            caer.setPower(carouselSpinnerPower);

            telemetry.addData("powerMult: ", powerMult);
            telemetry.update();
            idle();

        }
    }

}
