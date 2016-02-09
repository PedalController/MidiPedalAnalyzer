package br.com.smourasilva.observer;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.multistomp.connection.midi.MidiReader;
import br.com.srmourasilva.multistomp.connection.midi.MidiReader.MidiReaderListener;
import br.com.srmourasilva.multistomp.connection.midi.MidiSender;
import br.com.srmourasilva.multistomp.connection.midi.MidiTransmition;
import br.com.srmourasilva.util.BinaryUtil;

public class PedalObserver implements MidiReaderListener {
	private MidiReader reader;
	private MidiSender sender;
	private PedalObservable pedalObservable;

	public PedalObserver(String name, PedalObservable pedalObservable) throws DeviceNotFoundException {
		this.reader = MidiTransmition.getReaderOf(name);
		this.sender = MidiTransmition.getSenderOf(name);
		this.pedalObservable = pedalObservable;
		
		reader.setListener(this);
	}
	
	public void start() throws MidiUnavailableException {
		reader.start();
		sender.start();
		MidiMessages data = pedalObservable.start();
		
		for (MidiMessage midiMessage : data)
			sender.send(midiMessage);
	}
	
	public void stop() {
		pedalObservable.stop();
		reader.close();
	}

	@Override
	public void onDataReceived(MidiMessage message) {
		String data = BinaryUtil.byteArrayToHex(message.getMessage());
		System.out.println(data);
	}
}

