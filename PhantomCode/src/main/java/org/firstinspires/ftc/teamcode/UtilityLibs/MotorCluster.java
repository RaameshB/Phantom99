package org.firstinspires.ftc.teamcode.UtilityLibs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

public class MotorCluster {

    HardwareMap hardwareMap;
    DcMotor motor1;
    DcMotor motor2;

    public void setMotors(String motor1Name, String motor2Name, HardwareMap aHwMap) {
        hardwareMap = aHwMap;
        hardwareMap.get(DcMotor.class, motor1Name);
        hardwareMap.get(DcMotor.class, motor2Name);
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


}
