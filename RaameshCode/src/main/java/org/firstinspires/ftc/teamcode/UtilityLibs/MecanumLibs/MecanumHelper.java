package org.firstinspires.ftc.teamcode.UtilityLibs.MecanumLibs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.RobotConfig;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.TelemetryHelper;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.chassisType;

import java.util.ArrayList;

public class MecanumHelper {
    RobotConfig robot;
    TelemetryHelper console;
    LinearOpMode ln;

    public MecanumHelper(RobotConfig robot, TelemetryHelper console, LinearOpMode linearOpMode) {
        this.robot = robot;
        this.console = console;
        ln = linearOpMode;
    }

    ArrayList<Double> getTheoreticalRatios(double angle) {
        double ang = angle + 45;
        ang = -ang;
        if (ang < 0) {
            ang = 360 + ang;
        } else {
            if (ang > 360) {
                ang = ang - 360;
            }
        }
        double angleRad = Math.toRadians(ang);
        double ratioOneBR = Math.sin(angleRad);
        double ratioTwoFR = -1 * Math.cos(angleRad);
        ArrayList<Double> a = new ArrayList<>();
        a.add(ratioOneBR);
        a.add(ratioTwoFR);
        return(a);
    }

    double maximizer(ArrayList<Double> ratios, double power) {
        ratios.get(0);
        double greatestRatio;
        if (ratios.get(0) <= ratios.get(1)) {
            greatestRatio = ratios.get(1);
        } else {
            greatestRatio = ratios.get(0);
        }
        return 1/greatestRatio*power;
    }

    public void motorController(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        robot.frontLeft.setPower(frontLeftPower);
        robot.frontRight.setPower(frontRightPower);
        robot.backLeft.setPower(backLeftPower);
        robot.backRight.setPower(backRightPower);
    }

    public void driveWithoutImu (double angle, double power) {
        ArrayList<Double> ratios = getTheoreticalRatios(angle);
        double maximizerNumber = maximizer(ratios, power);
        double FRBL = ratios.get(1) * maximizerNumber;
        double FLBR = ratios.get(0) * maximizerNumber;
        motorController(FLBR,FRBL,FRBL,FLBR);
    }

    public void driveWithImu (double angle, double power) {
        stopThread();
        thread1 = new Threader(angle, power);
        thread1.start();
    }

    Threader thread1;

    public void stopThread () {
        threadStop = true;
        while (!isThreadStop) {
            ln.idle();
        }
        allowThreadToRun();
    }

    void allowThreadToRun() {
        threadStop = false;
    }

    double getCorrection(double targetAngle, double correctionMultiplier) {
        return targetAngle - robot.getAngle()/180 * correctionMultiplier;
    }

    boolean threadStop = false;

    boolean isThreadStop = true;

    public void stopMotors() {
        robot.stopMotors();
        stopThread();
    }

    class Threader extends Thread {

        double angle;
        double power;

        Threader (double angle, double power) {
            this.angle = angle;
            this.power = power;
        }

        @Override
        public void run() {
            isThreadStop = false;
            while (!ln.isStopRequested() && !threadStop) {
                ArrayList<Double> ratios = getTheoreticalRatios(angle);
                double maximizerNumber = maximizer(ratios, power);
                double FRBL = ratios.get(1) * maximizerNumber;
                double FLBR = ratios.get(0) * maximizerNumber;
                motorController(FLBR - getCorrection(0, 1),FRBL + getCorrection(0, 1),FRBL - getCorrection(0, 1),FLBR + getCorrection(0, 1));
                yield();
            }
            isThreadStop = true;
        }
    }

}
