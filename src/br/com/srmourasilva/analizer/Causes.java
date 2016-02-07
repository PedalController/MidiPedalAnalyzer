package br.com.srmourasilva.analizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Causes implements Iterable<br.com.srmourasilva.analizer.Causes.Cause> {
	
	private List<Cause> causes;

	public Causes() {
		this.causes = new ArrayList<>();
	}

	public void add(int index, String cause) {
		this.causes.add(new Cause(index, cause));
	}

	public List<Cause> getCauses() {
		return causes;
	}

	@Override
	public Iterator<br.com.srmourasilva.analizer.Causes.Cause> iterator() {
		return causes.iterator();
	}
	
	public static class Cause {
		private int index;
		private String cause;

		public Cause(int index, String cause) {
			this.index = index;
			this.cause = cause;
		}
		
		public int getIndex() {
			return index;
		}

		public String getCause() {
			return cause;
		}
	}
}
