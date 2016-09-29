import java.awt.Color;

import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.space.Object2DGrid;

/**
 * Class that implements the simulation agent for the rabbits grass simulation.
 * 
 * @author
 */

public class RabbitsGrassSimulationAgent implements Drawable {

	private int x = -1, y = -1, energy, energyByGrass;
	private static int IDNumber = 0;
	private int ID;
	RabbitsGrassSimulationSpace rgSpace;

	public RabbitsGrassSimulationAgent(int energyThreshold, int eg) {
		energyByGrass = eg;
		energy = (int) ((Math.random() * (energyThreshold-1))) + 1;
		IDNumber++;
		ID = IDNumber;
	}

	public void draw(SimGraphics arg0) {
		
			arg0.drawCircle(Color.white);
		
		
	}

	public void setXY(int newX, int newY) {
		x = newX;
		y = newY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getID() {
		return "A-" + ID;
	}

	public int getEnergy() {
		return energy;
	}

	public void updateEnergyBorn(int energyMinus) {
		energy -= energyMinus;
	}

	

	public void report() {
		System.out.println(getID() + " at " + x + ", " + y + " has "
				+ getEnergy() + " energy");
	}

	public void setRabbitsGrassSimulationSpace(RabbitsGrassSimulationSpace rgs) {
		rgSpace = rgs;
	}
	
	private void tryMove(){
		int vX = -1, vY = -1, alea = (int)Math.floor(Math.random() * 4) ;
		if(alea == 0){
			vX= 0; vY = 1;
		} else if(alea == 1){
			vX= 1; vY = 0;
		}else if(alea == 2){
			vX= 0; vY = -1;
		}else if(alea == 3){
			vX= -1; vY = 0;
		}
		Object2DGrid grid = rgSpace.getRabbitGrid();
		int newX = (x + vX + grid.getSizeX())%grid.getSizeX();
		int newY = (y + vY + grid.getSizeY())%grid.getSizeY();
		rgSpace.tryMove(x,y,newX,newY);
	}

	
	public void step() {
		energy--;
		if (rgSpace.isCellOccupiedGrass(x, y)) {
			energy += energyByGrass;
			rgSpace.decrementCountGrass();
		}
		tryMove();
	}
	
}
