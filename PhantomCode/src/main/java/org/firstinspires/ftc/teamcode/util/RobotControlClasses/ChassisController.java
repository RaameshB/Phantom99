package org.firstinspires.ftc.teamcode.util.RobotControlClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.RobotConfig;

public class ChassisController {

    RobotConfig.MotorCluster leftMotors;
    RobotConfig.MotorCluster rightMotors;
    RobotConfig robot;
    LinearOpMode linearOpMode;
    ElapsedTime runtime = new ElapsedTime();

    public ChassisController(RobotConfig robot, LinearOpMode linearOpMode) {
        this.robot = robot;
        leftMotors = this.robot.leftDrive;
        rightMotors = this.robot.rightDrive;
        this.linearOpMode = linearOpMode;
    }

    ElapsedTime stopwatch = new ElapsedTime();

    static final double COUNTS_PER_REVOLUTION = 1120;
    static final double SPROCKET_REDUCTION = 1.5;
    static final double GEAR_REDUCTION = 0.5;
    static final double WHEEL_DIAMETER_INCHES = 4;
    static final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * SPROCKET_REDUCTION * GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        leftMotors.setZeroPowerBehavior(behavior);
        rightMotors.setZeroPowerBehavior(behavior);
    }

    public void setMode(DcMotor.RunMode runMode) {
        leftMotors.setMode(runMode);
        rightMotors.setMode(runMode);
    }

    public void setPower(double power) {
        leftMotors.setPower(power);
        rightMotors.setPower(power);
    }

    public void setTargetPosition(int targetPosition) {
        leftMotors.setTargetPosition(targetPosition);
        rightMotors.setTargetPosition(targetPosition);
    }

    public void resetEncoders() {
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (linearOpMode.opModeIsActive()) {

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
            while (linearOpMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftDrive.motor1.isBusy() && robot.rightDrive.motor1.isBusy())) {

                // Display it for the driver.
                linearOpMode.telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                linearOpMode.telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.motor1.getCurrentPosition(),
                        robot.rightDrive.motor1.getCurrentPosition());
                linearOpMode.telemetry.update();
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

    public double getAvgPosition() {
        return (double) (leftMotors.getAvgPosition() + rightMotors.getAvgPosition())/2;
    }

    public boolean isBusy() {
        return leftMotors.isBusy() || rightMotors.isBusy();
    }

//    public void encoderDrive(double power, double distInches, double timeoutS) {
//        if (linearOpMode.isStopRequested()) {
//            return;
//        }
//        //        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        //        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        int pos = (int) (getAvgPosition() + (distInches * COUNTS_PER_INCH));
//        setTargetPosition(pos);
//        stopwatch.reset();
//        setPower(power);
//        while(!linearOpMode.isStopRequested() && isBusy() && stopwatch.seconds() < timeoutS) {
//            linearOpMode.telemetry.addData("TargetPos: ", pos);
//            linearOpMode.telemetry.addData("AveragePosition: ", getAvgPosition());
//        }
//        setPower(0);
//    }
}
