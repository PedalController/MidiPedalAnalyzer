package br.com.srmourasilva.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import org.junit.Test;

import br.com.srmourasilva.analizer.midi.ByteMessage;

public class ByteMensageTest {

	@Test
	public void compareTest() throws InvalidMidiDataException {
		ByteMessage mensagem1 = new ByteMessage("84 20");
		ByteMessage mensagem2 = new ByteMessage("62 21");
		
		System.out.println(mensagem1);
		System.out.println(mensagem2);
		
		List<Integer> posicoesMudadas = new ArrayList<>();

		//7 6 5 4  3 2 1 0  F E D C  B A 9 8  
		//1 0 0 0  0 1 0 0  0 0 1 0  0 0 0 0
		//0 1 1 0  0 0 1 0  0 0 1 0  0 0 0 1
		posicoesMudadas.add(1);
		posicoesMudadas.add(2);
		posicoesMudadas.add(5);
		posicoesMudadas.add(6);
		posicoesMudadas.add(7);
		posicoesMudadas.add(8);
		
		assertEquals(posicoesMudadas, mensagem1.compararCom(mensagem2));
	}
}