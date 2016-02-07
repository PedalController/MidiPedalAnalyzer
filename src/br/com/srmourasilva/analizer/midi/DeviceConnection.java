package br.com.srmourasilva.analizer.midi;

import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.multistomp.connection.midi.MidiReader;
import br.com.srmourasilva.multistomp.connection.midi.MidiReader.MidiReaderListener;
import br.com.srmourasilva.multistomp.connection.midi.MidiSender;
import br.com.srmourasilva.multistomp.connection.midi.MidiTransmition;

public class DeviceConnection implements MidiReaderListener {
	private MidiReader reader;
	private MidiSender sender;

	private boolean started = false;
	private List<MidiMessage> messages;

	public DeviceConnection(String name) throws DeviceNotFoundException {
		this.messages = new LinkedList<>();

		this.reader = MidiTransmition.getReaderOf(name);
		this.sender = MidiTransmition.getSenderOf(name);
		
		reader.setListener(this);
	}

	/////////////////////////////////////////////
	
	public void send(MidiMessages messages) {
		for (MidiMessage message : messages) {
			send(message);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void send(MidiMessage message) {
		sender.send(message);
	}
	
	/////////////////////////////////////////////

	public void start() throws MidiUnavailableException {
		reader.start();
		sender.start();
	}
	
	public void observe() {
		this.started = true;
	}
	
	public void stop() {
		this.started = false;
		reader.stop();
		sender.stop();
	}

	/////////////////////////////////////////////

	@Override
	public void onDataReceived(MidiMessage message) {
		if (started)
			this.messages.add(message);
	}
	
	public List<MidiMessage> getMessages() {
		return messages;
	}
}
