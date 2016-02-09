package br.com.srmourasilva.analizer;

import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;

import br.com.srmourasilva.analizer.Causes.Cause;
import br.com.srmourasilva.analizer.midi.ByteMessage;
import br.com.srmourasilva.analizer.midi.DeviceConnection;
import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.util.BinaryUtil;

public class MessagesAnalyzer {
	
	private Analyzable analizable;

	public MessagesAnalyzer(Analyzable analizable) {
		this.analizable = analizable;
	}

	public void analyze() throws DeviceNotFoundException, MidiUnavailableException {
		DeviceConnection connection = new DeviceConnection("Series");
		connection.start();

		connection.send(analizable.start());
		connection.send(analizable.initialize());
		
		connection.observe();

		for (MidiMessage message : analizable.execute()) {
			connection.send(analizable.getCurrentStatus());
			connection.send(message);
		}

		connection.send(analizable.getCurrentStatus());

		connection.send(analizable.close());

		connection.stop();
		
		analize(connection.getMessages(), analizable.getIndexCases());
	}
	
	private static void analize(List<MidiMessage> messages, Causes causes) {
		ByteMessage current = new ByteMessage("00");
		
		int index = 0;
		for (MidiMessage message : filterMessages(messages, causes)) {
			String data = toString(message);
			ByteMessage msg = new ByteMessage(data);
			
			System.out.println(causes.getCauses().get(index).getCause());
			System.out.println(current.sinalizar(current.compararCom(msg)));
			current = msg;
			
			index++;
		}
	}
	
	private static List<MidiMessage> filterMessages(List<MidiMessage> messages, Causes causes) {
		List<MidiMessage> analizable = new LinkedList<>();
		
		for (Cause cause : causes)
			analizable.add(messages.get(cause.getIndex()));

		return analizable;
	}

	public static String toString(MidiMessage message) {
		return BinaryUtil.byteArrayToHex(message.getMessage());
	}
}
