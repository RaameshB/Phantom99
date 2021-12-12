package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ChassisController {

    MotorCluster leftMotors;
    MotorCluster rightMotors;
    LinearOpMode linearOpMode;

    public ChassisController(MotorCluster leftMotors, MotorCluster rightMotors, LinearOpMode linearOpMode) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;
        this.linearOpMode = linearOpMode;
    }

    ElapsedTime stopwatch = new ElapsedTime();

    static final double COUNTS_PER_REVOLUTION = 1120;
    static final double SPROCKET_REDUCTION = 1.5;
    static final double GEAR_REDUCTION = 0.5;
    static final double WHEEL_DIAMETER_INCHES = 4;
    static final double COUNTS_PER_INCH = (COUNTS_PER_REVOLUTION * SPROCKET_REDUCTION * GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    //sets motor zeroPowerBehavior
    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        leftMotors.setZeroPowerBehavior(behavior);
        rightMotors.setZeroPowerBehavior(behavior);
    }
    //the mode the motors will run in
    public void setMode(DcMotor.RunMode runMode) {
        leftMotors.setMode(runMode);
        rightMotors.setMode(runMode);
    }
    //the motors' power
    public void setPower(double power) {
        leftMotors.setPower(power);
        rightMotors.setPower(power);
    }
    //the target position of the motors
    public void setTargetPosition(int targetPosition) {
        leftMotors.setTargetPosition(targetPosition);
        rightMotors.setTargetPosition(targetPosition);
    }
    //the average position of the motors' encoders.
    public double getAvgPosition() {
        return (double) (leftMotors.getAvgPosition() + rightMotors.getAvgPosition())/2;
    }
    //says if the motor is doing something actively
    public boolean isBusy() {
        return leftMotors.isBusy() || rightMotors.isBusy();
    }

    //TODO: Finish this lol
    public void encoderDrive(float accelPercent, double maxPower, double distInches) {
        if (linearOpMode.isStopRequested()) {
            return;
        }

        //resets the encoder, then tells the motors to drive using them
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //gets the average position of the motors and tell them to drive a certain amount of ticks forward.
        setTargetPosition((int) (getAvgPosition() + (distInches * COUNTS_PER_INCH)));
        stopwatch.reset();
//        setPower();
    }
}
