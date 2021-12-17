package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.source.doctree.StartElementTree;

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

    public double getAvgPosition() {
        return (double) (leftMotors.getAvgPosition() + rightMotors.getAvgPosition())/2;
    }

    public boolean isBusy() {
        return leftMotors.isBusy() || rightMotors.isBusy();
    }

    //TODO: Finish this lol
    public void encoderDrive(double power, double distInches, double timeoutS) {
        if (linearOpMode.isStopRequested()) {
            return;
        }
//        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int pos = (int) (getAvgPosition() + (distInches * COUNTS_PER_INCH));
        setTargetPosition(pos);
        stopwatch.reset();
        setPower(power);
        while(!linearOpMode.isStopRequested() && isBusy() && stopwatch.seconds() < timeoutS) {
            linearOpMode.telemetry.addData("TargetPos: ", pos);
            linearOpMode.telemetry.addData("AveragePosition: ", getAvgPosition());
        }
        setPower(0);
    }
}
