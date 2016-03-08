package org.usfirst.frc.team219.robot.subsystems;

import org.usfirst.frc.team219.robot.RobotMap;
import org.usfirst.frc.team219.robot.commands.Vision.PullData;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CameraServer camera;
    private NetworkTable table;

	private double[] centerXs;
	private double[] defaultValue = new double[0];
    
	public Vision() {
		camera = CameraServer.getInstance();
		camera.setQuality(100);
		camera.startAutomaticCapture("cam1");
	}
		
	public void post(){
//		System.out.println("");
	    table = NetworkTable.getTable("GRIP/myContoursReport");
	    
	    SmartDashboard.putString("Table", table.toString());
	    
	    centerXs = table.getNumberArray("centerX", defaultValue);
	    System.out.println("should be running");
	    
	    SmartDashboard.putNumber("Turn Right?", turnRight());
	    try{
	    	SmartDashboard.putNumber("X Num", centerXs[0]);
	    }
	    catch(ArrayIndexOutOfBoundsException q) {
	    }
	    	    
	}
	
	/**
	 * 
	 * @return 1 if has to turn right, -1 if has to turn left, 0 if nothing found
	 */
	public int turnRight(){
		try{
			if((RobotMap.PIXEL_CONSTANT - (centerXs[0])) <= 0)
			{
				return 1; 
			}
			else
			{
				if(RobotMap.PIXEL_CONSTANT - centerXs[0] >= 0)
				{
					return -1;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e){

		}
		
		return 0;
	}
	
	public double getXVal()
	{
		try{
			return centerXs[0];
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Target not found: "+e);
		}
		return RobotMap.PIXEL_CONSTANT;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new PullData());
    	
    }
}
