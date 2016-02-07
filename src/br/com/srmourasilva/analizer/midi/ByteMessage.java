package br.com.srmourasilva.analizer.midi;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import br.com.srmourasilva.util.BinaryUtil;

public class ByteMessage {
	private boolean[] conteudo;

	public ByteMessage(String conteudo) {
		this.conteudo = BinaryUtil.bytesForBits(conteudo);
	}

	private boolean[] conteudo() {
		return conteudo;
	}

	public Set<Integer> compararCom(ByteMessage mensagem) {
		Set<Integer> posicoesDiferentes = new HashSet<>();

		boolean[] conteudo1 = this.conteudo();
		boolean[] conteudo2 = mensagem.conteudo(); 

		for (int i=0; i < conteudo1.length; i++)
			if (conteudo1[i] != conteudo2[i])
				posicoesDiferentes.add(i);

		return posicoesDiferentes;
	}
	
	public String sinalizar(Set<Integer> diferencas) {
		SortedSet<Integer> diferencasOrdenadas = orderar(diferencas);

		StringBuilder builder = new StringBuilder(this.toString());
		builder.append("\n");

		int posAtual = 0;
		for (Integer posicao : diferencasOrdenadas) {
			while (posAtual < posicao) {
				builder.append(' ');
				posAtual++;

				if (isDivisoria(posAtual))
					builder.append('-');
			}

			builder.append('^');
			posAtual++;
		}

		return builder.toString();
	}

	private boolean isDivisoria(int posAtual) {
		return posAtual%8 == 0;
	}

	private SortedSet<Integer> orderar(Set<Integer> diferencas) {
		Comparator<Integer> comparator = Comparator.comparing((Integer i) -> i.intValue());

		SortedSet<Integer> diferencasOrdenadas = new TreeSet<Integer>(comparator);
		diferencasOrdenadas.addAll(diferencas);
		
		return diferencasOrdenadas;
	}

	@Override
	public String toString() {
		return BinaryUtil.toString(conteudo);
	}
}