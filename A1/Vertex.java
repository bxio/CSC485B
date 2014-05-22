import java.util.*;

public class Vertex {
	private String name;
	private float weight;
	public Vertex(String name)
	{
		this.name=name;
	}

	public String getName(){
		return this.name;
	}

	public float getWeight(){
		return this.weight;
	}
}
