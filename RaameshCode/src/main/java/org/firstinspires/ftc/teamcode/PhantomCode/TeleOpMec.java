package org.firstinspires.ftc.teamcode.PhantomCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.RobotConfig;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.TelemetryHelper;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.chassisType;

@TeleOp
public class TeleOpMec extends LinearOpMode {


    double side;
//
//    double newX;
//    double newY;

    int powerMultiplier = 5;

    double powerMultiplerDouble;

    double frontLeft;
    double backLeft;
    double backRight;
    double frontRight;



    boolean dpadPressed = false;

    @Override
    public void runOpMode() throws InterruptedException {

        TelemetryHelper telemetryHelper = new TelemetryHelper(this);
        RobotConfig robot = new RobotConfig();

        robot.initREEEE(hardwareMap);
        waitForStart();



        while(!isStopRequested()) {

            side = (gamepad1.left_stick_x + gamepad1.right_stick_x) / -2;
            frontRight = -gamepad1.left_stick_y + side;
            backRight = -gamepad1.left_stick_y - side;
            frontLeft = -gamepad1.right_stick_y - side;
            backLeft = -gamepad1.right_stick_y + side;

            telemetry.addData("PowerMult: ", powerMultiplier);
            telemetry.addData("frontRight: ", frontRight);
            telemetry.addData("frontLeft: ", frontLeft);
            telemetry.addData("backLeft: ", backLeft);
            telemetry.addData("backRight: ", backRight);
            telemetry.update();

            if (!dpadPressed) {
                if (gamepad1.right_bumper && powerMultiplier < 10) {
                    powerMultiplier += 1;
                    dpadPressed = true;
                }
                if (gamepad1.left_bumper && powerMultiplier > 0) {
                    powerMultiplier -= 1;
                    dpadPressed = true;
                }
            } else {
                if (!(gamepad1.right_bumper || gamepad1.left_bumper)) {
                    dpadPressed = false;
                }
            }

            powerMultiplerDouble = powerMultiplier;
//
//            leftPower = gamepad1.left_stick_y * (powerMultiplerDouble/10);
//            rightPower = gamepad1.right_stick_y * (powerMultiplerDouble/10);


            robot.frontLeft.setPower(frontLeft *(powerMultiplerDouble/10));
            robot.backLeft.setPower(backLeft * (powerMultiplerDouble/10));

            robot.frontRight.setPower(frontLeft * (powerMultiplerDouble/10));
            robot.backRight.setPower(backRight * (powerMultiplerDouble/10));

//            console.modData("Power Mult: ", powerMultiplier);

            idle();
        }
    }
}
