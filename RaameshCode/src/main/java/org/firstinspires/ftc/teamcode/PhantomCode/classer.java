package org.firstinspires.ftc.teamcode.PhantomCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.chassisType;

@Autonomous(name = "tester")
public class classer extends PhantomOpMode {
    @Override
    public void opModeCode() throws InterruptedException {
        setChassis(chassisType.MECANUM);
        init();
        waitForStart();
        chassis.motorController(0.5,0.5,0.5,0.5);
        sleep(1000);
        robot.stopMotors();
        sleep(1000);
        chassis.motorController(0.5,-0.5,-0.5,0.5);
        sleep(1000);
        robot.stopMotors();
        sleep(1000);
        chassis.motorController(-0.5,-0.5,-0.5,-0.5);
        sleep(1000);
        robot.stopMotors();
        sleep(1000);
        chassis.motorController(-0.5,0.5,0.5,-0.5);
        sleep(1000);
        robot.stopMotors();
    }
}
