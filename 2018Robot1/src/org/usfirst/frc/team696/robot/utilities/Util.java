
package org.usfirst.frc.team696.robot.utilities;

public class Util {
    public static double deadZone(double val, double lowVal, double highVal, double returnVal) {//y
        if ((val > lowVal)&&(val < highVal)) { 
            return (returnVal);
        }
        return (val);
    }
    
    public static double smoothDeadZone(double val, double lowVal, double highVal, double lowerEnd, double higherEnd, double returnVal) {//y
        if ((val > lowVal)&&(val < highVal)) { 
            return (returnVal);
        }
        else if(val<lowVal){
            
        	return(map(val,returnVal,lowerEnd,lowVal,lowerEnd));
        }else{
            return(map(val,returnVal,higherEnd,highVal,higherEnd));
        }
    }
    
    public static double map(double val, double lowIn, double highIn, double lowOut, double highOut) {//y
        return lerp(lowOut, highOut, norm(lowIn, highIn, val));
    }

    public static double norm(double low, double high, double input) {//y
        return ((input - low) / (high - low));
    }

    public static double lerp(double low, double high, double percent) {//y
        return (low + percent * (high - low));
        
    }
    
    public static double constrain(double val, double lVal, double hVal) {//y
        if (val < lVal) {
            return lVal;
        } else if (hVal < val) {
            return hVal;
        }
        return val;
    }
    
    public static double signOf(double num){
        if(num>=0){
            return(1);
        }
        return(-1);

    }

    public static double toInches(double feet, double inches){
    	return inches + (feet * 12);
    }
    
}