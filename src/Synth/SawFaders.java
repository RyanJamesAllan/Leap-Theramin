package Synth;

import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import java.math.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.data.SegmentedEnvelope;
import com.jsyn.swing.ExponentialRangeModel;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.swing.PortControllerFactory;
import com.jsyn.swing.PortModelFactory;
import com.jsyn.swing.RotaryTextController;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;
import com.jsyn.util.WaveRecorder;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;
import com.softsynth.jsyn.SynthEnvelope;

/**
 * Play a tone using a JSyn oscillator and some knobs.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */

class LeapListener extends Listener {
	SawFaders currentSynth;
	
	public LeapListener(SawFaders Synth) {
		this.currentSynth = Synth;
	}
	
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
		if (frame.hands().count() == 0) {
			currentSynth.lag.input.set(0);
			currentSynth.lag2.input.set(0);
			if (currentSynth.record == 2) {
				if (currentSynth.beat == 1) {
					
				}
				else {
				currentSynth.record = 0;
				currentSynth.recorder.stop();
				
				try {
					currentSynth.recorder.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}

			}
		}
		else {

		for(Hand hand : frame.hands()){
			LinearRamp currentlag;
			UnitOscillator currentosc;
			double zposition = hand.palmPosition().getZ();
			double yposition = hand.palmPosition().getY();
			double xposition = hand.palmPosition().getX();
			double grip = hand.grabStrength();
			if ( hand.isLeft()) {
				currentlag = currentSynth.lag2;
				currentosc = currentSynth.osc2;
			}
			else {
				currentlag = currentSynth.lag;
				currentosc = currentSynth.osc;
			}
			/*System.out.println(handType + " " + " hand id: " + hand.id()
					+ "palm position" + hand.palmPosition()
					+ " x axis equals: " + hand.palmPosition().getX());
			*/
			
			if (grip > 0.8) { 
				currentlag.input.set(0);
			}
			else {
			if (currentSynth.record == 1) {
					currentSynth.recorder.start();
					currentSynth.record = 2;
			}
			if (currentSynth.mode == 1) {
				if (xposition < -100) {
					if (yposition < 43) {
						currentosc.frequency.set(16.35);
					}
					else if (43*1 < yposition && yposition < 43*2) {
						currentosc.frequency.set(18.35);
					}
					else if (43*2 < yposition && yposition < 43*3) {
						currentosc.frequency.set(20.60);
					}
					else if (43*3 < yposition && yposition < 43*4) {
						currentosc.frequency.set(21.83);
					}
					else if (43*4 < yposition && yposition < 43*5) {
						currentosc.frequency.set(24.50);
					}
					else if (43*5 < yposition && yposition < 43*6) {
						currentosc.frequency.set(27.50);
					}
					else if (43*6 < yposition && yposition < 43*7) {
						currentosc.frequency.set(30.87);
					}
				}
				if (xposition < -60 && xposition >= -100 ) {
					if (yposition < 43) {
						currentosc.frequency.set(32.70);
					}
					else if (43*1 < yposition && yposition < 43*2) {
						currentosc.frequency.set(36.71);
					}
					else if (43*2 < yposition && yposition < 43*3) {
						currentosc.frequency.set(41.20);
					}
					else if (43*3 < yposition && yposition < 43*4) {
						currentosc.frequency.set(43.65);
					}
					else if (43*4 < yposition && yposition < 43*5) {
						currentosc.frequency.set(49.00);
					}
					else if (43*5 < yposition && yposition < 43*6) {
						currentosc.frequency.set(55.00);
					}
					else if (43*6 < yposition && yposition < 43*7) {
						currentosc.frequency.set(61.74);
					}
				}
				if (xposition < -20 && xposition >= -60 ) {
					if (yposition < 43) {
						currentosc.frequency.set(65.41);
					}
					else if (43*1 < yposition && yposition < 43*2) {
						currentosc.frequency.set(73.42);
					}
					else if (43*2 < yposition && yposition < 43*3) {
						currentosc.frequency.set(82.41);
					}
					else if (43*3 < yposition && yposition < 43*4) {
						currentosc.frequency.set(87.31);
					}
					else if (43*4 < yposition && yposition < 43*5) {
						currentosc.frequency.set(98.00);
					}
					else if (43*5 < yposition && yposition < 43*6) {
						currentosc.frequency.set(110.00);
					}
					else if (43*6 < yposition && yposition < 43*7) {
						currentosc.frequency.set(123.47);
					}
				}
				if (xposition < 20 && xposition >= -20 ) {
					if (yposition < 43) {
						currentosc.frequency.set(130.81);
					}
					else if (43*1 < yposition && yposition < 43*2) {
						currentosc.frequency.set(146.83);
					}
					else if (43*2 < yposition && yposition < 43*3) {
						currentosc.frequency.set(164.81);
					}
					else if (43*3 < yposition && yposition < 43*4) {
						currentosc.frequency.set(174.61);
					}
					else if (43*4 < yposition && yposition < 43*5) {
						currentosc.frequency.set(196.00);
					}
					else if (43*5 < yposition && yposition < 43*6) {
						currentosc.frequency.set(220.00);
					}
					else if (43*6 < yposition && yposition < 43*7) {
						currentosc.frequency.set(246.94);
					}
				}
				if (xposition < 60 && xposition >= 20 ) {
					if (yposition < 43) {
						currentosc.frequency.set(261.63);
					}
					else if (43*1 < yposition && yposition < 43*2) {
						currentosc.frequency.set(293.66);
					}
					else if (43*2 < yposition && yposition < 43*3) {
						currentosc.frequency.set(329.63);
					}
					else if (43*3 < yposition && yposition < 43*4) {
						currentosc.frequency.set(349.23);
					}
					else if (43*4 < yposition && yposition < 43*5) {
						currentosc.frequency.set(392.00);
					}
					else if (43*5 < yposition && yposition < 43*6) {
						currentosc.frequency.set(440.00);
					}
					else if (43*6 < yposition && yposition < 43*7) {
						currentosc.frequency.set(493.88);
					}
				}
				if (xposition < 100 && xposition >= 60 ) {
					if (yposition < 43) {
						currentosc.frequency.set(523.25);
					}
					else if (43*1 < yposition && yposition < 43*2) {
						currentosc.frequency.set(587.33);
					}
					else if (43*2 < yposition && yposition < 43*3) {
						currentosc.frequency.set(659.25);
					}
					else if (43*3 < yposition && yposition < 43*4) {
						currentosc.frequency.set(698.46);
					}
					else if (43*4 < yposition && yposition < 43*5) {
						currentosc.frequency.set(783.99);
					}
					else if (43*5 < yposition && yposition < 43*6) {
						currentosc.frequency.set(880.00);
					}
					else if (43*6 < yposition && yposition < 43*7) {
						currentosc.frequency.set(987.77);
					}
				}
				if (xposition >= 100) {
					if (yposition < 43) {
						currentosc.frequency.set(1046.50);
					}
					else if (43*1 < yposition && yposition < 43*2) {
						currentosc.frequency.set(1174.66);
					}
					else if (43*2 < yposition && yposition < 43*3) {
						currentosc.frequency.set(1318.51);
					}
					else if (43*3 < yposition && yposition < 43*4) {
						currentosc.frequency.set(1396.91);
					}
					else if (43*4 < yposition && yposition < 43*5) {
						currentosc.frequency.set(1567.98);
					}
					else if (43*5 < yposition && yposition < 43*6) {
						currentosc.frequency.set(1760.00);
					}
					else if (43*6 < yposition && yposition < 43*7) {
						currentosc.frequency.set(1975.53);
					}
				}
			}
			else {
			if (yposition > 350) {
				currentosc.frequency.set(900);

			}
			else if (yposition < 50) {
				currentosc.frequency.set(50);
			}
			else if (yposition < 350 && yposition >= 50) {
				currentosc.frequency.set(50+(yposition-50)*3);
			}
			}
			if(zposition < -100) {
				currentlag.input.set(0);
			}
			else if (zposition < -50 && zposition >= -100) {
				currentlag.input.set(0.25);
			} 
			else if (zposition < 50 && zposition >= -50) {
				currentlag.input.set(0.5);
			} 
			else if (zposition < 100 && zposition >= 50) {
				currentlag.input.set(0.75);
			} 
			else if (zposition > 100) {
				currentlag.input.set(1);
			}
			}
		}
		}
		
		
	}
}

