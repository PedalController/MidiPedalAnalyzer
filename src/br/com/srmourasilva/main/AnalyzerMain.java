package br.com.srmourasilva.main;

import javax.sound.midi.MidiUnavailableException;

import br.com.srmourasilva.analizer.MessagesAnalyzer;
import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.zoom.ZoomG3PedalTypeAnalyzer;

public class AnalyzerMain {
	public static void main(String[] args) throws DeviceNotFoundException, MidiUnavailableException {
		int patchTest = 7;

		MessagesAnalyzer analyzer = new MessagesAnalyzer(new ZoomG3PedalTypeAnalyzer(patchTest));
		analyzer.analyze();
	}
}
