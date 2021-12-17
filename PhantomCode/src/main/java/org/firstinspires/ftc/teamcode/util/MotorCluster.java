package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorCluster {

    HardwareMap hardwareMap;
    public DcMotor motor1;
    public DcMotor motor2;

    public void setMotors(String motor1Name, String motor2Name, HardwareMap aHwMap) {
        hardwareMap = aHwMap;
        motor1 = hardwareMap.get(DcMotor.class, motor1Name);
        motor2 = hardwareMap.get(DcMotor.class, motor2Name);
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        motor1.setDirection(direction);
        motor2.setDirection(direction);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {

        motor1.setZeroPowerBehavior(zeroPowerBehavior);
        motor2.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void setMode(DcMotor.RunMode runMode){
        motor1.setMode(runMode);
        motor2.setMode(runMode);
    }

    public void setPower(double power){
        motor1.setPower(power);
        motor2.setPower(power);
    }

    public void setTargetPosition(int targetPosition) {
        motor1.setTargetPosition(targetPosition);
        motor2.setTargetPosition(targetPosition);
    }

    public boolean isBusy() {
        return motor1.isBusy() || motor2.isBusy();
    }

    public double getAvgPosition() {
        return (double) (motor1.getCurrentPosition() + motor2.getCurrentPosition())/2;
    }

}
