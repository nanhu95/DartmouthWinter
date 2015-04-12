
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import java.io.*;

class LeapListener extends Listener{
	public void onInit(Controller controller) {
		System.out.println("Initialized");
	}
	public void onConnect(Controller controller){
		System.out.println("Connected to LeapMotion");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}
	public void onDisconnect(Controller controller){
		System.out.println("LeapMotion Disconnected");
	}
	public void onExit(Controller controller){
		System.out.println("Exited");
	}
	public void onFrame(Controller controller){
		//to-do
		Frame frame = controller.frame();
//		
//		System.out.println("id: " + frame.id());
//		System.out.println("timestamp: " +frame.timestamp());
//		System.out.println("numhands: " + frame.hands().count());
//		System.out.println("numfingers: " + frame.fingers().count());
//		System.out.println("numtool: " + frame.tools().count());
//		System.out.println("numgesture: " + frame.gestures().count());

		for (Hand hand: frame.hands()){
			String handType = hand.isLeft() ? "left hand": "right hand";
//			System.out.println(handType +"id: " +hand.id()
//					+"palm positions: "+ hand.palmPosition() );
			
			Vector normal = hand.palmNormal();
			Vector direction = hand.direction();
			
		}
		GestureList gestures = frame.gestures();
			for(int i=0 ; i < gestures.count(); i++){
				Gesture gesture = gestures.get(i);
				
				switch (gesture.type()) {
					case TYPE_SWIPE:
						SwipeGesture swipe = new SwipeGesture(gesture);
						System.out.println("Swipe id: "+ swipe.id()
								+"state: "+swipe.state()
								+"position: "+swipe.position()
								+"direction: " +swipe.direction()
								+"speed: " +swipe.speed());
						
						break;
				}
			}
		
	}
}
public class LeapController {

	public static void main(String []args){
		LeapListener listener = new LeapListener();
		Controller controller = new Controller();
		
		controller.addListener(listener);
		
		System.out.println("Enter to quit");
		
		try{
			System.in.read();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		controller.removeListener(listener);
		
		
	}
	
	
}
