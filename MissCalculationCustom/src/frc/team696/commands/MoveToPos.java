package frc.team696.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team696.Robot;

import static frc.team696.Robot.elevatorSubsystem;


public class MoveToPos extends Command {

    int loopNumber;
    String string;
    public MoveToPos(String string) {

        this.string = string;

        requires(elevatorSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }


    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {

    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        loopNumber++;
        elevatorSubsystem.moveToPos(string);

        System.out.println(loopNumber);

    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        if(loopNumber > 60){
            return true;
        }
        return false;
    }


    /**
     * Called once when the command ended peacefully; that is it is called once
     * after {@link #isFinished()} returns true. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the
     * command.
     */
    @Override
    protected void end() {

    }


    /**
     * <p>
     * Called when the command ends because somebody called {@link #cancel()} or
     * another command shared the same requirements as this one, and booted it out. For example,
     * it is called when another command which requires one or more of the same
     * subsystems is scheduled to run.
     * </p><p>
     * This is where you may want to wrap up loose ends, like shutting off a motor that was being
     * used in the command.
     * </p><p>
     * Generally, it is useful to simply call the {@link #end()} method within this
     * method, as done here.
     * </p>
     */
    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
