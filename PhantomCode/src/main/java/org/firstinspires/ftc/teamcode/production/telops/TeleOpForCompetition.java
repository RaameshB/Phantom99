package org.firstinspires.ftc.teamcode.production.telops;
 
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.RobotConfig;


@TeleOp(group = "final")
public class TeleOpForCompetition extends LinearOpMode {

    RobotConfig robot = new RobotConfig();

    double leftDrivePower;
    double rightDrivePower;
    double tempPower;
    double carouselSpinnerPower;
    double intakePower;

    
//    DcMotor carousel;
//    DcMotor intake;

//    Servo basketDump;
    float powerMult = 10;

    boolean isBumpersPressed = false;
    boolean isIntakeUp = true;
    boolean isTwoLeftBumpPressed = false;
    boolean bucketOverride = false;


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, this);


        waitForStart();

        while(!isStopRequested()) {

            // Define Power For the Chassis By Using the Sticks on Gamepad 1
            leftDrivePower = gamepad1.left_stick_y;
            rightDrivePower = gamepad1.right_stick_y;

            // A Power multiplier for more fine control
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
            telemetry.addData("Power: ", String.valueOf(powerMult * 10) + "%");

            // Set the Power to the motors
            robot.leftDrive.setPower(leftDrivePower);
            robot.rightDrive.setPower(rightDrivePower);


            // Carousel Code
            if(gamepad1.right_trigger > 0.5) {
                robot.carousel.setPower(-1.0);
            } else {
                robot.carousel.setPower(0);
            }

            // Intake Lift Code
            try {
                if (!isTwoLeftBumpPressed && gamepad2.left_bumper) {
                    if (isIntakeUp) {
                        robot.intakeRotationServo.setPosition(0.15);
                    } else {
                        robot.intakeRotationServo.setPosition(0.8);
                    }
                    isIntakeUp = !isIntakeUp;
                    isTwoLeftBumpPressed = true;
                } else if (!gamepad2.left_bumper) {
                    isTwoLeftBumpPressed = false;
                }
            } catch (Exception e) {

            }
            // Intake Spinner Servo Code
            if (gamepad2.dpad_up) {
                intakePower = 1;
                robot.bucket.setPosition(0);
                bucketOverride = true;
            } else if (gamepad2.dpad_down) {
                intakePower = -1;
                robot.bucket.setPosition(0);
                bucketOverride = true;
            } else {
                intakePower = 0;
                bucketOverride = false;
            }
            try {
                robot.rightIntakeSpinnerMotor.setPower(intakePower);
                robot.leftIntakeSpinnerMotor.setPower(intakePower);
            } catch (Exception e) {

            }
            // Slider Code
            if (gamepad2.a) {
                robot.slider.setPower(0.45);
            } else if (gamepad2.y) {
                robot.slider.setPower(-0.45);
            } else {
                robot.slider.setPower(gamepad2.right_stick_y * 0.60);
            }

            // Dumper Code
//            robot.bucket.setPosition(gamepad2.right_trigger);
            if (gamepad2.right_bumper) {
                robot.bucket.setPosition(1.0);
            } else if (!bucketOverride) {
                robot.bucket.setPosition(0.25);
            }

            telemetry.update();
            idle();
        }
    }
}