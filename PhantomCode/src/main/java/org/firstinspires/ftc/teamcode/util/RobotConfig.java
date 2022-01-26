package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.util.PhantomSecretStuff.vision.easyopencv.javaPipelines.PipelineMkTwo;
import org.firstinspires.ftc.teamcode.util.enums.initArgs;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
//import org.openftc.easyopencv.OpenCvWebcam;


public class   RobotConfig {

    public boolean isInit = false;

    HardwareMap hwMap;
    OpMode ln;

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

    public CRServo leftIntakeSpinnerMotor, rightIntakeSpinnerMotor;
    public Servo intakeRotationServo;

    public OpenCvWebcam cvWebcam;


    public void init(HardwareMap hwMap, LinearOpMode ln) {
        ln.telemetry.addLine("Initializing Robot...");
        ln.telemetry.update();

        rightDrive.setMotors("rightDrive1", "rightDrive2", ln.hardwareMap);
        leftDrive.setMotors("leftDrive1", "leftDrive2", ln.hardwareMap);

        bucket = hwMap.servo.get("bucket");

        slider = hwMap.dcMotor.get("sliderMotor");
        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        carousel = hwMap.get(DcMotor.class, "carousel");
        try {
            leftIntakeSpinnerMotor = hwMap.crservo.get("L_SUC_SRV");
            rightIntakeSpinnerMotor = hwMap.crservo.get("R_SUC_SRV");

            intakeRotationServo = hwMap.servo.get("INTK_ROT_SRV");

        } catch (Exception e) {

        }


        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



//        carousel = hwMap.dcMotor.get("carousel");
//        carousel.setDirection(DcMotorSimple.Direction.REVERSE);
//        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);



        slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        try {
            rightIntakeSpinnerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        } catch (Exception e) {

        }
        InitializeIMUParameters(hwMap, ln);

        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        cvWebcam = OpenCvCameraFactory.getInstance().createWebcam(hwMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        cvWebcam.setMillisecondsPermissionTimeout(2500);

        isInit = true;
        ln.telemetry.addLine("Initialization Complete!");
        ln.telemetry.update();
    }

    @Deprecated
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
        carousel.setDirection(DcMotorSimple.Direction.FORWARD);
        carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        try {
            leftIntakeSpinnerMotor = hwMap.crservo.get("L_SUC_SRV");
            rightIntakeSpinnerMotor = hwMap.crservo.get("R_SUC_SRV");
            rightIntakeSpinnerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            intakeRotationServo = hwMap.servo.get("INTK_ROT_SRV");
        } catch (Exception e) {

        }

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

    public static class MotorCluster {

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
}
