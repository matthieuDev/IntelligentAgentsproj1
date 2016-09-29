import uchicago.src.sim.space.Object2DGrid;

/**
 * Class that implements the simulation space of the rabbits grass simulation.
 * @author 
 */

public class RabbitsGrassSimulationSpace {
	private Object2DGrid gridGrass;
	private Object2DGrid rabbitGrid;
	private int countGrass = 0 ;

	public RabbitsGrassSimulationSpace(int xSize, int ySize){
		gridGrass = new Object2DGrid(xSize, ySize);
		rabbitGrid =  new Object2DGrid(xSize, ySize);

	    for(int i = 0; i < xSize; i++){
	      for(int j = 0; j < ySize; j++){
	    	  gridGrass.putObjectAt(i,j,new Integer(0));
	      }
	    }
	}
	
	public void fillGridBegin(int nbRabbit, int nbGrass){
		oneStepAddGrass( nbGrass);
		
	}
	
	public void oneStepAddGrass(int nbGrass){
		int i = nbGrass, count = 0;
		while(i!=0 && count < gridGrass.getSizeX()*gridGrass.getSizeY()*nbGrass){
			int x = (int)(Math.random()*(gridGrass.getSizeX()));
		    int y = (int)(Math.random()*(gridGrass.getSizeY()));
		    int lw;
		      if(gridGrass.getObjectAt(x,y)!= null){
		    	  lw = ((Integer)gridGrass.getObjectAt(x,y)).intValue();
		      }
		      else{
		    	  gridGrass.putObjectAt(x,y,new Integer(0));
		    	  lw = 0;
		      }
		      if (lw == 0){
		    	  gridGrass.putObjectAt(x,y,new Integer(1));
		    	  i--;
		    	  countGrass++ ;
		      }
		     count++; 
		}
	}
	
	
	
	public Object2DGrid getGridGrass(){
	    return gridGrass;
	  }
	
	public Object2DGrid getRabbitGrid(){
	    return rabbitGrid;
	  }
	
	 public boolean isCellOccupiedRabbit(int x, int y){
		return rabbitGrid.getObjectAt(x, y)  !=null;
		  }
	 
	 public boolean isCellOccupiedGrass(int x, int y){
		 if ( gridGrass.getObjectAt(x, y)  !=null && 
				 ((Integer)gridGrass.getObjectAt(x,y)).intValue()  != 0 ){
			 gridGrass.putObjectAt(x,y,0);
			 return true ;
		 } else {
			 return false;
		 }
		 
		  }
	 
	
	 public boolean addRabbit(RabbitsGrassSimulationAgent agent){
		    boolean retVal = false;
		    int count = 0;
		    int countLimit = 10 * rabbitGrid.getSizeX() * rabbitGrid.getSizeY();
		    while((retVal==false) && (count < countLimit)){
		      int x = (int)(Math.random()*(rabbitGrid.getSizeX()));
		      int y = (int)(Math.random()*(rabbitGrid.getSizeY()));
		      if(!isCellOccupiedRabbit(x,y)){
		        rabbitGrid.putObjectAt(x,y,agent);
		        agent.setXY(x,y);
		        agent.setRabbitsGrassSimulationSpace(this);
		        retVal = true;
		      }
		      count++;
		    }

		    return retVal;
		  }
	 
	 public void removeRabbitAt(int x, int y){
		    rabbitGrid.putObjectAt(x, y, null);
		  }
	 
	 public void tryMove(int x,int y,int newX, int newY) {
		 if(!isCellOccupiedRabbit(newX, newY)){
			 RabbitsGrassSimulationAgent rga =(RabbitsGrassSimulationAgent) rabbitGrid.getObjectAt(x, y);
			 removeRabbitAt(x, y);
			 rabbitGrid.putObjectAt(newX, newY, rga);
			 rga.setXY(newX, newY);
		 }
	 }
	 
	 public int getCountGrass(){
		 return countGrass;
	 }
	 
	 public void decrementCountGrass(){
		 countGrass--;
	 }
}
