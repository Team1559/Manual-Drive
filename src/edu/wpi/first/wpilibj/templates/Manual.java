/*=======================================================

 This code assumes that the drive system uses talons, but can
 be easily modified for jaguars, victors, whatever. 

 =======================================================*/
package edu.wpi.first.wpilibj.templates;

//import edu.wpi.first.wpilibj.Compressor; - Compressor cart
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Manual extends IterativeRobot {

    //!\\ Should be moved into Wiring class!
    public final int LEFT1 = 10; //maybe this will work (?)
//    public final int LEFT2 = 1;
    public final int RIGHT1 = 1; //see above
//    public final int RIGHT2 = 4;

    //variables
    int gear;
    int teeth1;
    int teeth2;
    double ratio1;
    double ratio2;
    int maxRPM;
//    boolean parkingBrake = false;

    //2 left motors
    Talon l1;
//    Talon l2;

    //2 right motors
    Talon r1;
//    Talon r2;

    //controls
    Joystick joy;
//    Encoder speed;

    //Pneumatics to control shifting
//    Compressor comp;
    Solenoid shift1;
    Solenoid shift2;

    //driving
    RobotDrive rd;

    public void robotInit() {

        l1 = new Talon(LEFT1);
//        l2 = new Talon(LEFT2);
        r1 = new Talon(RIGHT1);
//        r2 = new Talon(RIGHT2);
        joy = new Joystick(1);
        //!\\ GET Correct Values for compressor - (Pressure Switch channel, Compressor Relay Channel)
//        comp = new Compressor(1, 2);
        gear = 1;
        teeth1 = 50;
        teeth2 = 100;
        ratio1 = 1.05;
        ratio2 = 1.99;
        maxRPM = 3000;
        rd = new RobotDrive(l1, r1);
//        rd.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
//        rd.setInvertedMotor(RobotDrive.MotorType.kFrontRight, false);
        shift1 = new Solenoid(1);
        shift2 = new Solenoid(2);
    }

    public void disabledInit(){
        
    }
    
    public void disabled() {

//        comp.stop();
        l1.stopMotor();
//        l2.stopMotor();
        r1.stopMotor();
//        r2.stopMotor();
        gear1();

    }

    public void autonomousInit() {

    }

    public void autonomousPeriodic() {

    }

    public void teleopInit() {
        gear1();
    }

    public void teleopPeriodic() {
        rd.arcadeDrive(joy);
        if (joy.getRawButton(4)){
            gear1();
        }
        if (joy.getRawButton(5)){
            gear2();
        }
        
//        System.out.println("RIGHT: " + r1.getSpeed());
//        System.out.println("LEFT" + l1.getSpeed());
    }

    public void testInit() {

    }

    public void testPeriodic() {

    }

    //my own methods
    public double getThrottle(Joystick j) {
        return (double) j.getRawAxis(1) + j.getRawAxis(2);
    }

    public void gear1() {
        shift1.set(false);
        shift2.set(true);
        System.out.println("GEAR 1");
    }

    public void gear2() {
        shift1.set(true);
        shift2.set(false);
        System.out.println("GEAR 2");
    }

    public double getSpeed() {

        return (r1.getSpeed() + l1.getSpeed()) / 2;

    }
}
