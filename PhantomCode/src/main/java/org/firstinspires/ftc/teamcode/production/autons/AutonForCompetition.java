package org.firstinspires.ftc.teamcode.production.autons;

import  com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.ChassisController;
import org.firstinspires.ftc.teamcode.util.ImuController;
import org.firstinspires.ftc.teamcode.util.RobotConfig;
import org.firstinspires.ftc.teamcode.util.initArgs;

@Autonomous(group = "final")
public class AutonForCompetition extends LinearOpMode {

    RobotConfig robot = new RobotConfig();

    static final double COUNTS_PER_REVOLUTION = 1120;
    static final double SPROCKET_REDUCTION = 1.5;
    static final double GEAR_REDUCTION = 0.5;
fgc    static final double WHEEL_DIAMETER_INCHES = 4;
    static final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * SPROCKET_REDUCTION * GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    DcMotor carousel;
    Servo bucket;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        carousel = hardwareMap.dcMotor.get("carousel");
        carousel.setDirection(DcMotorSimple.Direction.REVERSE);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bucket = hardwareMap.servo.get("bucket");

        robot.init(hardwareMap, this, initArgs.CALIBRATE_IMU);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ImuController imuController = new ImuController(robot,this);
//        ChassisController chassis = new ChassisController(robot.leftDrive, robot.rightDrive, this);
        waitForStart();
        encoderDrive(0.5, -8, -8, 2);
        imuController.rotate(-35, 0.5);
        encoderDrive(0.4, 11.7, 11.7, 5);
        encoderDrive(0.2, 2, 2, 3);
        sleep(1000);
        carousel.setPower(1);
        sleep(2500);
        carousel.setPower(0);
        sleep(500);
        encoderDrive(0.5, -38.3, -38.3, 5);
//        sleep(1000);
        bucket.setPosition(1.0);
        sleep(2000);
        encoderDrive(0.5, 10, 10, 5);
        imuController.rotate(-30, 0.5);
        encoderDrive(0.75, -75, -75, 10);
        bucket.setPosition(0.5);

//        encoderDrive(0.75 , -37, -37 , 10);
//        encoderDrive(0.75 , 5, 5 , 5);
//        imuController.rotate(-60, 0.5);
//        encoderDrive(0.75 , -35, -37 , 10);
//        imuController.rotate(30, 0.5);
//        encoderDrive(0.75 , -35, -37 , 10);


        //chassis.encoderDrive(-0.75, -37, 3);2
        //chassis.encoderDrive(0.75, 5, 3);
        //imuController.rotate(-60, 0.5);
        //chassis.encoderDrive(-0.75, -37, 3);
        //imuController.rotate(30, 0.5);

    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftDrive.motor1.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightDrive.motor1.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.leftDrive.setTargetPosition(newLeftTarget);
            robot.rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftDrive.setPower(Math.abs(speed));
            robot.rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftDrive.motor1.isBusy() && robot.rightDrive.motor1.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.motor1.getCurrentPosition(),
                        robot.rightDrive.motor1.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Turn off RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}
