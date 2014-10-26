package Synth;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import java.io.IOException;
import java.math.*;
//import java.util.;

/*class LeapListener extends Listener {
	public void onInit(Controller controller){
		System.out.println("initialized");
	}
	public void onConnect(Controller controller){
		System.out.println("Connected to motion sensor");
		//where the gestures are picked up.
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}
	public void onDisconnect(Controller controller){
		System.out.println("motion sensor disconnected");
		
	}
	public void onExit(Controller controller){
		System.out.println("exited");
	}
	public void onFrame(Controller controller){
		//it sends to our java program 
		Frame frame = controller.frame();
		//^^^this is the frame we are getting from the controller^^^^
		/*System.out.println("Frame id: " + frame.id() 
				+ "also timestamp: " + frame.timestamp()
				+ " number of hands: " + frame.hands().count()
				+ " number of fingers: " + frame.fingers().count()
				+ " number of tools: " + frame.tools().count()
				+ " number of gestures: " + frame.gestures().count());
		if(frame.hands().count() == 1){
			System.out.println("55");
		
		}*/
	/*	for(Hand hand : frame.hands()){
			String handType = hand.isLeft() ? "left hand" : "right hand";
			System.out.println(handType + " " + " hand id: " + hand.id()
					+ "palm position" + hand.palmPosition()
					+ " x axis equals: " + hand.palmPosition().getX());
			
			
			
			Vector normal = hand.palmNormal();
			Vector direction = hand.direction();
			
		}
		
		
	}
}*/

public class Theramin {
	public static void main(String[] args){
	/*	LeapListener listener = new LeapListener();
		Controller controller = new Controller();
		
		controller.addListener(listener);
		System.out.println("press enter to quit");
		
		try{
			System.in.read();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		controller.removeListener(listener);*/
		
	}

}
