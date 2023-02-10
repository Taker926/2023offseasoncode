package frc.robot.Objects;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.*;

public class Limelight {

	private boolean useVision;
	private boolean useTape;
	private boolean hasValidTarget;

	private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
	private double tx = limelight.getEntry("tx").getDouble(0);
	private double ty = limelight.getEntry("ty").getDouble(0);
	private double ta = limelight.getEntry("ta").getDouble(0);

	public Limelight() {
		useVision = true;
		useTape = true;
		hasValidTarget = false;
	}

	public void updateLimelight() {

		// skips updating vision if the limelight is set to DriverCam
		if (useVision) {

			// changes what pipeline the light gets from
			if (useTape) {
				updateReflectiveTape();
			} else {
				updateAprilTags();
			}

		} else {
			setDisabled();
		}

	}

	public void updateReflectiveTape() {

		tx = limelight.getEntry("tx").getDouble(0);
		ty = limelight.getEntry("ty").getDouble(0);
		ta = limelight.getEntry("ta").getDouble(0);

		hasValidTarget = (limelight.getEntry("tv").getDouble(0) < 1) ? false : true;

	}

	public void updateAprilTags() {

	}

	/** takes a snapshot for later use */
	public void takeSnapshot() {
		limelight.getEntry("snapshot").setNumber(1);
	}

	// region getters

	

	/**
	 * Gets the target x offset of the limelight
	 * 
	 * @return Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27
	 *         degrees | LL2: -29.8 to 29.8 degrees)
	 */
	public double getTx() {
		return tx;
	}

	/**
	 * Gets the target y offset of the limelight
	 * 
	 * @return Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5
	 *         degrees | LL2: -24.85 to 24.85 degrees)
	 */
	public double getTy() {
		return ty;
	}

	/**
	 * Gets the area of the limelight target
	 * 
	 * @return Target Area (0% of image to 100% of image)
	 */
	public double getTa() {
		return ta;
	}

	// endregion

	// region setters

	/**
	 * sets cam mode to Driver Camera (Increases exposure, disables vision
	 * processing)
	 */
	public void setDisabled() {
		useVision = false;
		limelight.getEntry("camMode").setNumber(1);
	}

	/** sets cam mode to Vision processor */
	public void setEnabled() {
		useVision = true;
		limelight.getEntry("camMode").setNumber(0);
	}

	/**
	 * Sets the limelights current pipeline
	 * 
	 * @param pipeline the ID of the pipeline to be set
	 */
	public void setPipeline(int pipeline) {
		limelight.getEntry("pipeline").setNumber(pipeline);
	}

	// endregion

}
