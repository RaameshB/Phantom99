package org.firstinspires.ftc.teamcode.UtilityLibs;

import android.os.Environment;
import android.view.LayoutInflater;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.openftc.easyopencv.OpenCvWebcam;


public class RobotConfig {

    boolean isInit = false;



    HardwareMap hwMap;
    LinearOpMode ln;

    public RobotConfig () {

    }

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public MotorCluster rightDrive;

    public DcMotor encoderY1;
    public DcMotor encoderY2;
    public DcMotor encoderX;

    public OpenCvWebcam webcam;

    public VuforiaLocalizer vuforia;

    public BNO055IMU imu;

    public void init(HardwareMap hwMap, LinearOpMode ln) {
        ln.telemetry.addLine("Initializing Robot...");
        ln.telemetry.update();

        rightDrive.setMotors("front_right", "back_right", hwMap);
        frontRight = hwMap.get(DcMotor.class, "front_right");
        frontLeft  = hwMap.get(DcMotor.class, "front_left");
        backRight  = hwMap.get(DcMotor.class, "back_right");
        backLeft   = hwMap.get(DcMotor.class, "back_left");

        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight .setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft .setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft  .setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        encoderX   = hwMap.get(DcMotor.class, "back_left");
        encoderY1  = hwMap.get(DcMotor.class, "front_left");
        encoderY2  = hwMap.get(DcMotor.class, "front_right");

        encoderY1.setDirection(DcMotorSimple.Direction.REVERSE);

        encoderX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderY1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderY2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        encoderX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        encoderY1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        encoderY2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        isInit = true;

        ln.telemetry.addLine("Initialization Complete!");
    }

    public void init(HardwareMap hwMap, LinearOpMode ln, initArgs args) {
        ln.telemetry.addLine("Initializing Robot...");
        ln.telemetry.update();

        frontRight = hwMap.get(DcMotor.class, "front_right");
        frontLeft  = hwMap.get(DcMotor.class, "front_left");
        backRight  = hwMap.get(DcMotor.class, "back_right");
        backLeft   = hwMap.get(DcMotor.class, "back_left");

        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight .setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeft .setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft  .setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        encoderX   = hwMap.get(DcMotor.class, "back_left");
        encoderY1  = hwMap.get(DcMotor.class, "front_left");
        encoderY2  = hwMap.get(DcMotor.class, "front_right");

        encoderY1.setDirection(DcMotorSimple.Direction.REVERSE);

        encoderX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderY1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderY2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        encoderX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        encoderY1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        encoderY2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        isInit = true;

        if (args != initArgs.SKIP_IMU_CALIBRATION) {
            InitializeIMUParameters(hwMap, ln);
        }

        ln.telemetry.addLine("Initialization Complete!");
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
