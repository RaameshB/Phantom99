package org.firstinspires.ftc.teamcode.util.HelperClasses;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

public class RobotConfig {

    boolean isInit = false;

    HardwareMap hwMap;
    LinearOpMode ln;
    TelemetryHelper telemetry;
    public chassisType robotType = chassisType.PUSHBOT;

    public RobotConfig (HardwareMap ahwMap, LinearOpMode lnOpMode, TelemetryHelper tel){
        hwMap = ahwMap;
        ln = lnOpMode;
        telemetry = tel;
    }

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor leftMotor;
    public DcMotor rightMotor;
    public OpenCvWebcam webcam;

    public BNO055IMU imu;

    public void init() {

        telemetry.enableTelemetry();

        autoMotorFinder();

        setMotorDirection(DcMotorSimple.Direction.FORWARD);

        setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        stopMotors();

        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hwMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);

        isInit = true;
    }

    void autoMotorFinder() {
        if (robotType == chassisType.PUSHBOT) {
            findMotors("RM", "LM");
        } else {
            findMotors("front_right","front_left","back_right","back_left");
        }
    }

    public void findMotors(String frontRightName, String frontLeftName, String backRightName, String backLeftName) {
        frontLeft = hwMap.get(DcMotor.class, frontRightName);
        frontRight = hwMap.get(DcMotor.class, frontLeftName);
        backLeft = hwMap.get(DcMotor.class, backRightName);
        backRight = hwMap.get(DcMotor.class, backLeftName);

    }
    public void findMotors(String rightName, String leftName) {
        rightMotor = hwMap.get(DcMotor.class, rightName);
        leftMotor = hwMap.get(DcMotor.class, leftName);
    }

    public void stopMotors() {
        switch (robotType) {
            case PUSHBOT:
                rightMotor.setPower(0);
                leftMotor.setPower(0);
                return;
        }
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }

    public void setMotorDirection(DcMotorSimple.Direction direction) {
        switch (robotType) {
            case PUSHBOT:
                switch (direction) {
                    case FORWARD:
                        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    case REVERSE:
                        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                return;
        }
        switch (direction) {
            default:
                throw new IllegalArgumentException();
            case FORWARD:
                frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
                frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                backRight.setDirection(DcMotorSimple.Direction.FORWARD);
                backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            case REVERSE:
                frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
                backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }

    public void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior behaviour) {
        switch (robotType) {
            case PUSHBOT:
                rightMotor.setZeroPowerBehavior(behaviour);
                leftMotor.setZeroPowerBehavior(behaviour);
        }
        frontRight.setZeroPowerBehavior(behaviour);
        frontLeft.setZeroPowerBehavior(behaviour);
        backRight.setZeroPowerBehavior(behaviour);
        frontRight.setZeroPowerBehavior(behaviour);
    }

    public Orientation angles;

    boolean stopper = false;
    boolean stopped = true;

    double degX;
    double degY;
    double degZ;

    protected class Helpering extends Thread {
        @Override
        public void run() {
            stopped = false;
            while (!ln.isStopRequested() && !stopper) {
                telemetry.modData("Z: ", angles.firstAngle);
                telemetry.modData("Y: ", angles.secondAngle);
                telemetry.modData("X: ", angles.thirdAngle);
                degZ = angles.firstAngle;
                degY = angles.secondAngle;
                degX = angles.thirdAngle;
            }
            stopped = true;
        }
    }

    Helpering thread = new Helpering();

    public void calibrate() {
        imu = hwMap.get(BNO055IMU.class, "imu");

        ln.telemetry.addLine("got to point 1.1");
        ln.telemetry.update();

        BNO055IMU.Parameters para = new BNO055IMU.Parameters();
        para.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        ln.telemetry.addLine("got to point 1.2");
        ln.telemetry.update();

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        ln.telemetry.addLine("got to point 1.3");
        ln.telemetry.update();

        imu.initialize(para);

        telemetry.addLine("Gyro Calibrating...");

        ln.telemetry.addLine("got to point 1.4");
        ln.telemetry.update();

        while (!imu.isGyroCalibrated());

        ln.telemetry.addLine("got to point 1.5");
        ln.telemetry.update();

        telemetry.modLine("Gyro Calibrating...", "Gyro Calibration Complete! This message will disappear in 2 seconds.");

        ln.telemetry.addLine("got to point 1.6");
        ln.telemetry.update();

        ln.sleep(2000);

        telemetry.removeLine("Gyro Calibration Complete! This message will disappear in 2 seconds.");

        ln.telemetry.addLine("got to point 1.7");
        ln.telemetry.update();

    }

    public Orientation             lastAngles = new Orientation();
    public double globalAngle;

    public double getAngle() {
        // We experimentally determined the Y axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180) {
            deltaAngle += 360;
        }
        else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }
        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    public void enableImuTelemetry() {
        telemetry.addLine("Angles:");
        telemetry.addData("X: ", angles.firstAngle);
        telemetry.addData("Y: ", angles.secondAngle);
        telemetry.addData("Z: ", angles.thirdAngle);
        stopper = false;
        thread.start();
    }

    public void disableTelemetry() {
        stopper = true;
        while (!stopped);
        telemetry.removeLine("Angles:");
        telemetry.removeData("X: ");
        telemetry.removeData("Y: ");
        telemetry.removeData("Z: ");
    }

}
