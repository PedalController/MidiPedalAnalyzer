package br.com.srmourasilva.analizer;

import br.com.srmourasilva.domain.message.MidiMessages;

public interface Analyzable {
	MidiMessages start();
	MidiMessages initialize();
	MidiMessages close();
	MidiMessages getCurrentStatus();
	MidiMessages execute();
	Causes getIndexCases();
}
