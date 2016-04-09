package br.com.srmourasilva.observer;

import br.com.srmourasilva.domain.message.MidiMessages;

public interface PedalObservable {
	MidiMessages start();
	MidiMessages stop();
}
