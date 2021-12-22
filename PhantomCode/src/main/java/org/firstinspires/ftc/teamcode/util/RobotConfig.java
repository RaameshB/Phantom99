package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.openftc.easyopencv.OpenCvWebcam;


public class   RobotConfig {

    boolean isInit = false;

    HardwareMap hwMap;
    LinearOpMode ln;

    public RobotConfig () {

    }


    public  MotorCluster leftDrive = new MotorCluster();
    public MotorCluster rightDrive =  new MotorCluster();

//    public OpenCvWebcam webcam;

    public VuforiaLocalizer vuforia;

    public BNO055IMU imu;

    public DcMotor carousel;
    public Servo bucket;

    public DcMotor slider;

    public void init(HardwareMap hwMap, LinearOpMode ln) {
        ln.telemetry.addLine("Initializing Robot...");
        ln.telemetry.update();

        rightDrive.setMotors("rightDrive1", "rightDrive2", ln.hardwareMap);
        leftDrive.setMotors("leftDrive1", "leftDrive2", ln.hardwareMap);

        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);


        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        isInit = true;

        carousel = hwMap.dcMotor.get("carousel");
        carousel.setDirection(DcMotorSimple.Direction.REVERSE);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bucket = hwMap.servo.get("bucket");

        slider = hwMap.dcMotor.get("sliderMotor");

        ln.telemetry.addLine("Initialization Complete!");
        ln.telemetry.update();
    }

    public void init(HardwareMap hwMap, LinearOpMode ln, initArgs args) {
        ln.telemetry.addLine("Initializing Robot...");
        ln.telemetry.update();

        rightDrive.setMotors("rightDrive1", "rightDrive2", ln.hardwareMap);
        leftDrive.setMotors("leftDrive1", "leftDrive2", ln.hardwareMap);

        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive.setDirection(DcMotorSimple.Direction.FORWARD);


        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        carousel = hwMap.dcMotor.get("carousel");
        carousel.setDirection(DcMotorSimple.Direction.REVERSE);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bucket = hwMap.servo.get("bucket");

        if (args == initArgs.CALIBRATE_IMU) {
            InitializeIMUParameters(hwMap, ln);
        }

        isInit = true;

        ln.telemetry.addLine("Initialization Complete!");
        ln.telemetry.update();
    }

    void InitializeIMUParameters(HardwareMap hardwareMap, LinearOpMode ln){

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        ln.telemetry.addLine("Initializing Robot...");
        ln.telemetry.addLine("  Calibrating IMU...");
        ln.telemetry.update();
        while(!ln.isStopRequested() && imu.isSystemCalibrated()) {
            ln.sleep(50);
            ln.idle();
        }
        ln.telemetry.addLine("Initializing Robot...");
        ln.telemetry.addLine("  IMU Calibration Complete!");
        ln.telemetry.update();
    }

}
