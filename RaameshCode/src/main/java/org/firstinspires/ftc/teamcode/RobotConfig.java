package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotConfig {

    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public CRServo crservo1;
    public Servo servo1;

    public void init(HardwareMap hardwareMap) {
        leftDrive = hardwareMap.get(DcMotor.class, "LM");
        rightDrive = hardwareMap.dcMotor.get("RM");
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        crservo1 = hardwareMap.crservo.get("servocr");

        servo1 = hardwareMap.servo.get("servonorm");
    }
}
