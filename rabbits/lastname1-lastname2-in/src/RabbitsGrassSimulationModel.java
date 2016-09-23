import java.awt.Color;
import java.util.ArrayList;

import uchicago.src.sim.engine.BasicAction;
import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimInit;
import uchicago.src.sim.engine.SimModelImpl;
import uchicago.src.sim.gui.DisplaySurface;
import uchicago.src.sim.gui.ColorMap;
import uchicago.src.sim.gui.Object2DDisplay;
import uchicago.src.sim.gui.Value2DDisplay;
import uchicago.src.sim.util.SimUtilities;

/**
 * Class that implements the simulation model for the rabbits grass
 * simulation.  This is the first class which needs to be setup in
 * order to run Repast simulation. It manages the entire RePast
 * environment and the simulation.
 *
 * @author 
 */


public class RabbitsGrassSimulationModel extends SimModelImpl {		
	private Schedule schedule;
	private int number = 150 , birthThreshold = 15 , 
			grassGrowRate = 15 , grassEnergy = 5 ,
			xSize = 20 , ySize = 20;
	private RabbitsGrassSimulationSpace grid;
	private DisplaySurface displaySurf;
	private ArrayList<RabbitsGrassSimulationAgent> rabbitList;

		public static void main(String[] args) {
			
			System.out.println("Rabbit skeleton");
			SimInit init = new SimInit();
			RabbitsGrassSimulationModel model = new RabbitsGrassSimulationModel();
		    init.loadModel(model, "", false);
		}

		public void begin() {
			buildModel();
		    buildSchedule();
		    buildDisplay();
		    
		    displaySurf.display();
			
		}
		
		public void buildModel(){
			grid = new RabbitsGrassSimulationSpace(xSize, ySize);
			grid.fillGridBegin(number, grassGrowRate);
			for(int i = 0; i < number; i++){
				 addNewAgent();
			    }
			for(int i = 0; i < rabbitList.size(); i++){
				RabbitsGrassSimulationAgent cda = (RabbitsGrassSimulationAgent)rabbitList.get(i);
			      cda.report();
			    }
		  }
		private void addNewAgent(){
			RabbitsGrassSimulationAgent r = new RabbitsGrassSimulationAgent(birthThreshold,grassEnergy);
		    rabbitList.add(r);
		    grid.addRabbit(r);
		  }
		
		private int rabbitReaper(){
			 int count = 0;
			    for(int i = rabbitList.size()-1; i >0  ; i--){
			    	RabbitsGrassSimulationAgent rab = 
			    			(RabbitsGrassSimulationAgent)rabbitList.get(i);
			      if(rab.getEnergy() < 1){
			        grid.removeRabbitAt(rab.getX(), rab.getY());
			        rabbitList.remove(i);
			        count++;
			      }
			    }
			    return count;
		}
		
		private int rabbitCreater(){
			 int count = 0;
			    for(int i = rabbitList.size()-1; i >0  ; i--){
			    	RabbitsGrassSimulationAgent rab = 
			    			(RabbitsGrassSimulationAgent)rabbitList.get(i);
			      if(rab.getEnergy() > birthThreshold){
			    	 rab.updateEnergyBorn(birthThreshold);
			    	 addNewAgent();
			    	 count++;
			      }
			    }
			    return count;
		}


		public void buildSchedule(){
			 class CarryDropStep extends BasicAction {
			      public void execute() {
			        SimUtilities.shuffle(rabbitList);
			        grid.oneStepAddGrass( grassGrowRate);
			        for(int i =0; i < rabbitList.size(); i++){
			        	RabbitsGrassSimulationAgent cda = (RabbitsGrassSimulationAgent)rabbitList.get(i);
			          cda.step();
			        }
			        rabbitReaper();
			        rabbitCreater();
			        
			        
			        displaySurf.updateDisplay();
			      }
			      
			    }
			 

			    schedule.scheduleActionBeginning(0, new CarryDropStep());
		  }

		  public void buildDisplay(){
			  ColorMap map = new ColorMap();

			  map.mapColor(0, new Color(0, 0, 0));
			  map.mapColor(1, new Color(0, 255, 0));

			 

			    Value2DDisplay displayGrass = 
			        new Value2DDisplay(grid.getGridGrass(), map);
			    
			    Object2DDisplay displayRabbit = new Object2DDisplay(grid.getRabbitGrid());
			    displayRabbit.setObjectList(rabbitList);

			    displaySurf.addDisplayable(displayGrass, "Grass");
			    displaySurf.addDisplayable(displayRabbit, "Rabbit");
		  }

		public String[] getInitParam() {
			String[] s = { "XSize" , "YSize" , "Number", 
					"BirthThreshold","GrassGrowRate","GrassEnergy"};
			return s;
		}

		public String getName() {
			return "A Rabbits Grass Simulation";
		}

		public Schedule getSchedule() {
			return schedule;
		}

		public void setup() {
			grid = null;
			 schedule = new Schedule(1);
			if (displaySurf != null){
			      displaySurf.dispose();
			    }
			    displaySurf = null;

			    displaySurf = new DisplaySurface(this, "Rabbit Grass Model Window 1");

			    registerDisplaySurface("Rabbit Grass Model Window 1", displaySurf);
			    
			    rabbitList = new ArrayList<RabbitsGrassSimulationAgent>();

			
		}
		
		public int getNumber(){
			return number;
		}
		
		public void setNumber(int i){
			number = i;
		}
		
		public int getBirthThreshold(){
			return birthThreshold;
		}
		
		public void setBirthThreshold(int i){
			birthThreshold = i;
		}
		
		public int getGrassGrowRate(){
			return grassGrowRate;
		}
		
		public void setGrassGrowRate(int i){
			grassGrowRate = i;
		}
		
		public int getGrassEnergy(){
			return grassEnergy;
		}
		
		public void setGrassEnergy(int i){
			grassEnergy = i;
		}
		
		public int getXSize(){
			return xSize;
		}
		
		public void setXSize(int i){
			xSize = i;
		}
		
		public int getYSize(){
			return ySize;
		}
		
		public void setYSize(int i){
			ySize = i;
		}
}
