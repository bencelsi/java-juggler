public class Juggler {
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";

   	public static void main(String args[]) {
    	Ball blueBall = new Ball("blue ball", "right hand", ANSI_BLUE);
    	blueBall.start();
      
    	Ball redBall = new Ball("red ball", "right hand", ANSI_RED);
    	redBall.start();

    	Ball yellowBall = new Ball("yellow ball", "left hand", ANSI_YELLOW);
    	yellowBall.start();
   	}
}


public class Ball implements Runnable {
   	private Thread t;
   	public String ballName;
   	public String currentHand;
   	public String ansiColor;

   	Ball( String name, String startingHand, String ansiColor) {
    	this.ballName = name;
    	this.currentHand = startingHand;
    	this.ansiColor = ansiColor;
   	}
   
   	public void run() {
   		while (true) {
	    	try {
	      		styledPrint("Throwing " + ballName + " from " + currentHand);
	    		throw new BallException(this);
	    	} catch (BallException ballException) {
	   			switchHands();
	        	styledPrint("Catching " +  ballException.ball.ballName + " in " + currentHand);
	    	}
      	}
   	}
   
   	public void start () {
      	if (t == null) {
         	t = new Thread(this, ballName);
         	t.start();
      	}
   	}

   	private void styledPrint(String string) {
   		String indent = "";
   		if (currentHand.equals("right hand")) {
   			indent = "                                      ";
   		}
   		System.out.println(indent + ansiColor + string);
   	}

   	private void switchHands() {
   		if (currentHand.equals("right hand")) {
  			currentHand = "left hand";
  		} else {
  			currentHand = "right hand";
  		}
   	}
}


public class BallException extends Exception {
	public Ball ball;
    public BallException (Ball ball) {
    	this.ball = ball;
    }
}