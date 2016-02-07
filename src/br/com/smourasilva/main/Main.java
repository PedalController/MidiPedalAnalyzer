package br.com.smourasilva.main;

import javax.sound.midi.MidiUnavailableException;

import br.com.srmourasilva.analizer.Analyzer;
import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.zoom.ZoomG3PedalTypeAnalyzer;

public class Main {
	public static void main(String[] args) throws DeviceNotFoundException, MidiUnavailableException {
		int patchTest = 7;

		Analyzer analyzer = new Analyzer(new ZoomG3PedalTypeAnalyzer(patchTest));
		analyzer.analyze();
	}
}
