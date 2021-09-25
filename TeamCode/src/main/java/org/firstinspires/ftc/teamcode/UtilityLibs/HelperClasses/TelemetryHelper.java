package org.firstinspires.ftc.teamcode.UtilityLibs.HelperClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;

import java.util.ArrayList;

public class TelemetryHelper {



    LinearOpMode ln;

    int length;

    ArrayList<String> captionsAndLines = new ArrayList<>();
    ArrayList<Object> dataPts = new ArrayList<>();


    public TelemetryHelper (LinearOpMode lnOpMd) {
        ln = lnOpMd;
        thread.start();
    }

    boolean telemEnabled = false;

    helperThing thread = new helperThing();

    public void enableTelemetry() {
        telemEnabled = true;
    }

    int i = 0;

    public void addData(String caption, Object data) {
//        if (captionsAndLines.contains(caption)) {
//            throw new IllegalArgumentException();
//        }
        captionsAndLines.add(caption);
        dataPts.add(data);
    }

    public void addLine(String caption) {
//        if (!captionsAndLines.contains(caption)) {
//            throw new IllegalArgumentException();
//        }
        captionsAndLines.add(caption);
        dataPts.add(" ");
    }

    public void modData(String caption, Object data) {
//        if (!captionsAndLines.contains(caption)) {
//            throw new IllegalArgumentException();
//        }
        dataPts.set(captionsAndLines.indexOf(caption), data);
    }

    public void modData(String oldCaption, String newCaption, Object data) {
//        if (!captionsAndLines.contains(oldCaption)) {
//            throw new IllegalArgumentException();
//        }
        captionsAndLines.set(captionsAndLines.indexOf(oldCaption), newCaption);
        dataPts.set(captionsAndLines.indexOf(oldCaption), data);
    }

    public void modLine(String oldCaption, String newCaption) {
//        if (!captionsAndLines.contains(oldCaption)) {
//            throw new IllegalArgumentException();
//        }
        captionsAndLines.set(captionsAndLines.indexOf(oldCaption), newCaption);
    }

    public void removeData(String caption) {
//        if (!captionsAndLines.contains(caption)) {
//            throw new IllegalArgumentException();
//        }
        ln.telemetry.addLine("got to point 1.6.1");
        ln.telemetry.update();
        int a = captionsAndLines.indexOf(caption);
        captionsAndLines.remove(a);
        ln.telemetry.addLine("got to point 1.6.2");
        ln.telemetry.update();
        dataPts.remove(a);
        ln.telemetry.addLine("got to point 1.6.3");
        ln.telemetry.update();
    }

    public void removeLine(String caption) {
        removeData(caption);
    }

    protected class helperThing extends Thread{
        Telemetry tel = new TelemetryImpl(ln);
        @Override
        public void run() {
            while(!telemEnabled);
            while (!ln.isStopRequested()) {
                compose();
                tel.update();
            }
        }
        void compose() {
            length = captionsAndLines.size() - 1;
            while (i < length) {
                if(dataPts.get(i) != ""){
                    tel.addData(captionsAndLines.get(i), dataPts.get(i));
                } else {
                    tel.addLine(captionsAndLines.get(i));
                }
            }
        }
    }

}

