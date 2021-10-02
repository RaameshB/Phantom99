package org.firstinspires.ftc.teamcode.PhantomCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.PhantomOpMode;
import org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses.chassisType;

@Autonomous (name = "MecanumTestClass")
public class MecanumTestClass extends PhantomOpMode {
    @Override
    public void opModeCode() throws InterruptedException {
        setChassis(chassisType.MECANUM);

        robot.init();

        waitForStart();

        chassis.driveWithImu(0, 0.5);

        sleep(2000);

        chassis.driveWithImu(90, 0.5);

        sleep(2000);

        chassis.driveWithImu(180, 0.5);

        sleep(2000);

        chassis.driveWithImu(-90, 0.5);

        sleep(2000);

        chassis.stopMotors();

    }
}