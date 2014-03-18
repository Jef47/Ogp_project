package worms.model;


import java.util.regex.Matcher;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import worms.util.Util;


/**
 * A class of worms, all located in a certain position (x,y).
 * Each worm also has a certain name, radius, orientation, mass and amount of AP's (action points).
 * 
 * minimum radius = 0.25
 * shape = circle
 * density = 1062
 * 
 * 
 * 
 * @authors Marnix Michiel Denys & Jef De Bie
 * @version 1.0
 */

public class Worm {
	
	/**
	 * @param positionX
	 * @param positionY
	 * @param radius
	 * @param name
	 * @param minRadius (= 0.25)
	 */
	
	public Worm (double positionX, double positionY, double radius, double orientation, String name) {
		this.setPosition(positionX,positionY);
		this.setRadius(radius);
		this.setOrientation(orientation);
		this.setName(name);
		this.setMinRadius(0.25);
		this.setMass(radius);
		this.setMaxActionPoints();
	    this.setCurrentAPToMaxAP(); 
	}
	
	/**
	 * private variables that define the minimum radius and the density of the worms.
	 */
	
	private static  double density = 1062;
	
	
	/**
	 * POSITION, ORIENTATION, RADIUS, MASS AND ACTION POINTS
	 */
	
	
	/**
	 * Sets the position of the Worm.
	 * @param positionX
	 * 			the X-coordinate of the Worm.
	 * @param positionY
	 * 			the Y-coordinate of the Worm.
	 * 
	 * @post the new position is set to the given coordinates
	 * 			| getPositionX() = positionX
	 * 			| getPositionY() = positionY
	 */
	private void setPosition(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	/**
	 * Gets the X-coordinate of the Worm.
	 * @return the X-coordinate
	 */
	@Basic
	public double getPositionX() {
		return this.positionX;
	}
	
	/**
	 * Gets the Y-coordinate of the Worm.
	 * @return the Y-coordinate
	 */
	@Basic
	public double getPositionY() {
		return this.positionY;
	}
	
	private double positionX;
	private double positionY;
	
	/**
	 * Sets the radius of the Worm.
	 * 
	 * @param radius
	 * 			the radius of the worm (in meters)
	 * 
	 * @pre The radius must be greater than or equal to the minimum radius.
	 * 			| radius >= minRadius
	 * 
	 * @throws IllegalArgumentException
	 * 			The radius must be greater than or equal to the minimum radius.
	 * 			| radius >= minRadius
	 * 
	 * @post the radius of the Worm must be set to the given radius.
	 * 			| this.radius = radius
	 * 
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if (Util.fuzzyGreaterThanOrEqualTo(radius, this.getMinRadius())) {
			this.radius = radius;
			this.setMass(radius);
			this.setMaxActionPoints();
		}
		else{
			throw new IllegalArgumentException("The given radius is not greater than or equal to the predefined minimum radius.");
		}
	}
	
	/**
	 * Gets the radius of the worm.
	 * @return the radius
	 */
	@Basic
	public double getRadius() {
		return this.radius;
	}
	
	/*
	 * 
	 */
	private double minRadius;
	
	private void setMinRadius(double minRadius) {
		this.minRadius = minRadius;
	}
	
	/**
	 * 
	 */
	public final double getMinRadius() {
		return this.minRadius;
	}
	
	private double radius;
	
	/**
	 * Sets the mass of the worm (in kg), according to it's radius.
	 * 
	 * @param radius
	 * 
	 * @post the mass must be : m = p * (4/3) * Pi * (r^3)
	 */
	public double mass;
	private void setMass(double radius) {
		this.mass = density * ((4/3) * Math.PI * Math.pow(radius, 3));
		this.setMaxActionPoints();
	}
	
	/**
	 * returns the mass of the worm.
	 * @return the mass
	 */
	@Basic
	public double getMass() {
		return this.mass;
	}
	

	
	/**
	 * set the orientation to the value between 0 and 2*PI that matches the value given as orientation.
	 * 
	 * @param orientation
	 * 
	 * @post The orientation is a value between 0 and 2Pi.
	 * 			| orientation >=0
	 * 			| orientation <=2*Math.PI
	 * @post The orientation matches the given value for orientation
	 * 			| new.orientation = orientation
	 * 
	 */
	private void setOrientation(double orientation) {
		double sign = Math.signum(orientation);
		orientation = Math.abs(orientation);
		while (Util.fuzzyGreaterThanOrEqualTo(orientation, 2*Math.PI)) {
			orientation -= 2*Math.PI;
		}
		if (sign == -1.0) {
			orientation = (2*Math.PI) - orientation;
		}
		this.orientation = orientation;
	}
	
	/**
	 * Gets the orientation.
	 * 
	 * @return the orientation
	 */
	public double getOrientation() {
		return this.orientation;
	}
	
	private double orientation;
	
	/**
	 * Sets the Name of the worm to the given string.
	 * 
	 * @param name
	 * 
	 * @pre name must be valid
	 * 			| this.isValidName(name) == true
	 * 
	 * @post                                                   ! ! nog niet ingevuld ! ! 
	 */
	public String name;
	
	public void setName(String name) throws IllegalArgumentException{
		if (this.isValidName(name)) {
			this.name = name;
		}
		else {
			throw new IllegalArgumentException("Name is invalid.");
		}
	}
	
	/**
	 * Gets the name of the worm.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Checks if given name is a valid name.
	 * 
	 * (http://www.tutorialspoint.com/java/java_string_matches.htm : uitleg over de functie "matches")
	 * 
	 * @param name
	 * 
	 * @return boolean (true if name is valid)
	 * 
	 * @post if returns true : 
	 * 		name must be valid (longer than 2, first character must be uppercase and all characters must be of the given set)
	 * 			|name.length() > 2
	 * 			|Character.isUpperCase(name.CharAt(0))
	 * 			|name.matches("[a-zA-Z'\" ]*")
	 */
	private boolean isValidName(String name) {
		return (name.length() > 2) && (Character.isUpperCase(name.charAt(0))) && name.matches("[a-zA-Z'\" ]*");
	}
	
	/**
	 * Sets the maximum AP's, according to the mass of the worm.
	 * 
	 * @param mass
	 * 
	 * @post the maxActionPoints must be equal to the mass rounded to the closest integer.
	 */
	public int maxActionPoints;
	
	private void setMaxActionPoints() {
		double mass = this.getMass();
		this.maxActionPoints = (int) Math.round(mass);
	}
	
	/**
	 * Gets the maximum AP's.
	 * 
	 * @return max AP's
	 */
	public int getMaxActionPoints() {
		return this.maxActionPoints;
	}
	public int currentActionPoints;
	
	private void setCurrentAPToMaxAP() {
		this.currentActionPoints = this.getMaxActionPoints();
	}
	
	/**
	 * Sets the currentActionPoints to the given integer.
	 * 
	 * @param l
	 */
	private void setCurrentActionPoints(int l) {
		if (l < 0) {
			this.currentActionPoints = 0;
		}
		if (l > this.getMaxActionPoints()) {
			this.setCurrentAPToMaxAP();
		}
		else {
			this.currentActionPoints = l;
		}
	}
	
	/**
	 * Gets the current action points.
	 * 
	 * @return current AP's
	 */
	public int getCurrentActionPoints() {
		return this.currentActionPoints;
	}
	
		
	/**
	 * TURNING AND MOVING
	 */
	
	
	/**
	 * private variables that define the cost to turn 2Pi radians, the cost to move horizontally and vertically.
	 */
	private static final double turnCost = 60;
	private static final double horizontalCost = 1;
	private static final double verticalCost = 4;
	/**
	 *  ! ! Cost nog regelen ! !
	 */
	
	public void move(int numberOfSteps) throws IllegalArgumentException {
		double moveDistance = this.getRadius() * numberOfSteps;
		if (this.isLegalMove(numberOfSteps)) {
			double newPosX = this.getPositionX() + (Math.cos(this.getOrientation()) * moveDistance);
			double newPosY = this.getPositionY() + (Math.sin(this.getOrientation()) * moveDistance);
			this.setPosition(newPosX, newPosY);
			this.costMove(numberOfSteps);
		}
		else {
			throw new IllegalArgumentException ("Illegal move (Insufficient ActionPoints / Less then 0 steps given)");
		}
	}
	
	/**
	 *  ! ! costMove() nog definieren, en denken over argumenten ! !
	 *  
	 *  Checks if a movement is legal.
	 */
	public boolean isLegalMove(int numberOfSteps) {
		return ( !(numberOfSteps < 0)) && ( !(this.calculateCostMove(numberOfSteps) > this.getCurrentActionPoints()));
	}
	
	/**
	 *  ! ! Hoe moet dit met verticale en horizontale stappen?! ! ! 
	 */
	private int calculateCostMove(int numberOfSteps) {
		double moveCoststep = horizontalCost*Math.abs(Math.cos(this.getOrientation())) + verticalCost*Math.abs(Math.sin(this.getOrientation()));
		int totalMoveCost =(int) Math.round(numberOfSteps*moveCoststep);
		return totalMoveCost;
	}
	
	/**
	 * Subtracts the cost of this move from the current amount of APs.
	 * 
	 *  ! ! hier nog comment afmaken ! ! 
	 */
	private void costMove(int numberOfSteps) {
		this.setCurrentActionPoints(this.getCurrentActionPoints() - this.calculateCostMove(numberOfSteps));
	}
	
	/**
	 *  Method to turn.
	 * 
	 * @param angle
	 * 
	 * @post the new orientation must be equal to the old orientation plus the given angle.
	 * 			| 
	 * 
	 * @ throws NullPointerException
	 * 			The worm needs sufficient ActionPoints to turn over the given angle.
	 */
	public void turn(double angle) throws NullPointerException {
		double newOrientation = this.getOrientation() + angle;
		if ( Util.fuzzyGreaterThanOrEqualTo(newOrientation, this.getOrientation()) ) {
			double newAngle = newOrientation - this.getOrientation();
			if (this.isLegalTurn(newAngle)) {
				this.setOrientation(newOrientation);
				this.costTurn(newAngle);
			}
			else {
				throw new NullPointerException ("Illegal turn (Insufficient ActionPoints)");
			}
		} 
		else {
			double newAngle = this.getOrientation() - newOrientation;
			if (this.isLegalTurn(newAngle)) {
				this.setOrientation(newOrientation);
				this.costTurn(newAngle);
			}
			else {
				throw new NullPointerException ("Illegal turn (Insufficient ActionPoints)");
			}
		}
	}
	
	/**
	 * Checks if the worm has sufficient ActionPoints to turn over the given angle.
	 * 
	 * @param angle
	 * 
	 * @return boolean (true if turn is legal)
	 * 
	 * @post if returns true :
	 * 		Current ActionPoints must be greater than the cost of the turn.
	 * 			| this.getCurrentActionPoints() > this.calculateCostTurn(angle)
	 */
	public boolean isLegalTurn(double angle) {
		return ( this.getCurrentActionPoints() > this.calculateCostTurn(angle) );
	}
	
	/**
	 * Calculates the cost to turn over the given angle.
	 * 
	 * @param angle
	 * 
	 * @return int cost
	 * 
	 * @post the cost is equal to the integer closest to turnCost * (angle / 2Pi)
	 * 			| cost = turnCost * (angle / (2 * Math.PI))
	 */
	private int calculateCostTurn(double angle) {
		double fraction = angle / (2 * Math.PI);
		int cost = (int) Math.round(fraction * turnCost);
		return cost;
	}
	
	/**
	 * Subtracts the cost of a given angle from the current ActionPoints.
	 * 
	 * @param angle
	 * 
	 * @post the current APs must be equal to the APs before minus the cost of this angle
	 * 			|new.getCurrentActionPoints() = this.getCurrentActionPoints - this.calculateCostTurn(angle)
	 */
	private void costTurn(double angle) {
		this.setCurrentActionPoints(this.getCurrentActionPoints() - this.calculateCostTurn(angle));
	}
	
	
	/**
	 * JUMPING
	 */
	
	
	/**
	 * private variable gravity that equals the Earth's standard acceleration (9.80665).
	 */
	private static final double gravity = 9.80665;
	
	
	/**
	 * Method jump.
	 * 
	 * 
	 */
	public void jump() throws NullPointerException {
		if (! this.isFacedDown()) {
			if (this.getCurrentActionPoints() > 0) {
				double newPositionX = this.getPositionX() + this.getJumpDistance();
				this.setPosition(newPositionX, getPositionY() );
				this.setCurrentActionPoints(0);
			}
			else {
				throw new NullPointerException ("You don't have any ActionPoints left.");
			}
		}
	}
	
	/**
	 * Method to calculate the force of the jump
	 * 
	 * @return force =  (5*APs) + (mass*gravity)
	 */
	private double getJumpForce() {
		double APs = this.getCurrentActionPoints();
		double mass = this.getMass();
		double force = (5 * APs) + (mass * gravity);
		return force;
	}
	
	
	/**
	 * method to calculate the initial velocity o the worm that jumps
	 * 
	 * @return initial velocity
	 */
	private double getInitialVelocity() {
		double velocity = (this.getJumpForce() / this.getMass()) * 0.5;
		return velocity;
	}
	
	/**
	 * method to calculate the distance of the jump
	 * 
	 * @return distance
	 */
	private double getJumpDistance() {
		double v0 = this.getInitialVelocity();
		double theta = this.getOrientation();
		double distance = (Math.pow(v0, 2) * Math.sin(2*theta)) / gravity;
		return distance;
	}
	
	/**
	 * Calculates the time of the jump
	 * 
	 * @return time
	 */
	public double jumpTime() {
		double v0 = this.getInitialVelocity();
		double theta = this.getOrientation();
		double d = this.getJumpDistance();
		double time = d / (v0 * Math.cos(theta));
		return time;
	}
	
	/**
	 * Checks if the worm is facing downwards.
	 * 
	 */
	private boolean isFacedDown() {
		return ( (this.getOrientation() > Math.PI) && (this.getOrientation() < 2*Math.PI) );
	}
	
	/**
	 * Calculates the position of the jumping worm at a given time.
	 * Die else nog veranderen!!!
	 */
	public double[] jumpStep(double deltaT) {
		if ( (! this.isFacedDown()) && this.getCurrentActionPoints() > 0) {
			double v0 = this.getInitialVelocity();
			double theta = this.getOrientation();
			double v0x = v0 * Math.cos(theta);
			double v0y = v0 * Math.sin(theta);
			double xT = this.getPositionX() + (v0x * deltaT);
			double yT = this.getPositionY() + (v0y * deltaT);
			double[] jumpstep = {xT, yT};
			return jumpstep;
		}
		else{
			return null;
		}
	}
	
	
	
	
	
}