class SawListener implements ActionListener {
	public SawFaders cool;
	
	public SawListener(SawFaders cool) {
		this.cool = cool;
	}
	public void actionPerformed(ActionEvent e) {
		if("sawtooth".equals(e.getActionCommand())) {
			cool.synth = JSyn.createSynthesizer();


			cool.osc = new SawtoothOscillatorBL(); 
			cool.synth.add( cool.osc );
			cool.synth.add( cool.lag = new LinearRamp() );
			// Add an output mixer.
			cool.synth.add( cool.lineOut = new LineOut() );
			// Add a lag to smooth out amplitude changes and avoid pops.
			// Add an output mixer.
			cool.synth.add(cool.osc2 = new SawtoothOscillatorBL());
			cool.synth.add( cool.lag2 = new LinearRamp() );
			cool.osc2.output.connect( 0, cool.lineOut.input, 0 );
			cool.osc2.output.connect( 0, cool.lineOut.input, 1 );
			cool.lag2.output.connect( cool.osc2.amplitude );
			cool.lag2.input.setup( 0.0, 0.0, 1.0 );
			cool.lag2.time.set(  0.2 );

			// Connect the oscillator to the output.
			cool.osc.output.connect( 0, cool.lineOut.input, 0 );
			cool.osc.output.connect( 0, cool.lineOut.input, 1 );
			cool.lag.output.connect( cool.osc.amplitude );
			cool.lag.input.setup( 0.0, 0.0, 1.0 );
			cool.lag.time.set(  0.2 );
			
			// Set the minimum, current and maximum values for the port.
			cool.osc.frequency.setup( 50.0, 300.0, 10000.0 );
			cool.osc2.frequency.setup( 50.0, 300.0, 10000.0 );

			cool.synth.start();
			// We only need to start the LineOut. It will pull data from the
			// oscillator.
			cool.lineOut.start();
		}
		if("sin".equals(e.getActionCommand())) {
			System.out.println("cool");
			cool.synth = JSyn.createSynthesizer();
		
			cool.osc = new 	SineOscillator();
			cool.synth.add( cool.osc );
			cool.synth.add( cool.lag = new LinearRamp() );
			// Add an output mixer.
			cool.synth.add( cool.lineOut = new LineOut() );
			// Add a lag to smooth out amplitude changes and avoid pops.
			// Add an output mixer.
			cool.synth.add(cool.osc2 = new SineOscillator());
			cool.synth.add( cool.lag2 = new LinearRamp() );
			cool.osc2.output.connect( 0, cool.lineOut.input, 0 );
			cool.osc2.output.connect( 0, cool.lineOut.input, 1 );
			cool.lag2.output.connect( cool.osc2.amplitude );
			cool.lag2.input.setup( 0.0, 0.0, 1.0 );
			cool.lag2.time.set(  0.2 );

			// Connect the oscillator to the output.
			cool.osc.output.connect( 0, cool.lineOut.input, 0 );
			cool.osc.output.connect( 0, cool.lineOut.input, 1 );
			cool.lag.output.connect( cool.osc.amplitude );
			cool.lag.input.setup( 0.0, 0.0, 1.0 );
			cool.lag.time.set(  0.2 );
			
			// Set the minimum, current and maximum values for the port.
			cool.osc.frequency.setup( 50.0, 300.0, 10000.0 );
			cool.osc2.frequency.setup( 50.0, 300.0, 10000.0 );

			// Start synthesizer using default stereo output at 44100 Hz.
			cool.synth.start();
			// We only need to start the LineOut. It will pull data from the
			// oscillator.
			cool.lineOut.start();
		}
		if ("music".equals(e.getActionCommand())) {
			if (cool.mode == 0) {
				cool.mode = 1;
			}
			else {
				cool.mode = 0;
			}
		}
		if ("record".equals(e.getActionCommand())) {
			if (cool.record == 0) {
				cool.record = 1;
				try {
					Files.deleteIfExists(FileSystems.getDefault().getPath("temp_recording.wav"));
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				File waveFile = new File("temp_recording.wav");
				try {
					cool.recorder = new WaveRecorder (cool.synth, waveFile);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cool.osc.output.connect(0, cool.recorder.getInput(), 0);
				cool.osc2.output.connect(0, cool.recorder.getInput(), 0);
				if (cool.beat==1) {
					cool.recorder.start();
					cool.record = 2;
				}

			}
		}
		if("play".equals(e.getActionCommand())) {
			File loopFile = new File("temp_recording.wav");
			try {
				cool.loopsample = SampleLoader.loadFloatSample(loopFile);
			} catch (IOException f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}
			cool.samplereader = new VariableRateStereoReader();
			cool.samplereader.rate.set(cool.loopsample.getFrameRate());
			cool.lineOut2 = new LineOut();
			cool.samplereader.output.connect( 0, cool.lineOut2.input, 0 );
			cool.samplereader.output.connect( 0, cool.lineOut2.input, 1);
			cool.synth.add(cool.lineOut2);
			cool.synth.add(cool.samplereader);
			cool.lineOut2.start();
			cool.samplereader.dataQueue.queueLoop(cool.loopsample, 500, cool.loopsample.getNumFrames()-500);


		}
		
		if("beat".equals(e.getActionCommand())) {
			if (cool.beat == 0) {
			double [] data = 
				{
					0.05, 1.0,
					0.2, 1.0,
					0.05, 0.0,
					0.4, 0.0
				};
			cool.envelope = new SegmentedEnvelope(data);
			cool.envelopereader = new VariableRateMonoReader();
			cool.envelopereader.dataQueue.clear();
			cool.envelopereader.dataQueue.queueLoop(cool.envelope, 0, cool.envelope.getNumFrames());
			cool.synth.add(cool.envelopereader);

			cool.envelopereader.output.connect(cool.osc.amplitude);
			cool.envelopereader.output.connect(cool.osc2.amplitude);

			cool.beat = 1;
			}
			else if (cool.beat ==1) {
				cool.beat = 2;
				cool.envelopereader.dataQueue.clear();
				cool.osc.amplitude.disconnect(cool.envelopereader.output);
				cool.osc2.amplitude.disconnect(cool.envelopereader.output);
				//cool.lag.output.connect( cool.osc.amplitude );
			}
			else {
				cool.envelopereader.output.connect(cool.osc.amplitude);
				cool.envelopereader.output.connect(cool.osc2.amplitude);
				cool.envelopereader.dataQueue.queueLoop(cool.envelope, 0, cool.envelope.getNumFrames());
				cool.beat = 1;
			}
		}
		
	}
	
}

public class SawFaders extends JApplet
{
	private static final long serialVersionUID = -2704222221111608377L;
	public Synthesizer synth;
	public UnitOscillator osc;
	public UnitOscillator osc2;
	public LinearRamp lag;
	public LinearRamp lag2;
	public LineOut lineOut;
	public LineOut lineOut2;
	public SawListener listen;
	public int mode = 0;
	public int record = 0;
	public WaveRecorder recorder;
	public FloatSample loopsample;
	public VariableRateStereoReader samplereader;
	public VariableRateMonoReader envelopereader;
	public SegmentedEnvelope envelope;
	public int beat = 0;

	public void init()
	{
		this.listen = new SawListener(this);
		synth = JSyn.createSynthesizer();
	/*	double [] data = 
			{
				0.1, 1.0,
				0.2, 1.0,
				0.1, 0.0,
				0.2, 0.0
			};
		envelope = new SegmentedEnvelope(data);
		envelopereader = new VariableRateMonoReader();
		envelopereader.dataQueue.clear();
		envelopereader.dataQueue.queueLoop(envelope, 0, envelope.getNumFrames());
		envelopereader.rate.set(1.0);
		synth.add(envelopereader);*/

		// Add a tone generator.
		synth.add( osc = new SawtoothOscillatorBL() );
		// Add a lag to smooth out amplitude changes and avoid pops.
		synth.add( lag = new LinearRamp() );
		// Add an output mixer.
		synth.add( lineOut = new LineOut() );
		// Connect the oscillator to the output.
		osc.output.connect( 0, lineOut.input, 0 );
		osc.output.connect( 0, lineOut.input, 1 );
		
		synth.add(osc2 = new SawtoothOscillatorBL());
		synth.add( lag2 = new LinearRamp() );
		osc2.output.connect( 0, lineOut.input, 0 );
		osc2.output.connect( 0, lineOut.input, 1 );
		lag2.output.connect( osc2.amplitude );
		lag2.input.setup( 0.0, 0.0, 1.0 );
		lag2.time.set(  0.2 );
		// Set the minimum, current and maximum values for the port.
		lag.output.connect( osc.amplitude );
		lag.input.setup( 0.0, 0.0, 1.0 );
		lag.time.set(  0.2 );
		// Arrange the faders in a stack.
		setLayout( new GridLayout( 0, 1 ) );

		ExponentialRangeModel amplitudeModel = PortModelFactory.createExponentialModel( lag.input );
		RotaryTextController knob = new RotaryTextController( amplitudeModel, 5 );
		JPanel knobPanel = new JPanel();
		knobPanel.add( knob );
		add( knobPanel );
		
		osc.frequency.setup( 50.0, 300.0, 10000.0 );
		osc2.frequency.setup( 50.0, 300.0, 10000.0 );

		add( PortControllerFactory.createExponentialPortSlider( osc.frequency ) );
		JRadioButton sawtooth = new JRadioButton("SawTooth");
		sawtooth.setActionCommand("sawtooth");
		JRadioButton sine = new JRadioButton("Sine Wave");
		sine.setActionCommand("sin");
		ButtonGroup group = new ButtonGroup();
		group.add(sawtooth);
		group.add(sine);
		sawtooth.addActionListener(listen);
		sine.addActionListener(listen);
		JCheckBox musicmode = new JCheckBox("Music Mode");
		musicmode.setActionCommand("music");
		musicmode.addActionListener(listen);
		JButton record = new JButton("Record");
		record.setActionCommand("record");
		record.addActionListener(listen);
		JButton stop = new JButton("Play");
		stop.setActionCommand("play");
		stop.addActionListener(listen);
		JButton beat = new JButton("beat");
		beat.setActionCommand("beat");
		beat.addActionListener(listen);
		add(sawtooth);
		add(sine);
		add(musicmode);
		add(record);
		add(stop);
		add(beat);
		validate();
	}

	public void start()
	{
		// Start synthesizer using default stereo output at 44100 Hz.
		synth.start();
		// We only need to start the LineOut. It will pull data from the
		// oscillator.
		lineOut.start();
	}

	public void stop()
	{
		synth.stop();
	}
	

	/* Can be run as either an application or as an applet. */
	public static void main( String args[] )
	{
		

		System.out.println("press enter to quit");
		
		SawFaders applet = new SawFaders();
		JAppletFrame frame = new JAppletFrame( "test", applet );

		frame.setSize( 700, 500 );
		frame.setVisible( true );
		frame.test();
		
		LeapListener listener = new LeapListener(applet);
		Controller controller = new Controller();
		
		controller.addListener(listener);
		
		
		
		try{
			System.in.read();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		controller.removeListener(listener);
		
	

	}

}