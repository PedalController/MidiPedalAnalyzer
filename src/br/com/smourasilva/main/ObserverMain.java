package br.com.smourasilva.main;

import javax.sound.midi.MidiUnavailableException;

import br.com.smourasilva.observer.PedalObserver;
import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.zoom.ZoomG3PedalObservable;

public class ObserverMain {
	public static void main(String[] args) throws DeviceNotFoundException, MidiUnavailableException {
		PedalObserver observer = new PedalObserver("Series", new ZoomG3PedalObservable());
		
		observer.start();
	}
}
