package br.com.srmourasilva.zoom;

import br.com.srmourasilva.analizer.Analyzable;
import br.com.srmourasilva.analizer.Causes;
import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesCause;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesMessageEncoder;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesMessages;

public class ZoomG3PedalTypeAnalyzer implements Analyzable {

	private MessageEncoder encoder;
	private Causes cases = new Causes();
	private int patchTest;

	public ZoomG3PedalTypeAnalyzer(int patchTest) {
		this.encoder = new ZoomGSeriesMessageEncoder();
		this.patchTest = patchTest;
	}

	@Override
	public MidiMessages start() {
		Messages messages = Messages.Empty()
				.add(ZoomGSeriesMessages.LISSEN_ME())
				.add(ZoomGSeriesMessages.YOU_CAN_TALK());
		
		return encoder.encode(messages);
	}

	@Override
	public MidiMessages close() {
		return encoder.encode(Messages.Empty());
	}

	@Override
	public MidiMessages initialize() {
		MultistompDetails details = new MultistompDetails();
		details.patch = patchTest;
		
		Message toPatch = new MultistompMessage(CommonCause.TO_PATCH, details);
		
		return encoder.encode(Messages.For(toPatch)).concatWith(setEffect(0, 0));
	}

	@Override
	public MidiMessages execute() {
		MidiMessages messages = new MidiMessages();

		// Clear current params
		messages = messages.concatWith(encoder.encode(cleanParams(0)));

		cases.add(messages.getMidiMessages().size(), "Clean");

		messages = messages.concatWith(setEffect(0, 0));
		cases.add(messages.getMidiMessages().size(), "byte 0");

		messages = messages.concatWith(setEffect(0, 1));
		cases.add(messages.getMidiMessages().size(), "byte 1");

		messages = messages.concatWith(setEffect(0, 2));
		cases.add(messages.getMidiMessages().size(), "byte 2");

		messages = messages.concatWith(setEffect(0, 4));
		cases.add(messages.getMidiMessages().size(), "byte 4");

		messages = messages.concatWith(setEffect(0, 8));
		cases.add(messages.getMidiMessages().size(), "byte 8");
		
		messages = messages.concatWith(setEffect(0, 16));
		cases.add(messages.getMidiMessages().size(), "byte 16");
		
		messages = messages.concatWith(setEffect(0, 32));
		cases.add(messages.getMidiMessages().size(), "byte 32");
		
		messages = messages.concatWith(setEffect(0, 64));
		cases.add(messages.getMidiMessages().size(), "byte 64");

		return messages;
	}

	private MidiMessages setEffect(int effect, int typeEffect) {
		Message setEffect;
		Messages cleanParams;

		MultistompDetails details = new MultistompDetails();
		details.effect = effect;
		details.value = typeEffect;

		setEffect = new MultistompMessage(CommonCause.EFFECT_TYPE, details);

		cleanParams = cleanParams(effect);

		MidiMessages messages = new MidiMessages();
		messages = messages.concatWith(encoder.encode(Messages.For(setEffect)))
						   .concatWith(encoder.encode(cleanParams));

		return messages;
	}
	
	@Override
	public Causes getIndexCases() {
		return cases;
	}
	
	public Messages cleanParams(int effect) {
		Messages messages = Messages.Empty();
		for (int i = 0; i < 8; i++) {
			MultistompDetails details = new MultistompDetails();

			details.effect = effect;
			details.param = i;
			details.value = 0;

			Message message = new MultistompMessage(CommonCause.PARAM_VALUE, details);
			messages.add(message);
		}
		
		return messages;
	}

	@Override
	public MidiMessages getCurrentStatus() {
		Messages messages = Messages.For(new MultistompMessage(ZoomGSeriesCause.REQUEST_CURRENT_PATCH_DETAILS));

		return encoder.encode(messages);
	}
}
