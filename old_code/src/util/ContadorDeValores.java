/**
 * 
 */
package util;

/**
 * @author UTN user
 *
 */
@SuppressWarnings("unchecked")
public class ContadorDeValores implements Comparable{
	private int valor;
	private int apariciones;
	private int valorCentral;
	private int multiplicador;

	/**
	 * 
	 */
	public ContadorDeValores(int valor,int valorCentral, boolean cercano) {
		super();
		this.valor=valor;
		this.apariciones=1;
		this.valorCentral=valorCentral;
		if(cercano)
			this.multiplicador=1;
		else
			this.multiplicador=-1;
	}

	public int compareTo(Object arg0) {
		if (!(arg0 instanceof ContadorDeValores)) return 0;
		if(this.apariciones-((ContadorDeValores)arg0).getApariciones()==0)
			return (multiplicador * Math.abs(((ContadorDeValores)arg0).getValor()-((ContadorDeValores)arg0).getValorCentral()) +
					multiplicador * -1 * Math.abs(valor-valorCentral));
		return this.apariciones-((ContadorDeValores)arg0).getApariciones();
	}
	
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof ContadorDeValores)) return false;
		return (this.valor==((ContadorDeValores)arg0).getValor());
	}

	public int getApariciones() {
		return apariciones;
	}

	public void setApariciones(int apariciones) {
		this.apariciones = apariciones;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getValorCentral() {
		return valorCentral;
	}

	public void setValorCentral(int valorCentral) {
		this.valorCentral = valorCentral;
	}
	
	public void incrementarApariciones()
	{
		this.apariciones++;
	}
	
}
