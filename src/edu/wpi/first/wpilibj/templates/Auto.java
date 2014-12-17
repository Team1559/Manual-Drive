/*=======================================================

    This code assumes thta the drive system uses talons, but can
    be easily modified for jaguars, victors, whatever. 

  =======================================================*/
package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Auto extends IterativeRobot {

    //!\\ Should be moved into Wiring class!
    public final int LEFT1 = 1;
    public final int LEFT2 = 2;
    public final int RIGHT1 = 3;
    public final int RIGHT2 = 4;
    
    //variables
    int gear;
    int teeth1;
    int teeth2;
    double ratio1;
    double ratio2;
    int maxRPM;
    boolean parkingBrake = false;
    
    //2 left motors
    Talon l1;
    Talon l2;
    
    //2 right motors
    Talon r1;
    Talon r2;
    
    //controls
    Joystick joy;
    Encoder speed;
    
    //Pneumatics to control shifting
    Compressor comp;
    Solenoid leftShift;
    
    //driving
    RobotDrive rd;
    public void robotInit() {

        l1 = new Talon(LEFT1);
        l2 = new Talon(LEFT2);
        r1 = new Talon(RIGHT1);
        r2 = new Talon(RIGHT2);
        joy = new Joystick(1);
        //!\\ GET Correct Values for compressor - (Pressure Switch channel, Compressor Relay Channel)
        comp = new Compressor(1,2);
        gear = 1;
        teeth1 = 50;
        teeth2 = 100;
        ratio1 = 1.05;
        ratio2 = 1.99;
        maxRPM = 3000;
        rd = new RobotDrive(l1, l2, r1, r2);
    }
    
    

    public void disabled(){
        
        comp.stop();
        l1.stopMotor();
        l2.stopMotor();
        r1.stopMotor();
        r2.stopMotor();
        gear1();
        
    }
    
    public void autonomousInit(){
        
    }
    
    public void autonomousPeriodic() {

    }

    public void teleopInit(){
        gear1();
    }
    
    public void teleopPeriodic() {
        
        if(!parkingBrake){
            rd.arcadeDrive(joy);
        }
        
        if(parkingBrake){ //PRESS 5+6 to toggle parking brake
            
            r1.stopMotor();
            r2.stopMotor();
            l1.stopMotor();
            l2.stopMotor();
            
        }
        
        if(joy.getRawButton(5) && joy.getRawButton(6)){
        
            parkingBrake = !parkingBrake;
            
        }
        
        switch(gear){

            case 1: 
                SmartDashboard.putBoolean("1st GEAR", true);
                SmartDashboard.putBoolean("2nd GEAR", false);
                
                //normal driving
                
                //do we need to shift?
                if((getSpeed() >= (maxRPM-50)) && !joy.getRawButton(2)){ //hold button 2 to push
                    gear2();
                    gear = 2;
                }
                break;
            case 2:
                SmartDashboard.putBoolean("2nd GEAR", true);
                SmartDashboard.putBoolean("1st GEAR", false);
                if(((getSpeed() <= 100) && (getThrottle(joy) >= .5))){
                    gear1();
                    gear = 1;
                }
                
                if(getSpeed() <= 10){
                
                    gear1();
                    gear = 1;
                                    
                }
                break;
            
        }
        
    }

    public void testInit(){
        
    }
    
    public void testPeriodic() {
    
    }
        
    //my own methods
    public double getThrottle(Joystick j){
        return (double) j.getRawAxis(1)+j.getRawAxis(2);
    }
    
    public void gear1(){
        leftShift.set(true);
        System.out.println("GEAR 1");
    }
    
    public void gear2(){
        leftShift.set(false);
        System.out.println("GEAR 2");
    }
    
    public double getSpeed(){
        
        return (r1.getSpeed() + l1.getSpeed())/2;
    
    }
}
