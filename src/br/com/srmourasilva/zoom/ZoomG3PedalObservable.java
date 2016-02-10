package br.com.srmourasilva.zoom;

import br.com.smourasilva.observer.PedalObservable;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesMessageEncoder;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesMessages;

public class ZoomG3PedalObservable implements PedalObservable {

	private ZoomGSeriesMessageEncoder encoder;

	public ZoomG3PedalObservable() {
		this.encoder = new ZoomGSeriesMessageEncoder();
	}

	@Override
	public MidiMessages start() {
		Messages messages = Messages.Empty()
				.add(ZoomGSeriesMessages.LISSEN_ME())
				.add(ZoomGSeriesMessages.YOU_CAN_TALK());
		
		return encoder.encode(messages);
	}

	@Override
	public MidiMessages stop() {
		return encoder.encode(Messages.Empty());
	}

}
